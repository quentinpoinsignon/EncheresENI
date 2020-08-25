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
 * @Commentaire Cette classe contient les diff�rentes m�thodes permettant de
 *              r�aliser les requ�te sql concernant les ench�res effectu�es sur
 *              l'application
 *
 */

public class EnchereDAOJdbcImpl implements EnchereDAO {

	/**
	 * @Constante ARTICLE_SEARCH_BY_USER_REQUEST -> Chaine de caract�res contenant
	 *            une requ�te SQL effectuant une recherche dans la table enchere
	 *            permettant de r�cup�rer une liste d'encheres correspondant � ce
	 *            que l'utilisateur a rentr� dans le champs de recherche ainsi que
	 *            la cat�gorie qu'il a s�lectionn�
	 * 
	 * @value "SELECT utl.pseudo as acheteur,ctgr.no_categorie, ctgr.libelle,
	 *        nom_article, description,date_debut_encheres,date_fin_encheres,
	 *        prix_initial,
	 *        prix_vente,ench.date_enchere,ench.montant_enchere,utl2.pseudo as
	 *        vendeur\n" + "FROM ENCHERES ench\n" + "INNER JOIN ARTICLES_VENDUS
	 *        artvd ON artvd.no_article = ench.no_article\n" + "INNER JOIN
	 *        UTILISATEURS utl ON utl.no_utilisateur = ench.no_utilisateur \n" +
	 *        "INNER JOIN UTILISATEURS utl2 ON utl2.no_utilisateur =
	 *        artvd.no_utilisateur\n" + "INNER JOIN CATEGORIES ctgr ON
	 *        ctgr.no_categorie = artvd.no_categorie\n" + "WHERE (nom_article LIKE
	 *        '?%' AND vente_effectuee = 0) OR (nom_article LIKE '?%' AND
	 *        ctgr.no_categorie = ? AND vente_effectuee = 0) OR (ctgr.no_categorie =
	 *        ? AND vente_effectuee = 0 )\n" + "ORDER BY artvd.date_fin_encheres
	 *        ASC";
	 */
	private final String ARTICLE_SEARCH_BY_USER_REQUEST = "SELECT utl.pseudo as acheteur,ctgr.no_categorie, ctgr.libelle, nom_article, description,date_debut_encheres,date_fin_encheres, prix_initial, prix_vente,ench.date_enchere,ench.montant_enchere,utl2.pseudo as vendeur\n"
			+ "FROM ENCHERES ench\n" + "INNER JOIN ARTICLES_VENDUS artvd ON artvd.no_article = ench.no_article\n"
			+ "INNER JOIN UTILISATEURS utl ON utl.no_utilisateur = ench.no_utilisateur \n"
			+ "INNER JOIN UTILISATEURS utl2 ON utl2.no_utilisateur = artvd.no_utilisateur\n"
			+ "INNER JOIN CATEGORIES ctgr ON ctgr.no_categorie = artvd.no_categorie\n"
			+ "WHERE (nom_article LIKE '?%' AND vente_effectuee = 0) OR (nom_article LIKE '?%' AND ctgr.no_categorie = ? AND vente_effectuee = 0) OR (ctgr.no_categorie = ? AND vente_effectuee = 0 )\n"
			+ "ORDER BY artvd.date_fin_encheres ASC";

	/**
	 * @author jarrigon2020
	 * 
	 * @param research        -> Chaine de caract�res correspondant au texte rentr�
	 *                        dans le champs de recherche de la page accueil par
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
	 *              donn�es de r�cup�rer des encheres par rapport � la chaine de
	 *              caract�re rentr�e par un utilisateur dans le champs "Recherche"
	 *              et le selecteur de categories de la page d'accueil. Nous
	 *              prendronss �galement en compte les boutons radios et les
	 *              checkbox s�lectionn�es par l'utilisateur
	 * 
	 */
	@Override
	public List<Enchere> articleSearchByUserRequest(String research, int idCategorie, Boolean shoppingChecked,
			Boolean openAuction, Boolean winAuction, Boolean myAuction, Boolean mySales, Boolean myCurrentSales,
			Boolean notSartedSales, Boolean endedSales) throws Exception {

		ResultSet myResultset = null;
		String SQLRequest = null;

		Enchere enchere = null;
		Utilisateur sellerUser = null;
		Utilisateur buyerUser = null;
		Article article = null;
		Categorie categorie = null;

		List<Enchere> searchResult = new ArrayList<Enchere>();

		/**
		 * Nous allons v�rifier les champs coch�s par l'utilisateur gr�ce � des booleens
		 * et adapter la requ�te SQL en fonction
		 */
		if (shoppingChecked) { // Si le bouton radio achats est selectionn�

			if (openAuction && !myAuction && !winAuction) { // OK / NotOK / NotOk

				SQLRequest = "";
			} else if (!openAuction && myAuction && !winAuction) { // NotOK / OK / NotOk

				SQLRequest = "";
			} else if (!openAuction && !myAuction && winAuction) { // NotOK /NotOK / OK

				SQLRequest = "";
			} else if (openAuction && myAuction && !winAuction) { // OK / OK / NotOk
				SQLRequest = "";
			} else if (openAuction && !myAuction && winAuction) { // OK / NotOK / Ok
				SQLRequest = "";
			} else if (!openAuction && myAuction && winAuction) { // NotOK / OK / Ok
				SQLRequest = "";
			}
		} else if (mySales) { // Si le boutons mes ventes est selectionn�

			if (myCurrentSales && !notSartedSales && !endedSales) { // OK / NotOK / NotOk
				SQLRequest = "";
			} else if (!myCurrentSales && notSartedSales && !endedSales) { // NotOK / OK / NotOk
				SQLRequest = "";
			} else if (!myCurrentSales && !notSartedSales && endedSales) { // NotOK /NotOK / OK
				SQLRequest = "";
			} else if (myCurrentSales && notSartedSales && !endedSales) { // OK / OK / NotOk
				SQLRequest = "";
			} else if (myCurrentSales && !notSartedSales && endedSales) { // OK / NotOK / Ok
				SQLRequest = "";
			} else if (!myCurrentSales && notSartedSales && endedSales) { // NotOK / OK / Ok
				SQLRequest = "";
			}

		} else {
			SQLRequest = ARTICLE_SEARCH_BY_USER_REQUEST;
		}

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(SQLRequest)) {

			preparedStatement.setString(1, research);
			preparedStatement.setString(2, research);
			preparedStatement.setInt(3, idCategorie);
			preparedStatement.setInt(4, idCategorie);

			myResultset = preparedStatement.executeQuery();

			while (myResultset.next()) {

				// Objets Utilisateur

				String Buyer = myResultset.getString(1); // ACHETEUR
				buyerUser = new Utilisateur(Buyer);

				String seller = myResultset.getString(12); // VENDEUR
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

				article = new Article(articleName, description, startOfAuction, endOfAuction, initialPrice, soldPrice,
						sellerUser, categorie);

				// Objet Enchere

				Date auctionDate = myResultset.getDate(10);
				int auctionAmount = myResultset.getInt(11);

				enchere = new Enchere(auctionDate, auctionAmount, article, buyerUser, sellerUser);

				searchResult.add(enchere);

			}

			myResultset.close();

		} catch (SQLException e) {

			throw new Exception(e.getMessage());

		}
		return searchResult;
	}

}
