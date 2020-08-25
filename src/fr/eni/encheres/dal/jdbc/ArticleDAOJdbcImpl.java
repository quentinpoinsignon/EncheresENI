package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * @commentaire Cette classe contient les diffï¿½rentes mï¿½thodes permettant de
 *              rï¿½aliser les requï¿½te sql concernant lees articles vendus sur
 *              l'application
 */

public class ArticleDAOJdbcImpl implements ArticleDAO {

	/**
	 * @Constante SELECT_ALL_ARTICLE_TOP3 -> Chaine de caractï¿½res contenant une
	 *            requï¿½te SQL permettant de rï¿½cupï¿½rer les trois derniers
	 *            articles enregistrï¿½s dans la base de donnï¿½es ainsi que
	 *            l'utilisateur les ayant mis en vente et la catï¿½gorie ï¿½
	 *            laquelle ils appartiennent
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
	 * @Constante SELECT_ALL_ARTICLE -> Chaine de caractï¿½res contenant une
	 *            requï¿½te SQL permettant de rï¿½cupï¿½rer l'ensemble des articles
	 *            enregistrï¿½s dans la base de donnï¿½es ainsi que l'utilisateur
	 *            les ayant mis en vente et la catï¿½gorie ï¿½ laquelle ils
	 *            appartiennent
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
	 * @Constante INSERT_NEW_ARTICLE -> Chaine de caractï¿½res contenant une
	 *            requï¿½te SQL permettant d'enregister un nouvel article dans la
	 *            base de donnï¿½es
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
	 * @Commentaire Cette mï¿½thode permet de rï¿½cupï¿½rer l'ensemble des articles
	 *              prï¿½sents dans la base de donnï¿½es
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

				// Catï¿½gorie
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
	 * @Commentaire Cette mï¿½thode permet de rï¿½cupï¿½rer l'ensemble des articles
	 *              prï¿½sents dans la base de donnï¿½es et limite le rï¿½sultat aux
	 *              trois derniers enregistrï¿½s
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

				// Catï¿½gorie
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
	 * @Commentaire Cette mï¿½thode permet d'enregistrer un nouvel article dans la
	 *              base de donnï¿½es
	 */
	@Override
	public void insertNewArticle(Article newArticle, String street, String town, String postalCode) throws Exception {

		String streetRetrait = street;
		String townRetrait = town;
		String postalCodeRetrait = postalCode;

		ResultSet generatedKey;
		int idArticle;

		// On enregistre le nouvel article dans la base de donnï¿½es

		int idUserOfArticle = newArticle.getUtilisateur().getNoUtilisateur();
		int idCategorieOfArticle = newArticle.getCategorie().getNoCategorie();

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(INSERT_NEW_ARTICLE,
						Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, newArticle.getNomArticle());
			preparedStatement.setString(2, newArticle.getDescription());
			preparedStatement.setDate(3, newArticle.getDateDebutEncheres());
			preparedStatement.setDate(4, newArticle.getDateFinEncheres());
			preparedStatement.setInt(5, newArticle.getPrixInitial());
			preparedStatement.setInt(6, 0); // Le 0 correspond ï¿½ la valeur par default du prix de vente car aucune
											// enchï¿½re n'a encore pu ï¿½tre effectuï¿½e pour cet article.
			preparedStatement.setInt(7, idUserOfArticle);
			preparedStatement.setInt(7, idCategorieOfArticle);
			preparedStatement.setBoolean(7, newArticle.getVenteEffectuee());

			preparedStatement.executeUpdate();

			generatedKey = preparedStatement.getGeneratedKeys();

			idArticle = generatedKey.getInt(1);

			if (generatedKey != null) {

				// On appelle une fonction permettant d'enregistrer les
				// informations liées aux point de retrait de l'article dans la table RETRAITS

				RetraitDAO DAORetrait = DAOFactory.getRetraitDAO();
				DAORetrait.insertWithdrawalPoint(idArticle, streetRetrait, townRetrait, postalCodeRetrait);
			}

		}

		catch (SQLException e) {

			throw new Exception(e.getMessage());

		}

	}

}
