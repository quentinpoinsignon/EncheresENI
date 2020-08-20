package fr.eni.encheres.bll;

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
	
	public void editUserProfile() {
	}
}
