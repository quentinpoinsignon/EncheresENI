package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
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

	private final String SELECT_ALL_ARTICLE = "SELECT TOP 3 utl.pseudo,ctgr.libelle,artvd.nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente FROM ARTICLES_VENDUS artvd\r\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\r\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\r\n"
			+ "WHERE vente_effectuee = 0\r\n" + "ORDER BY date_debut_encheres DESC";

	/**
	 * @author jarrigon2020
	 * @return listeArticles -> Objet de type List contenant des Objets de type
	 *         Article
	 * 
	 * @Commentaire Cette m�thode permet de r�cup�rer l'ensemble des articles
	 *              pr�sents dans la base de donn�es et limite le r�sultat aux trois
	 *              derniers enregistr�s
	 * 
	 */

	@Override
	public List<Article> selectAllArticle() throws Exception {

		ResultSet myResultset = null;
		Utilisateur user = null;
		Article article = null;
		Categorie categorie = null;
		List<Article> listeArticles = new ArrayList<Article>();

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(SELECT_ALL_ARTICLE)) {

			myResultset = preparedStatement.executeQuery();

			while (myResultset.next()) {

				// Utilisateur
				String userPseudo = myResultset.getString(1);

				user = new Utilisateur(userPseudo);

				// Cat�gorie
				String categorieLibelle = myResultset.getString(2);

				categorie = new Categorie(categorieLibelle);

				// Articles
				String articleName = myResultset.getString(3);
				String desription = myResultset.getString(4);
				Date dateDebut = myResultset.getDate(5);
				Date dateFin = myResultset.getDate(6);
				int prixInitial = myResultset.getInt(7);
				int prixVente = myResultset.getInt(8);

				article = new Article(articleName, desription, dateDebut, dateFin, prixInitial, prixVente, user,
						categorie);

				listeArticles.add(article);

			}

			myResultset.close();

		} catch (SQLException e) {

			throw new Exception(e.getMessage());

		}
		return listeArticles;
	}

}
