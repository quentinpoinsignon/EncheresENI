package fr.eni.encheres.bo;

/**
 * @author qpoinsig2020
 * @Commentaire BO Categorie 
 * @param noCategorie généré automatiquement par le SGBD en Identity
 * @param libelle nom de la catégorie
 * 
 */
public class Categorie {
	private int noCategorie;
	String libelle;

	public Categorie(String libelle) {
		this.libelle = libelle;
	}

	
	public int getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return "Categorie [noCategorie=" + noCategorie + ", libelle=" + libelle + "]";
	}

}
