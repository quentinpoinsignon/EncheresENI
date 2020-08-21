package fr.eni.encheres.dal.interfaces;

import java.sql.SQLException;
import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {

	void userInsert(Utilisateur user) throws SQLException, Exception;

	public Utilisateur userConnection(String email, String password) throws Exception;

	public Utilisateur userConnectionPseudo(String pseudo, String password) throws Exception;

	public Utilisateur showUserProfil(String pseudo) throws Exception;

	public Utilisateur editUserProfil(Utilisateur user) throws SQLException;

	public Utilisateur getUserInformation(int idUser) throws Exception;

	void removeUserProfil(int idUser) throws Exception;

	List<String> selectAllPseudo() throws Exception;

	List<String> selectAllEmail() throws Exception;

	public void editUserPassword(Utilisateur user, String oldPassword, String newPassword) throws Exception;

}
