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
	 * @param research    -> Chaine de caractères correspondant au texte rentré dans
	 *                    le champs de recherche de la page accueil par
	 *                    l'utilisateur
	 * 
	 * @param idCategorie -> Identifiant de la categorie sélectionné
	 * 
	 * @return searchResult -> Objet de type ArrayList contenant des objets de type
	 *         Enchere
	 * 
	 * @Commentaire Cette méthode permet d'effectuer une recherche dans la base de
	 *              données de récupérer des encheres par rapport à la chaine de
	 *              caractère rentrée par un utilisateur dans le champs "Recherche"
	 *              et le selecteur de categories de la page d'accueil.
	 * 
	 */
	@Override
	public List<Enchere> articleSearchByUserRequest(String research, int idCategorie) throws Exception {

		ResultSet myResultset = null;

		Enchere enchere = null;
		Utilisateur sellerUser = null;
		Utilisateur buyerUser = null;
		Article article = null;
		Categorie categorie = null;

		List<Enchere> searchResult = new ArrayList<Enchere>();

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection
						.prepareStatement(ARTICLE_SEARCH_BY_USER_REQUEST)) {

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
