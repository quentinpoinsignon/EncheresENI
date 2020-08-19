package fr.eni.encheres.ihm;

public class ihmUtils {

	public static Boolean isAlphaNumeric(String s) {
		return s != null && s.matches("^[a-zA-Z0-9]*$");
	}
	
	public static Boolean isEmail (String s) {
		return s != null && s.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$");
	}
	
	public static String natureIdentifiant (String s) {
		if(ihmUtils.isAlphaNumeric(s)) return "pseudo";
		else if (ihmUtils.isEmail(s)) return "email";
		else return null;
	}
	
}
