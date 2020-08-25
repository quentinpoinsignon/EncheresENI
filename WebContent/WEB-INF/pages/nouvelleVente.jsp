<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@page import="java.util.List"%>
    <%@page import="fr.eni.encheres.bll.CategorieManager"%>
    <%@page import="fr.eni.encheres.bo.Categorie"%>
    <%@page import="fr.eni.encheres.bll.ArticleManager"%>
    
        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/nouvelleVente.css">

<title>Nouvelle Vente</title>
</head>
<body>
<div class="divPage">
<header>
<img src="${pageContext.request.contextPath}/resources/logo-eni.png" alt="logo-eni">
<h1>ENI-Enchères </h1>
<br><br><br>
</header>

<form method="post" name="nouvelleVente" action="${pageContext.request.contextPath}/nouvelleVente">
		<h2>Nouvelle vente</h2>
		
		<label for="nomArticle">Article:</label>
		<input class="input" name="nomArticle" id="nomArticle" type="text" value="${nom}" autofocus title="libellé article" size="30" required><br><br>
		
		<label for="description">Description:</label>
		<textarea name="description" cols="30" rows="5" value="${description}" placeholder="description de l'article" required></textarea><br><br>
		
		<!-- affichage de la liste des catégories -->
		<%! CategorieManager cMger = new CategorieManager();%>
		<%! List<Categorie> listeCategories = cMger.selectAllCategories();%>
		<label for="listCategories">Catégorie : </label>
		<select id="listCategories" name="selectedCategorie">
			<%for(Categorie cat : listeCategories) {%>
			<option value=<%=cat.getNoCategorie()%>><%=cat.getLibelle()%></option>
			<%}%>
		</select><br><br>
		
		<!-- lien pour telecharger une photo -->
		<label for="photo">Photo de l'article:</label>
		<input type="file" id="photo" name="photo" accept="image/png, image/jpeg"><br><br>
		
		<label for="prixDepart">Mise à prix</label>
		<input type="number" id="prixDepart" name="prixDepart" value="${prixDepart}" min="10" max="1000"><br><br>
		
			
		<label for="dateDebut">Début de l'enchère</label>
		<input type="date" id="dateDebut" name="dateDebut" value="${dateDebut}"><br><br>
		
		<label for="dateFin">Fin de l'enchère</label>
		<input type="date" id="dateFin" name="dateFin" value="${dateFin}"><br><br>
	
		
		
		<fieldset>
  			<legend > Retrait </legend>
  			<label for="rue">Rue:</label>
			<input class="input" name="rue" id="rue" type="text" value="${rue}" size="30"><br>
			<label for="codepostal">Code postal:</label>
			<input class="input" name="codepostal" id="codepostal" type="text" value="${codepostal}" size="30"><br>
			<label for="ville">Ville:</label>
			<input class="input" name="ville" id="ville" type="text" value="${ville}" size="30">
		</fieldset><br><br>
		
		<div class="boutons">
		<button type= "submit" name="enregistrer" id="enregistrer" value="1">Enregistrer</button>
        <a href="accueilConnecte"> <input type="button" name="Annuler" value="Annuler"> </a>
		</div>
</form>
</div>
<p>${msg}</p>
</body>
</html>