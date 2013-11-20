package tests.eit.nl.utwente.sdm;

import java.sql.SQLException;

import eit.nl.utwente.sdm.Hospital;

public class PopulateDB {

	public static void main(String[] args) throws SQLException {
		Hospital h1 = new Hospital("Stichting Medisch Spectrum Twente", "Enschede", "Haaksbergerstraat 55");
		h1.persist();
		Hospital h2 = new Hospital("Stichting Mercuur", "Eindhoven", "Frankrijkstraat 126");
		h2.persist();
		Hospital h3 = new Hospital("Boerhaave Medisch Centrum", "Amsterdam", "Johannes Vermeerstraat 31");
		h3.persist();
	}
	
}
