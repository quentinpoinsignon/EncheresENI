package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interfaces.EnchereDAO;

/**
 * 
 * @author jarrigon2020
 * 
 * @Commentaire Cette classe contient les diff�rentes m�thodes permettant de
 *              r�aliser les requ�te sql concernant les ench�res
 *              effectu�es sur l'application
 *
 */

public class EnchereDAOJdbcImpl implements EnchereDAO {

	/**
	 * @Constante ARTICLE_SEARCH_BY_USER_REQUEST -> Chaine de caract�res contenant
	 *            une requ�te SQL effectuant une recherche dans la table enchere
	 *            permettant de r�cup�rer une liste d'encheres correspondant �
	 *            ce que l'utilisateur a rentr� dans le champs de recherche ainsi
	 *            que la cat�gorie qu'il a s�lectionn�
	 * 
	 * @value "SELECT ctgr.no_categorie, ctgr.libelle, nom_article,
	 *        description,date_debut_encheres,date_fin_encheres, prix_initial,
	 *        prix_vente,utl2.pseudo as vendeur, artvd.no_article\r\n" + "FROM
	 *        ARTICLES_VENDUS artvd \r\n" + "INNER JOIN UTILISATEURS utl2 ON
	 *        utl2.no_utilisateur = artvd.no_utilisateur\r\n" + "INNER JOIN
	 *        CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\r\n" +
	 *        "WHERE nom_article LIKE TRIM(?) AND vente_effectuee = 0 AND
	 *        artvd.date_fin_encheres > GETDATE() AND artvd.date_debut_encheres <
	 *        GETDATE()\r\n" + "ORDER BY artvd.date_fin_encheres ASC";
	 */
	private final String ARTICLE_SEARCH_BY_USER_REQUEST_ALL_AUCTION = "SELECT ctgr.no_categorie, ctgr.libelle, nom_article, description,date_debut_encheres,date_fin_encheres, prix_initial, prix_vente,utl2.pseudo as vendeur, artvd.no_article\r\n"
			+ "FROM ARTICLES_VENDUS artvd \r\n"
			+ "INNER JOIN UTILISATEURS utl2 ON utl2.no_utilisateur = artvd.no_utilisateur\r\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\r\n"
			+ "WHERE nom_article LIKE TRIM(?) AND vente_effectuee = 0 AND artvd.date_fin_encheres > GETDATE() AND artvd.date_debut_encheres < GETDATE()\r\n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * @Constante ARTICLE_SEARCH_BY_USER_REQUEST_OPEN_AUCTION -> Chaine de
	 *            caract�res contenant une requ�te SQL effectuant une recherche
	 *            dans la table ARTICLE_VENDUS permettant de r�cup�rer une liste
	 *            d'encheres correspondant � ce que l'utilisateur a rentr� dans
	 *            le champs de recherche, la cat�gorie qu'il a s�lectionn�, si
	 *            le bouton radio achat est s�lectionn� et que seule la checkbox
	 *            encheres ouvertes est coch�e
	 * 
	 * @Value "SELECT ctgr.no_categorie, ctgr.libelle, nom_article,
	 *        description,date_debut_encheres,date_fin_encheres, prix_initial,
	 *        prix_vente,utl2.pseudo as vendeur, artvd.no_article\n" + "FROM
	 *        ARTICLES_VENDUS artvd \n" + "INNER JOIN UTILISATEURS utl2 ON
	 *        utl2.no_utilisateur = artvd.no_utilisateur\n" + "INNER JOIN CATEGORIES
	 *        ctgr ON ctgr.no_categorie = artvd.no_categorie\n" + "WHERE nom_article
	 *        LIKE TRIM(?) AND date_debut_encheres < GETDATE() And date_fin_encheres
	 *        > GETDATE() AND vente_effectuee = 0\n" + "ORDER BY
	 *        artvd.date_fin_encheres ASC";
	 */

	private final String ARTICLE_SEARCH_BY_USER_REQUEST_OPEN_AUCTION = "SELECT ctgr.no_categorie, ctgr.libelle, nom_article, description,date_debut_encheres,date_fin_encheres, prix_initial, prix_vente,utl2.pseudo as vendeur, artvd.no_article\n"
			+ "FROM ARTICLES_VENDUS artvd \n"
			+ "INNER JOIN UTILISATEURS utl2 ON utl2.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM(?)  AND date_debut_encheres < GETDATE() And date_fin_encheres > GETDATE() AND vente_effectuee = 0\n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * Constante ARTICLE_SEARCH_BY_USER_REQUEST_MY_AUCTION -> Chaine de caract�res
	 * contenant une requ�te SQL effectuant une recherche dans la table
	 * ARTICLE_VENDUS permettant de r�cup�rer une liste d'encheres correspondant
	 * � ce que l'utilisateur a rentr� dans le champs de recherche, la
	 * cat�gorie qu'il a s�lectionn�, si le bouton radio achat est
	 * s�lectionn� et que seule la checkbox mes encheres est coch�e
	 * 
	 * @value "SELECT utl.pseudo as acheteur, ctgr.no_categorie, ctgr.libelle,
	 *        nom_article, description,date_debut_encheres,date_fin_encheres,
	 *        prix_initial, prix_vente,utl2.pseudo as vendeur,
	 *        ench.montant_enchere,ench.date_enchere, artvd.no_article\n" + "FROM
	 *        ARTICLES_VENDUS artvd \n" + "INNER JOIN ENCHERES ench ON
	 *        ench.no_article = artvd.no_article\n" + "INNER JOIN UTILISATEURS utl2
	 *        ON utl2.no_utilisateur = artvd.no_utilisateur\n" + "INNER JOIN
	 *        UTILISATEURS utl ON utl.no_utilisateur = ench.no_utilisateur\n" +
	 *        "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie =
	 *        artvd.no_categorie\n" + "WHERE nom_article LIKE TRIM(?) AND
	 *        date_debut_encheres < GETDATE() And date_fin_encheres > GETDATE() AND
	 *        vente_effectuee = 0 AND utl.pseudo = ? \n" + "ORDER BY
	 *        artvd.date_fin_encheres ASC";
	 */

	private final String ARTICLE_SEARCH_BY_USER_REQUEST_MY_AUCTION = "SELECT utl.pseudo as acheteur, ctgr.no_categorie, ctgr.libelle, nom_article, description,date_debut_encheres,date_fin_encheres, prix_initial, prix_vente,utl2.pseudo as vendeur, ench.montant_enchere,ench.date_enchere, artvd.no_article\n"
			+ "FROM ARTICLES_VENDUS artvd \n" + "INNER JOIN ENCHERES ench  ON ench.no_article = artvd.no_article\n"
			+ "INNER JOIN UTILISATEURS utl2 ON utl2.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = ench.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM(?)  AND date_debut_encheres < GETDATE() And date_fin_encheres > GETDATE() AND vente_effectuee = 0 AND utl.pseudo = ? \n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * @Contante ARTICLE_SEARCH_BY_USER_REQUEST_WIN_AUCTION -> Chaine de
	 *           caract�res contenant une requ�te SQL effectuant une recherche
	 *           dans la table ARTICLE_VENDUS permettant de r�cup�rer une liste
	 *           d'encheres correspondant � ce que l'utilisateur a rentr� dans
	 *           le champs de recherche, la cat�gorie qu'il a s�lectionn�, si
	 *           le bouton radio achat est s�lectionn� et que seule la checkbox
	 *           mes encheres remport�es est coch�e
	 * 
	 * @value "SELECT utl.pseudo as acheteur, ctgr.no_categorie, ctgr.libelle,
	 *        nom_article, description, prix_initial, prix_vente,utl2.pseudo as
	 *        vendeur, ench.montant_enchere, MAX(ench.date_enchere) as dateEnchere,
	 *        artvd.no_article\n" + "FROM ARTICLES_VENDUS artvd\n" + "INNER JOIN
	 *        ENCHERES ench ON ench.no_article = artvd.no_article\n" + "INNER JOIN
	 *        UTILISATEURS utl2 ON utl2.no_utilisateur = artvd.no_utilisateur\n" +
	 *        "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur =
	 *        ench.no_utilisateur\n" + "INNER JOIN CATEGORIES ctgr ON
	 *        ctgr.no_categorie = artvd.no_categorie\n" + "WHERE nom_article LIKE
	 *        TRIM(?) AND vente_effectuee = 0 AND utl.pseudo = ?\n" + "GROUP BY
	 *        utl.pseudo,ctgr.no_categorie,ctgr.libelle,nom_article, description,
	 *        prix_initial, prix_vente,utl2.pseudo, ench.montant_enchere,
	 *        ench.date_enchere,artvd.date_fin_encheres \n" + "ORDER BY
	 *        artvd.date_fin_encheres ASC";
	 */
	private final String ARTICLE_SEARCH_BY_USER_REQUEST_WIN_AUCTION = "SELECT utl.pseudo as acheteur, ctgr.no_categorie, ctgr.libelle, nom_article, description, prix_initial, prix_vente,utl2.pseudo as vendeur, ench.montant_enchere, MAX(ench.date_enchere) as dateEnchere, artvd.no_article\n"
			+ "FROM  ARTICLES_VENDUS artvd\n" + "INNER JOIN ENCHERES ench ON ench.no_article = artvd.no_article\n"
			+ "INNER JOIN UTILISATEURS utl2 ON utl2.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = ench.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM(?) AND vente_effectuee = 0 AND utl.pseudo = ?\n"
			+ "GROUP BY utl.pseudo,ctgr.no_categorie,ctgr.libelle,nom_article, description, prix_initial, prix_vente,utl2.pseudo, ench.montant_enchere, ench.date_enchere,artvd.date_fin_encheres \n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * @Constante ARTICLE_SEARCH_BY_USER_REQUEST_CURRENT_SALES -> Chaine de
	 *            caract�res contenant une requ�te SQL effectuant une recherche
	 *            dans la table ARTICLE_VENDUS permettant de r�cup�rer une liste
	 *            d'encheres correspondant � ce que l'utilisateur a rentr� dans
	 *            le champs de recherche, la cat�gorie qu'il a s�lectionn�, si
	 *            le bouton radio achat est s�lectionn� et que seule le bouton
	 *            radio mes ventes en cours est coch�
	 * 
	 * @value "SELECT utl.pseudo, ctgr.no_categorie, ctgr.libelle, nom_article,
	 *        description, prix_initial, prix_vente,artvd.date_debut_encheres,
	 *        artvd.date_fin_encheres, artvd.no_article\n" + "FROM ARTICLES_VENDUS
	 *        artvd\n" + "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur =
	 *        artvd.no_utilisateur\n" + "INNER JOIN CATEGORIES ctgr ON
	 *        ctgr.no_categorie = artvd.no_categorie\n" + "WHERE nom_article LIKE
	 *        TRIM(?) AND artvd.date_debut_encheres <= GETDATE() AND
	 *        artvd.date_fin_encheres > GETDATE() AND vente_effectuee = 0 AND
	 *        utl.pseudo = ?\n" + "ORDER BY artvd.date_fin_encheres ASC";
	 */

	private final String ARTICLE_SEARCH_BY_USER_REQUEST_CURRENT_SALES = "SELECT utl.pseudo, ctgr.no_categorie, ctgr.libelle, nom_article, description, prix_initial, prix_vente,artvd.date_debut_encheres, artvd.date_fin_encheres, artvd.no_article\n"
			+ "FROM ARTICLES_VENDUS artvd\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM(?) AND artvd.date_debut_encheres <= GETDATE() AND artvd.date_fin_encheres > GETDATE() AND vente_effectuee = 0 AND utl.pseudo = ?\n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * @Constante ARTICLE_SEARCH_BY_USER_REQUEST_NOT_STARTED_SALES -> Chaine de
	 *            caract�res contenant une requ�te SQL effectuant une recherche
	 *            dans la table ARTICLE_VENDUS permettant de r�cup�rer une liste
	 *            d'encheres correspondant � ce que l'utilisateur a rentr� dans
	 *            le champs de recherche, la cat�gorie qu'il a s�lectionn�, si
	 *            le bouton radio achat est s�lectionn� et que seule le bouton
	 *            radio mes ventes non d�but�es est coch�
	 * 
	 * @value "SELECT utl.pseudo, ctgr.no_categorie, ctgr.libelle, nom_article,
	 *        description, prix_initial, prix_vente,artvd.date_debut_encheres,
	 *        artvd.date_fin_encheres, artvd.no_article\n" + "FROM ARTICLES_VENDUS
	 *        artvd\n" + "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur =
	 *        artvd.no_utilisateur\n" + "INNER JOIN CATEGORIES ctgr ON
	 *        ctgr.no_categorie = artvd.no_categorie\n" + "WHERE nom_article LIKE
	 *        TRIM(?) AND artvd.date_debut_encheres >= GETDATE() AND vente_effectuee
	 *        = 0 AND utl.pseudo = ?\n" + "ORDER BY artvd.date_fin_encheres ASC";
	 */

	private final String ARTICLE_SEARCH_BY_USER_REQUEST_NOT_STARTED_SALES = "SELECT utl.pseudo, ctgr.no_categorie, ctgr.libelle, nom_article, description, prix_initial, prix_vente,artvd.date_debut_encheres, artvd.date_fin_encheres, artvd.no_article\n"
			+ "FROM ARTICLES_VENDUS artvd\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM(?) AND artvd.date_debut_encheres >= GETDATE() AND vente_effectuee = 0 AND utl.pseudo = ?\n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * @Constante ARTICLE_SEARCH_BY_USER_REQUEST_ENDED_SALES -> Chaine de
	 *            caract�res contenant une requ�te SQL effectuant une recherche
	 *            dans la table ARTICLE_VENDUS permettant de r�cup�rer une liste
	 *            d'encheres correspondant � ce que l'utilisateur a rentr� dans
	 *            le champs de recherche, la cat�gorie qu'il a s�lectionn�, si
	 *            le bouton radio achat est s�lectionn� et que seule le bouton
	 *            radio mes ventes termin�es est coch�
	 * 
	 * @value "SELECT utl.pseudo, ctgr.no_categorie, ctgr.libelle, nom_article,
	 *        description, prix_initial, prix_vente,artvd.date_debut_encheres,
	 *        artvd.date_fin_encheres, artvd.no_article\n" + "FROM ARTICLES_VENDUS
	 *        artvd\n" + "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur =
	 *        artvd.no_utilisateur\n" + "INNER JOIN CATEGORIES ctgr ON
	 *        ctgr.no_categorie = artvd.no_categorie\n" + "WHERE nom_article LIKE
	 *        TRIM(?) AND artvd.date_fin_encheres <= GETDATE() AND utl.pseudo = ?\n"
	 *        + "ORDER BY artvd.date_fin_encheres ASC";
	 */

	private String ARTICLE_SEARCH_BY_USER_REQUEST_ENDED_SALES = "SELECT utl.pseudo, ctgr.no_categorie, ctgr.libelle, nom_article, description, prix_initial, prix_vente,artvd.date_debut_encheres, artvd.date_fin_encheres, artvd.no_article\n"
			+ "FROM ARTICLES_VENDUS artvd\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM(?) AND artvd.date_fin_encheres <= GETDATE()  AND utl.pseudo = ?\n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * @author jarrigon2020
	 * 
	 * @param research        -> Chaine de caract�res correspondant au texte
	 *                        rentr� dans le champs de recherche de la page
	 *                        accueil par l'utilisateur
	 * 
	 * @param ->              Chaine de caract�res correspondant au pseudo de
	 *                        l'utilisateur
	 * 
	 * @param idCategorie     -> Identifiant de la categorie s�lectionn�e
	 * 
	 * @param shoppingChecked -> Boolean permettant de savoir si le bouton radio
	 *                        achat est s�lectionn�
	 * 
	 * @param openAuction     -> Boolean permettant de savoir si la checkbox enchere
	 *                        en cour achat est s�lectionn�e
	 * 
	 * @param winAuction      -> Boolean permettant de savoir si la checkbox
	 *                        encheres remport�es est s�lectionn�e
	 * 
	 * @param myAuction       -> Boolean permettant de savoir si la checkbox mes
	 *                        ench�res est s�lectionn�e
	 * 
	 * @param mySales         -> Boolean permettant de savoir si le bouton radio mes
	 *                        ventes est s�lectionn�
	 * 
	 * @param myCurrentSales  -> Boolean permettant de savoir si la checkbox mes
	 *                        ventes en cours est s�lectionn�e
	 * 
	 * @param notSartedSales  -> Boolean permettant de savoir si la checkbox ventes
	 *                        non d�but�es est s�lectionn�e
	 * 
	 * @param endedSales      -> Boolean permettant de savoir si la checkbox ventes
	 *                        termin�es est s�lectionn�e
	 * 
	 * @return searchResult -> Objet de type ArrayList contenant des objets de type
	 *         Enchere
	 * 
	 * @Commentaire Cette m�thode permet d'effectuer une recherche dans la base de
	 *              donn�es de r�cup�rer des encheres par rapport � la
	 *              chaine de caract�re rentr�e par un utilisateur dans le
	 *              champs "Recherche" et le selecteur de categories de la page
	 *              d'accueil. Nous prendronss �galement en compte les boutons
	 *              radios et les checkbox s�lectionn�es par l'utilisateur
	 * 
	 *              public static <T> T addTo(T e, Collection<T> c){ c.add(e);
	 *              return e; }
	 */
	@Override
	public List<Article> articleSearchByUserRequest(String research, String userPseudo, int idCategorie,
			Boolean openAuction, Boolean winAuction, Boolean myAuction, Boolean myCurrentSales, Boolean notSartedSales,
			Boolean endedSales) throws Exception {

		PreparedStatement preparedStatement;
		ResultSet myResultset = null;

		Enchere enchere = null;
		Utilisateur sellerUser = null;
		Utilisateur buyerUser = null;
		Article article = null;
		Categorie categorie = null;

		List<Article> searchResult = new ArrayList<Article>();

		/**
		 * Nous allons v�rifier les champs coch�s par l'utilisateur gr�ce � des
		 * booleens et adapter la requ�te SQL en fonction
		 */

		/**
		 * ENCHERES OUVERTES
		 */
		if (openAuction) {

			Connection databaseConnection = JdbcTools.getConnection();
			preparedStatement = databaseConnection.prepareStatement(ARTICLE_SEARCH_BY_USER_REQUEST_OPEN_AUCTION);

			preparedStatement.setString(1, "%" + research + "%");

			myResultset = preparedStatement.executeQuery();

			while (myResultset.next()) {

				// Objet Categorie

				int noCategorie = myResultset.getInt(1);
				String libelleCategorie = myResultset.getString(2);

				categorie = new Categorie(noCategorie, libelleCategorie);

				// Objet Article

				String articleName = myResultset.getString(3);
				String description = myResultset.getString(4);
				Date startOfAuction = myResultset.getDate(5);
				Date endOfAuction = myResultset.getDate(6);
				int initialPrice = myResultset.getInt(7);
				int soldPrice = myResultset.getInt(8);
				int idArticle = myResultset.getInt(10);

				// Objets Utilisateur

				String seller = myResultset.getString(9); // VENDEUR
				sellerUser = new Utilisateur(seller);

				article = new Article(idArticle, articleName, description, startOfAuction, endOfAuction, initialPrice,
						soldPrice, sellerUser, categorie);

				searchResult.add(article);
			}

			/**
			 * MES ENCHERES
			 */

		} else if (myAuction) {

			Connection databaseConnection = JdbcTools.getConnection();
			preparedStatement = databaseConnection.prepareStatement(ARTICLE_SEARCH_BY_USER_REQUEST_MY_AUCTION);

			preparedStatement.setString(1, "%" + research + "%");
			preparedStatement.setString(2, userPseudo);

			myResultset = preparedStatement.executeQuery();

			while (myResultset.next()) {

				// Objets Utilisateur

				String Buyer = myResultset.getString(1); // ACHETEUR
				buyerUser = new Utilisateur(Buyer);

				String seller = myResultset.getString(10); // VENDEUR
				sellerUser = new Utilisateur(seller);

				// Objet Categorie

				int noCategorie = myResultset.getInt(2);
				String libelleCategorie = myResultset.getString(3);

				categorie = new Categorie(noCategorie, libelleCategorie);

				// Objet Article

				String articleName = myResultset.getString(4);
				String description = myResultset.getString(5);
				Date startOfAuction = myResultset.getDate(6);
				Date endOfAuction = myResultset.getDate(7);
				int initialPrice = myResultset.getInt(8);
				int soldPrice = myResultset.getInt(9);
				int idArticle = myResultset.getInt(13);

				// Objet Enchere

				Date auctionDate = myResultset.getDate(11);
				int auctionAmount = myResultset.getInt(12);

				enchere = new Enchere(auctionDate, auctionAmount, buyerUser, sellerUser);

				article = new Article(idArticle, articleName, description, startOfAuction, endOfAuction, initialPrice,
						soldPrice, sellerUser, categorie, enchere);

				searchResult.add(article);
			}

			/**
			 * ENCHERES REMPORTEES
			 */

		} else if (winAuction) {

			Connection databaseConnection = JdbcTools.getConnection();
			preparedStatement = databaseConnection.prepareStatement(ARTICLE_SEARCH_BY_USER_REQUEST_WIN_AUCTION);

			preparedStatement.setString(1, "%" + research + "%");
			preparedStatement.setString(2, userPseudo);

			myResultset = preparedStatement.executeQuery();

			while (myResultset.next()) {

				// Objets Utilisateur

				String Buyer = myResultset.getString(1); // ACHETEUR
				buyerUser = new Utilisateur(Buyer);

				String seller = myResultset.getString(8); // VENDEUR
				sellerUser = new Utilisateur(seller);

				// Objet Categorie

				int noCategorie = myResultset.getInt(2);
				String libelleCategorie = myResultset.getString(3);

				categorie = new Categorie(noCategorie, libelleCategorie);

				// Objet Article

				String articleName = myResultset.getString(4);
				String description = myResultset.getString(5);
				int initialPrice = myResultset.getInt(6);
				int soldPrice = myResultset.getInt(7);
				int idArticle = myResultset.getInt(11);

				// Objet Enchere

				int auctionAmount = myResultset.getInt(9);
				Date auctionDate = myResultset.getDate(10);

				enchere = new Enchere(auctionDate, auctionAmount, buyerUser, sellerUser);

				article = new Article(idArticle, articleName, description, initialPrice, soldPrice, sellerUser,
						categorie, enchere);

				searchResult.add(article);
			}

		}

		/**
		 * VENTE EN COURS
		 */

		else if (myCurrentSales) {

			Connection databaseConnection = JdbcTools.getConnection();
			preparedStatement = databaseConnection.prepareStatement(ARTICLE_SEARCH_BY_USER_REQUEST_CURRENT_SALES);

			preparedStatement.setString(1, "%" + research + "%");
			preparedStatement.setString(2, userPseudo);

			myResultset = preparedStatement.executeQuery();

			while (myResultset.next()) {

				// Objets Utilisateur

				String seller = myResultset.getString(1); // Vendeur
				sellerUser = new Utilisateur(seller);

				// Objet Categorie

				int noCategorie = myResultset.getInt(2);
				String libelleCategorie = myResultset.getString(3);

				categorie = new Categorie(noCategorie, libelleCategorie);

				// Objet Article

				String articleName = myResultset.getString(4);
				String description = myResultset.getString(5);
				int initialPrice = myResultset.getInt(6);
				int soldPrice = myResultset.getInt(7);
				Date startOfAuction = myResultset.getDate(8);
				Date endOfAuction = myResultset.getDate(9);
				int idArticle = myResultset.getInt(10);

				article = new Article(idArticle, articleName, description, startOfAuction, endOfAuction, initialPrice,
						soldPrice, sellerUser, categorie);

				searchResult.add(article);
			}

			/**
			 * VENTES NON DEBUTES
			 */

		} else if (notSartedSales) {

			Connection databaseConnection = JdbcTools.getConnection();
			preparedStatement = databaseConnection.prepareStatement(ARTICLE_SEARCH_BY_USER_REQUEST_NOT_STARTED_SALES);

			preparedStatement.setString(1, "%" + research + "%");
			preparedStatement.setString(2, userPseudo);

			myResultset = preparedStatement.executeQuery();

			while (myResultset.next()) {

				// Objets Utilisateur

				String seller = myResultset.getString(1); // Vendeur
				sellerUser = new Utilisateur(seller);

				// Objet Categorie

				int noCategorie = myResultset.getInt(2);
				String libelleCategorie = myResultset.getString(3);

				categorie = new Categorie(noCategorie, libelleCategorie);

				// Objet Article

				String articleName = myResultset.getString(4);
				String description = myResultset.getString(5);
				int initialPrice = myResultset.getInt(6);
				int soldPrice = myResultset.getInt(7);
				Date startOfAuction = myResultset.getDate(8);
				Date endOfAuction = myResultset.getDate(9);
				int idArticle = myResultset.getInt(10);

				article = new Article(idArticle, articleName, description, startOfAuction, endOfAuction, initialPrice,
						soldPrice, sellerUser, categorie);

				searchResult.add(article);
			}

			/**
			 * VENTES TERMINEES
			 */

		} else if (endedSales) {

			Connection databaseConnection = JdbcTools.getConnection();
			preparedStatement = databaseConnection.prepareStatement(ARTICLE_SEARCH_BY_USER_REQUEST_ENDED_SALES);

			preparedStatement.setString(1, "%" + research + "%");
			preparedStatement.setString(2, userPseudo);

			myResultset = preparedStatement.executeQuery();

			while (myResultset.next()) {

				// Objets Utilisateur

				String seller = myResultset.getString(1); // Vendeur
				sellerUser = new Utilisateur(seller);

				// Objet Categorie

				int noCategorie = myResultset.getInt(2);
				String libelleCategorie = myResultset.getString(3);

				categorie = new Categorie(noCategorie, libelleCategorie);

				// Objet Article

				String articleName = myResultset.getString(4);
				String description = myResultset.getString(5);
				int initialPrice = myResultset.getInt(6);
				int soldPrice = myResultset.getInt(7);
				Date startOfAuction = myResultset.getDate(8);
				Date endOfAuction = myResultset.getDate(9);
				int idArticle = myResultset.getInt(10);

				article = new Article(idArticle, articleName, description, startOfAuction, endOfAuction, initialPrice,
						soldPrice, sellerUser, categorie);

				searchResult.add(article);
			}

		} else {

			Connection databaseConnection = JdbcTools.getConnection();

			preparedStatement = databaseConnection.prepareStatement(ARTICLE_SEARCH_BY_USER_REQUEST_ALL_AUCTION);
			preparedStatement.setString(1, "%" + research + "%");

			myResultset = preparedStatement.executeQuery();

			while (myResultset.next()) {

				// Objets Utilisateur

				String seller = myResultset.getString(9); // VENDEUR
				sellerUser = new Utilisateur(seller);

				// Objet Categorie

				int noCategorie = myResultset.getInt(1);
				String libelleCategorie = myResultset.getString(2);

				categorie = new Categorie(noCategorie, libelleCategorie);

				// Objet Article

				String articleName = myResultset.getString(3);
				String description = myResultset.getString(4);
				Date startOfAuction = myResultset.getDate(5);
				Date endOfAuction = myResultset.getDate(6);
				int initialPrice = myResultset.getInt(7);
				int soldPrice = myResultset.getInt(8);
				int idArticle = myResultset.getInt(10);

				article = new Article(idArticle, articleName, description, startOfAuction, endOfAuction, initialPrice,
						soldPrice, sellerUser, categorie);

				searchResult.add(article);
			}
		}

		myResultset.close();
		return searchResult;

	}

}
