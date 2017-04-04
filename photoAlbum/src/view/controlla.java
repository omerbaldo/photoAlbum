package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.*;
import view.controlla;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javafx.scene.control.*;

/**
 * @author      Omer Baldo, Peter Santana-Kroh <address @ osb5@scarletmail.rutgers.edu> <address @ psantana94@gmail.com>
 * @version     2.0         
 * @since       1.1       
 */
public class controlla {
	
	/**
	 * Data object that holds all users to later be serialized 
	 */
	public static data info = new data(); //holds all users. we are u
	
	/**
	 * Who ever the admin is
	 */
	public static  user admin;
	
	/**
	 * Who ever is the user currently logged in 
	 */
	public static  user currentUser; //point to current user being processed

	/**
	 * Whatever album is currently opened
	 */
	public static  album currentAlbum;
	
	
	/**
	 * Current stage  . doesn't change
	 */
	public static Stage curr;
	
	/**
	 * Login scene
	 */
	public static Scene loginScene;
	
	/**
	 * Admin scene
	 */
	public static Scene adminScene;
	
	/**
	 * Album scene
	 */
	public static Scene albumScene;
	
	/**
	 * Photo scene
	 */
	public static Scene photoScene;
	
	/**
	 * slide show scene
	 */
	public static Scene slideShowScene;
	
	/**
	 * search scene
	 */
	public static Scene searchScene;

	
	/**
	 * Container
	 */
	@FXML
	AnchorPane pane;
	
	/**
	 * Cancel button 
	 */
	@FXML
	Button cancelButton;
	
	/**
	 * Login button
	 */
	@FXML
	Button loginButton;
	
	/**
	 * User name 
	 */
	@FXML
	TextField username;
	
	/**
	 * Login password
	 */
	@FXML
	PasswordField loginpassword;
	
	/**
	 * Logout button
	 */
	@FXML
	MenuItem logoutButton;
	
	/**
	 * Safe Quit 
	 */
	@FXML
	MenuItem safequitButton;
		
	/**
	 * Logs user out and loads login page. Saves info by calling write data
	 * @throws IOException 
	 */	
	@FXML
	public void logout(ActionEvent event) throws IOException{
		writeData();
		switchScenes(1);//login screen
		
	}
	
	/**
	 * Closes system but saves data.
	 * @throws IOException 
	 */
	@FXML
	public void safequit(ActionEvent event) throws IOException{	
		writeData();
		System.exit(0);//exits
	}

	/**
	 * Checks if username is admin or something else 
	 * @throws IOException 
	 */
	@FXML
	public void login(ActionEvent event) throws IOException{	
		String x = username.getText();
		if (username.getText().equals("Admin") || username.getText().equals("admin")) {
			loadAdmin();
			switchScenes(2);
		}
		else if(data.users.contains(new user(x))){	
			for(user u : data.users){
				if(x.equals(u.userName)){
					currentUser = u;
				}
			}
			loadAlbum();
			switchScenes(3);
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Invalid Request");
    		alert.setContentText("non existent user please get added by the Admin"); //also check if the song and artist are already in the list
    		alert.showAndWait();
		}
	}
	
	/**
	 * Cancels the login.
	 */
	@FXML
	public void cancellogin(ActionEvent event){
		username.setText("");
		loginpassword.setText("");
	}
	
			
	
	/**
	 * Switches the scene
	 * @param a number which refers to state in state diagram
	 */
	public static void switchScenes(int fxmlnum){
				 
		switch(fxmlnum){
					
		   case 1://login screen 
			   curr.setScene(loginScene);
	     		break;
	       case 2: 
			   curr.setScene(adminScene);
			   break;
	       case 3: //user view
			   curr.setScene(albumScene);  	   
			   break;
		   case 4:  //search
			    curr.setScene(searchScene);
			    break;
		   case 5:  //album content
			    curr.setScene(photoScene);
			    break;
		   case 6:  //slide show
			    curr.setScene(slideShowScene);
			    break;
			      
			                
			       //Case 7 and 8 are pop ups			
				
				}				
	}
				
			
			
	/**
	 * Loads 5.fxml
	 * @throws IOException 
	 */
	public void loadPhoto() throws IOException{
				//ALBUM SCENE 3)
				FXMLLoader loader = new FXMLLoader();   
				loader.setLocation(
						getClass().getResource("/view/5.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				
				photoScene = new Scene(root, 600, 500);
	}
			
	/**
	 * Loads 3.fxml
	 * @throws IOException 
	 */	
	public void loadAlbum() throws IOException{
				//ALBUM SCENE 3)
				FXMLLoader loader = new FXMLLoader();   
				loader.setLocation(
						getClass().getResource("/view/3.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				
				albumScene = new Scene(root, 600, 500);
	}
	/**
	 * Loads 2.fxml 
	 * @throws IOException 
	 */		
			public void loadAdmin() throws IOException{
				//ADMIN SCENE 2)
				FXMLLoader loader = new FXMLLoader();   
				loader.setLocation(
						getClass().getResource("/view/2.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				
				//loading from controller
				
				adminScene = new Scene(root, 600, 500);
				
			}
			
			
			/**
			 * Starts the controller
			 * @throws IOException 
			 * @throws ClassNotFoundException
			 *
			 *		
			 * */	
			//right now create an user and album
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
		curr = primaryStage;
								
				//LOGIN SCENE 1)
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(
						getClass().getResource("/view/1.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
							
		loginScene = new Scene(root, 600, 500);
		data.users.clear();
		readData();			
	}
	
	/**
	 * Deletes a directory recursively
	 */	
	static public void deleteDirectory(File ff) {
		if (ff.exists()==true) {
	        File[] files = ff.listFiles();
	        
	        int j =0;
	        while(j<files.length){
	        
	        	if (files[j].isDirectory()==true) {
	                deleteDirectory(files[j]);
	            } else {
	                files[j].delete();
	            }
	        	j++;
	        }
	        
	    }
	    
		 ff.delete();
	}
	
	
	/**
	 * Writes to data file for users using serialization
	 * @throws IOException 
	 *  */				
	public static void writeData() throws IOException {
	
		//CLEAR
		File folder = new File("data" + File.separator);
		folder.mkdir();
		String[] directories = folder.list();

		for(String name: directories){
			File ff = new File(folder + File.separator + name);
			if(ff.isDirectory()){
				deleteDirectory(ff);
			}	
		}
		
		
		
		
		//add all user data
		for(user u: data.users){ 
			//System.out.println("user is " +u.userName);
					
			File f = new File("data" + File.separator+ u.userName);			//make a file with user name

			f.mkdir(); //make the file a directory
			
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(f + File.separator+ "user-state")); 
			
			//create stream with file name at the end
			
			 oos.writeObject(u); 
			
			//write objects 
			 oos.close();
		}
		 
	}
			

	/**
	 * Writes to data back to the info data structure using serialization
	 * @throws IOException 
	 * @throws ClassNotFoundException
	 *  
	 *  */			
	public static void readData()throws IOException, ClassNotFoundException  {
		
		File f = new File("data" + File.separator);
		f.mkdir();
		String[] directories = f.list();
		ArrayList<user> list = new ArrayList<user>();

		//Go through all directories and add users to the list
		for(String name: directories){
			ObjectInputStream ois = null;
			//System.out.println("at" + f +File.separator + name);

			if(new File(f + File.separator + name).isDirectory()){//file is a directory
				
				//System.out.println(f + File.separator + name + File.separator + "user-state");
				ois = new ObjectInputStream(
						new FileInputStream(f + File.separator + name + File.separator + "user-state"));
						list.add((user)ois.readObject()); 
			}
			if(ois!=null)
				ois.close();
			
		}
		
		//clear old list
		data.users.clear();
		
		//re populate
		for(user u : list){
			data.users.add(u);
		}

	}
		
	
	
	
	
	
	
	
}
