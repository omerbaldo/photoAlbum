package view;

import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.photo;
import model.user;
/**
 * @author      Omer Baldo, Peter Santana-Kroh <address @ osb5@scarletmail.rutgers.edu> <address @ psantana94@gmail.com>
 * @version     2.0         
 * @since       1.1       
 */
public class photoController {
		public static Stage s = new Stage();

	
		/**
		 * the current photo  
		 */	
		public static photo currentPhoto;
		
		/*
		 * Page 4 Delete Selected
		 */
		@FXML Button deletePhoto;
		
		/*
		 * Page 4 Add Photo 
		 */
		@FXML Button addPhoto;
		
		
		/*
		 * Page 4 Move photo to new album or copy it 
		 */
		@FXML Button movePhoto;
		
		/*
		 * Page 4 Edit Photo Details
		 */
		@FXML Button editPhoto;
		

		/*
		 * Page 4 Photo Details
		 */
		@FXML TextArea photoDetails;
		
		
		/*
		 * Page 4 Album Detail Page
		 */
		@FXML ListView<photo> image_listView;

		/**
		 * When the user clicks display image
		 */	
		@FXML ImageView displayImage;
		
		/**
		 * When the user clicks thumbnail image
		 */	
		@FXML ImageView thumbnailImage;
		
		/**
		 * button!
		 */	
		@FXML Button displayPhoto;
		
		/**
		 * list of photos in album
		 */	
		public static ObservableList<photo> obsListPhoto  = FXCollections.observableArrayList();

		/**
		 * display photo in image view
		 */	
		@FXML
		public void displayPhoto(ActionEvent event){
			if(!(image_listView.getSelectionModel().getSelectedIndex() < 0 || image_listView.getSelectionModel().getSelectedIndex() >= controlla.currentAlbum.photos.size())){
				Image image = new Image(image_listView.getSelectionModel().getSelectedItem().file.toURI().toString());
				displayImage.setImage(image);
			}
		}
		
		
		/**
		 * create a new photo using file chooser
		 */			
		@FXML		
		public void createPhoto(ActionEvent event){
		
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			
			File file = fileChooser.showOpenDialog(controlla.curr);
			
			if(file == null || file.isFile()==false){
				Alert alert = new Alert(AlertType.INFORMATION);
				//alert.initOwner(editPage);
				alert.setTitle("Error. ");
				alert.setHeaderText("Nothing ");
				alert.setContentText("Nothing selected! ");
				alert.showAndWait();
				return;
			}
		
			//test to make sure it is an image
			photo p = new photo(file,file.getName());
			controlla.currentAlbum.photos.add(p);
			obsListPhoto.add(p);			
		}
		
		
		/**
		 * move the photo to a new album
		 */	
		@FXML		
		public void movePhoto(ActionEvent event) throws IOException{
		
			int d = image_listView.getSelectionModel().getSelectedIndex();

			if(d < 0 || d >= controlla.currentAlbum.photos.size()){
				return;
			}
			
			currentPhoto = image_listView.getSelectionModel().getSelectedItem();
			
			
			s = new Stage();
			FXMLLoader loader = new FXMLLoader();   
			loader.setLocation(
					getClass().getResource("/view/8.fxml"));
			AnchorPane grid = (AnchorPane)loader.load();

			
			Scene scene = new Scene(grid, 700, 700);
			s.setScene(scene);
			s.initModality(Modality.APPLICATION_MODAL);
			s.showAndWait();
			
			
		}
		
		
		/**
		 * delete the photo
		 */	
		@FXML		
		public void deletePhoto(ActionEvent event){
		//	System.out.println("delete photo  ");

			//	System.out.println("delete");
			 
				int d = image_listView.getSelectionModel().getSelectedIndex();
				
				if(d < 0 || d >= controlla.currentAlbum.photos.size()){
					//System.out.println("true, index is " + d + "size is " + obsList.size());
					return;
				}
			
				controlla.currentAlbum.photos.remove(d);
				obsListPhoto.remove(d);
				if(controlla.currentAlbum.photos.size() == 0){ //Nothing in list
					String info = "";
					photoDetails.setText(info);
				
				}else if(controlla.currentAlbum.photos.size()-(d+1)==0){ //nothing next
					
					image_listView.getSelectionModel().select(d-1);
					showImage();											
				}else{
					image_listView.getSelectionModel().select(d+1);
					showImage();	
				}
				
				return;
			
		}
		
		
		/**
		 * Show the image selected
		 */	
		public void showImage(){
		//	System.out.println("showing image");

			if(!(image_listView.getSelectionModel().getSelectedIndex() < 0 || image_listView.getSelectionModel().getSelectedIndex() >= controlla.currentAlbum.photos.size())){
				
				photo p = image_listView.getSelectionModel().getSelectedItem();
				
				String info = "";
				
				String name = "name: " + p.name;
										
				String date = "date: "  + p.date.toString();

				String caption = "caption: ";
				
				if(p.caption!=null){
					caption = "caption: "  + p.caption;			
				}
				
				String hashTags = "hashtags ";
				for(String s : p.hashTag){
					if(s!=null){
						hashTags += "" + s;
					}
				}
				/*
				String atTags = "";
				for (user u : p.atTag){
					if(u)
					atTags += "" + u.toString();
				}
				
				*/
				String locationTag = "location ";
				if(p.locationTag!=null){
					locationTag += p.locationTag;
				}
				
				info = name + "\n" + date + "\n" + caption +"\n" + hashTags + "\n"+ locationTag;
				photoDetails.setText(info);
			}else{
				String info = "";
				photoDetails.setText(info);
			}		
		}
		
		/**
		 * update the image
		 */	
		public void updateImage(){
			
			//System.out.println("updating image");
			showImage();
			if(!(image_listView.getSelectionModel().getSelectedIndex() < 0 || image_listView.getSelectionModel().getSelectedIndex() >= controlla.currentAlbum.photos.size())){
				Image image = new Image(image_listView.getSelectionModel().getSelectedItem().file.toURI().toString());
				thumbnailImage.setImage(image);
			}else{
				thumbnailImage.setImage(null);
			}
	
			
			
			
		}
		
		
		
		
		@FXML
		public void applyChanges(ActionEvent event){
		
		}
		
		
		/**
		 * update the photo details
		 */	
		@FXML		
		public void updateCaption(ActionEvent event){
		//	System.out.println("caption editing");
			

			if((image_listView.getSelectionModel().getSelectedIndex() < 0 || image_listView.getSelectionModel().getSelectedIndex() >= controlla.currentAlbum.photos.size())){
				Alert alert = new Alert(AlertType.INFORMATION);
				//alert.initOwner(editPage);
				alert.setTitle("Error. ");
				alert.setHeaderText("Nothing ");
				alert.setContentText("Nothing selected! ");
				alert.showAndWait();
				return;
			}
		      
			
			//POP UP
		
			Stage addPage = new Stage();
			
			GridPane grid = new GridPane();
			grid.setPadding(new Insets(10, 10, 10, 10));
			grid.setVgap(8);
			grid.setHgap(10);
			
			//Caption
			Label captionLabel = new Label("Caption ");
			GridPane.setConstraints(captionLabel, 0, 0);
			
			
			TextField captionInput = new TextField();
			GridPane.setConstraints(captionInput, 1, 0);
			
			//Hashtag
			Label hashLabel = new Label("Hashtag: ");
			GridPane.setConstraints(hashLabel, 0, 1);
			
			
			TextField hashInput = new TextField();
			GridPane.setConstraints(hashInput, 1, 1);
			
			//GeoTag
			Label geoLabel = new Label("GeoTag ");
			GridPane.setConstraints(geoLabel, 0, 2);
			
			
			TextField geoInput = new TextField();
			GridPane.setConstraints(geoInput, 1, 2);
			
			
			//AddSong button
			Button addDetail = new Button("addDetails");
			GridPane.setConstraints(addDetail, 1, 4);
			
			addDetail.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e){
					add2(addPage,captionInput.getText(),hashInput.getText(),geoInput.getText());
				}
			});
			
			grid.getChildren().addAll(addDetail,captionLabel, captionInput, hashLabel, hashInput, geoLabel, geoInput);
			
			Scene scene = new Scene(grid, 300, 200);
			addPage.setScene(scene);
			addPage.initModality(Modality.APPLICATION_MODAL);
			addPage.showAndWait();
			
			
			
			
		}
		/**
		 * helper method for adding details
		 */	
		public void add2(Stage mainStage, String c, String h, String g){
			
			c = c.trim();
			h = h.trim();
			g = g.trim();
			if(c.length()==0 && h.length()==0 && g.length()==0){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(mainStage);
				alert.setTitle("Error. No info given");
				alert.setHeaderText("Not Enough Info! ");
				alert.setContentText("Add more info");
				alert.showAndWait();
				return;
			}
			
			//HASHTAG
			if(h.length()!=0){
				
				String[] splited = h.split("\\s+");
				for(String s: splited){
					if(s.charAt(0)!='#'){
						
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.initOwner(mainStage);
						alert.setTitle("Error.");
						alert.setHeaderText("Hashtag");
						alert.setContentText("Hashtags must start with #");
						alert.showAndWait();
						return;
						
						
					}
				}
				
				for(String s: splited){
					image_listView.getSelectionModel().getSelectedItem().hashTag.add(s);
				}
			}
			//GEOTAG
			if(g.length()!=0){
				image_listView.getSelectionModel().getSelectedItem().locationTag = g;
			}
			//CAPTION
			if(c.length()!=0){
				image_listView.getSelectionModel().getSelectedItem().caption = c;

			}
			updateImage();
			
			mainStage.close();
			
			//
			
		}
		
		/**
		 * start a slideshow
		 */	
		@FXML
		public void slideShow(ActionEvent event) throws IOException{
			
			
			if(controlla.currentAlbum.photos.size()==0){
			
				Alert alert = new Alert(AlertType.INFORMATION);
				//alert.initOwner(editPage);
				alert.setTitle("Error. ");
				alert.setHeaderText("Nothing ");
				alert.setContentText("Nothing in album to start slideshow with ");
				alert.showAndWait();
				return;
			}
			
			
			
			
			
			
		//	System.out.println("slide show start");
			loadSlideShow();
			controlla.switchScenes(6);
			
		}
		
		public void loadSlideShow() throws IOException{
				//ALBUM SCENE 3)
			
				//System.out.println("hi");
				FXMLLoader loader = new FXMLLoader();   
				loader.setLocation(
						getClass().getResource("/view/6.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				
				controlla.slideShowScene = new Scene(root, 600, 500);
			}
			
		
		
		
		
		
		
		
		
		
		/**
		 * initialize
		 */	
		@FXML
	    public void initialize() throws IOException {	
			obsListPhoto.clear();
			for(photo p : controlla.currentAlbum.photos){
				obsListPhoto.add(p);
			}
			
			
			image_listView.setItems(obsListPhoto);
			
			image_listView
	        .getSelectionModel()
	        .selectedItemProperty()
	        .addListener(
	           (obs, oldVal, newVal) -> 
	           updateImage());	
			
			
				
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
		 * Go back
		 * <p>
		 * goes back to prevous page
		 * </p>	   
		 */	
		@FXML
		public void goBack(ActionEvent event) throws IOException{
			
			loadAlbum();
			controlla.switchScenes(3);
		}
		
		
		
		/**
		 * load fxml 3
		 */	
		public void loadAlbum() throws IOException{
			//ALBUM SCENE 3)
			FXMLLoader loader = new FXMLLoader();   
			loader.setLocation(
					getClass().getResource("/view/3.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			controlla.albumScene = new Scene(root, 600, 500);
		}
		
		
		
		
		
	
	


}
