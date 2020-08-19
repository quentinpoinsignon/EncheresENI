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
 * @commentaire Cette classe contient les différentes méthodes permettant de
 *              réaliser les requête sql concernant lees articles vendus sur
 *              l'application
 */

public class ArticleDAOJdbcImpl implements ArticleDAO {

	/**
	 * @Constante SELECT_ALL_ARTICLE -> Chaine de caractère contenant une requête
	 *            SQL permettant de récupérer les trois derniers articles
	 *            enregistrés dans la base de données ainsi que l'utilisateur les
	 *            ayant mis en vente et la catégorie à laquelle ils appartiennent
	 */

	private final String SELECT_ALL_ARTICLE = "SELECT TOP 3 utl.pseudo,utl.nom,ctgr.libelle,artvd.nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente FROM ARTICLES_VENDUS artvd\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "ORDER BY date_debut_encheres DESC";

	/**
	 * @author jarrigon2020
	 * @return liste -> Objet de type List contenant des Objets de type Article
	 * 
	 *         Cette méthode permet de récupérer l'ensemble des articles présents
	 *         dans la base de données et limite le résultat aux trois derniers
	 *         enregistrés
	 * 
	 */

	@Override
	public List<Article> selectAllArticle() throws Exception {

		Statement request = null;
		ResultSet myResultset = null;

		List<Article> liste = new ArrayList<Article>();

		try (Connection databaseConnexion = JdbcTools.getConnection();) {

			request = databaseConnexion.createStatement();
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
