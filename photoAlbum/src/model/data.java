package model;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author      Omer Baldo, Peter Santana-Kroh <address @ osb5@scarletmail.rutgers.edu> <address @ psantana94@gmail.com>
 * @version     2.0         
 * @since       1.1       
 */
public class data {

	/**
	 * Obslist that holds all users 
	 */
	public static ObservableList<user> users = FXCollections.observableArrayList();
	
	/**
	 * Adding Users to User array list
	 * &
	 * Deleting Users from User array list
	 */
	public static List<user> usersAdded = new ArrayList<user>();
	public static List<user> usersDeleted= new ArrayList<user>();;
	
}
