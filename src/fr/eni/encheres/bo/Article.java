package fr.eni.encheres.bo;

import java.sql.Date;

public class Article {

	private int noArticle;
	String nomArticle;
	String description;
	Date dateDebutEncheres;
	Date dateFinEncheres;
	int prixInitial;
	int prixVente;
	Utilisateur utilisateur;
	Categorie categorie;
	int venteEffectuee;
	Retrait pointDeRetrait;
	Enchere enchere;

	public Article(String nomArticle, String description, Date dateDebutEncheres, Date dateFinEncheres, int prixInitial,
			int prixVente, Utilisateur utilisateur, Categorie categorie, int venteEffectuee) {
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.venteEffectuee = venteEffectuee;
	}

	public Article(int idArticle, String nomArticle, String description, Date dateDebutEncheres, Date dateFinEncheres,
			int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie) {

		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.noArticle = idArticle;
	}

	public Article(String nomArticle, String description, Date dateDebutEncheres, Date dateFinEncheres, int prixInitial,
			int prixVente, Utilisateur utilisateur, Categorie categorie) {

		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;

	}

	public Article(int idArticle, String nomArticle, String description, Date dateDebutEncheres, Date dateFinEncheres,
			int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie, Enchere enchere) {

		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.noArticle = idArticle;
		this.enchere = enchere;
	}

	public Article(int idArticle, String nomArticle, String description, int prixInitial, int prixVente,
			Utilisateur vendeur, Categorie categorie, Enchere enchere) {

		this.nomArticle = nomArticle;
		this.description = description;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = vendeur;
		this.categorie = categorie;
		this.enchere = enchere;
		this.noArticle = idArticle;

	}

	public Article(String nomArticle, String description, int prixInitial, int prixVente, Utilisateur vendeur,
			Categorie categorie, Enchere enchere) {

		this.nomArticle = nomArticle;
		this.description = description;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = vendeur;
		this.categorie = categorie;
		this.enchere = enchere;

	}

	public Article(String nomArticle, String description, Date dateDebutEncheres, Date dateFinEncheres, int prixInitial,
			int prixVente, Utilisateur utilisateur, Categorie categorie, Enchere enchere) {

		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.enchere = enchere;
	}

	public Article(String nomArticle, String description, Date dateDebutEncheres, Date dateFinEncheres, int prixInitial,
			int prixVente, Utilisateur utilisateur, Categorie categorie, Retrait pointDeRetrait) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.pointDeRetrait = pointDeRetrait;
	}

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(Date dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public Date getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(Date dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getPrixInitial() {
		return prixInitial;
	}

	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public int getVenteEffectuee() {
		return venteEffectuee;
	}

	public void setVenteEffectuee(int venteEffectuee) {
		this.venteEffectuee = venteEffectuee;
	}

	public Retrait getPointDeRetrait() {
		return pointDeRetrait;
	}

	public void setPointDeRetrait(Retrait pointDeRetrait) {
		this.pointDeRetrait = pointDeRetrait;
	}

	@Override
	public String toString() {
		return "Article [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", prixInitial="
				+ prixInitial + ", prixVente=" + prixVente + ", utilisateur=" + utilisateur + ", categorie=" + categorie
				+ ", venteEffectuee=" + venteEffectuee + "]";
	}

}
