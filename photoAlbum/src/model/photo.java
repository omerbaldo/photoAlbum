package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.collections.ObservableList;
/**
 * @author      Omer Baldo, Peter Santana-Kroh <address @ osb5@scarletmail.rutgers.edu> <address @ psantana94@gmail.com>
 * @version     2.0         
 * @since       1.1       
 */
public class photo implements Serializable {

	/**
	 * Filepath
	 */
	public String filepath;
	/**
	 * Date
	 */
	public Date date;
	/**
	 * Caption
	 */
	public String caption;
	/**
	 * Name
	 */
	public String name;
	/**
	 * File
	 */
	public File file;

	// Tags 
	/**
	 * Array list for hashtag
	 */
	public ArrayList<String> hashTag = new ArrayList<String>();
	/**
	 * Geo Location tag
	 */
	public String locationTag;
	/**
	 * Constructor for file
	 * gets file and name
	 * calculations date
	 */
	public photo(File f, String n) {
		// TODO Auto-generated constructor stub
		
		this.file = f;
		this.name = n;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND,0);
		
		this.date = new Date(file.lastModified());
	}

	/**
	 * Returns name
	 */
	public String toString() { 
	    return this.name;
	} 
	/**
	 * Rewrites Caption
	 */
	public void rename(String s){
		this.caption = s;
	}
	
	
	
}