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
 * @Commentaire Cette classe contient l'ensemble des méthodes permettant de
 *              réaliser les requêtes SQl concernant les différentes catégories
 *              enregistrées dans la base de données
 */

public class CategorieDAOJdbcImpl implements CategorieDAO {

	/**
	 * @Constante ALL_CATEGORIE_REQUEST -> Chaine de caractère contenant une requête
	 *            sql permettant de récupérer l'ensemble des catégories enregistrées
	 *            dans la base de données
	 */

	private final String ALL_CATEGORIE_REQUEST = "SELECT no_categorie, libelle FROM CATEGORIES;";

	/**
	 * @author jarrigon2020
	 * 
	 * @return listCategorie -> Retourne un objet de type ArrayList contenant des
	 *         objets de type Catégorie
	 * @throws Exception
	 * 
	 * @commentaire Permet de récupérer l'ensemble des catégories enregistrées dans
	 *              la base de données
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
