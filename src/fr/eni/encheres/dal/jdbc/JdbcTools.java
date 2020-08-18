package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import fr.eni.encheres.dal.ConnectionProvider;

public class JdbcTools {

	/**
	 * Permet d'aller récupérer la méthode getConnection de la class
	 * ConnectionProvider afin de nous retourner une connexion
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {

		return ConnectionProvider.getConnection();

	}
}
