package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.interfaces.RetraitDAO;

/**
 * 
 * @author jarrigon2020
 * 
 * @Commentaire Cette classe contient l'int�gralit� des m�thodes permettant
 *              d'utiliser des requ�tes SQL concernant la table RETRAITS dans la
 *              base de donn�es
 *
 */

public class RetraitDAOJdbcImpl implements RetraitDAO {

	/**
	 * @Constante INSERT_WITHDRAWAL_POINT -> Chaine de caract�res contenant une
	 *            requ�te SQL permettant d'enregister un nouveau point de retrait
	 *            dans la base de donn�es dans la base de donn�es
	 * 
	 * @value "INSERT INTO RETRAITS(no_article,rue,code_postal,ville)
	 *        VALUE(?,?,?,?)";
	 */
	private final String INSERT_WITHDRAWAL_POINT = "INSERT INTO RETRAITS(no_article,rue,code_postal,ville) VALUE(?,?,?,?)";

	/**
	 * @author jarrigon2020
	 * 
	 * @param newArticle -> Objet de type Article qui va nous permettre de r�cup�rer
	 *                   les informations li�es au point de retrait de l'article
	 * @throws Exception
	 * 
	 * @Commentaire Cette fonction permet d'enregistrer un nouveau point de retrait
	 *              dans la base de donn�es. Elle est appel�e au sein de la m�thode
	 *              insertNewArticle
	 * 
	 */
	public void insertWithdrawalPoint(Article newArticle) throws Exception {

		int noArticle = newArticle.getNoArticle();

		String street = newArticle.getPointRetrait().getRue();
		String town = newArticle.getPointRetrait().getVille();
		String postalCode = newArticle.getPointRetrait().getCodePostal();

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(INSERT_WITHDRAWAL_POINT)) {

			preparedStatement.setInt(1, noArticle);
			preparedStatement.setString(2, newArticle.getDescription());

			preparedStatement.setInt(6, 0);

			preparedStatement.executeUpdate();

		}

		catch (SQLException e) {

			throw new Exception(e.getMessage());

		}

	}

}
