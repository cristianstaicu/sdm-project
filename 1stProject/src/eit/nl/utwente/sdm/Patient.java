package eit.nl.utwente.sdm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Patient {
	
	private int id = DBUtils.ID_NOT_SET;
	private String name;
	private String surname;
	private Date birthDate;
	private int idDoc;
	private int idEmpl;
	private int idIns;

	public Patient(int id, String namePat, String surnamePat, Date birthDatePat,
			int idDocPat, int idEmplPat, int idInsPat) {
		this.id = id;
		name = namePat;
		surname = surnamePat;
		birthDate = birthDatePat;
		idDoc = idDocPat;
		idEmpl = idEmplPat;
		idIns = idInsPat;
	}
	
	public Patient(String namePat, String surnamePat, Date birthDatePat,
			int idDocPat, int idEmplPat, int idInsPat) {
		name = namePat;
		surname = surnamePat;
		birthDate = birthDatePat;
		idDoc = idDocPat;
		idEmpl = idEmplPat;
		idIns = idInsPat;
	}


	public void insertPatientHealthData() throws SQLException {
		Connection dbConnection = null;
		PreparedStatement insertData = null;
		String insertString = "insert into "
				+ "personal_health_data"
				+ "(name, surname, birthdate, gender, address, id_doctor, id_employer, id_insurance) VALUES"
				+ "(?,?,?,?,?,?,?,?,?)";

		try {
			dbConnection = DBUtils.getDBConnection();
			insertData = dbConnection.prepareStatement(insertString);
			insertData.setString(2, name);
			insertData.setString(3, surname);
			java.sql.Date birthDaySQL = new java.sql.Date(birthDate.getTime());
			insertData.setDate(4, birthDaySQL);
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	public int getIdEmpl() {
		return idEmpl;
	}

	public void setIdEmpl(int idEmpl) {
		this.idEmpl = idEmpl;
	}

	public int getIdIns() {
		return idIns;
	}

	public void setIdIns(int idIns) {
		this.idIns = idIns;
	}

}
