package view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data;
import model.user;
/**
 * @author      Omer Baldo, Peter Santana-Kroh <address @ osb5@scarletmail.rutgers.edu> <address @ psantana94@gmail.com>
 * @version     2.0         
 * @since       1.1       
 */
public class adminController {

	
	/**
	 * add a new user
	 */
	@FXML
	Button addUserButton;
	
	/**
	 * delete a user
	 */
	@FXML
	Button deleteUserButton;
	
	/**
	 * cancel adding a user
	 */
	@FXML
	Button cancelButton;
	
	/**
	 * field for username
	 */
	@FXML
	TextField username;
	
	/**
	 * list of users
	 */
	@FXML
	ListView<user> userView;
	
	/**
	 * logout button
	 */
	@FXML
	MenuItem logoutButton;
	
	/**
	 * safe quit
	 */
	@FXML
	MenuItem safequitButton;
	
	/**
	 * new stage
	 */
	@FXML
	public static Stage stage =  new Stage();
	
	/**
	 * the prevous stage
	 */
	@FXML
	public static Stage prevStage = new Stage();
	
	/**
	 * points to data object
	 */
	private static data info; // point to data object
	
	/**
	 * add a user
	 */
	@FXML
	public void adduser(ActionEvent event){

		String un = username.getText();
				
		//ERROR 1)  nothing given
		if(un.length()==0){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("Not Enough Info! ");
			alert.setContentText("Whats the name you want?");
			alert.showAndWait();
			return;
		}
		
		//ERROR 2)  existing album already	
		user x = new user(un);
		
			if(data.users.contains(x)){	
				Alert alert = new Alert(AlertType.INFORMATION);
				//alert.initOwner(editPage);
				alert.setTitle("Error. ");
				alert.setHeaderText("Duplicate Entry ");
				alert.setContentText("Username already exists!");
				alert.showAndWait();
				return;
			}
			
			data.usersAdded.add(x);
			data.users.add(x);
		
			userView.getSelectionModel().select(data.users.indexOf(x));
	}
	
	/**
	 * delete a user
	 */
	@FXML
	public void deleteuser(ActionEvent event){
		
		String un = username.getText();
		
		//ERROR 1)  nothing given
		if(un.length()==0){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setHeaderText("Not Enough Info! ");
			alert.setContentText("Whats the name you want?");
			alert.showAndWait();
			return;
		}
		
		user x = new user(un);
		
		if(data.users.contains(x)){
			//System.out.println("deleting " + x.userName);
			data.users.remove(x);
			data.usersDeleted.add(x);
			data.usersAdded.remove(x);
			username.setText("");
			
			 // set listener for the items

			int d = userView.getSelectionModel().getSelectedIndex();
			
			if(data.users.size()-(d+1)==0)
			{
				userView.getSelectionModel().select(d-1);
			}
			else if(data.users.size()==0)
			{
				
			}
			else{
				userView.getSelectionModel().select(d+1);
			}
		}
	}
	/**
	 * update users
	 */
	public void updateUsers(){
		
		if(!(userView.getSelectionModel().getSelectedIndex() < 0 || userView.getSelectionModel().getSelectedIndex() >= info.users.size())){
			username.setText(userView.getSelectionModel().getSelectedItem().userName);
		}
		
	}
	
	/**
	 * set username box to nothing
	 */
	@FXML
	public void calceluser(ActionEvent event){
		username.setText("");

	}
	/**
	 * logout
	 * <p>
	 * writes data to system
	 * </p>	   
	 */	
	@FXML
	public void logout(ActionEvent event) throws IOException{
		controlla.writeData();

		controlla.switchScenes(1);
		
	}
		
	
	/**
	 * safe quit
	 * <p>
	 * writes data to system
	 * </p>	   
	 */	
	@FXML
	public void safequit(ActionEvent event) throws IOException{
			
			//save all info 
			controlla.writeData();
			System.exit(0);//exits
	}
	
	
	
	
	
	
	
	/**
	 * Initialize the data
	 */	
	@FXML
    public void initialize() {		
			
        /*
		stage.setTitle("Admin Screen");
		
		//STEP 1) STAGES INITIAL OWNER IS THE OTHER STAGES
		stage.initOwner(controlla.curr);
        
		//STEP 2) STAGES MODALITY MEANS YOU CANT CLICK ANYWHERE ELSE
        stage.initModality(Modality.WINDOW_MODAL);
        
        //STEP 3) SET SCENE TO THE NEW FXML
        stage.setScene(controlla.nextScene);
        stage.show();
        */
         
		adminController.info = controlla.info; //info holds list of users 
		
		userView.setItems(data.users); //set our user view to look at data of users

		//when some user is selected switch him on the screen 
		userView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
          (obs, oldVal, newVal) -> 
          updateUsers());	
	}

}
