package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Exception.ValidationException;
import model.entities.Seller;
import model.services.SellerServices;

public class SellerFormController implements Initializable {

	private Seller entity;
	
	private SellerServices services;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private DatePicker dpBirthDate;
	
	@FXML
	private TextField txtBaseSalary;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorEmail;
	
	@FXML
	private Label labelErrorBirthDate;
	
	@FXML
	private Label labelErrorBaseSalary;
	
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
		catch(ValidationException e) {
			setErrorMessage(e.getErrors());
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

	private Seller getFormData() {
		Seller obj = new Seller();
		
		ValidationException exception = new ValidationException("Validation Error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText()== null || txtName.getText().trim().equals("")) {
			exception.addErros("Name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
	}

	@FXML
	private void onCancelBtsAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void setSeller(Seller entity) {
		this.entity = entity;
	}
	
	public void setSellerServices(SellerServices services) {
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
		Constraints.setTextFieldDouble(txtBaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 70);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
	}
	
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Was not Data");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f", entity.getSalary()));
		if(entity.getBirthDate() != null) {
		dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()) );
		}
	}
	
	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("Name")) {
			labelErrorName.setText(errors.get("Name"));
		}	
	}

}
