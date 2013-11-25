package eit.nl.utwente.sdm;

import it.unisa.dia.gas.jpbc.Element;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import eit.nl.utwente.sdm.datastructures.Ciphertext;
import eit.nl.utwente.sdm.datastructures.PublicKey;
import eit.nl.utwente.sdm.policy.Node;

public class HealthRecord {
	
	public static final int USER_INSERTED_HR = 1;
	public static final int HOSPITAL_INSERTED_HR = 2;
	public static final int HEALTH_CLUB_INSERTED_HR = 3;

	private int id;
	private int idPatient;
	private int idHospital;
	private int idDoctor;
	private int idHealthClub;
	private int measurementType;
	private String value;
	private String date;
	private String statement;
	private String policy;

	public HealthRecord(int id, int idPatient, int idHospital, int idDoctor,
			int idHealthClub, int measurementType, String value, String date,
			String statement, String policy) {
		this.id = id;
		this.idPatient = idPatient;
		this.idHospital = idHospital;
		this.idDoctor = idDoctor;
		this.idHealthClub = idHealthClub;
		this.measurementType = measurementType;
		this.value = value;
		this.date = date;
		this.statement = statement;
		this.policy = policy;
	}

	public HealthRecord(int idPatient, int idHospital, int idDoctor,
			int idHealthClub, int measurementType, String value, String date,
			String statement, String policy) {
		this.idPatient = idPatient;
		this.idHospital = idHospital;
		this.idDoctor = idDoctor;
		this.idHealthClub = idHealthClub;
		this.measurementType = measurementType;
		this.value = value;
		this.date = date;
		this.statement = statement;
		this.policy = policy;
	}

	public int getId() {
		return id;
	}

	public void setId(int idHealthClub) {
		this.id = idHealthClub;
	}

	public int getIdHospital() {
		return idHospital;
	}

	public void setIdHospital(int idHospital) {
		this.idHospital = idHospital;
	}

	public int getIdDoctor() {
		return idDoctor;
	}

	public void setIdDoctor(int idDoctor) {
		this.idDoctor = idDoctor;
	}

	public int getIdHealthClub() {
		return idHealthClub;
	}

	public void setIdHealthClub(int idHealthClub) {
		this.idHealthClub = idHealthClub;
	}

	public int getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(int measurementType) {
		this.measurementType = measurementType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public void persist(PublicKey pk) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement insertData = null;
		String insertString = "insert into "
				+ "health_data"
				+ "(id_patient, id_hospital, id_doctor, id_healthclub, value, date, measurement_type, statement, policy) VALUES"
				+ "(?,?,?,?,?,?,?,?,?)";

		try {
			dbConnection = DBUtils.getDBConnection();
			Node pol = Node.deserializeOrPolicy(policy);
			Element randEl = pk.Zr.newRandomElement();
			Ciphertext encryptedValue = MCPABEHelper.encrypt(value, pol, pk, randEl);
			randEl = pk.Zr.newRandomElement();
			Ciphertext encryptedDate = MCPABEHelper.encrypt(date, pol, pk, randEl);
			randEl = pk.Zr.newRandomElement();
			Ciphertext encryptedStat = MCPABEHelper.encrypt(statement, pol, pk, randEl);
			insertData = dbConnection.prepareStatement(insertString,
					Statement.RETURN_GENERATED_KEYS);
			insertData.setInt(1, idPatient);
			insertData.setInt(2, idHospital);
			insertData.setInt(3, idDoctor);
			insertData.setInt(4, idHealthClub);
			insertData.setString(5, encryptedValue.toString());
			insertData.setString(6, encryptedDate.toString());
			insertData.setInt(7, measurementType);
			insertData.setString(8, encryptedStat.toString());
			insertData.setString(9, policy);

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
			// entity not yet persisted
			return;
		}
		Connection dbConnection = null;
		PreparedStatement sqlStatement = null;
		String sqlString = "delete from health_data where id = ?";

		try {
			dbConnection = DBUtils.getDBConnection();
			sqlStatement = dbConnection.prepareStatement(sqlString);
			sqlStatement.setInt(1, getId());
			sqlStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(int idPatient) {
		this.idPatient = idPatient;
	}

	public void updatePolicy(Node policy, PublicKey pk, String value, String date, String statement) {
		this.policy = policy.getPolicyAsString();
		Connection dbConnection = null;
		PreparedStatement sqlStatement = null;
		String sqlString = "update health_data set policy = ?, value = ?, date = ?, statement = ? where id = ?";

		try {
			dbConnection = DBUtils.getDBConnection();
			Element randEl = pk.Zr.newRandomElement();
			Ciphertext encryptedValue = MCPABEHelper.encrypt(value, policy, pk, randEl);
			randEl = pk.Zr.newRandomElement();
			Ciphertext encryptedDate = MCPABEHelper.encrypt(date, policy, pk, randEl);
			randEl = pk.Zr.newRandomElement();
			Ciphertext encryptedStat = MCPABEHelper.encrypt(statement, policy, pk, randEl);
			
			dbConnection = DBUtils.getDBConnection();
			sqlStatement = dbConnection.prepareStatement(sqlString);
			sqlStatement.setString(1, this.policy);
			sqlStatement.setString(2, encryptedValue.toString());
			sqlStatement.setString(3, encryptedDate.toString());
			sqlStatement.setString(4, encryptedStat.toString());
			sqlStatement.setInt(5, getId());
			sqlStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
