package fr.eni.encheres.dal.interfaces;

import java.sql.SQLException;

/**
 * 
 * @author jarrigon2020 Interface int�grant les diff�rentes fonctions li�es aux
 *         utilisateurs du site
 */
public interface UtilisateurDAO {

	public void userConnection(String email, String password) throws SQLException;
}
