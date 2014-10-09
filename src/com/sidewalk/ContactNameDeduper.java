package com.sidewalk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ContactNameDeduper {
	
	/**
	 * Merge contacts from various different sources
	 */
	public static void mergeContacts(ArrayList<ContactDetails> contactDetailsList, String outputName)
	{
		HashMap<String, ContactDetails> mergedMap =  new HashMap<>();
		try {
			File outputFile = new File(outputName);
			if(!outputFile.exists())
				outputFile.createNewFile();
			for (ContactDetails contactDetails : contactDetailsList) 
			{
				String firstName = contactDetails.getFirstName();
				String lastName = contactDetails.getLastName();
				String[] possibleKeys = { firstName.toLowerCase() + " " + lastName.toLowerCase() , // Both Names
										  firstName.toLowerCase().charAt(0) + " " + lastName.toLowerCase() , // First Initial, last name
										  firstName.toLowerCase() + " " + lastName.toLowerCase().charAt(0) , // First Name, Last initial
										  firstName.toLowerCase().charAt(0) + " " + lastName.toLowerCase().charAt(0) }; // First initial, Last initial
				
				for (String possibleKey : possibleKeys) 
				{
					 ContactDetails uniqueContact = mergedMap.get(possibleKey);
					 if(uniqueContact != null)
					 {
						 uniqueContact.setSourceDetails(firstName + " " +lastName, uniqueContact.getSourceList().get(0));
						 mergedMap.put(possibleKey, uniqueContact);
					 }
					 else
						 mergedMap.put(firstName.toLowerCase() + " " +lastName.toLowerCase(), uniqueContact);
				}
			}
			
			//for (Entry<String, ContactDetails> contactDetails : mergedMap.entrySet()) {
				//System.out.println("contact  : " + contactDetails.getValue().getSourceList());
			//}
			
			
			
			FileOutputStream fos = new FileOutputStream(outputFile);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(mergedMap);
	        oos.close();
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String inputDir = "";
		String outputFile = "/home/patronus/padfoot/src/ContactDeduper/merged_contacts.txt"; // Default output file		
		ArrayList<ContactDetails> inputContactDetails = new ArrayList<>();
		if(args.length < 1)
		{
			System.out.println("Input directory is not passed. So assuming default input directory...");
			inputDir = "/home/patronus/padfoot/src/ContactDeduper/input";
		}
		else
			inputDir = args[0];
		
		File inputDirectory = new File(inputDir.trim());
		
		try {
			String line = "";
			
			// Contact Details are fetched from different files as in real-time scenario
			// we tend to get different inputfiles from different sources
			if(inputDirectory.exists())
			{
				for (File file : inputDirectory.listFiles()) 
				{				
					System.out.println("Reading file... " + file.getName());
					BufferedReader br = new BufferedReader(new FileReader(file));				
					while((line = br.readLine()) != null)
					{
						line = line.trim();					
						StringTokenizer tokenizer = new StringTokenizer(line);
						ContactDetails contact = new ContactDetails();
						String first = tokenizer.nextToken();
						String last = tokenizer.nextToken();
						contact.setFirstName(first);
						contact.setLastName(last);				
						
						// Source as file name (removing ext)					
						String fileName = file.getName();
						fileName = fileName.substring(0, fileName.indexOf("."));
						contact.setSourceDetails(line, fileName);
						inputContactDetails.add(contact);
					}
					br.close();
				}
				
				mergeContacts(inputContactDetails, outputFile);
			
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
