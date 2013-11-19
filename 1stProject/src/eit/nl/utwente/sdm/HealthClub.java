package eit.nl.utwente.sdm;
import java.util.LinkedList;
import java.util.Random;


public class HealthClub {
	int idHealthClub;
	String name;
	String city; //location in database
	String contact; // could be email or telephone number, to decide, not so important btw
	LinkedList<Doctor> doctorList; //doctor who are working at this health club
	public static int prime = 4801; 

	
	public HealthClub(int idHc, String nm, String ct, String cont){
		idHealthClub = idHc;
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
		idHealthClub = id;
	}
	
	public void setCity(String newCity){
		city = newCity;
	}
	
	public void setContact(String newContact){
		contact = newContact;
	}
	
	public void addDoctor(Doctor doc){
		doc.setIdHosp(idHealthClub);
		doctorList.add(doc);
	}
	
	
}
