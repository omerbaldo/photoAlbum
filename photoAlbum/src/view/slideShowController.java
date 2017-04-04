package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
/**
 * @author      Omer Baldo, Peter Santana-Kroh <address @ osb5@scarletmail.rutgers.edu> <address @ psantana94@gmail.com>
 * @version     2.0         
 * @since       1.1       
 */
public class slideShowController {

	/**
	 * next photos 
	 */	
	@FXML Button next;
	
	/**
	 * prev photos 
	 */	
	@FXML Button prev;
	
	/**
	 * image  
	 */	
	@FXML ImageView image;
	
	/**
	 * logout  
	 */	
	@FXML
	MenuItem logoutButton;
	
	/**
	 * safe quit  
	 */	
	@FXML
	MenuItem safequitButton;
	
	/**
	 * refers to index of photo in the list
	 */	
	int curr = 0;
	
	/**
	 * goes to next photo 
	 */	
	@FXML		
	public void next(ActionEvent event){
		
		int d = curr+1;
		if((d>=0)&&(d<controlla.currentAlbum.photos.size())){
			Image i = new Image(controlla.currentAlbum.photos.get(d).file.toURI().toString());
			image.setImage(i);
			curr = d;
		}
	
	}
	
	/**
	 * goes to prev photo 
	 */	
	@FXML		
	public void prev(ActionEvent event){
		int d = curr-1;
		if((d>=0)&&(d<controlla.currentAlbum.photos.size())){
			Image i = new Image(controlla.currentAlbum.photos.get(d).file.toURI().toString());
			image.setImage(i);
			curr = d;
		}
		
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
	 * safequit
	 * <p>
	 * writes data to system
	 * </p>	   
	 */	
	@FXML
	public void safequit(ActionEvent event) throws IOException{
		controlla.writeData();

			System.exit(0);//exits
	}
	
	/**
	 * initilialze 
	 */	
	@FXML
    public void initialize() throws IOException {	
			
			Image i = new Image(controlla.currentAlbum.photos.get(0).file.toURI().toString());
			image.setImage(i);
	}
	
	/**
	 * go back to albums  
	 */	
	@FXML		
	public void returnTo(ActionEvent event) throws IOException{

		loadPhoto();
		controlla.switchScenes(5);
	}
	
	/**
	 *load photos
	 */	
	public void loadPhoto() throws IOException{
		//ALBUM SCENE 3)
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/5.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		controlla.photoScene = new Scene(root, 600, 500);
	}
	
	
	
	
	

}
