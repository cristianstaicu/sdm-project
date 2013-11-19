package eit.nl.utwente.sdm;

public class Insurance {
	
	private int idInsurance;
	private String name;
	private String city; 
	private String contact;  

	
	public Insurance(int idIns, String nm, String ct, String cont){
		idInsurance = idIns;
		name = nm;
		city = ct;
		contact = cont;
	}


	public int getIdInsurance() {
		return idInsurance;
	}


	public void setIdInsurance(int idInsurance) {
		this.idInsurance = idInsurance;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}
	
}
