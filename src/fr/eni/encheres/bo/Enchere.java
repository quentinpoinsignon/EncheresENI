package fr.eni.encheres.bo;

import java.sql.Date;

public class Enchere {
	private int noEnchere;
	private Date dateEnchere;
	private int montantEnchere;
	private Article article;
	private Utilisateur acheteur;
	private Utilisateur vendeur;

	public Enchere(Date dateEnchere, int montantEnchere, Article article, Utilisateur acheteur, Utilisateur vendeur) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.article = article;
		this.acheteur = acheteur;
		this.vendeur = vendeur;
	}

	public int getNoEnchere() {
		return noEnchere;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	public void setNoEnchere(int noEnchere) {
		this.noEnchere = noEnchere;
	}

	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Utilisateur getUtilisateur() {
		return acheteur;
	}

	public void setUtilisateur(Utilisateur acheteur) {
		this.acheteur = acheteur;
	}

	@Override
	public String toString() {
		return "Enchere [noEnchere=" + noEnchere + ", dateEnchere=" + dateEnchere + ", montantEnchere=" + montantEnchere
				+ ", article=" + article + ", utilisateur=" + acheteur + "]";
	}

}
