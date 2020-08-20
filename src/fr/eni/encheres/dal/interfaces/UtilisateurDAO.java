package fr.eni.encheres.dal.interfaces;

import java.sql.SQLException;
import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {

	void userInsert(Utilisateur user) throws SQLException, Exception;

	public Utilisateur userConnection(String email, String password) throws Exception;

	public Utilisateur userConnectionPseudo(String pseudo, String password) throws Exception;

	public Utilisateur showUserProfil(String pseudo) throws Exception;

	public void editUserProfil(String pseudo, String name, String firstName, String email, String telephoneNumber,
			String street, String postalCode, String password, int idUser) throws SQLException;

	public Utilisateur getUserInformation(int idUser) throws Exception;

	void removeUserProfil(int idUser) throws Exception;

	List<String> selectAllPseudo() throws Exception;

	/**
	 * @author jarrigon2020
	 * 
	 * @return listPseudo -> Objet de type ArrayList contenant des chaines de
	 *         caractères contenant l'ensemble des pseudo enregistrés sur le site
	 * 
	 * @Commentaire Cette méthode permet de récupérer l'ensemble des pseudo
	 *              enregistrés dans la base de données
	 */
	List<String> selectAllEmail() throws Exception;
}
