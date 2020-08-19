package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.interfaces.UtilisateurDAO;

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

}
