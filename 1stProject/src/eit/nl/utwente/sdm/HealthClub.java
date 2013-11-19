package eit.nl.utwente.sdm;


public class HealthClub {
	
	private int idHealthClub;
	private String name;
	private String city;
	private String contact;  

	
	public HealthClub(int idHc, String nm, String ct, String cont){
		idHealthClub = idHc;
		name = nm;
		city = ct;
		contact = cont;
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
