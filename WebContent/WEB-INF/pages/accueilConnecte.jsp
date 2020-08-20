<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="fr.eni.encheres.bll.ArticleManager"%>
<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<%@page import="fr.eni.encheres.bo.Article"%>
<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@page import="fr.eni.encheres.bll.CategorieManager"%>


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




<a href="${pageContext.request.contextPath}/accueilConnecte?action=encheres">Enchères    </a>
<a href="${pageContext.request.contextPath}/accueilConnecte?action=vendre">Vendre un article    </a>
<a href="${pageContext.request.contextPath}/accueilConnecte?action=profil?user="">Mon profil    </a>
<a href="${pageContext.request.contextPath}/accueilConnecte?action=deconnexion?user="">S'inscrire    </a>

 
<h2>Liste des enchères</h2>
</header>

<form action="${pageContext.request.contextPath}/accueilConnecte" method="get">

	<label for="txtSearch">Filtres : </label>
	<input type="search" name="txtSearch" id="txtSearch"><br><br>

<!-- affichage de la liste des catégories -->
<%! CategorieManager cMger = new CategorieManager();%>
<%! List<Categorie> listeCategories = cMger.selectAllCategories();%>
	<label for="listCategories">Catégorie : </label>
	<select id="listCategories" name="listCategories">
			<option value="0" selected>Toutes</option>
			<%for(Categorie cat : listeCategories) {%>
			<option value=<%=cat.getNoCategorie()%>><%=cat.getLibelle()%></option>
			<%}%>
	</select><br><br>

	<button type= "submit" name="btnRechercher" id="btnRechercher" value="1">Rechercher</button><br><br>
	

<!-- EN CHANTIER : affichage conditionnel du menu radio + checkboxes Achats ou Ventes -->
<% String rdoAchatsVentes = (String)request.getAttribute("rdoAchatsVentes");%>
<% System.out.println(rdoAchatsVentes); %>


		<div>
		<div>
			<input type="radio" name="rdoSelectAchatsVentes" value="achats" name="rdoAchats">Achats<br>
		  	<input type="radio" name="rdoAchats" value="encheresOuvertes" checked="checked">enchères ouvertes<br>
		  	<input type="radio" name="rdoAchats" value="mesEncheres">mes enchères<br>
		  	<input type="radio" name="rdoAchats" value="encheresRemportees">mes enchères remportées<br>
		
		</div>
		<div>
			<input type="radio" name="rdoSelectAchatsVentes" value="ventes" name="rdoVentes">Ventes<br>
		  	<input type="radio" name="rdoVentes" value="ventesEnCours">mes ventes en cours<br>
		  	<input type="radio" name="rdoVentes" value="ventesNonDebutees">ventes non débutées<br>
		  	<input type="radio" name="rdoVentes" value="ventesTerminees">ventes terminées<br>
	  	</div>
		</div>


</form>

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
			<p>Vendeur : <a href="${pageContext.request.contextPath}/profil?user=<%=article.getUtilisateur().getPseudo()%>"><%=article.getUtilisateur().getPseudo()%></a></p><br>
		</div>
		<br><br>
		 <%}%>
<%}%>

</body>
</html>