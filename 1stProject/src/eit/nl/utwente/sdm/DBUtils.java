package eit.nl.utwente.sdm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBUtils {

	public static final int ID_NOT_SET = -1;

	public static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			GlobalProperties propProvider = GlobalProperties.getInstance();
			String DB_CONNECTION = propProvider.getProperty("DB_CONNECTION");
			String DB_USER = propProvider.getProperty("DB_USER");
			String DB_PASSWORD = propProvider.getProperty("DB_PASSWORD");
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return dbConnection;
	}

	public static List<Patient> getPatients() {
		List<Patient> result = new ArrayList<Patient>();
		Connection dbConnection = getDBConnection();
		PreparedStatement insertData = null;
		String insertString = "select * from patient";
		try {
			insertData = dbConnection.prepareStatement(insertString);
			ResultSet resultSet = insertData.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String surname = resultSet.getString(3);
				Date bday = resultSet.getDate(4);
				String address = resultSet.getString(5);
				int idDoc = resultSet.getInt(6);
				int idEmp = resultSet.getInt(7);
				int idIns = resultSet.getInt(8);
				Patient p = new Patient(id, name, surname, bday, address, idDoc, idEmp, idIns);
				result.add(p);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Insurance> getInsurances() {
		List<Insurance> result = new ArrayList<Insurance>();
		Connection dbConnection = getDBConnection();
		PreparedStatement insertData = null;
		String insertString = "select * from insurance";
		try {
			insertData = dbConnection.prepareStatement(insertString);
			ResultSet resultSet = insertData.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String location = resultSet.getString(3);
				String contact = resultSet.getString(4);
				Insurance i = new Insurance(id, name, location, contact);
				result.add(i);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Hospital> getHospitals() {
		List<Hospital> result = new ArrayList<Hospital>();
		Connection dbConnection = getDBConnection();
		PreparedStatement insertData = null;
		String insertString = "select * from hospital";
		try {
			insertData = dbConnection.prepareStatement(insertString);
			ResultSet resultSet = insertData.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String location = resultSet.getString(3);
				String contact = resultSet.getString(4);
				Hospital i = new Hospital(id, name, location, contact);
				result.add(i);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<HealthClub> getHealthClubs() {
		List<HealthClub> result = new ArrayList<HealthClub>();
		Connection dbConnection = getDBConnection();
		PreparedStatement insertData = null;
		String insertString = "select * from health_club";
		try {
			insertData = dbConnection.prepareStatement(insertString);
			ResultSet resultSet = insertData.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String location = resultSet.getString(3);
				String contact = resultSet.getString(4);
				HealthClub i = new HealthClub(id, name, location, contact);
				result.add(i);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
