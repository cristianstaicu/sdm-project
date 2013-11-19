package eit.nl.utwente.sdm;


public class Employer {
	
	private int id;
	private String name;
	private String surname; 

	
	public Employer(int idEmp, String nm, String snm, int idComp){
		id = idEmp;
		name = nm;
		surname = snm;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
}

