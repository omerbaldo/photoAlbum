package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.controlla;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * @author      Omer Baldo, Peter Santana-Kroh <address @ osb5@scarletmail.rutgers.edu> <address @ psantana94@gmail.com>
 * @version     2.0         
 * @since       1.1       
 */
public class Main extends Application {
	
	

	/**
	 * Loads login page
	 * @param primary stage that will be what we use
	 */
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
		/* Loads fxml */
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/1.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		//loading from controller
		controlla mainController = loader.getController();
		mainController.start(primaryStage);
		
		/* loads new scene when application is started */
		Scene scene = new Scene(root, 600, 500);
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
