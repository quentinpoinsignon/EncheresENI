package fr.eni.encheres.dal.interfaces;

import java.sql.SQLException;

public interface UtilisateurDAO {

	void userConnection(String email, String password) throws SQLException;

}
