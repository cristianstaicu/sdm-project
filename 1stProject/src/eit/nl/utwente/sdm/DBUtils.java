package eit.nl.utwente.sdm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

	
}
