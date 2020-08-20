package fr.eni.encheres.bll;

import java.sql.SQLException;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.interfaces.ArticleDAO;

/**
 * @author qpoinsig2020
 * Classe de la bll gérant les articles
 */

public class ArticleManager {
	private ArticleDAO aDAO;
	
	public ArticleManager () {
		aDAO = DAOFactory.getArticleDAO();
	}
	
	public List<Article> selectTop3Articles() {
		List<Article> top3Articles = null;
		
			try {
				top3Articles = aDAO.selectAllArticle();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		return top3Articles;
	}
	
	public List<Article> selectAllArticles() {
		List<Article> allArticles = null;
		
		try {
			allArticles = aDAO.selectAllArticleInDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return allArticles;
	}
	
	
}
