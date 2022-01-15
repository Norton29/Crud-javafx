package gui;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentServices;

public class DepartmentListController implements Initializable {

	private DepartmentServices services;
	
	
	@FXML
	private TableView <Department> tableViewDep;
	
	@FXML
	private TableColumn<Department, Integer> idTableCollumn;
	
	@FXML
	private TableColumn<Department, String> nameTableCollumn;
	
	@FXML
	private Button newButton;
	
	private ObservableList<Department> obsList;
	
	@FXML
	private void onNewButtonAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
	}
	
	public void setDepartmentService(DepartmentServices services){
		this.services = services;
		
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		idTableCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameTableCollumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDep.prefHeightProperty().bind(stage.heightProperty());
			
	}
	
	public void updateTableView() {
		if(services == null) {
			throw new IllegalStateException("Services was null");
		}
		List<Department> list = services.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDep.setItems(obsList);
	}
	
	public void createDialogForm(Department obj, String absoluteName, Stage parentStage) 
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			DepartmentFormController controller = loader.getController();
			controller.setDepartment(obj);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department Data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
			
		}
		catch(IOException e){
			Alerts.showAlerts("Error", "Error to input new Department", e.getMessage(), AlertType.ERROR);
					
		}
		
	}

}
