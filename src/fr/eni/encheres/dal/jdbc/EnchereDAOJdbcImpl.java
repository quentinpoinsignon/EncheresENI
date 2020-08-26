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
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interfaces.EnchereDAO;

/**
 * 
 * @author jarrigon2020
 * 
 * @Commentaire Cette classe contient les différentes méthodes permettant de
 *              réaliser les requête sql concernant les enchères effectuées sur
 *              l'application
 *
 */

public class EnchereDAOJdbcImpl implements EnchereDAO {

	/**
	 * @Constante ARTICLE_SEARCH_BY_USER_REQUEST -> Chaine de caractères contenant
	 *            une requête SQL effectuant une recherche dans la table enchere
	 *            permettant de récupérer une liste d'encheres correspondant à ce
	 *            que l'utilisateur a rentré dans le champs de recherche ainsi que
	 *            la catégorie qu'il a sélectionné
	 * 
	 * @value "SELECT ctgr.no_categorie, ctgr.libelle, nom_article,
	 *        description,date_debut_encheres,date_fin_encheres, prix_initial,
	 *        prix_vente,utl2.pseudo as vendeur\n" + "FROM ARTICLES_VENDUS artvd \n"
	 *        + "INNER JOIN UTILISATEURS utl2 ON utl2.no_utilisateur =
	 *        artvd.no_utilisateur\n" + "INNER JOIN CATEGORIES ctgr ON
	 *        ctgr.no_categorie = artvd.no_categorie\n" + "WHERE nom_article LIKE
	 *        TRIM('%?%') \n" + "ORDER BY artvd.date_fin_encheres ASC;";
	 */
	private final String ARTICLE_SEARCH_BY_USER_REQUEST_ALL_AUCTION = "SELECT ctgr.no_categorie, ctgr.libelle, nom_article, description,date_debut_encheres,date_fin_encheres, prix_initial, prix_vente,utl2.pseudo as vendeur\n"
			+ "FROM ARTICLES_VENDUS artvd \n"
			+ "INNER JOIN UTILISATEURS utl2 ON utl2.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM('%?%')  \n" + "ORDER BY artvd.date_fin_encheres ASC;";

	/**
	 * @Constante ARTICLE_SEARCH_BY_USER_REQUEST_OPEN_AUCTION -> Chaine de
	 *            caractères contenant une requête SQL effectuant une recherche dans
	 *            la table ARTICLE_VENDUS permettant de récupérer une liste
	 *            d'encheres correspondant à ce que l'utilisateur a rentré dans le
	 *            champs de recherche, la catégorie qu'il a sélectionné, si le
	 *            bouton radio achat est sélectionné et que seule la checkbox
	 *            encheres ouvertes est cochée
	 * 
	 * @Value "SELECT ctgr.no_categorie, ctgr.libelle, nom_article,
	 *        description,date_debut_encheres,date_fin_encheres, prix_initial,
	 *        prix_vente,utl2.pseudo as vendeur\n" + "FROM ARTICLES_VENDUS artvd \n"
	 *        + "INNER JOIN UTILISATEURS utl2 ON utl2.no_utilisateur =
	 *        artvd.no_utilisateur\n" + "INNER JOIN CATEGORIES ctgr ON
	 *        ctgr.no_categorie = artvd.no_categorie\n" + "WHERE nom_article LIKE
	 *        TRIM('%?%') AND date_debut_encheres < GETDATE() And date_fin_encheres
	 *        > GETDATE() AND vente_effectuee = 0\n" + "ORDER BY
	 *        artvd.date_fin_encheres ASC";
	 */

	private final String ARTICLE_SEARCH_BY_USER_REQUEST_OPEN_AUCTION = "SELECT ctgr.no_categorie, ctgr.libelle, nom_article, description,date_debut_encheres,date_fin_encheres, prix_initial, prix_vente,utl2.pseudo as vendeur\n"
			+ "FROM ARTICLES_VENDUS artvd \n"
			+ "INNER JOIN UTILISATEURS utl2 ON utl2.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM('%?%')  AND date_debut_encheres < GETDATE() And date_fin_encheres > GETDATE() AND vente_effectuee = 0\n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * Constante ARTICLE_SEARCH_BY_USER_REQUEST_MY_AUCTION -> Chaine de caractères
	 * contenant une requête SQL effectuant une recherche dans la table
	 * ARTICLE_VENDUS permettant de récupérer une liste d'encheres correspondant à
	 * ce que l'utilisateur a rentré dans le champs de recherche, la catégorie qu'il
	 * a sélectionné, si le bouton radio achat est sélectionné et que seule la
	 * checkbox mes encheres est cochée
	 * 
	 * @value "SELECT utl.pseudo as acheteur, ctgr.no_categorie, ctgr.libelle,
	 *        nom_article, description,date_debut_encheres,date_fin_encheres,
	 *        prix_initial, prix_vente,utl2.pseudo as vendeur,
	 *        ench.montant_enchere,ench.date_enchere\n" + "FROM ENCHERES ench \n" +
	 *        "INNER JOIN ARTICLES_VENDUS artvd ON ench.no_article =
	 *        artvd.no_article\n" + "INNER JOIN UTILISATEURS utl2 ON
	 *        utl2.no_utilisateur = artvd.no_utilisateur\n" + "INNER JOIN
	 *        UTILISATEURS utl ON utl.no_utilisateur = ench.no_utilisateur\n" +
	 *        "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie =
	 *        artvd.no_categorie\n" + "WHERE nom_article LIKE TRIM('%%') AND
	 *        date_debut_encheres < GETDATE() And date_fin_encheres > GETDATE() AND
	 *        vente_effectuee = 0 AND utl.pseudo = '?'\n" + "ORDER BY
	 *        artvd.date_fin_encheres ASC";
	 */

	private final String ARTICLE_SEARCH_BY_USER_REQUEST_MY_AUCTION = "SELECT utl.pseudo as acheteur, ctgr.no_categorie, ctgr.libelle, nom_article, description,date_debut_encheres,date_fin_encheres, prix_initial, prix_vente,utl2.pseudo as vendeur, ench.montant_enchere,ench.date_enchere\n"
			+ "FROM ARTICLES_VENDUS artvd \n" + "INNER JOIN ENCHERES ench  ON ench.no_article = artvd.no_article\n"
			+ "INNER JOIN UTILISATEURS utl2 ON utl2.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = ench.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM('%%')  AND date_debut_encheres < GETDATE() And date_fin_encheres > GETDATE() AND vente_effectuee = 0 AND utl.pseudo = '?'\n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * @Contante ARTICLE_SEARCH_BY_USER_REQUEST_WIN_AUCTION -> Chaine de caractères
	 *           contenant une requête SQL effectuant une recherche dans la table
	 *           ARTICLE_VENDUS permettant de récupérer une liste d'encheres
	 *           correspondant à ce que l'utilisateur a rentré dans le champs de
	 *           recherche, la catégorie qu'il a sélectionné, si le bouton radio
	 *           achat est sélectionné et que seule la checkbox mes encheres
	 *           remportées est cochée
	 * 
	 * @value "SELECT utl.pseudo as acheteur, ctgr.no_categorie, ctgr.libelle,
	 *        nom_article, description, prix_initial, prix_vente,utl2.pseudo as
	 *        vendeur, ench.montant_enchere, MAX(ench.date_enchere) as
	 *        dateEnchere\n" + "FROM ARTICLES_VENDUS artvd\n" + "INNER JOIN ENCHERES
	 *        ench ON ench.no_article = artvd.no_article\n" + "INNER JOIN
	 *        UTILISATEURS utl2 ON utl2.no_utilisateur = artvd.no_utilisateur\n" +
	 *        "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur =
	 *        ench.no_utilisateur\n" + "INNER JOIN CATEGORIES ctgr ON
	 *        ctgr.no_categorie = artvd.no_categorie\n" + "WHERE nom_article LIKE
	 *        TRIM('%%') AND vente_effectuee = 0 AND utl.pseudo = 'Jeanjean'\n" +
	 *        "GROUP BY utl.pseudo,ctgr.no_categorie,ctgr.libelle,nom_article,
	 *        description, prix_initial, prix_vente,utl2.pseudo,
	 *        ench.montant_enchere, ench.date_enchere,artvd.date_fin_encheres \n" +
	 *        "ORDER BY artvd.date_fin_encheres ASC";
	 */
	private final String ARTICLE_SEARCH_BY_USER_REQUEST_WIN_AUCTION = "SELECT utl.pseudo as acheteur, ctgr.no_categorie, ctgr.libelle, nom_article, description, prix_initial, prix_vente,utl2.pseudo as vendeur, ench.montant_enchere, MAX(ench.date_enchere) as dateEnchere\n"
			+ "FROM  ARTICLES_VENDUS artvd\n" + "INNER JOIN ENCHERES ench ON ench.no_article = artvd.no_article\n"
			+ "INNER JOIN UTILISATEURS utl2 ON utl2.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = ench.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM('%%') AND vente_effectuee = 0 AND utl.pseudo = 'Jeanjean'\n"
			+ "GROUP BY utl.pseudo,ctgr.no_categorie,ctgr.libelle,nom_article, description, prix_initial, prix_vente,utl2.pseudo, ench.montant_enchere, ench.date_enchere,artvd.date_fin_encheres \n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * @Constante ARTICLE_SEARCH_BY_USER_REQUEST_CURRENT_SALES -> Chaine de
	 *            caractères contenant une requête SQL effectuant une recherche dans
	 *            la table ARTICLE_VENDUS permettant de récupérer une liste
	 *            d'encheres correspondant à ce que l'utilisateur a rentré dans le
	 *            champs de recherche, la catégorie qu'il a sélectionné, si le
	 *            bouton radio achat est sélectionné et que seule le bouton radio
	 *            mes ventes en cours est coché
	 * 
	 * @value "SELECT utl.pseudo, ctgr.no_categorie, ctgr.libelle, nom_article,
	 *        description, prix_initial, prix_vente,artvd.date_debut_encheres,
	 *        artvd.date_fin_encheres\n" + "FROM ARTICLES_VENDUS artvd\n" + "INNER
	 *        JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\n"
	 *        + "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie =
	 *        artvd.no_categorie\n" + "WHERE nom_article LIKE TRIM('%?%') AND
	 *        artvd.date_debut_encheres <= GETDATE() AND artvd.date_fin_encheres >
	 *        GETDATE() AND vente_effectuee = 0 AND utl.pseudo = '?'\n" + "ORDER BY
	 *        artvd.date_fin_encheres ASC";
	 */

	private final String ARTICLE_SEARCH_BY_USER_REQUEST_CURRENT_SALES = "SELECT utl.pseudo, ctgr.no_categorie, ctgr.libelle, nom_article, description, prix_initial, prix_vente,artvd.date_debut_encheres, artvd.date_fin_encheres\n"
			+ "FROM ARTICLES_VENDUS artvd\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM('%?%') AND artvd.date_debut_encheres <= GETDATE() AND artvd.date_fin_encheres > GETDATE() AND vente_effectuee = 0 AND utl.pseudo = '?'\n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * @Constante ARTICLE_SEARCH_BY_USER_REQUEST_NOT_STARTED_SALES -> Chaine de
	 *            caractères contenant une requête SQL effectuant une recherche dans
	 *            la table ARTICLE_VENDUS permettant de récupérer une liste
	 *            d'encheres correspondant à ce que l'utilisateur a rentré dans le
	 *            champs de recherche, la catégorie qu'il a sélectionné, si le
	 *            bouton radio achat est sélectionné et que seule le bouton radio
	 *            mes ventes non débutées est coché
	 * 
	 * @value "SELECT utl.pseudo, ctgr.no_categorie, ctgr.libelle, nom_article,
	 *        description, prix_initial, prix_vente,artvd.date_debut_encheres,
	 *        artvd.date_fin_encheres\n" + "FROM ARTICLES_VENDUS artvd\n" + "INNER
	 *        JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\n"
	 *        + "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie =
	 *        artvd.no_categorie\n" + "WHERE nom_article LIKE TRIM('%?%') AND
	 *        artvd.date_debut_encheres >= GETDATE() AND vente_effectuee = 0 AND
	 *        utl.pseudo = '?'\n" + "ORDER BY artvd.date_fin_encheres ASC";
	 */

	private final String ARTICLE_SEARCH_BY_USER_REQUEST_NOT_STARTED_SALES = "SELECT utl.pseudo, ctgr.no_categorie, ctgr.libelle, nom_article, description, prix_initial, prix_vente,artvd.date_debut_encheres, artvd.date_fin_encheres\n"
			+ "FROM ARTICLES_VENDUS artvd\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM('%?%') AND artvd.date_debut_encheres >= GETDATE() AND vente_effectuee = 0 AND utl.pseudo = '?'\n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * @Constante ARTICLE_SEARCH_BY_USER_REQUEST_ENDED_SALES -> Chaine de caractères
	 *            contenant une requête SQL effectuant une recherche dans la table
	 *            ARTICLE_VENDUS permettant de récupérer une liste d'encheres
	 *            correspondant à ce que l'utilisateur a rentré dans le champs de
	 *            recherche, la catégorie qu'il a sélectionné, si le bouton radio
	 *            achat est sélectionné et que seule le bouton radio mes ventes
	 *            terminées est coché
	 * 
	 * @value "SELECT utl.pseudo, ctgr.no_categorie, ctgr.libelle, nom_article,
	 *        description, prix_initial, prix_vente,artvd.date_debut_encheres,
	 *        artvd.date_fin_encheres\n" + "FROM ARTICLES_VENDUS artvd\n" + "INNER
	 *        JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\n"
	 *        + "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie =
	 *        artvd.no_categorie\n" + "WHERE nom_article LIKE TRIM('%?%') AND
	 *        artvd.date_fin_encheres <= GETDATE() AND utl.pseudo = '?'\n" + "ORDER
	 *        BY artvd.date_fin_encheres ASC";
	 */

	private String ARTICLE_SEARCH_BY_USER_REQUEST_ENDED_SALES = "SELECT utl.pseudo, ctgr.no_categorie, ctgr.libelle, nom_article, description, prix_initial, prix_vente,artvd.date_debut_encheres, artvd.date_fin_encheres\n"
			+ "FROM ARTICLES_VENDUS artvd\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE nom_article LIKE TRIM('%?%') AND artvd.date_fin_encheres <= GETDATE()  AND utl.pseudo = '?'\n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * @author jarrigon2020
	 * 
	 * @param research        -> Chaine de caractères correspondant au texte rentré
	 *                        dans le champs de recherche de la page accueil par
	 *                        l'utilisateur
	 * 
	 * @param ->              Chaine de caractères correspondant au pseudo de
	 *                        l'utilisateur
	 * 
	 * @param idCategorie     -> Identifiant de la categorie sélectionnée
	 * 
	 * @param shoppingChecked -> Boolean permettant de savoir si le bouton radio
	 *                        achat est sélectionné
	 * 
	 * @param openAuction     -> Boolean permettant de savoir si la checkbox enchere
	 *                        en cour achat est sélectionnée
	 * 
	 * @param winAuction      -> Boolean permettant de savoir si la checkbox
	 *                        encheres remportées est sélectionnée
	 * 
	 * @param myAuction       -> Boolean permettant de savoir si la checkbox mes
	 *                        enchères est sélectionnée
	 * 
	 * @param mySales         -> Boolean permettant de savoir si le bouton radio mes
	 *                        ventes est sélectionné
	 * 
	 * @param myCurrentSales  -> Boolean permettant de savoir si la checkbox mes
	 *                        ventes en cours est sélectionnée
	 * 
	 * @param notSartedSales  -> Boolean permettant de savoir si la checkbox ventes
	 *                        non débutées est sélectionnée
	 * 
	 * @param endedSales      -> Boolean permettant de savoir si la checkbox ventes
	 *                        terminées est sélectionnée
	 * 
	 * @return searchResult -> Objet de type ArrayList contenant des objets de type
	 *         Enchere
	 * 
	 * @Commentaire Cette méthode permet d'effectuer une recherche dans la base de
	 *              données de récupérer des encheres par rapport à la chaine de
	 *              caractère rentrée par un utilisateur dans le champs "Recherche"
	 *              et le selecteur de categories de la page d'accueil. Nous
	 *              prendronss également en compte les boutons radios et les
	 *              checkbox sélectionnées par l'utilisateur
	 * 
	 *              public static <T> T addTo(T e, Collection<T> c){ c.add(e);
	 *              return e; }
	 */
	@Override
	public List<Article> articleSearchByUserRequest(String research, String userPseudo, int idCategorie,
			Boolean shoppingChecked, Boolean openAuction, Boolean winAuction, Boolean myAuction, Boolean mySales,
			Boolean myCurrentSales, Boolean notSartedSales, Boolean endedSales) throws Exception {

		PreparedStatement preparedStatement;
		ResultSet myResultset = null;

		Enchere enchere = null;
		Utilisateur sellerUser = null;
		Utilisateur buyerUser = null;
		Article article = null;
		Categorie categorie = null;

		List<Article> searchResult = new ArrayList<Article>();

		/**
		 * Nous allons vérifier les champs cochés par l'utilisateur grâce à des booleens
		 * et adapter la requête SQL en fonction
		 */
		if (shoppingChecked) { // Si le bouton radio achats est selectionné

			/**
			 * ENCHERES OUVERTES
			 */
			if (openAuction) {

				preparedStatement = getPreparedStatement(ARTICLE_SEARCH_BY_USER_REQUEST_OPEN_AUCTION);

				preparedStatement.setString(1, research);

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

					article = new Article(articleName, description, startOfAuction, endOfAuction, initialPrice,
							soldPrice, sellerUser, categorie);

					searchResult.add(article);
				}
				/**
				 * MES ENCHERES
				 */

			} else if (myAuction) {

				preparedStatement = getPreparedStatement(ARTICLE_SEARCH_BY_USER_REQUEST_MY_AUCTION);

				preparedStatement.setString(1, research);
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

					// Objet Enchere

					Date auctionDate = myResultset.getDate(11);
					int auctionAmount = myResultset.getInt(12);

					enchere = new Enchere(auctionDate, auctionAmount, buyerUser, sellerUser);

					article = new Article(articleName, description, startOfAuction, endOfAuction, initialPrice,
							soldPrice, sellerUser, categorie, enchere);

					searchResult.add(article);
				}
				/**
				 * ENCHERES REMPORTEES
				 */
			} else if (winAuction) {

				preparedStatement = getPreparedStatement(ARTICLE_SEARCH_BY_USER_REQUEST_WIN_AUCTION);

				preparedStatement.setString(1, research);
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

					// Objet Enchere

					int auctionAmount = myResultset.getInt(9);
					Date auctionDate = myResultset.getDate(10);

					enchere = new Enchere(auctionDate, auctionAmount, buyerUser, sellerUser);

					article = new Article(articleName, description, initialPrice, soldPrice, sellerUser, categorie,
							enchere);

					searchResult.add(article);
				}

			}
		} else if (mySales) { // Si le boutons mes ventes est selectionné

			/**
			 * VENTE EN COURS
			 */

			if (myCurrentSales) {

				preparedStatement = getPreparedStatement(ARTICLE_SEARCH_BY_USER_REQUEST_CURRENT_SALES);

				preparedStatement.setString(1, research);
				preparedStatement.setString(2, userPseudo);

				myResultset = preparedStatement.executeQuery();

				while (myResultset.next()) {

					// Objets Utilisateur

					String seller = myResultset.getString(1); // Vendeur
					buyerUser = new Utilisateur(seller);

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

					article = new Article(articleName, description, startOfAuction, endOfAuction, initialPrice,
							soldPrice, sellerUser, categorie);

					searchResult.add(article);
				}

				/**
				 * VENTES NON DEBUTES
				 */

			} else if (notSartedSales) {

				preparedStatement = getPreparedStatement(ARTICLE_SEARCH_BY_USER_REQUEST_NOT_STARTED_SALES);

				preparedStatement.setString(1, research);
				preparedStatement.setString(2, userPseudo);

				myResultset = preparedStatement.executeQuery();

				while (myResultset.next()) {

					// Objets Utilisateur

					String seller = myResultset.getString(1); // Vendeur
					buyerUser = new Utilisateur(seller);

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

					article = new Article(articleName, description, startOfAuction, endOfAuction, initialPrice,
							soldPrice, sellerUser, categorie);

					searchResult.add(article);
				}

				/**
				 * VENTES TERMINEES
				 */

			} else if (endedSales) {

				preparedStatement = getPreparedStatement(ARTICLE_SEARCH_BY_USER_REQUEST_ENDED_SALES);

				preparedStatement.setString(1, research);
				preparedStatement.setString(2, userPseudo);

				myResultset = preparedStatement.executeQuery();

				while (myResultset.next()) {

					// Objets Utilisateur

					String seller = myResultset.getString(1); // Vendeur
					buyerUser = new Utilisateur(seller);

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

					article = new Article(articleName, description, startOfAuction, endOfAuction, initialPrice,
							soldPrice, sellerUser, categorie);

					searchResult.add(article);
				}

			} else {
				preparedStatement = getPreparedStatement(ARTICLE_SEARCH_BY_USER_REQUEST_ALL_AUCTION);
				preparedStatement.setString(1, research);

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

					article = new Article(articleName, description, startOfAuction, endOfAuction, initialPrice,
							soldPrice, sellerUser, categorie);

					searchResult.add(article);
				}
			}

			myResultset.close();

		}
		return searchResult;
	}

	/**
	 * 
	 * @param SQLRequest -> Chaine de caractères contenant une requête SQL
	 * 
	 * @return preparedStatement -> Objet de type PreparedStatement
	 * 
	 * @throws SQLException
	 * 
	 * @Commentaire
	 * 
	 *              Cette méthode permet de créer une requête préparée. Elle est
	 *              utilisée dans la méthode articleSearchByUserRequest
	 */
	private PreparedStatement getPreparedStatement(String SQLRequest) throws SQLException {

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(SQLRequest)) {

			return preparedStatement;
		}
	}
}
