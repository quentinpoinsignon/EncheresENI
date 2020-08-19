package fr.eni.encheres.dal.interfaces;

import java.util.List;

import fr.eni.encheres.bo.Categorie;

public interface CategorieDAO {

	public List<Categorie> listAllCategories() throws Exception;
}
