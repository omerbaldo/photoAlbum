package view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.album;
import model.photo;
/**
 * @author      Omer Baldo, Peter Santana-Kroh <address @ osb5@scarletmail.rutgers.edu> <address @ psantana94@gmail.com>
 * @version     2.0         
 * @since       1.1       
 */
public class movePhotoController {
	
	/**
	 * list view
	 */	
	@FXML ListView<album> album_listView;

	/**
	 * copy to another album
	 */	
	@FXML Button copy;
	
	/**
	 * move to new album
	 */	
	@FXML Button move;
	
	/**
	 * image for preview
	 */	
	@FXML ImageView image;
	
	/**
	 * list of albums
	 */	
	public static ObservableList<album> obsListAlbum = FXCollections.observableArrayList(); 
	
	/**
	 * list of photos
	 */	
	public static ObservableList<photo> obsListPhoto = FXCollections.observableArrayList(); 

	/**
	 * copying photos 
	 */	
	@FXML		
	public void copy(ActionEvent event){
		//System.out.println("copy photo");

		int d = album_listView.getSelectionModel().getSelectedIndex();
		if(d<0 || d>=obsListAlbum.size()){
			return;
		}
		
		album a = album_listView.getSelectionModel().getSelectedItem();
		
		obsListPhoto.add(photoController.currentPhoto);
		a.photos.add(photoController.currentPhoto);
		photoController.s.close();

	}
	
	
	
	/**
	 * moving photos
	 */	
	@FXML		
	public void move(ActionEvent event){
		
		//System.out.println("moving photo");

		int d = album_listView.getSelectionModel().getSelectedIndex();
		if(d<0 || d>=obsListAlbum.size()){
			return;
		}
		album a = album_listView.getSelectionModel().getSelectedItem();
		
		photo p = photoController.currentPhoto;
		
		
		
		a.photos.add(p);
		controlla.currentAlbum.photos.remove(p);
		photoController.s.close();
		
	}
	

	/**
	 * initializes the lists 
	 */	
	@FXML
    public void initialize() throws IOException {	
		
		obsListAlbum.clear();
		for(album a: controlla.currentUser.albums){
			obsListAlbum.add(a);
		}
		obsListPhoto.clear();
		
		
		
		album_listView.setItems(obsListAlbum);
		
		Image i = new Image(photoController.currentPhoto.file.toURI().toString());
		image.setImage(i);
	}
	
	
	

}
