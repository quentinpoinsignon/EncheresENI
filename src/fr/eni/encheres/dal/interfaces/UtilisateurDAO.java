package fr.eni.encheres.dal.interfaces;

import java.sql.SQLException;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {

	void userInsert(Utilisateur user) throws SQLException, Exception;

	public Utilisateur userConnection(String email, String password) throws Exception;

	public Utilisateur userConnectionPseudo(String pseudo, String password) throws Exception;

	public Utilisateur showUserProfil(String pseudo) throws Exception;

	public void editUserProfil(String pseudo, String name, String firstName, String email, String telephoneNumber,
			String street, String postalCode, String password);

	public Utilisateur getUserInformation(int idUser) throws Exception;
}
