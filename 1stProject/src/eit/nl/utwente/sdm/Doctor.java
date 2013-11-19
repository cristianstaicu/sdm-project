package eit.nl.utwente.sdm;
import java.util.Random;


public class Doctor {
	
	public static int prime = 4801; 
		
	int idDoctor;
	String name;
	String surname;
	String department; //could be also an enum
	int idHospital;
	int idHealthClub;
	
	public Doctor(int idDoc, String nm, String snm, String dptm, int idHos, int idHc){
		idDoctor = idDoc;
		name = nm;
		surname = snm;
		department = dptm;
		idHospital = idHos;
		idHealthClub = idHc;
	}
	
//TODO same problem as in Patient class
	public void setIdDoctor(){
		Random rn = new Random();
		int id = rn.nextInt(1000000000);
		id = id%prime;
		idDoctor = id;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public void setSurname(String newSurname){
		surname = newSurname;
	}
	
	public void setDepartment(String newDepartmnet){
		department = newDepartmnet;
	}
	
	public void setIdHosp (int newIdHosp){
		idHospital = newIdHosp;
	}
	
	public void setIdHc (int newIdHc){
		idHealthClub = newIdHc;
	}

}
