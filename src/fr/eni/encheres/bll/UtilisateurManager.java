package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.interfaces.UtilisateurDAO;

public class UtilisateurManager {
	private UtilisateurDAO uMger;

	public UtilisateurManager(UtilisateurDAO uMger) {
		uMger = DAOFactory.getUtilisateurDAO();
	}
	
	public void addUtilisateur(Utilisateur utilisateur) {
		
	}
	
}
