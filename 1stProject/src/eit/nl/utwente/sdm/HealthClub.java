package eit.nl.utwente.sdm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class HealthClub {
	
	private int id;
	private String name;
	private String location;
	private String contact;  

	
	public HealthClub(int idHc, String nm, String ct, String cont){
		id = idHc;
		name = nm;
		location = ct;
		contact = cont;
	}

	public HealthClub(String nm, String ct, String cont){
		name = nm;
		location = ct;
		contact = cont;
	}

	public int getId() {
		return id;
	}


	public void setId(int idHealthClub) {
		this.id = idHealthClub;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCity() {
		return location;
	}


	public void setCity(String city) {
		this.location = city;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public void persist() throws SQLException {
		Connection dbConnection = null;
		PreparedStatement insertData = null;
		String insertString = "insert into "
				+ "health_club"
				+ "(name, location, contact) VALUES"
				+ "(?,?,?)";

		try {
			dbConnection = DBUtils.getDBConnection();
			insertData = dbConnection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
			insertData.setString(1, name);
			insertData.setString(2, location);
			insertData.setString(3, contact);

			// execute insert SQL statement
			insertData.execute();
			ResultSet generatedKeys = insertData.getGeneratedKeys();
			if (generatedKeys.next()) {
				setId(generatedKeys.getInt(1));
			}
			System.out.println("New health club was persisted");

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
	
	public void delete() {
		if (getId() == DBUtils.ID_NOT_SET) { 
			//entity not yet persisted
			return;
		}
		Connection dbConnection = null;
		PreparedStatement sqlStatement = null;
		String sqlString = "delete from health_club where id = ?";

		try {
			dbConnection = DBUtils.getDBConnection();
			sqlStatement = dbConnection.prepareStatement(sqlString);
			sqlStatement.setInt(1, getId());
			sqlStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
