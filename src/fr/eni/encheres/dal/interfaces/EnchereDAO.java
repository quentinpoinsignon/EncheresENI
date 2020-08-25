package fr.eni.encheres.dal.interfaces;

import java.util.List;

import fr.eni.encheres.bo.Enchere;

public interface EnchereDAO {

	List<Enchere> articleSearchByUserRequest(String research, int idCategorie, Boolean shoppingChecked,
			Boolean openAuction, Boolean WinAuction, Boolean myAuction, Boolean mySales, Boolean myCurrentSales,
			Boolean notSartedSales, Boolean endedSales) throws Exception;

}
