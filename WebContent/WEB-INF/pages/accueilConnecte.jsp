<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="fr.eni.encheres.bll.ArticleManager"%>
<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<%@page import="fr.eni.encheres.bo.Article"%>
<%@page import="fr.eni.encheres.bo.Enchere"%>
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
<div></div>
<div class="headerAndForm">
<header>
<img src="${pageContext.request.contextPath}/resources/logo-eni.png" alt="logo-eni">
	<h1>ENI-Enchères</h1>
	<br>

	<div class="liens">
		<a href="${pageContext.request.contextPath}/nouvelleVente">Vendre un article    </a>
		<a href="${pageContext.request.contextPath}/profil?user=${connectedUser.getPseudo()}">Mon profil    </a>
		<a href="${pageContext.request.contextPath}/accueilConnecte?action=deconnexion">Déconnexion    </a>
	</div>
 
</header>


<!-- affichage de la liste des catégories -->
<form action="${pageContext.request.contextPath}/accueilConnecte" method="post">
	<h2>Liste des enchères</h2>
	<label for="txtSearch">Filtres : </label>
	<input type="search" name="search" id="txtSearch"><br><br>



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



		<div class="menuRadio">
			<div id="divAV">
			<p>Achats : </p>
		  	<input type="radio" name="rdoAV" value="encheresOuvertes" ${openAuction?'checked':''}>enchères ouvertes<br>
		  	<input type="radio" name="rdoAV" value="mesEncheres" ${myAuction?'checked':''}>mes enchères<br>
		  	<input type="radio" name="rdoAV" value="encheresRemportees" ${winAuction?'checked':''}>mes enchères remportées<br>
		  	</div>
		  	<div id="divAV">
			<p>Ventes : </p>
		  	<input type="radio" name="rdoAV" value="ventesEnCours" ${myCurrentSales?'checked':''}>mes ventes en cours<br>
		  	<input type="radio" name="rdoAV" value="ventesNonDebutees" ${notSartedSales?'checked':''}>ventes non débutées<br>
		  	<input type="radio" name="rdoAV" value="ventesTerminees" ${endedSales?'checked':''}>ventes terminées<br>
		  	</div>
		</div>
</form>
</div>	
<div></div>
<!-- affichage de la liste des articles -->


<% List<Article> listeEncheres = (List<Article>)request.getAttribute("listeEncheres");%>
<%! SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); %>
<% if (listeEncheres != null) {%>
<div class="articleContainer">
<%for (Article article : listeEncheres) {%>
<%if(article != null && (selectedCategorie.equals((String.valueOf(article.getCategorie().getNoCategorie()))) || (selectedCategorie).equals("0"))){%>
		<div class="article">
			<p><%=article.getNomArticle()%></p><br>
			<p>Description : <%=article.getDescription()%></p><br>
			<p>Prix : <%=article.getPrixInitial()%> points</p><br>
			<p>Fin de l'enchère : <%=dateFormat.format(article.getDateFinEncheres())%></p><br>
			<p>Vendeur : <a href="${pageContext.request.contextPath}/profil?user=<%=article.getUtilisateur().getPseudo()%>"><%=article.getUtilisateur().getPseudo()%></a></p><br>
		</div>
		<%}%>
		 <%}%>
<%}%>
</div>
</div>
</body>
</html>