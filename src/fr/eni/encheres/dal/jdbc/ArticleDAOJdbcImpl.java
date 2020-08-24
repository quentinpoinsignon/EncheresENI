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
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.interfaces.ArticleDAO;
import fr.eni.encheres.dal.interfaces.RetraitDAO;

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
	 * @Constante SELECT_ALL_ARTICLE_TOP3 -> Chaine de caractères contenant une
	 *            requête SQL permettant de récupérer les trois derniers articles
	 *            enregistrés dans la base de données ainsi que l'utilisateur les
	 *            ayant mis en vente et la catégorie à laquelle ils appartiennent
	 * 
	 * @value "SELECT TOP 3
	 *        utl.pseudo,ctgr.libelle,ctgr.no_categorie,artvd.nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente
	 *        FROM ARTICLES_VENDUS artvd\r\n" + "INNER JOIN UTILISATEURS utl ON
	 *        utl.no_utilisateur = artvd.no_utilisateur\r\n" + "INNER JOIN
	 *        CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\r\n" +
	 *        "WHERE vente_effectuee = 0\r\n" + "ORDER BY date_debut_encheres DESC";
	 */

	private final String SELECT_ALL_ARTICLE_TOP3 = "SELECT TOP 3 utl.pseudo,ctgr.libelle,ctgr.no_categorie,artvd.nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente FROM ARTICLES_VENDUS artvd\r\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\r\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\r\n"
			+ "WHERE vente_effectuee = 0\r\n" + "ORDER BY date_debut_encheres DESC";

	/**
	 * @Constante SELECT_ALL_ARTICLE -> Chaine de caractères contenant une requête
	 *            SQL permettant de récupérer l'ensemble des articles enregistrés
	 *            dans la base de données ainsi que l'utilisateur les ayant mis en
	 *            vente et la catégorie à laquelle ils appartiennent
	 * 
	 * @value "SELECT utl.pseudo, ctg.libelle, nom_article, description,
	 *        date_debut_encheres,date_fin_encheres,prix_initial,prix_vente FROM
	 *        ARTICLES_VENDUS artvd\r\n" + "INNER JOIN UTILISATEURS utl ON
	 *        artvd.no_utilisateur = utl.no_utilisateur\r\n" + "INNER JOIN
	 *        CATEGORIES ctg ON artvd.no_categorie = ctg.no_categorie";
	 */
	private final String SELECT_ALL_ARTICLE = "SELECT utl.pseudo, ctg.libelle, ctg.no_categorie, nom_article, description, date_debut_encheres,date_fin_encheres,prix_initial,prix_vente FROM ARTICLES_VENDUS artvd\r\n"
			+ "INNER JOIN UTILISATEURS utl ON artvd.no_utilisateur = utl.no_utilisateur\r\n"
			+ "INNER JOIN CATEGORIES ctg ON artvd.no_categorie = ctg.no_categorie";

	/**
	 * @Constante INSERT_NEW_ARTICLE -> Chaine de caractères contenant une requête
	 *            SQL permettant d'enregister un nouvel article dans la base de
	 *            données
	 * 
	 * @value "INSERT INTO
	 *        ARTICLES_VENDUS(nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie,vente_effectuee)
	 *        \r\n" + "VALUES (?,?,?,?,?,?,?,?,?)";
	 * 
	 */
	private final String INSERT_NEW_ARTICLE = "INSERT INTO ARTICLES_VENDUS(nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie,vente_effectuee) \r\n"
			+ "VALUES (?,?,?,?,?,?,?,?,?)";

	

	/**
	 * @author jarrigon2020
	 * @return listeArticles -> Objet de type List contenant des Objets de type
	 *         Article
	 * 
	 * @Commentaire Cette méthode permet de récupérer l'ensemble des articles
	 *              présents dans la base de données
	 * 
	 */

	@Override
	public List<Article> selectAllArticleInDatabase() throws Exception {

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

				// Catégorie
				String categorieLibelle = myResultset.getString(2);
				int idCategorie = myResultset.getInt(3);

				categorie = new Categorie(idCategorie, categorieLibelle);

				// Articles
				String articleName = myResultset.getString(4);
				String desription = myResultset.getString(5);
				Date dateDebut = myResultset.getDate(6);
				Date dateFin = myResultset.getDate(7);
				int prixInitial = myResultset.getInt(8);
				int prixVente = myResultset.getInt(9);

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

	/**
	 * @author jarrigon2020
	 * @return listeArticles -> Objet de type List contenant des Objets de type
	 *         Article
	 * 
	 * @Commentaire Cette méthode permet de récupérer l'ensemble des articles
	 *              présents dans la base de données et limite le résultat aux trois
	 *              derniers enregistrés
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
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(SELECT_ALL_ARTICLE_TOP3)) {

			myResultset = preparedStatement.executeQuery();

			while (myResultset.next()) {

				// Utilisateur
				String userPseudo = myResultset.getString(1);

				user = new Utilisateur(userPseudo);

				// Catégorie
				String categorieLibelle = myResultset.getString(2);
				int idCategorie = myResultset.getInt(3);

				categorie = new Categorie(idCategorie, categorieLibelle);

				// Articles
				String articleName = myResultset.getString(4);
				String desription = myResultset.getString(5);
				Date dateDebut = myResultset.getDate(6);
				Date dateFin = myResultset.getDate(7);
				int prixInitial = myResultset.getInt(8);
				int prixVente = myResultset.getInt(9);

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

	/**
	 * @author jarrigon2020
	 * 
	 * @param newArticle -> Objet de type Article
	 * @throws Exception
	 * 
	 * @Commentaire Cette méthode permet d'enregistrer un nouvel article dans la
	 *              base de données
	 */
	@Override
	public void insertNewArticle(Article newArticle) throws Exception {

		// On commence par appeler une fonction permettant d'enregistrer les
		// informations liées aux point de retrait de l'article dans la table RETRAITS

		RetraitDAO DAORetrait = DAOFactory.getRetraitDAO();
		DAORetrait.insertWithdrawalPoint(newArticle);

		// On enregistre ensuite le nouvel article dans la base de données

		int idUserOfArticle = newArticle.getUtilisateur().getNoUtilisateur();
		int idCategorieOfArticle = newArticle.getCategorie().getNoCategorie();

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(INSERT_NEW_ARTICLE)) {

			preparedStatement.setString(1, newArticle.getNomArticle());
			preparedStatement.setString(2, newArticle.getDescription());
			preparedStatement.setDate(3, newArticle.getDateDebutEncheres());
			preparedStatement.setDate(4, newArticle.getDateFinEncheres());
			preparedStatement.setInt(5, newArticle.getPrixInitial());
			preparedStatement.setInt(6, 0); // Le 0 correspond à la valeur par default du prix de vente car aucune
											// enchère n'a encore pu être effectuée pour cet article.
			preparedStatement.setInt(7, idUserOfArticle);
			preparedStatement.setInt(7, idCategorieOfArticle);
			preparedStatement.setBoolean(7, newArticle.getVenteEffectuee());

			preparedStatement.executeUpdate();

		}

		catch (SQLException e) {

			throw new Exception(e.getMessage());

		}

	}

	

}
