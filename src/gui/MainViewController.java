package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentServices;

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
		loadView("/gui/SellerList.fxml");
	}
	@FXML
	public void onMenuItemDepartmenAction() {
		loadView2("/gui/DepartmentList.fxml");
	}
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}
	
	@FXML
	public void onMenuItemGutActio() {
		Alerts.showAlerts("Guten Morgen", null, "Ich hoffe, dass dich eine guten Tag hast.", AlertType.INFORMATION);
	}

	@Override
	public void initialize(URL ur, ResourceBundle rsb) {

	}
	
	private synchronized void loadView(String absoluteName) 
	{
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVbox = loader.load();
		
		Scene aboutScene = Main.getMainScene();
		VBox mainVbox = (VBox) ((ScrollPane) aboutScene.getRoot()).getContent();
		
		Node mainMenu = mainVbox.getChildren().get(0);
		mainVbox.getChildren().clear();
		mainVbox.getChildren().add(mainMenu);
		mainVbox.getChildren().addAll(newVbox.getChildren());
		
		}
		catch(IOException e ){
			Alerts.showAlerts("ErroR", null , "Error ao acessar", AlertType.ERROR);
			
			
		}
	}
	
	private synchronized void loadView2(String absoluteName) 
	{
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVbox = loader.load();
		
		Scene aboutScene = Main.getMainScene();
		VBox mainVbox = (VBox) ((ScrollPane) aboutScene.getRoot()).getContent();
		
		Node mainMenu = mainVbox.getChildren().get(0);
		mainVbox.getChildren().clear();
		mainVbox.getChildren().add(mainMenu);
		mainVbox.getChildren().addAll(newVbox.getChildren());
		
		
		DepartmentListController controller = loader.getController();
		controller.setDepartmentService(new DepartmentServices());
		controller.updateTableView();
		
		}
		catch(IOException e ){
			Alerts.showAlerts("ErroR", null , "Error ao acessar", AlertType.ERROR);
			
			
		}
	}

}
