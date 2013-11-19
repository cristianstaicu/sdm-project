package eit.nl.utwente.sdm;
import java.util.Random;


public class Doctor {
	
	private int id;
	private String name;
	private String surname;
	private String department; //could be also an enum
	private int idHospital;
	private int idHealthClub;
	
	public Doctor(int idDoc, String nm, String snm, String dptm, int idHos, int idHc){
		id = idDoc;
		name = nm;
		surname = snm;
		department = dptm;
		idHospital = idHos;
		idHealthClub = idHc;
	}
	
	public Doctor(String nm, String snm, String dptm, int idHos, int idHc){
		name = nm;
		surname = snm;
		department = dptm;
		idHospital = idHos;
		idHealthClub = idHc;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdHospital() {
		return idHospital;
	}

	public void setIdHospital(int idHospital) {
		this.idHospital = idHospital;
	}

	public int getIdHealthClub() {
		return idHealthClub;
	}

	public void setIdHealthClub(int idHealthClub) {
		this.idHealthClub = idHealthClub;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getDepartment() {
		return department;
	}	

}
