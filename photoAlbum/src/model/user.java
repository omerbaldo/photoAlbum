package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
/**
 * @author      Omer Baldo, Peter Santana-Kroh <address @ osb5@scarletmail.rutgers.edu> <address @ psantana94@gmail.com>
 * @version     2.0         
 * @since       1.1       
 */
public class user implements Comparable, Serializable{
	public String userName;
	
	/**
	 * Array List for Albums
	 */
	public List <album> albums =  new ArrayList<album>();
	
	/**
	 * Serialization # implementation
	 */
	static final long serialVersionUID = 1L;
	/**
	 * Username
	 */
	public user(String n){
		this.userName = n;
	}
	/**
	 * Returns Username 
	 */
	public String getUserName(){
		return this.userName;
	}
	
	/**
	 * Gives back Username
	 */
	 public String toString() { 
		    return this.userName;
		} 
	 /**
	 * Checks to see if username is greater then the other
	 */
	 @Override
		public int compareTo(Object o) {
		 	user u = (user) o;
			return this.userName.compareTo(u.userName);
		}
	 
	 /**
	 * comparing usernames to see if they already exist
	 */
		@Override
		public boolean equals(Object obj) {
			if (obj == null || (!(obj instanceof user))) {
				 return false;
			}
			user other = (user )obj; 
			return this.userName.equals(other.userName);
		}
	 
	 
	 
	 

}
	