package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentServices;

public class DepartmentFormController implements Initializable {

	private Department entity;
	
	private DepartmentServices services;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelError;
	
	@FXML
	private Button saveBts;
	
	@FXML
	private Button cancelBts;
	
	@FXML
	private void onSaveBtsAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (services == null) {
			throw new IllegalStateException("Services was null");
		}
		try {
			entity = getFormData();		
			services.saveOrUpdate(entity);
			notifyDataChangeListener();
			Utils.currentStage(event).close();
			
		}
		catch(DbException e) {
			Alerts.showAlerts("Erro saving Objects", null, e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private void notifyDataChangeListener() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Department getFormData() {
		Department obj = new Department();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		return obj;
	}

	@FXML
	private void onCancelBtsAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	public void setDepartmentServices(DepartmentServices services) {
		this.services = services;
	}
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes(){
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Was not Data");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}

}
