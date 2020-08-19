package fr.eni.encheres.ihm;

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
	
	
//	public static void main(String[] args) {
//		String pseudo = "Jéjé";
//		System.out.println(natureIdentifiant(pseudo));
//	}
}
