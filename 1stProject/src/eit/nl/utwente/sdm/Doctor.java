package eit.nl.utwente.sdm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Doctor {
	
	private int id;
	private String name;
	private String surname;
	private String department; //could be also an enum
	private int idHospital;
	private int idHealthClub;
	
	public Doctor(int idDoc, String nm, String snm, String dptm, int idHos, int idHc){
		id = idDoc;
		name = nm;
		surname = snm;
		department = dptm;
		idHospital = idHos;
		idHealthClub = idHc;
	}
	
	public Doctor(String nm, String snm, String dptm, int idHos, int idHc){
		name = nm;
		surname = snm;
		department = dptm;
		idHospital = idHos;
		idHealthClub = idHc;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public void setSurname(String newSurname){
		surname = newSurname;
	}
	
	public void setDepartment(String newDepartmnet){
		department = newDepartmnet;
	}
	
	public void setIdHosp (int newIdHosp){
		idHospital = newIdHosp;
	}
	
	public void setIdHc (int newIdHc){
		idHealthClub = newIdHc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdHospital() {
		return idHospital;
	}

	public void setIdHospital(int idHospital) {
		this.idHospital = idHospital;
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

	public String getSurname() {
		return surname;
	}

	public String getDepartment() {
		return department;
	}	
	
	public void persist() throws SQLException {
		Connection dbConnection = null;
		PreparedStatement insertData = null;
		String insertString = "insert into "
				+ "doctor"
				+ "(name, surname, department, id_hospital, id_healthclub) VALUES"
				+ "(?,?,?,?,?)";

		try {
			dbConnection = DBUtils.getDBConnection();
			insertData = dbConnection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
			insertData.setString(1, name);
			insertData.setString(2, surname);
			insertData.setString(3, department);
			insertData.setInt(4, idHospital);
			insertData.setInt(5, idHealthClub);

			// execute insert SQL statement
			insertData.execute();
			ResultSet generatedKeys = insertData.getGeneratedKeys();
			if (generatedKeys.next()) {
				setId(generatedKeys.getInt(1));
			}
			System.out.println("New doctor was persisted");

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
		String sqlString = "delete from doctor where id = ?";

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
