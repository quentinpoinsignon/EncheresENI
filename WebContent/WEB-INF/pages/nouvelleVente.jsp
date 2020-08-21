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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/accueil.css">

<title>Nouvelle Vente</title>
</head>
<body>
<header>
<h1>ENI-Enchères </h1>
<h2>Nouvelle vente</h2>
</header>

<form method="post" name="nouvelleVente" action="${pageContext.request.contextPath}/nouvelleVente">
	
		
		<label for="nomArticle">Article:</label>
		<input class="input" name="nomArticle" id="nomArticle" type="text" value='${nom}' autofocus title="libellé article" size="30" required><br><br>
		
		<label for="description">Description:</label>
		<textarea name="description" cols="30" rows="5" value='${description}' required>Description de l'article</textarea><br><br>
		
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
		
		<!-- lien pour telecharger une photo -->
		<label for="photo">Photo de l'article:</label>
		<input type="file" id="photo" name="photo" accept="image/png, image/jpeg"><br><br>
		
		<label for="prixDepart">Mise à prix</label>
		<input type="number" id="prixDepart" name="prixDepart" value='${prixDepart}' min="10" max="1000"><br><br>
		
			
		<label for="dateDebut">Début de l'enchère</label>
		<input type="datetime-local" id="dateDebut" name="dateDebut" value='${dateDebut}'><br><br>
		
		<label for="dateFin">Fin de l'enchère</label>
		<input type="datetime-local" id="dateFin" name="dateFin" value='${dateFin}'><br><br>
	
		
		
		<fieldset>
  			<legend > Retrait </legend>
  			<label for="rue">Rue:</label>
			<input class="input" name="rue" id="rue" type="text" value='${rue}' size="30" required><br>
			<label for="codepostal">Code postal:</label>
			<input class="input" name="codepostal" id="codepostal" type="text" value='${codepostal}' size="30" required><br>
			<label for="ville">Ville:</label>
			<input class="input" name="ville" id="ville" type="text" value='${ville}' size="30" required>
		</fieldset><br><br>
		
		<div class="boutons"><button type= "submit" name="enregistrer" id="enregistrer" style="width:130px; height:30px" value="1">Enregistrer</button>
        <a href="accueil"> <input type="button" style="width:130px; height:30px" value="Annuler"> </a>
		</div>
      	
	
</form>
</body>
</html>