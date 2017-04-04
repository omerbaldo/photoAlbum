package view;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.album;
import model.photo;
import model.user;
/**
 * @author      Omer Baldo, Peter Santana-Kroh <address @ osb5@scarletmail.rutgers.edu> <address @ psantana94@gmail.com>
 * @version     2.0         
 * @since       1.1       
 */
public class searchController {
	/**
	 * Pane that holds everything
	 */	
	@FXML
	AnchorPane pane;
	
	/**
	 * search button
	 */	
	@FXML
	Button Searchh;
	
	/**
	 * create new album
	 */	
	@FXML
	Button createAlbum;
	
	/**
	 * image
	 */	
	@FXML
	ImageView image;
	
	/**
	 * results 
	 */	
	@FXML
	ListView<photo> results;
	
	/**
	 * log in
	 */	
	@FXML
	Button loginButton;
	
	/**
	 * location tag
	 */	
	@FXML
	TextField locationn;
	
	/**
	 * hashtag
	 */	
	@FXML
	TextField hashtagg;
	
	/**
	 * album name
	 */	
	@FXML
	TextField albumName;
	
	/**
	 * start date
	 */	
	@FXML
	DatePicker startDate;
	
	/**
	 * end date
	 */	
	@FXML
	DatePicker endDate;
	
	/**
	 * go back to page 1
	 */	
	@FXML
	MenuItem logoutButton;
	
	/**
	 * write to file
	 */	
	@FXML
	MenuItem safequitButton;
	
	/**
	 * list for search results
	 */	
	public ObservableList<photo> searchResults  = FXCollections.observableArrayList();    
	
	/**
	 * list for albums
	 */	
	public static ObservableList<album> obsListAlbum = FXCollections.observableArrayList(); 

	/**
	 * list for photos
	 */	
	public static ObservableList<photo> obsListPhoto  = FXCollections.observableArrayList();

	
	/**
	 * Create a new album based on results
	 */	
	@FXML
	public void create(ActionEvent event){
		if(searchResults.size()==0){
			return;
		}
		if(albumName.getText().length()==0){
			Alert alert = new Alert(AlertType.INFORMATION);
			//alert.initOwner(editPage);
			alert.setTitle("Error. ");
			alert.setHeaderText("Nothing ");
			alert.setContentText("Please enter album name for results! ");
			alert.showAndWait();
			return;
		}
		album n = new album(albumName.getText().trim());
		for(photo p : searchResults){
			obsListPhoto.add(p);
			n.photos.add(p);
		}
		
		
		obsListAlbum.add(n);
		controlla.currentUser.albums.add(n);
		
		
	}
	
	/**
	 * search using discrete math. /**
	 * write to file
	 *
	 */	
	@FXML
	public void s(ActionEvent event){
		
		//System.out.println("search");
		
		searchResults.clear();
		//DATE
		
		ArrayList<photo> d = new ArrayList<photo>();
		if(startDate.getValue() !=null && endDate.getValue()!=null){
			
		
			//Get start and end date
			LocalDate localDate = startDate.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			Date sdate = Date.from(instant);	
		
		
			localDate = endDate.getValue();
			instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			Date edate = Date.from(instant);	
			
			//compare start and end date
			if (sdate.compareTo(edate)>0){
				Alert alert = new Alert(AlertType.INFORMATION);
				//alert.initOwner(editPage);
				alert.setTitle("Error. ");
				alert.setHeaderText("Start Date ");
				alert.setContentText("Start date is larger than end date! ");
				alert.showAndWait();
				return;
			}
			for(album a : obsListAlbum){
				for(photo p: a.photos){
					
					if((p.date.getTime()>=sdate.getTime())&&(p.date.getTime()<=edate.getTime())){
						//add this
					//	System.out.println("add date");
						d.add(p);
					}
				}
			}
		}
		
		//location 
		ArrayList<photo> loc = new ArrayList<photo>();
		if(locationn.getText().length()!=0){
			String l = locationn.getText().trim();
			
			for(album a : obsListAlbum){
				for(photo p: a.photos){
					
					if(p.locationTag!=null){
						if(p.locationTag.equals(l)){
							loc.add(p);
						}
					}
					
					
				}
			}
			
		}
		
		//Hash Tag
		ArrayList<photo> hash = new ArrayList<photo>();

		if(hashtagg.getText().length()!=0){
				//System.out.println("searching hashtag");
			
				String[] splited = hashtagg.getText().trim().split("\\s+");
							
				for(album a : obsListAlbum){
						for(photo p: a.photos){
										
							    if(p.hashTag.size()!=0){
										for(String str : splited){
												
											for(String str2: p.hashTag){
												
												//System.out.println("str " + str + "str2 " + str2);
												if(str.equals(str2)){
														hash.add(p);
														//System.out.println("add hash");
												}	
											}
										}
						    	}
					    }
				}
		}
		
		//go through the three array lists . 7 different combinations
		
		
		// (), (h), (d), (l), (l,d), (h,d), (h,l), (h,l,d)
		
		//()
		if(hash.size()==0 && d.size()==0 && loc.size()==0){
			return;
		}
		//(h)
		if(hash.size()!=0 && d.size()==0 && loc.size()==0){
			for(photo p : hash)
				searchResults.add(p);
		}
		//(d)
		if(hash.size()==0 && d.size()!=0 && loc.size()==0){
			for(photo p : d)
				searchResults.add(p);
		}
		//(l)
		if(hash.size()==0 && d.size()==0 && loc.size()!=0){
			for(photo p : loc)
				searchResults.add(p);
		}
		//(l,d)
		if(hash.size()==0 && d.size()!=0 && loc.size()!=0){
			
			for(photo p : loc){
				for(photo p1:d){
					if(p1==p){
						searchResults.add(p);
					}
				}
			}
			
		}
		//(h,d)
		if(hash.size()!=0 && d.size()!=0 && loc.size()==0){
			for(photo p : hash){
				for(photo p1:d){
					if(p1==p){
						searchResults.add(p);
					}
				}
			}
			
		}
		//(h,l)
		if(hash.size()!=0 && d.size()==0 && loc.size()!=0){
			for(photo p : loc){
				for(photo p1:hash){
					if(p1==p){
						searchResults.add(p);
					}
				}
			}
			
		}
		//(h,l,d)
		if(hash.size()!=0 && d.size()!=0 && loc.size()!=0){
			for(photo p : loc){
				for(photo p1:d){
					for(photo p2:hash){
						if(p1==p && p2==p1){
							searchResults.add(p);
						}
					}
				}
			}
		}
		
		
	
		
		results.setItems(searchResults);
		results.getSelectionModel().select(0);
	//	System.out.println("got here");
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
	 * safely quits everything 
	 * <p>
	 * writes data to system
	 * </p>	   
	 */	
	
	@FXML
	public void safeQuit(ActionEvent event) throws IOException{
		controlla.writeData();
		System.exit(1);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * go back to album
	 */	
	@FXML
	public void goBack(ActionEvent event) throws IOException{
		
		loadAlbum();
		controlla.switchScenes(3);
	}
	
	/**
	 * load album fxml
	 */	
	public void loadAlbum() throws IOException{
		//ALBUM SCENE 3)
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/3.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		controlla.albumScene = new Scene(root, 600, 500);
	}
	

	/**
	 * initialize the list views 
	 */	
	@FXML
    public void initialize() throws IOException {	
		obsListAlbum.clear();
		for(album a : controlla.currentUser.albums)
			obsListAlbum.add(a);
		
		searchResults.clear();
		results.setItems(searchResults);
		
		results
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
           (obs, oldVal, newVal) -> 
           updateImage());	
		
		
			
	}

	/**
	 * update the image to the selected search result one 
	 */	
	public void updateImage(){
		
		if(!(results.getSelectionModel().getSelectedIndex() < 0 || results.getSelectionModel().getSelectedIndex() >= searchResults.size())){
			Image i = new Image(results.getSelectionModel().getSelectedItem().file.toURI().toString());
			image.setImage(i);
		}else{
			image.setImage(null);
		}
		
	}

}