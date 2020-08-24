package fr.eni.encheres.dal.interfaces;

import java.util.List;

import fr.eni.encheres.bo.Enchere;

public interface EnchereDAO {

	List<Enchere> articleSearchByUserRequest(String research, int idCategorie) throws Exception;

}
