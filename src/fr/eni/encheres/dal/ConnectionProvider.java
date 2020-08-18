package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 
 * @author jarrigon2020
 * 
 *         Cette classe permet d'�tablir une connexion avec la base de donn�es
 * @Param datasource Permet d'aller chercher les informations de connexion dans
 *        le fichier xml
 */

public abstract class ConnectionProvider {

	private static DataSource dataSource;

	static {
		try {
			Context context = new InitialContext();
			Context envContext = (Context) context.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("/jdbc/pool_cnx");
		} catch (NamingException e) {
			throw new RuntimeException("Impossible d'acc�der �la base de donn�es");
		}

	}

	/**
	 * 
	 * @return Permet de retourner la connexion � la base de donn�es
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {

		return dataSource.getConnection();
	}

}