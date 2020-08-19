package fr.eni.encheres.bll;

import java.sql.SQLException;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.interfaces.CategorieDAO;

public class CategorieManager {
		private CategorieDAO cDAO;
		
		public CategorieManager () {
			cDAO = DAOFactory.getCategorieDAO();
		}
		
		public List<Categorie> selectAllCategories() {
			List<Categorie> listeCategories = null;
			
				try {
					listeCategories = cDAO.listAllCategories();
				} catch (Exception e) {
					e.printStackTrace();
				}
			return listeCategories;
		}
}
