package eit.nl.utwente.sdm;
import eit.nl.utwente.sdm.datastructures.SecretKey;
import eit.nl.utwente.sdm.policy.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Patient {
	
	private int id;
	private String name;
	private String surname;
	private Date birthDate;
	private String address;
	private int idDoc;
	private int idEmpl;
	private int idIns;
	//not persisted in DB
	private SecretKey key;

	public Patient(int id, String namePat, String surnamePat, Date birthDatePat, String address,
			int idDocPat, int idEmplPat, int idInsPat) {
		this.id = id;
		name = namePat;
		surname = surnamePat;
		birthDate = birthDatePat;
		idDoc = idDocPat;
		this.address = address;
		idEmpl = idEmplPat;
		idIns = idInsPat;
	}
	
	public Patient(String namePat, String surnamePat, Date birthDatePat, String address,
			int idDocPat, int idEmplPat, int idInsPat) {
		name = namePat;
		surname = surnamePat;
		birthDate = birthDatePat;
		this.address = address;
		idDoc = idDocPat;
		idEmpl = idEmplPat;
		idIns = idInsPat;
	}


	public void persist() throws SQLException {
		Connection dbConnection = null;
		PreparedStatement insertData = null;
		String insertString = "insert into "
				+ "patient"
				+ "(name, surname, birthday, address, id_doc, id_emp, id_ins) VALUES"
				+ "(?,?,?,?,?,?,?)";

		try {
			dbConnection = DBUtils.getDBConnection();
			insertData = dbConnection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
			insertData.setString(1, name);
			insertData.setString(2, surname);
			java.sql.Date birthDaySQL = new java.sql.Date(birthDate.getTime());
			insertData.setDate(3, birthDaySQL);
			insertData.setString(4, address);
			insertData.setInt(5, idDoc);
			insertData.setInt(6, idEmpl);
			insertData.setInt(7, idIns);

			// execute insert SQL statement
			insertData.execute();
			ResultSet generatedKeys = insertData.getGeneratedKeys();
			if (generatedKeys.next()) {
				setId(generatedKeys.getInt(1));
			}
			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {
			e.printStackTrace();
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

	public void delete() {
		if (getId() == DBUtils.ID_NOT_SET) { 
			//entity not yet persisted
			return;
		}
		Connection dbConnection = null;
		PreparedStatement sqlStatement = null;
		String sqlString = "delete from patient where id = ?";

		try {
			dbConnection = DBUtils.getDBConnection();
			sqlStatement = dbConnection.prepareStatement(sqlString);
			sqlStatement.setInt(1, getId());
			sqlStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void readAccessPolicy(boolean idDocB, boolean idEmpB, boolean idInsB){
		OrNode root = new OrNode(null, null);
		ArrayList<AttributeNode> list = new ArrayList<AttributeNode>();
		if (idDocB == true){
			AttributeNode idDocNode = new AttributeNode(null, null, this.idDoc+"");
			list.add(idDocNode);
		}
		if (idEmpB == true){
			AttributeNode idEmpNode = new AttributeNode(null, null, this.idEmpl+"");
			list.add(idEmpNode);
		}
		if (idInsB == true){
			AttributeNode idInsNode = new AttributeNode(null, null, this.idIns+"");
			list.add(idInsNode);
		}
		
		AttributeNode idPatientNode = new AttributeNode(null, null, this.id+"");
		for (AttributeNode attnodes : list){
			OrNode or1 = new OrNode(idPatientNode, attnodes);
		}
	}

	public void setKey(SecretKey key) {
		this.key = key;
		
	}

}
