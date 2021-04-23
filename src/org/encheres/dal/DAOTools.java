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
		try {
			Context context = new InitialDirContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------");
		System.out.println("Connexion à la base de donnée TROCENCHERES");
		System.out.println("--------------------------");
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
