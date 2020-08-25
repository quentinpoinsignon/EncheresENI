package fr.eni.encheres.dal.interfaces;

public interface RetraitDAO {

	void insertWithdrawalPoint(int idArticle, String street, String town, String postaCode) throws Exception;

}
