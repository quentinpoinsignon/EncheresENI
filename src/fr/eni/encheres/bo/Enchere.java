package fr.eni.encheres.bo;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Enchere {
	private int noEnchere;
	private LocalDateTime dateEnchere;
	private int montantEnchere;
	private Article article;
	private Utilisateur utilisateur;

	
	public Enchere(LocalDateTime dateEnchere, int montantEnchere, Article article, Utilisateur utilisateur) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.article = article;
		this.utilisateur = utilisateur;
	}


	public int getNoEnchere() {
		return noEnchere;
	}


	public void setNoEnchere(int noEnchere) {
		this.noEnchere = noEnchere;
	}


	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}


	public void setDateEnchere(LocalDateTime dateEnchere) {
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
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}


	@Override
	public String toString() {
		return "Enchere [noEnchere=" + noEnchere + ", dateEnchere=" + dateEnchere + ", montantEnchere=" + montantEnchere
				+ ", article=" + article + ", utilisateur=" + utilisateur + "]";
	}




}
