package org.encheres.dal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.sql.DataSource;

public class DAOTools {
	private static DataSource dataSource;

	static {
		System.out.println("--------------------------");
		System.out.print("Tentative de connexion à la base de donnée : ");
		try {
			Context context = new InitialDirContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
			System.out.println("Ok !");
			System.out.println("-------------");
			System.out.println("Connexion à la base de donnée TROCENCHERES");
			
		} catch (NamingException e) {
			System.out.println("Erreur !");
			e.printStackTrace();
		}
		System.out.println("--------------------------");
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
