package fr.eni.encheres.dal.interfaces;

import java.sql.SQLException;
import java.util.List;

import fr.eni.encheres.bo.Article;

public interface ArticleDAO {

	List<Article> selectAllArticle() throws SQLException, Exception;

	List<Article> selectAllArticleInDatabase() throws Exception;

	void insertNewArticle(Article article) throws Exception;

	List<Article> articleSearchByText(String Research) throws Exception;

}
