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
 *         Cette classe permet d'établir une connexion avec la base de données
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
			throw new RuntimeException("Impossible d'accéder à la base de données");
		}

	}

	/**
	 * 
	 * @return Permet de retourner la connexion à la base de données
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {

		return dataSource.getConnection();
	}

}