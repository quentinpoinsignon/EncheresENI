package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.interfaces.ArticleDAO;

public class ArticleDAOJdbcImpl implements ArticleDAO {

	private final String SELECT_ALL_ARTICLE = "";

	@Override
	public List<Article> selectAllArticle() throws SQLException {

		Statement request = null;
		ResultSet myResultset = null;

		List<Article> liste = new ArrayList<Article>();

		try (Connection databaseconnexion = JdbcTools.getConnection();) {

			request = databaseconnexion.createStatement();
			myResultset = request.executeQuery(SQL_SELECT_ALL);
			Article art = null;

			while (rs.next()) {

			}

		}
		liste.add(art);
	}}catch(SQLException e)
	{
			throw new Exception(e.getMessage());
		}return liste;

	return null;
}

}
