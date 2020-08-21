package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.interfaces.CategorieDAO;

/**
 * 
 * @author jarrigon2020
 *
 * @Commentaire Cette classe contient l'ensemble des m�thodes permettant de
 *              r�aliser les requ�tes SQl concernant les diff�rentes cat�gories
 *              enregistr�es dans la base de donn�es
 */

public class CategorieDAOJdbcImpl implements CategorieDAO {

	/**
	 * @Constante ALL_CATEGORIE_REQUEST -> Chaine de caract�re contenant une requ�te
	 *            sql permettant de r�cup�rer l'ensemble des cat�gories enregistr�es
	 *            dans la base de donn�es
	 */

	private final String ALL_CATEGORIE_REQUEST = "SELECT no_categorie, libelle FROM CATEGORIES;";

	/**
	 * @author jarrigon2020
	 * 
	 * @return listCategorie -> Retourne un objet de type ArrayList contenant des
	 *         objets de type Cat�gorie
	 * @throws Exception
	 * 
	 * @commentaire Permet de r�cup�rer l'ensemble des cat�gories enregistr�es dans
	 *              la base de donn�es
	 */
	@Override
	public List<Categorie> listAllCategories() throws Exception {

		ResultSet myResultset = null;
		Categorie categorie = null;

		List<Categorie> listCategorie = new ArrayList<Categorie>();

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(ALL_CATEGORIE_REQUEST)) {

			myResultset = preparedStatement.executeQuery();

			while (myResultset.next()) {

				int idCategorie = myResultset.getInt(1);
				String libelle = myResultset.getString(2);

				categorie = new Categorie(idCategorie, libelle);

				listCategorie.add(categorie);

			}

			myResultset.close();

		} catch (SQLException e) {

			throw new Exception(e.getMessage());

		}
		return listCategorie;
	}

}
