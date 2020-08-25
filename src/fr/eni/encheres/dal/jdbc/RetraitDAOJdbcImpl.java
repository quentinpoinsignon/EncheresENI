package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.eni.encheres.dal.interfaces.RetraitDAO;

/**
 * 
 * @author jarrigon2020
 * 
 * @Commentaire Cette classe contient l'intégralité des méthodes permettant
 *              d'utiliser des requêtes SQL concernant la table RETRAITS dans la
 *              base de données
 *
 */

public class RetraitDAOJdbcImpl implements RetraitDAO {

	/**
	 * @Constante INSERT_WITHDRAWAL_POINT -> Chaine de caractères contenant une
	 *            requête SQL permettant d'enregister un nouveau point de retrait
	 *            dans la base de données dans la base de données
	 * 
	 * @value "INSERT INTO RETRAITS(no_article,rue,code_postal,ville)
	 *        VALUE(?,?,?,?)";
	 */
	private final String INSERT_WITHDRAWAL_POINT = "INSERT INTO RETRAITS(no_article,rue,code_postal,ville) VALUE(?,?,?,?)";

	/**
	 * @author jarrigon2020
	 * 
	 * @param newArticle -> Objet de type Article qui va nous permettre de récupérer
	 *                   les informations liées au point de retrait de l'article
	 * @throws Exception
	 * 
	 * @Commentaire Cette fonction permet d'enregistrer un nouveau point de retrait
	 *              dans la base de données. Elle est appelée au sein de la méthode
	 *              insertNewArticle dans la classe ArticleDAOJdbcImpl
	 * 
	 */
	@Override
	public void insertWithdrawalPoint(int idArticle, String street, String town, String postalCode) throws Exception {

		int noArticle = idArticle;
		String streetRetrait = street;
		String townRetrait = town;
		String postalCodeRetrait = postalCode;

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(INSERT_WITHDRAWAL_POINT)) {

			preparedStatement.setInt(1, noArticle);
			preparedStatement.setString(2, streetRetrait);
			preparedStatement.setString(3, townRetrait);
			preparedStatement.setString(4, postalCodeRetrait);

			preparedStatement.executeUpdate();

		}

		catch (SQLException e) {

			throw new Exception(e.getMessage());

		}

	}

}
