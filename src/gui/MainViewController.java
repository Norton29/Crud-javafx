package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.SellerServices;

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
	public void onMenuItemSellerAction() 
	{
		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {
			controller.setSellerService(new SellerServices());
			controller.updateTableView();
		});
	}
	@FXML
	public void onMenuItemDepartmenAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentServices());
			controller.updateTableView();
		});
	}
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	@FXML
	public void onMenuItemGutActio() {
		Alerts.showAlerts("Guten Morgen", null, "Ich hoffe, dass dich eine guten Tag hast.", AlertType.INFORMATION);
	}

	@Override
	public void initialize(URL ur, ResourceBundle rsb) {

	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> actionInitialing) 
	{
		try 
		{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVbox = loader.load();
		
		Scene aboutScene = Main.getMainScene();
		VBox mainVbox = (VBox) ((ScrollPane) aboutScene.getRoot()).getContent();
		
		Node mainMenu = mainVbox.getChildren().get(0);
		mainVbox.getChildren().clear();
		mainVbox.getChildren().add(mainMenu);
		mainVbox.getChildren().addAll(newVbox.getChildren());
		
		T controller = loader.getController();
		actionInitialing.accept(controller);
		
		}
		catch(IOException e ){
			Alerts.showAlerts("ErroR", null , "Error ao acessar", AlertType.ERROR);
			
			
		}
	}
	
}
