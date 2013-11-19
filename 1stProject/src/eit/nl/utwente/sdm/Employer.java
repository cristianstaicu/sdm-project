package eit.nl.utwente.sdm;
import java.util.Random;


public class Employer {
	int idEmployer;
	String name;
	String surname; //location in database
	int idCompany; // could be email or telephone number, to decide, not so important btw
	public static int prime = 4801; 

	
	public Employer(int idEmp, String nm, String snm, int idComp){
		idEmployer = idEmp;
		name = nm;
		surname = snm;
		idCompany = idComp;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	//TODO same problem as in Patient class
	public void setIdHospital(){
		Random rn = new Random();
		int id = rn.nextInt(1000000000);
		id = id%prime;
		idEmployer = id;
	}
	
	public void setSurname(String newSurname){
		surname = newSurname;
	}
	
	public void setContact(int newIdCompany){
		idCompany = newIdCompany;
	}
}

