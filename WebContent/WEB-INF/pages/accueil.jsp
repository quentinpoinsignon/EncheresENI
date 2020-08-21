<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="fr.eni.encheres.bll.ArticleManager"%>
<%@page import="fr.eni.encheres.bll.CategorieManager"%>
<%@page import="fr.eni.encheres.bo.Article"%>
<%@page import="fr.eni.encheres.bo.Categorie"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/accueil.css">
<title>Accueil encheres</title>
</head>
<body>
<header>
<h1>ENI Enchères</h1>

<a href="${pageContext.request.contextPath}/encheres?action=inscription">S'inscrire - </a>
<a href="${pageContext.request.contextPath}/encheres?action=login">Se connecter</a>
 
<h2>Liste des enchères</h2>
</header>
<form action="${pageContext.request.contextPath}/accueil">

	<label for="txtSearch">Filtres : </label>
	<input type="search" name="txtSearch" id="txtSearch"><br><br>
	
	

</form>

<form action="${pageContext.request.contextPath}/accueil" method="post">

<!-- affichage de la liste des catégories -->
<%! CategorieManager cMger = new CategorieManager();%>
<%! List<Categorie> listeCategories = cMger.selectAllCategories();%>
	<label for="listCategories">Catégorie : </label>
	<select id="listCategories" name="selectedCategorie">
			<option value="0" selected>Toutes</option>
			<%for(Categorie cat : listeCategories) {%>
			<%System.out.println(cat.getNoCategorie());%>
			<option value=<%=cat.getNoCategorie()%>><%=cat.getLibelle()%></option>
			<%}%>
	</select><br><br>
	<button type= "submit" name="btnRechercher" id="btnRechercher" value="1">Rechercher</button><br><br>
	
<!-- affichage de la liste des articles -->
<%! ArticleManager aMger = new ArticleManager();%>
<%! List<Article> listeArticles = aMger.selectAllArticles();%>
<%! String formatDate = "dd/mm/yyyy"; %>
<%! DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate); %>
<% if (listeArticles != null) {%>
<%for (Article article : listeArticles) {%>
		<div id=listArticles>
			<p><%=article.getNomArticle()%></p><br>
			<p>Prix : <%=article.getPrixInitial()%> points</p><br>
			<p>Fin de l'enchère : <%=article.getDateFinEncheres().toString()%></p><br>
			<p>Vendeur : <%=article.getUtilisateur().getPseudo()%></p><br>
		</div>
		<br><br>
		 <%}%>
<%}%>
</form>

</body>
</html>