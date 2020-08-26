package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.interfaces.EnchereDAO;

public class EnchereManager {
	private EnchereDAO eDAO;

	public EnchereManager() {
		eDAO = DAOFactory.getEnchereDAO();
	}

	public List<Article> userSearchEncheres(String research, String userPseudo, int idCategorie,
			Boolean shoppingChecked, Boolean openAuction, Boolean winAuction, Boolean myAuction, Boolean mySales,
			Boolean myCurrentSales, Boolean notSartedSales, Boolean endedSales) {
		List<Article> listeEncheres = null;
		try {
			listeEncheres = eDAO.articleSearchByUserRequest(research, userPseudo, idCategorie, shoppingChecked,
					openAuction, winAuction, myAuction, mySales, myCurrentSales, notSartedSales, endedSales);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeEncheres;
	}

}
