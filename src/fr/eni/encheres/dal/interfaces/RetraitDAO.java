package fr.eni.encheres.dal.interfaces;

import fr.eni.encheres.bo.Article;

public interface RetraitDAO {

	void insertWithdrawalPoint(Article newArticle) throws Exception;

}
