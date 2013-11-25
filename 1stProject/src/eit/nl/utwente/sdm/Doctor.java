package eit.nl.utwente.sdm;

import it.unisa.dia.gas.jpbc.Element;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import eit.nl.utwente.sdm.datastructures.Ciphertext;
import eit.nl.utwente.sdm.datastructures.PublicKey;
import eit.nl.utwente.sdm.datastructures.SecretKey;
import eit.nl.utwente.sdm.policy.Node;


public class Doctor {
	
	private int id;
	private String name;
	private String surname;
	private String department; //could be also an enum
	private int idHospital;
	private int idHealthClub;
	//not persisted
	private SecretKey key;
	private Mediator mediator;
	
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
	
	public void setMediator(Mediator m) {
		this.mediator = m;
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

	public void setKey(SecretKey key) {
		this.key = key;
	}

	public SecretKey getKey() {
		return key;
	}

	public String getAttributesAsString() {
		return key.getComponentsAsString();
	}

	private List<String> getAttributes() {
		return key.getComponents();
	}

	public boolean canDecrypt(String policy) {
		Node pol = Node.deserializeOrPolicy(policy);
		System.out.println(getAttributes());
		Set<String> minimalAttrSet = pol.getMinimalAttrSet(getAttributes());
		System.out.println(minimalAttrSet);
		return minimalAttrSet != null;
	}

	public String decrypt(String ctAsString, String policy, PublicKey pk) {
		Ciphertext ct = new Ciphertext(ctAsString, pk.G0, pk.G1);
		Node pol = Node.deserializeOrPolicy(policy);
		List<String> minimalAttrSet = new ArrayList<String>(pol.getMinimalAttrSet(getAttributes()));
		Element cMed = mediator.mDecrypt(ct, minimalAttrSet, "D" + getId());
		String msg = MCPABEHelper.decrypt(minimalAttrSet, ct, cMed, key, pk);
		return msg;
	}
	
}
