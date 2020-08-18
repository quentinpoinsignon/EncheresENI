package fr.eni.encheres.dal.interfaces;

import java.sql.SQLException;
import java.util.List;

import fr.eni.encheres.bo.Article;

public interface ArticleDAO {

	public List<Article> selectAllArticle() throws SQLException;

}
