package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.DepartmentServices;
import model.services.SellerServices;

public class SellerListController implements Initializable, DataChangeListener {

	private SellerServices services;

	@FXML
	private TableView<Seller> tableViewDep;

	@FXML
	private TableColumn<Seller, Integer> idTableCollumn;

	@FXML
	private TableColumn<Seller, String> nameTableCollumn;
	
	@FXML
	private TableColumn<Seller, String> emailTableCollumn;
	@FXML
	private TableColumn<Seller, Date> birthDateTableCollumn;
	@FXML
	private TableColumn<Seller, Double> baseSalaryTableCollumn;
	
	@FXML
	private TableColumn<Seller, String> departmentTableCollum;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;

	@FXML
	private Button newButton;

	private ObservableList<Seller> obsList;

	@FXML
	private void onNewButtonAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Seller obj = new Seller();
		createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
	}

	public void setSellerService(SellerServices services) {
		this.services = services;

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		idTableCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameTableCollumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		emailTableCollumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		birthDateTableCollumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(birthDateTableCollumn, "dd/MM/yyyy");
		baseSalaryTableCollumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
		Utils.formatTableColumnDouble(baseSalaryTableCollumn, 2);
		departmentTableCollum.setCellValueFactory(new PropertyValueFactory<>("department"));
		
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDep.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTableView() {
		if (services == null) {
			throw new IllegalStateException("Services was null");
		}
		List<Seller> list = services.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDep.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	public void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			SellerFormController controller = loader.getController();
			controller.setSeller(obj);
			controller.setServices(new SellerServices(), new DepartmentServices() );
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Seller Data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlerts("Error", "Error to input new Seller", e.getMessage(), AlertType.ERROR);
		}
		

	}

	@Override
	public void onDataChanged() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Seller obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to Delete?");
	
		if(result.get() == ButtonType.OK) {
			if(services == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				services.remove(obj);
				updateTableView();				
			}
			catch (DbException e) {
				Alerts.showAlerts("Error remove Object", null, e.getMessage(), AlertType.ERROR);
			}
		}
		
	}

	
}
