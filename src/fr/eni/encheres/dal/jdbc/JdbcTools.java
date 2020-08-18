package fr.eni.encheres.dal.jdbc;

import java.sql.SQLException;

import fr.eni.encheres.dal.ConnectionProvider;

public class JdbcTools {

	/**
	 * Permet d'aller récupérer la méthode getConnection de la class
	 * ConnectionProvider afin de nous retourner une connexion
	 * 
	 * @throws SQLException
	 */
	public static void getConnection() throws SQLException {

		ConnectionProvider.getConnection();

	}
}
