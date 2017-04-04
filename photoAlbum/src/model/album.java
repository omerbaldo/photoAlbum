package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * @author      Omer Baldo, Peter Santana-Kroh <address @ osb5@scarletmail.rutgers.edu> <address @ psantana94@gmail.com>
 * @version     2.0         
 * @since       1.1       
 */
public class album implements Comparable, Serializable {
	
	/**
	 * album name
	 */
	public String name;
	
	
	public  List <photo> photos =  new ArrayList<photo>();
	
	/**
	 * oldest Photo
	 */
	public photo oldestPhoto;
	
	/**
	 * newest photo
	 */
	public photo newestPhoto;

	public album(String n) {
		this.name = n;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Sorts Album list
	 */
	@Override
	public int compareTo(Object o) {
		album a = (album)o;
		return this.name.compareTo(a.name);
	}

	/**
	 * checks for already existing name of album
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || (!(obj instanceof album))) {
			 return false;
		}
		album other = (album )obj; 
		return this.name.equals(other.name);
	}
	
	/**
	 * returns album name
	 */
	public String toString() { 
	    return this.name;
	} 
	/**
	 * renames album deeting old name
	 */
	public void rename(String s){
		this.name = s;
	}
	/**
	 * deletes photo
	 */
	public boolean deletePhoto(photo p){
		return false;
	}
	/**
	 * updates photo data
	 */
	public void update(){
	
		if(photos.size()==0){
			return;
		}
		photo o = photos.get(0);
		photo n = photos.get(0);
		
		for(photo p: photos){
		
			if(p.date.getTime() < n.date.getTime()){
				n = p;
			}
			if(p.date.getTime() > o.date.getTime()){
				o = p;
			}
			
		}
		oldestPhoto = o;
		newestPhoto = n;
		
		
		
	}
	
	

}
