package fr.eni.encheres.bll;

import java.sql.SQLException;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.interfaces.UtilisateurDAO;

/**
 * @author qpoinsig2020
 * Classe de la bll gerant les utilisateurs
 */
public class UtilisateurManager {
	private UtilisateurDAO uDAO;
	
	
	public UtilisateurManager() {
		uDAO = DAOFactory.getUtilisateurDAO();
	}
	
	public void addUtilisateur(Utilisateur utilisateur) {
		try {
			uDAO.userInsert(utilisateur);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getListPseudos() {
		List<String> listPseudos = null;
		try {
			listPseudos = uDAO.selectAllPseudo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listPseudos;
	}
	
	public List<String> getListEmail() {
		List<String> listEmail = null;
		try {
			listEmail = uDAO.selectAllEmail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listEmail;
	}
	
	public Utilisateur connectionByEmail(String email, String password) {
		Utilisateur userBDD = null;
		try {
			userBDD = uDAO.userConnection(email, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userBDD;
	}

	public Utilisateur connectionByPseudo(String pseudo, String password) {
		Utilisateur userBDD = null;
		try {
			userBDD = uDAO.userConnectionPseudo(pseudo, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userBDD;
	}
	
	public Utilisateur getUserProfileByPseudo(String pseudo) {
		Utilisateur userBDD = null;
		try {
			userBDD = uDAO.showUserProfil(pseudo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userBDD;
	}
	
	public Utilisateur editUserProfile(Utilisateur utilisateur) {
		Utilisateur updatedUser = null;
		try {
			updatedUser = uDAO.editUserProfil(utilisateur);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updatedUser;
	}
	
	public void editUserPassword (Utilisateur utilisateur, String oldPassword, String newPassword) {
		try {
			uDAO.editUserPassword(utilisateur, oldPassword, newPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeUserProfile (int idUser) {
		try {
			uDAO.removeUserProfil(idUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
