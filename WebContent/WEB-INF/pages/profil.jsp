<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/profil.css">
<title>Profil utilisateur</title>
</head>
<body>
<header>
<h1>ENI Enchères</h1><br><br>
</header>
<form method="post" name="profil" action="${pageContext.request.contextPath}/profil">
                <div id="userProfile">
  				    <div><label for="pseudo"> Pseudo : </label>
                        <input class="input" name="pseudo" id="pseudo" type="text" required >
                    </div>
                    <div><label for="nom"> Nom :</label>
                        <input class="input" name="nom"  id="nom" type="text"  required>
                    </div>
                    <div><label for="prenom" > Prénom: : </label>
                        <input  class="input" name="prenom" id="prenom" type="text" required>
                    </div>
                    <div><label for="email"> Email : </label>
                        <input class="input" name="email" id="email" type="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}" title="de type nomprenom@domaine.fr" required>
                    </div>
                    <div><label for="telephone"> Téléphone : </label>
                        <input  class="input" name="telephone" id="telelphone" type="text" pattern="[0-9]{10}" title="sur 10 chiffres" required>
                    </div>
                    <div><label for="rue" > Rue : </label>
                        <input class="input" name="rue" id="rue" type="text"  required>
                    </div>
                    <div><label for="codepostal" > Code postal: </label>
                         <input class="input" name="codepostal" id="codepostal" type="text" pattern="[0-9]{5}" title="sur 5 chiffres" required>
                    </div>
                     <div><label for="ville" > Ville : </label>
                        <input class="input" name="ville" id="ville" type="text"  required>
                    </div>
                    <div>
                    	<button type="submit" name="btnModifier" id ="btnModifier"></button>
                    </div>
                </div>
</body>
</html>