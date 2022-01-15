package gui;


import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Departments;

public class DepartmentListController implements Initializable {

	
	@FXML
	private TableView <Departments> tableViewDep;
	
	@FXML
	private TableColumn<Departments, Integer> idTableCollumn;
	
	@FXML
	private TableColumn<Departments, String> nameTableCollumn;
	
	@FXML
	private Button newButton;
	
	@FXML
	private void onNewButtonAction() {
		System.out.println("Hallo!");
	}
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		idTableCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameTableCollumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDep.prefHeightProperty().bind(stage.heightProperty());
			
	}

}
