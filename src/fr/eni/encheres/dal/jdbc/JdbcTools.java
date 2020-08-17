package fr.eni.encheres.dal.jdbc;

import java.sql.SQLException;

import fr.eni.encheres.dal.ConnectionProvider;

public class JdbcTools {

	public static void getConnection() throws SQLException {

		ConnectionProvider.getConnection();

	}
}
