package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.interfaces.ArticleDAO;

/**
 * 
 * @author jarrigon2020
 * 
 *         Cette classe contient les différentes méthodes permettant de réaliser
 *         les requête sql concernant lees articles vendus sur l'application
 */

public class ArticleDAOJdbcImpl implements ArticleDAO {

	private final String SELECT_ALL_ARTICLE = "SELECT no_article,nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial_prix_vente,no_utilisateur,no_categorie,vente_effectuee FROM ARTICLES_VENDUS";

	/**
	 * @author jarrigon2020
	 * @return liste -> Objet de type List contenant des Objets de type Article
	 * 
	 *         Cette méthode permet de récupérer l'ensemble des articles présents
	 *         dans la base de données
	 * 
	 */

	@Override
	public List<Article> selectAllArticle() throws Exception {

		Statement request = null;
		ResultSet myResultset = null;

		List<Article> liste = new ArrayList<Article>();

		try (Connection databaseconnexion = JdbcTools.getConnection();) {

			request = databaseconnexion.createStatement();
			myResultset = request.executeQuery(SELECT_ALL_ARTICLE);
			Article article = null;

			while (myResultset.next()) {

				liste.add(article);

			}
		} catch (SQLException e) {

			throw new Exception(e.getMessage());
		}

		return liste;

	}

}
