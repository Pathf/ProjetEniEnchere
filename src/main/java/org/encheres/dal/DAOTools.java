package org.encheres.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOTools {
	private static String urldb;
	private static String userdb;
	private static String passworddb;

	static {
		try {
			Class.forName(Settings.getProperties("driverdb"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		urldb = Settings.getProperties("urldb");
		userdb = Settings.getProperties("userdb");
		passworddb = Settings.getProperties("passworddb");

		System.out.println("--------------------------");
		System.out.println("Connexion à la base de donnée CHIFUMI");
		System.out.println("----");
		System.out.println("Url : " + urldb);
		System.out.println("Identifiant utilisé : " + userdb);
		System.out.println("--------------------------");
	}

	public static Connection getConnection() throws SQLException {
		return  DriverManager.getConnection(urldb,userdb,passworddb);
	}
}
