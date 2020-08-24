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
<%@page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./accueilConnecte.css">
<title>Accueil enchères</title>
</head>
<body>
<div class="divPage">
<header>
<img src="${pageContext.request.contextPath}/resources/logo-eni.png" alt="logo-eni">
	<h1>ENI-Enchères</h1>
	<br>

	<div class="liens">
		<a href="${pageContext.request.contextPath}/accueilConnecte?action=encheres">Enchères    </a>
		<a href="${pageContext.request.contextPath}/accueilConnecte?action=nouvelleVente">Vendre un article    </a>
		<a href="${pageContext.request.contextPath}/profil?user=${connectedUser.getPseudo()}">Mon profil    </a>
		<a href="${pageContext.request.contextPath}/accueilConnecte?action=deconnexion">Déconnexion    </a>
	</div>
 
</header>

<form action="${pageContext.request.contextPath}/accueilConnecte" method="get">
	<h2>Liste des enchères</h2>
	<label for="txtSearch">Filtres : </label>
	<input type="search" name="txtSearch" id="txtSearch"><br><br>
</form>

<!-- affichage de la liste des catégories -->
<form action="${pageContext.request.contextPath}/accueilConnecte" method="post">
<%! CategorieManager cMger = new CategorieManager();%>
<%! List<Categorie> listeCategories = cMger.selectAllCategories();%>
<% String selectedCategorie = (String)request.getAttribute("selectedCategorie")==null?"0":(String)request.getAttribute("selectedCategorie");%>
	<label for="listCategories">Catégorie : </label>
	<select id="listCategories" name="selectedCategorie">
			<option value="0" <%=("0").equals(selectedCategorie) ? "selected":""%> %>Toutes</option>
			<%for(Categorie cat : listeCategories) {%>
			<option value=<%=cat.getNoCategorie()%> <%=String.valueOf(cat.getNoCategorie()).equals(selectedCategorie) ? " selected":""%>><%=cat.getLibelle()%></option>
			<%}%>
	</select><br><br>
	<button type= "submit" name="rechercher" id="btnRechercher" value="rechercher">Rechercher</button><br><br>
</form>

	

<!-- EN CHANTIER : affichage conditionnel du menu radio + checkboxes Achats ou Ventes -->
<%-- <% String rdoAchatsVentes = (String)request.getAttribute("rdoAchatsVentes");%> --%>


<!-- 		<div> -->
<!-- 		<div> -->
<!-- 			<input type="radio" name="rdoSelectAchatsVentes" value="achats" name="rdoAchats">Achats<br> -->
<!-- 		  	<input type="radio" name="rdoAchats" value="encheresOuvertes" checked="checked">enchères ouvertes<br> -->
<!-- 		  	<input type="radio" name="rdoAchats" value="mesEncheres">mes enchères<br> -->
<!-- 		  	<input type="radio" name="rdoAchats" value="encheresRemportees">mes enchères remportées<br> -->
		
<!-- 		</div> -->
<!-- 		<div> -->
<!-- 			<input type="radio" name="rdoSelectAchatsVentes" value="ventes" name="rdoVentes">Ventes<br> -->
<!-- 		  	<input type="radio" name="rdoVentes" value="ventesEnCours">mes ventes en cours<br> -->
<!-- 		  	<input type="radio" name="rdoVentes" value="ventesNonDebutees">ventes non débutées<br> -->
<!-- 		  	<input type="radio" name="rdoVentes" value="ventesTerminees">ventes terminées<br> -->
<!-- 	  	</div> -->
<!-- 		</div> -->



<!-- affichage de la liste des articles -->
<div class="article">
<%! ArticleManager aMger = new ArticleManager();%>
<%! List<Article> listeArticles = aMger.selectTop3Articles();%>
<%! String formatDate = "dd/mm/yyyy H:m"; %>
<%! DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate); %>
<% if (listeArticles != null) {%>
<%for (Article article : listeArticles) {%>
<%if(article != null && (selectedCategorie.equals((String.valueOf(article.getCategorie().getNoCategorie()))) || (selectedCategorie).equals("0"))){%>
		<div id=listArticles>
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