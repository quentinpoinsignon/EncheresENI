package fr.eni.encheres.dal.interfaces;

import java.util.List;

import fr.eni.encheres.bo.Article;

public interface EnchereDAO {

	List<Article> articleSearchByUserRequest(String research, String userPseudo, int idCategorie, Boolean openAuction,
			Boolean WinAuction, Boolean myAuction, Boolean myCurrentSales, Boolean notSartedSales, Boolean endedSales)
			throws Exception;

}
