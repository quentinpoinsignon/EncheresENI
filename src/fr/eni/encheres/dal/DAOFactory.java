package fr.eni.encheres.dal;

import fr.eni.encheres.dal.interfaces.ArticleDAO;
import fr.eni.encheres.dal.interfaces.CategorieDAO;
import fr.eni.encheres.dal.interfaces.EnchereDAO;
import fr.eni.encheres.dal.interfaces.RetraitDAO;
import fr.eni.encheres.dal.interfaces.UtilisateurDAO;
import fr.eni.encheres.dal.jdbc.ArticleDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.CategorieDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.EnchereDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.RetraitDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.UtilisateurDAOJdbcImpl;

public class DAOFactory {

	public static UtilisateurDAO getUtilisateurDAO() {

		return new UtilisateurDAOJdbcImpl();

	}

	public static ArticleDAO getArticleDAO() {

		return new ArticleDAOJdbcImpl();

	}

	public static RetraitDAO getRetraitDAO() {

		return new RetraitDAOJdbcImpl();

	}

	public static EnchereDAO getEnchereDAO() {

		return new EnchereDAOJdbcImpl();

	}

	public static CategorieDAO getCategorieDAO() {

		return new CategorieDAOJdbcImpl();

	}
}
