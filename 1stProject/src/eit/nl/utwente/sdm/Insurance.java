package eit.nl.utwente.sdm;
import java.util.Random;


public class Insurance {
	int idInsurance;
	String name;
	String city; //location in database
	String contact; // could be email or telephone number, to decide, not so important btw
	public static int prime = 4801; 

	
	public Insurance(int idIns, String nm, String ct, String cont){
		idInsurance = idIns;
		name = nm;
		city = ct;
		contact = cont;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	//TODO same problem as in Patient class
	public void setIdHospital(){
		Random rn = new Random();
		int id = rn.nextInt(1000000000);
		id = id%prime;
		idInsurance = id;
	}
	
	public void setCity(String newCity){
		city = newCity;
	}
	
	public void setContact(String newContact){
		contact = newContact;
	}
}
