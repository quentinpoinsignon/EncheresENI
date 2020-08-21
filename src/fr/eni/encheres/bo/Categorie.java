package fr.eni.encheres.bo;

public class Categorie {
	private int noCategorie;
	String libelle;

	public Categorie(String libelle) {
		this.libelle = libelle;
	}

	public Categorie(int idCategorie, String libelle) {
		this.noCategorie = idCategorie;
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
