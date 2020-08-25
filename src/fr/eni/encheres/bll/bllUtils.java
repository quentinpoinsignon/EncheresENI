package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import fr.eni.encheres.bo.Categorie;

public class bllUtils {
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
	
	/**
	 * @author qpoinsig2020
	 * @param utilDate
	 * @return sqlDate
	 */
	public static java.sql.Date dateUtilToSql (java.util.Date utilDate) {
		return new java.sql.Date(utilDate.getTime());
	}
	
	
	public int compareToNow (Date date) {
		return 0;
	}
	
}
