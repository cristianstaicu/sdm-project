package tests.eit.nl.utwente.sdm;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import eit.nl.utwente.sdm.Doctor;
import eit.nl.utwente.sdm.Employer;
import eit.nl.utwente.sdm.HealthClub;
import eit.nl.utwente.sdm.Hospital;
import eit.nl.utwente.sdm.Insurance;
import eit.nl.utwente.sdm.Patient;

public class PopulateDB {

	public static void main(String[] args) throws SQLException, ParseException {
		//Hospital creation + insertion in the db
		Hospital h1 = new Hospital("Stichting Medisch Spectrum Twente", "Enschede", "Haaksbergerstraat 55");
		h1.persist();
		int h1Id = h1.getId();
		Hospital h2 = new Hospital("Stichting Mercuur", "Eindhoven", "Frankrijkstraat 126");
		h2.persist();
		int h2Id = h2.getId();
		Hospital h3 = new Hospital("Boerhaave Medisch Centrum", "Amsterdam", "Johannes Vermeerstraat 31");
		h3.persist();
		int h3Id = h3.getId();
		Hospital h4 = new Hospital("Santa Chiara", "Trento", "Via dei Mille 35");
		h4.persist();
		int h4Id = h4.getId();
		Hospital h5 = new Hospital("San Raffaele", "Milano", "Via Pellegrino Rossi 54");
		h5.persist();
		int h5Id = h5.getId();
		
		//Health Club creation + insertion in the db
		HealthClub hc1 = new HealthClub("Fitness4ever", "New York", "+33 123456789");
		hc1.persist();
		int hc1Id = hc1.getId();
		HealthClub hc2 = new HealthClub("ReHab", "Berlin", "+42 987654321"); 
		hc2.persist();
		int hc2Id = hc2.getId();
		HealthClub hc3 = new HealthClub("Physiotherapy", "Paris", "+56 123498765");
		int hc3Id = hc2.getId();
		hc3.persist();
		
		//Employer creation + insertion in the db
		Employer e1 = new Employer("Jonker", "Phillips");
		e1.persist();
		int e1Id = e1.getId();
		Employer e2 = new Employer("Kargl","University of Twente");
		e2.persist();
		int e2Id = e2.getId();
		Employer e3 = new Employer("Hartel","University of Twente");
		e3.persist();
		int e3Id = e3.getId();
		Employer e4 = new Employer("Gates","Microsoft");
		e4.persist();
		int e4Id = e4.getId();
		
		//Doctor creation + insertion in the db
		Doctor d1 = new Doctor("Gregory", "House", "Diagnostic Medicine",h1Id, hc3Id);
		d1.persist();
		int d1Id = d1.getId();
		Doctor d2 = new Doctor("Hansel", "Gretel", "Cardiology", h2Id, hc2Id);
		d2.persist();
		int d2Id = d2.getId();
		Doctor d3 = new Doctor("Giulio", "Cesare", "Neurology", h3Id, hc1Id);
		d3.persist();
		int d3Id = d3.getId();
		Doctor d4 = new Doctor("Zlatan", "Ibrahimovic", "Surgery", h4Id, hc3Id);
		d4.persist();
		int d4Id = d4.getId();
		Doctor d5 = new Doctor("Steve", "Buscemi", "Surgery", h5Id, hc1Id);
		d5.persist();
		int d5Id = d5.getId();
		Doctor d6 = new Doctor("Brad", "Pitt", "Emergency Room", h2Id, hc3Id);
		d6.persist();
		int d6Id = d6.getId();
		Doctor d7 = new Doctor("Angela", "Merkel", "Cardiology", h3Id, hc2Id);
		d7.persist();
		int d7Id = d7.getId();
		Doctor d8 = new Doctor("Peter", "Parker", "Surgery", h5Id, hc2Id);
		d8.persist();
		int d8Id = d8.getId();
		
		//Insurance
		Insurance i1 = new Insurance("Allianz", "Munchen", "+41 981276345");
		i1.persist();
		int i1Id = i1.getId();
		Insurance i2 = new Insurance("Allied Insurance", "Los Angeles", "+02 674351298");
		i2.persist();
		int i2Id = i2.getId();
		Insurance i3 = new Insurance("MetLife", "Marseille", "+52 123456798");
		i3.persist();
		int i3Id = i3.getId();
		
		//Patients
		String oldstring1 = "1990-07-12";
		Date birthDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring1);
		Patient p1 = new Patient("Hans", "Schultz", birthDate1, "Haaksbergerstraat 82", d4Id, e1Id, i1Id);
		p1.persist();
		String oldstring2 = "1938-01-11";
		Date birthDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring2);
		Patient p2 = new Patient("Christian", "Bale", birthDate2, "Haaksbergerstraat 82", d8Id, e3Id, i1Id);
		p2.persist();
		String oldstring3 = "1989-08-30";
		Date birthDate3 = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring3);
		Patient p3 = new Patient("Bruno", "Vespa", birthDate3, "via Montenapoleone 82", d2Id, e2Id, i1Id);
		p3.persist();
		String oldstring4 = "1992-10-16";
		Date birthDate4 = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring4);
		Patient p4 = new Patient("Hans", "Zimmer", birthDate4, "Naturlich strasse 2", d5Id, e2Id, i1Id);
		p4.persist();
		String oldstring5 = "1985-12-22";
		Date birthDate5 = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring5);
		Patient p5 = new Patient("Gareth", "Bale", birthDate5, "Boston Road 666", d3Id, e4Id, i2Id);
		p5.persist();
		String oldstring6 = "1934-02-01";
		Date birthDate6 = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring6);
		Patient p6 = new Patient("Lesean", "McCoy", birthDate6, "Philadelphia Avenue 25", d5Id, e3Id, i2Id);
		p6.persist();
		String oldstring7 = "1955-03-02";
		Date birthDate7 = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring7);
		Patient p7 = new Patient("Calvin", "Johnson", birthDate7, "Lions road 90", d6Id, e3Id, i2Id);
		p7.persist();
		String oldstring8 = "1966-04-18";
		Date birthDate8 = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring8);
		Patient p8 = new Patient("Javier", "Zanetti", birthDate8, "Corso Como 92", d7Id, e1Id, i3Id);
		p8.persist();
		String oldstring9 = "1975-07-12";
		Date birthDate9 = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring9);
		Patient p9 = new Patient("Mary", "Poppins", birthDate9, "St Martin Lane 1", d8Id, e1Id, i3Id);
		p9.persist();
	}
	
}
