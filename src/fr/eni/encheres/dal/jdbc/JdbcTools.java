package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import fr.eni.encheres.dal.ConnectionProvider;

public class JdbcTools {

	public static Connection getConnection() throws SQLException {

		return ConnectionProvider.getConnection();

	}
}
