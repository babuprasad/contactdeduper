package com.sidewalk;

import java.util.ArrayList;

/**
 * ContactDetails POJO class to store information about 
 * contact details from different sources
 * @author Girish
 *
 */
public class ContactDetails {

	private String firstName = "";
	private String lastName = "";
	private ArrayList<String> nameList = new ArrayList<>();
	private ArrayList<String> sourceList = new ArrayList<>();
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the sourceName
	 */
	public ArrayList<String> getSourceList() {
		return sourceList;
	}
	
	/**
	 * @return the nameList
	 */
	public ArrayList<String> getNameList() {
		return nameList;
	}
	/**
	 * @param nameList the nameList to set
	 */
	public void setNameList(ArrayList<String> nameList) {
		this.nameList = nameList;
	}
	/**
	 * @param sourceName the sourceName to set
	 */
	public void setSourceDetails(String contactName, String sourceName) {
		this.nameList.add(contactName);
		this.sourceList.add(sourceName);
	}	
	
	
	
}
