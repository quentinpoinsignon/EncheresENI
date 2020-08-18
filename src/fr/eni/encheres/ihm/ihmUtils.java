package fr.eni.encheres.ihm;

public class ihmUtils {

	public static Boolean isAlphaNumeric(String s) {
		return s != null && s.matches("^[a-zA-Z0-9]*$");
	}

}
