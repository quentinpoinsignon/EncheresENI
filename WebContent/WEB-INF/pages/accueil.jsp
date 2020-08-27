<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="fr.eni.encheres.bll.ArticleManager"%>
<%@page import="fr.eni.encheres.bll.CategorieManager"%>
<%@page import="fr.eni.encheres.bo.Article"%>
<%@page import="fr.eni.encheres.bo.Enchere"%>
<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@page session="true"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./accueil.css">
<title>Accueil encheres</title>
</head>
<body>
<div class="divPage">
<div></div>
<div class="headerAndForm">
<header>
	<img src="${pageContext.request.contextPath}/resources/logo-eni.png" alt="logo-eni">
	<h1>ENI-Enchères</h1>
	<br>
	<div class="liens">
	<a href="${pageContext.request.contextPath}/encheres?action=inscription">S'inscrire</a>
	<a href="${pageContext.request.contextPath}/encheres?action=login">Se connecter</a>
	</div>
</header>

<!-- affichage de la liste des catégories -->
<form action="${pageContext.request.contextPath}/accueil" method="post">
<h2>Liste des enchères</h2>
<label for="txtSearch">Filtres : </label>
<input type="search" name="search" id="txtSearch" placeholder="Le nom de l'article contient"><br><br>
<%! CategorieManager cMger = new CategorieManager();%>
<%! List<Categorie> listeCategories = cMger.selectAllCategories();%>
<% String selectedCategorie = (String)request.getAttribute("selectedCategorie")==null?"0":(String)request.getAttribute("selectedCategorie");%>
	<label for="listCategories">Catégorie : </label>
	<select id="listCategories" name="selectedCategorie">
			<option value="0" <%=("0").equals(selectedCategorie) ? "selected":""%>>Toutes</option>
			<%for(Categorie cat : listeCategories) {%>
			<option value=<%=cat.getNoCategorie()%> <%=String.valueOf(cat.getNoCategorie()).equals(selectedCategorie) ? " selected":""%>><%=cat.getLibelle()%></option>
			<%}%>
	</select><br><br>
	<button type= "submit" name="rechercher" id="btnRechercher" value="rechercher">Rechercher</button><br><br>
</form>
</div>
<div></div>
<!-- affichage de la liste des articles -->


<% List<Article> listeEncheres = (List<Article>)request.getAttribute("listeEncheres");%>
<%! String formatDate = "dd/mm/yyyy H:m"; %>
<%! DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate); %>
<% if (listeEncheres != null) {%>
<div class="articleContainer">
<%for (Article article : listeEncheres) {%>
<%if(article != null && (selectedCategorie.equals((String.valueOf(article.getCategorie().getNoCategorie()))) || (selectedCategorie).equals("0"))){%>
		<div class="article">
			<p><%=article.getNomArticle()%></p><br>
			<p>Prix : <%=article.getPrixInitial()%> points</p><br>
			<p>Fin de l'enchère : <%=article.getDateFinEncheres().toString()%></p><br>
			<p>Vendeur : <%=article.getUtilisateur().getPseudo()%></p><br>
		</div>
		<%}%>
		 <%}%>
<%}%>
</div>
</div>
</body>
</html>