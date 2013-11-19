package eit.nl.utwente.sdm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

public class Patient {

	// use a prime number to create a unique ID for patients, so here we can
	// have around 4000 patients
	public static int prime = 4801;

	int healthID;
	String name;
	String surname;
	Date birthDate;
	String address;
	String gender;
	int idDoc;
	int idEmpl;
	int idIns;

	public Patient(int healthIDPat, String namePat, String surnamePat,
			String genderPat, Date birthDatePat, String addressPat,
			int idDocPat, int idEmplPat, int idInsPat) {
		healthID = healthIDPat; // represents personal_health_data column id in
								// the database
		name = namePat;
		surname = surnamePat;
		gender = genderPat;
		birthDate = birthDatePat;
		address = addressPat;
		idDoc = idDocPat;
		idEmpl = idEmplPat;
		idIns = idInsPat;
	}

	// TODO fix this problem if ID generation, it should have: same length +
	// unique number + "id for patient" (could be an
	// appending of 2 numbers at the end, like xxxx11 or at the beginning, just
	// to recognise it's a patient id

	// TODO careful, problem with database, maybe we have to change ID (int) in
	// ID (VARCHAR)
	public void setHealthIdPat() {
		Random rn = new Random();
		int id = rn.nextInt(1000000000);
		id = id % prime;
		healthID = id;
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setSurname(String newSurname) {
		surname = newSurname;
	}

	public void setBirthDate(Date newBirth) {
		birthDate = newBirth;
	}

	public void setAddress(String newAdd) {
		address = newAdd;
	}

	public void setDoc(int newDoc) {
		idDoc = newDoc;
	}

	public void setEmp(int newEmp) {
		idEmpl = newEmp;
	}

	public void setIns(int newIns) {
		idIns = newIns;
	}

	public void insertPatientHealthData() throws SQLException {
		Connection dbConnection = null;
		PreparedStatement insertData = null;
		String insertString = "insert into "
				+ "personal_health_data"
				+ "(id, name, surname, birthdate, gender, address, id_doctor, id_employer, id_insurance) VALUES"
				+ "(?,?,?,?,?,?,?,?,?)";

		try {
			dbConnection = DBUtils.getDBConnection();
			insertData = dbConnection.prepareStatement(insertString);
			insertData.setInt(1, healthID);
			insertData.setString(2, name);
			insertData.setString(3, surname);
			java.sql.Date birthDaySQL = new java.sql.Date(birthDate.getTime());
			insertData.setDate(4, birthDaySQL);
			insertData.setString(5, gender);
			insertData.setString(6, address);
			insertData.setInt(7, idDoc);
			insertData.setInt(8, idEmpl);
			insertData.setInt(9, idIns);

			// execute insert SQL statement
			insertData.executeUpdate();
			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (insertData != null) {
				insertData.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
	}

}
