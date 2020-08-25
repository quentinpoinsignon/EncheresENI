package fr.eni.encheres.bll;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import javafx.util.converter.LocalDateStringConverter;

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
	 * @commentaire méthode pour passer du format Java au format compatible sql
	 */
	public static java.sql.Date dateUtilToSql (java.util.Date utilDate) {
		return new java.sql.Date(utilDate.getTime());
	}
	
	/**
	 * @param date
	 * @return true
	 * @Commentaire si la date est superieure ou égale à la date d'enregistrement de nouvel article
	 */
	
	public static Boolean validerDate (Date date) {
		LocalDate now = dateToLocalDate(new Date());
		LocalDate dateConverted = dateToLocalDate(date);
		return (date != null && dateConverted.isAfter(now) || dateConverted.equals(now));
	}
	
	/**
	 * @param date
	 * @return LocalDate
	 * @Commentaire pour utilisation avec la méthode validerDate
	 */
	public static LocalDate dateToLocalDate (Date date) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = date.toInstant();
		return  instant.atZone(defaultZoneId).toLocalDate();
	}
	
}
