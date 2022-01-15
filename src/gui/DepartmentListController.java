package gui;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private void onNewButtonAction() {
		System.out.println("Hallo!");
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

}
