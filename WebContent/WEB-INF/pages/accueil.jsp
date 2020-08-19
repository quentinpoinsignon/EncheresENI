<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="fr.eni.encheres.bll.ArticleManager"%>
<%@page import="fr.eni.encheres.bo.Article"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="styles.css">
<title>Accueil encheres</title>
</head>
<body>
<header>
<h1>ENI Encheres</h1>

<a href="${pageContext.request.contextPath}/encheres?action=inscription">S'inscrire - </a>
<a href="${pageContext.request.contextPath}/encheres?action=login">Se connecter</a>
 
<h2>Liste des enchères</h2>
</header>
<form action="${pageContext.request.contextPath}/accueil">

	<label for="txtSearch">Filtres : </label>
	<input type="search" name="txtSearch" id="txtSearch"><br><br>
	
	<label for="listCategories">Catégorie : </label>
	<select id="listCategories" name="listCategories">
			<option value="0" selected disabled hidden>Toutes</option>
			<option value="1">Informatique</option>
			<option value="2">Ameublement</option>
			<option value="2">Vêtement</option>
			<option value="3">Sports&Loisirs</option>
	</select><br><br>

	<button type= "submit" name="btnRechercher" id="btnRechercher" value="1">Rechercher</button><br><br>
</form>

<!-- affichage de la liste des articles -->
<%! ArticleManager aMger = new ArticleManager();%>
<%! List<Article> listeArticles = aMger.selectTop3Articles();%>

<% for (Article article : listeArticles) {
		out.println(article.getNomArticle());
		} %>


</body>
</html>