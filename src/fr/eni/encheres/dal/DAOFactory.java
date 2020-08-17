package fr.eni.encheres.dal;

import fr.eni.encheres.dal.jdbc.ArticleDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.CategorieDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.EnchereDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.RetraitDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.UtilisateurDAOJdbcImpl;

public class DAOFactory {

	public static UtilisateurDAOJdbcImpl getUtilisateurDAO() {

		return new UtilisateurDAOJdbcImpl();

	}

	public static ArticleDAOJdbcImpl getArticleDAO() {

		return new ArticleDAOJdbcImpl();

	}

	public static RetraitDAOJdbcImpl getRetraitDAO() {

		return new RetraitDAOJdbcImpl();

	}

	public static EnchereDAOJdbcImpl getEnchereDAO() {

		return new EnchereDAOJdbcImpl();

	}

	public static CategorieDAOJdbcImpl getCategorieDAO() {

		return new CategorieDAOJdbcImpl();

	}
}
