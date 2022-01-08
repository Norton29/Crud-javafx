package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;

	@FXML
	private MenuItem menuItemDepartment;

	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	private MenuItem menuItemGut;
	
	
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("1");
	}
	@FXML
	public void onMenuItemDepartmenAction() {
		System.out.println("2");
	}
	@FXML
	public void onMenuItemAboutAction() {
		System.out.println("3");
	}
	
	@FXML
	public void onMenuItemGutActio() {
		Alerts.showAlerts("Guten Morgen", null, "Ich hoffe, dass dich eine guten Tag hast.", AlertType.INFORMATION);
	}

	@Override
	public void initialize(URL ur, ResourceBundle rsb) {

	}

}
