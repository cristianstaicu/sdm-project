package eit.nl.utwente.sdm;


public class Hospital {

	private int idHospital;
	private String name;
	private String city;
	private String contact;  

		
	public Hospital(int idHos, String nm, String ct, String cont){
		idHospital = idHos;
		name = nm;
		city = ct;
		contact = cont;
	}


	public int getIdHospital() {
		return idHospital;
	}


	public void setIdHospital(int idHospital) {
		this.idHospital = idHospital;
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
