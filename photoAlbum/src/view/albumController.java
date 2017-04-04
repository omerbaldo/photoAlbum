package view;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.album;
import model.photo;
import model.user;
/**
 * @author      Omer Baldo, Peter Santana-Kroh <address @ osb5@scarletmail.rutgers.edu> <address @ psantana94@gmail.com>
 * @version     2.0         
 * @since       1.1       
 */
public class albumController {

	/*
	 * Page 3 Logout
	 */
	@FXML
	MenuItem logoutButton;
	
	/*
	 * Page 3 SafeQuit
	 */
	@FXML
	MenuItem safequitButton;
	
	/*
	 * Page 3 Album Detail Page
	 */
	@FXML
	TextArea albumDetailsTextArea;
	
	/*
	 * Page 3 Delete Selected Album
	 */
	@FXML
	 Button deleteSelectedAlbum;
	
	/*
	 * Page 3 Open selected 
	 */
	@FXML 
	 Button openSelectedAlbum;
	
	/*
	 * Page 3 Rename Selected
	 */
	@FXML 
	 Button renameSelectedAlbum;
	
	/*
	 * Page 3 Create Album
	 */
	@FXML 
	 Button createAlbum;
	
	/*
	 * Page 3 For creating or renaming selected album
	 */
	@FXML 
	 TextField third_Field;
	
	/*
	 * Page 3 Album Detail Page
	 */
	@FXML
	 ListView<album> album_listView;

	/*
	* observable list of albums
	*/
	public static ObservableList<album> obsListAlbum = FXCollections.observableArrayList(); 
	
	/*
	* observable list of photos
	*/
	public static ObservableList<photo> obsListPhoto  = FXCollections.observableArrayList();
	
	/**
	 * Creates Album                     						   
	 * <p>
	 * throws errors if nothing is given with a pop up
	 * </p>
	 */
	@FXML
	public void createAlbum(ActionEvent event){
		
		String s = third_Field.getText();//get button value
		
		//ERROR 1)  nothing given
		if(s.length()==0){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(controlla.curr);
			alert.setTitle("Error");
			alert.setHeaderText("Not Enough Info! ");
			alert.setContentText("Whats the album name?");
			alert.showAndWait();
			return;
		}
		
		//ERROR 2)  existing album already
		
		album a = new album(s);
		
			if(obsListAlbum.contains(a)){	
				Alert alert = new Alert(AlertType.INFORMATION);
				//alert.initOwner(editPage);
				alert.setTitle("Error. ");
				alert.setHeaderText("Duplicate Entry ");
				alert.setContentText("You cannot have the same album name");
				alert.showAndWait();
				return;
			}
			
			
			obsListAlbum.add(a);
			controlla.currentUser.albums.add(a);

			album_listView.getSelectionModel().select(obsListAlbum.indexOf(a));

			updateAlbum();	
	}
	
		
	
	
	
	/**
	 * Rename Album                     						   
	 *<p>
	 *throws errors if nothing is given
	 *</p>
	 */		
	@FXML
	public void renameAlbum(ActionEvent event){
		//System.out.println("rename");

		String s = third_Field.getText();//get button value
		
		//ERROR 1)  nothing given
		if(s.length()==0 ||obsListAlbum.size() == 0){
			Alert alert = new Alert(AlertType.INFORMATION);
			//alert.initOwner(mainStage);
			alert.setTitle("Error");
			alert.setHeaderText("Not Enough Info! ");
			if(obsListAlbum.size() == 0){
				alert.setContentText("Nothing selected");
			}else{
				alert.setContentText("Whats the album name?");
			}
			
			alert.showAndWait();
			return;
		}
		
		//ERROR 2)  existing album already
		for(album a: obsListAlbum){
			if(a.name.equals(s)){	
				Alert alert = new Alert(AlertType.INFORMATION);
				//alert.initOwner(editPage);
				alert.setTitle("Error. ");
				alert.setHeaderText("Duplicate Entry ");
				alert.setContentText("You cannot have the same album name");
				alert.showAndWait();
				return ;
			}
		}
	
		//change the name here
		album a = album_listView.getSelectionModel().getSelectedItem();	
		a.name = s; 
		
		album_listView.getSelectionModel().select(obsListAlbum.indexOf(a));
		updateAlbum();
		return;
	}
	 
	 
	/**
	 * Delete Album                     						   
	 */
	 @FXML
	public void deleteAlbum(ActionEvent event){
		
		// System.out.println("delete");
		 
		int d = album_listView.getSelectionModel().getSelectedIndex();
		
		if(d < 0 || d >= obsListAlbum.size()){
			//System.out.println("true, index is " + d + "size is " + obsList.size());
			return;
		}
	
		obsListAlbum.remove(d);
		controlla.currentUser.albums.remove(album_listView.getSelectionModel().getSelectedItem());
		
		
		if(obsListAlbum.size() == 0){ //Nothing in list
			String info = "";
			String name = "name: ";
			String artist = "artist: ";
			String year = "year: ";
			String album = "album: ";
			info = name + "\n" + artist + "\n" + year +"\n" + album;
			albumDetailsTextArea.setText(info);
		}else if(obsListAlbum.size()-(d+1)==0){ //nothing next
			
			album_listView.getSelectionModel().select(d-1);
			updateAlbum();				
								
		}else{
			album_listView.getSelectionModel().select(d+1);
			updateAlbum();				

		}
		
		return;
	}
	
	 
	 
	 
	 
	 
	 /**
	  * Opens the selected Album                     						   
      */
		@FXML
		public void openAlbum(ActionEvent event) throws IOException{
			
			//System.out.println("open album");
			if(album_listView.getSelectionModel().getSelectedItem()==null){
				
				Alert alert = new Alert(AlertType.INFORMATION);
				//alert.initOwner(editPage);
				alert.setTitle("Error. ");
				alert.setHeaderText("Nothing ");
				alert.setContentText("Nothing selected! ");
				alert.showAndWait();
				return ;
			}
			
			controlla.currentAlbum = album_listView.getSelectionModel().getSelectedItem();
			
			loadPhoto();
			controlla.switchScenes(5);
			
		    
		}
	
		 /**
		  * Loads the photo fxml to get to the next scene                     						   
	      */
		public void loadPhoto() throws IOException{
			//ALBUM SCENE 3)
			FXMLLoader loader = new FXMLLoader();   
			loader.setLocation(
					getClass().getResource("/view/5.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			controlla.photoScene = new Scene(root, 600, 500);
		}
		
		
		
		
		
		

	 /**
	 * Sort List of Albums                     						   
	 * @return true or false if the instruction is valid.
	 */
	public static void sortListAlbum(){
		
		Collections.sort(obsListAlbum,new Comparator<album>(){
            public int compare(album s1,album s2){
                  return s1.name.compareTo(s2.name);
            }});
	}


	
	/**
	 * Show Album Information
	 * <p>
	 *  name, number of photos in it, date of older photo, range of dates 
	 * </p>                     						   
	 */
	
	public void showAlbum(){
		if(!(album_listView.getSelectionModel().getSelectedIndex() < 0 || album_listView.getSelectionModel().getSelectedIndex() >= obsListAlbum.size())){
				String info = "";
				
				String name = "name: " +album_listView.getSelectionModel().getSelectedItem().name;
										
				String num = "numberofphotos: "  + Integer.toString(album_listView.getSelectionModel().getSelectedItem().photos.size());

				String oldest;
				
				if(album_listView.getSelectionModel().getSelectedItem().oldestPhoto!= null){
					oldest = "oldest photo: "  + album_listView.getSelectionModel().getSelectedItem().oldestPhoto.date.toString();
				}else{
					oldest = "oldest photo: ";
				}
				
				String range;	
				if(album_listView.getSelectionModel().getSelectedItem().newestPhoto != null){
					range = "range : " + oldest + " - " + album_listView.getSelectionModel().getSelectedItem().newestPhoto.date.toString();
				}else{
					range = "range : ";
				}
				
				
				info = name + "\n" + num + "\n" + oldest +"\n" + range;
				albumDetailsTextArea.setText(info);
			}else{
				String info = "";
				String name = "name: ";
				String num = "numberofphotos: " ;
				String oldest = "oldest photo: ";
				String range = "range : ";
				info = name + "\n" + num + "\n" + oldest +"\n" + range;
				albumDetailsTextArea.setText(info);
			}
	}
	
	/**
	 * Updates the album list and information box		   
	 */		
	public  void updateAlbum(){
		
		album a = album_listView.getSelectionModel().getSelectedItem();
		
		if(a!=null)
			a.update();
			
		
		//sortListAlbum();
					
		album_listView.getSelectionModel().select(obsListAlbum.indexOf(a));
		
		showAlbum();
	}
	
	
	/**
	 * initialize the album controllers
	 * <p>
	 * set album list view to look at observable list
	 * </p>	   
	 */	
	@FXML
    public void initialize() throws IOException {		

		obsListAlbum.clear();
		for(album a: controlla.currentUser.albums){
			obsListAlbum.add(a);
		}
		
		album_listView.setItems(obsListAlbum);
		
		album_listView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
           (obs, oldVal, newVal) -> 
           updateAlbum());
		
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
	 * Loads up search page	   
	 */	
	@FXML
	public void search(ActionEvent event) throws IOException{
		
		int num = 0;
		for(album a : controlla.currentUser.albums)
			for(photo p :a.photos)
				num++;
		
		if(num==0){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(controlla.curr);
			alert.setTitle("Error");
			alert.setHeaderText("There are no photos in any albums! ");
			alert.setContentText("Please add some photos");
			alert.showAndWait();
			return;
		}
		
		
		loadSearch();
		controlla.switchScenes(4);
	}
	
	/**
	 * Loads search fxml	   
	 */	
	public void loadSearch() throws IOException{

		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/4.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		controlla.searchScene = new Scene(root, 600, 500);
	}
	
	
	
	
	
	
	@FXML
	public void safequit(ActionEvent event) throws IOException{
			
			//save all info 
		controlla.writeData();

			System.exit(0);//exits
	}
	
	
	
	
	
	
}	