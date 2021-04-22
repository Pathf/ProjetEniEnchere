package org.encheres.dal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.sql.DataSource;

public class DAOTools {
	private static DataSource dataSource;
	private static String urldb;
	private static String userdb;
	//private static String passworddb;

	static {
		try {
			Context context = new InitialDirContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		/*try {
			Class.forName(Settings.getProperties("driverdb"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/
		urldb = Settings.getProperties("urldb");
		userdb = Settings.getProperties("userdb");
		//passworddb = Settings.getProperties("passworddb");

		System.out.println("--------------------------");
		System.out.println("Connexion à la base de donnée TROCENCHERES");
		System.out.println("----");
		System.out.println("Url : " + urldb);
		System.out.println("Identifiant utilisé : " + userdb);
		System.out.println("--------------------------");
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	/*public static Connection getConnection() throws SQLException {
		return  DriverManager.getConnection(urldb,userdb,passworddb);
	}*/
}
