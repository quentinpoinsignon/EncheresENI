package fr.eni.encheres.ihm;

import java.util.List;

import fr.eni.encheres.bo.Categorie;

/**
 * @author qpoinsig2020
 * Classe d'utilitaires pour utilisation dans les servlets 
 */

public class ihmUtils {

	/**
	 * @param s chaîne de caractères à tester 
	 * @return Booleen true si s est alphanumérique + accents acceptés
	 */
	public static Boolean isAlphaNumeric(String s) {
		return s != null && s.matches("^[a-zA-Z0-9]*$");
	}
	
	/**
	 * @param s chaîne de caractères à tester 
	 * @return Booleen true si s correspond à un pattern email valide
	 */
	public static Boolean isEmail (String s) {
		return s != null && s.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$");
	}
	
	/**
	 * @param s chaîne de caractères à tester
	 * @return chaîne de caractères pseudo ou email en fonction de s, null sinon
	 */
	public static String natureIdentifiant (String s) {
		if(ihmUtils.isAlphaNumeric(s)) return "pseudo";
		else if (ihmUtils.isEmail(s)) return "email";
		else return null;
	}
	
	/**
	 * @author qpoinsig2020
	 * @param listeCategories
	 * @param noCategorie
	 * @return
	 * @commentaire méthode pour retourner un objet categorie by id
	 */
	public static Categorie returnCategorieById (List<Categorie> listeCategories, int noCategorie) {
		Categorie selectedCategorie = null;
		for (Categorie categorie : listeCategories) {
			if(categorie.getNoCategorie() == noCategorie) {
				selectedCategorie = categorie;
			}
		}
		return selectedCategorie;
	}
}
