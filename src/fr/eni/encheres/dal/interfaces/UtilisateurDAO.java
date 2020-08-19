package fr.eni.encheres.dal.interfaces;

import java.sql.SQLException;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {

	void userInsert(Utilisateur user) throws SQLException, Exception;

	public Utilisateur userConnection(String email, String password) throws Exception;

	public Utilisateur userConnectionPseudo(String pseudo, String password) throws Exception;
}
