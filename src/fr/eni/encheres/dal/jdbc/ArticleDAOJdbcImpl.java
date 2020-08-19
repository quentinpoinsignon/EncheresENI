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
 * @commentaire Cette classe contient les diff�rentes m�thodes permettant de
 *              r�aliser les requ�te sql concernant lees articles vendus sur
 *              l'application
 */

public class ArticleDAOJdbcImpl implements ArticleDAO {

	/**
	 * @Constante SELECT_ALL_ARTICLE -> Chaine de caract�re contenant une requ�te
	 *            SQL permettant de r�cup�rer les trois derniers articles
	 *            enregistr�s dans la base de donn�es ainsi que l'utilisateur les
	 *            ayant mis en vente et la cat�gorie � laquelle ils appartiennent
	 */

	private final String SELECT_ALL_ARTICLE = "SELECT TOP 3 utl.pseudo,utl.nom,ctgr.libelle,artvd.nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente FROM ARTICLES_VENDUS artvd\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "ORDER BY date_debut_encheres DESC";

	/**
	 * @author jarrigon2020
	 * @return liste -> Objet de type List contenant des Objets de type Article
	 * 
	 *         Cette m�thode permet de r�cup�rer l'ensemble des articles pr�sents
	 *         dans la base de donn�es et limite le r�sultat aux trois derniers
	 *         enregistr�s
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
