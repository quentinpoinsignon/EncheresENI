<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="inscription.css">
<title>Inscription encheres</title>
</head>
<body>
<div class="divPage">
	<header>
		<img src="${pageContext.request.contextPath}/resources/logo-eni.png" alt="logo-eni">
		<h1>ENI Encheres</h1>
	</header>
	<br><br>
	<br><br>
<article>
<form method="post" name="inscription" action="${pageContext.request.contextPath}/inscription">
	<h2>Mon profil</h2>
             <div class="data">
                <div style="float:left;width:50%;">
  				    <div><label for="pseudo"> Pseudo : </label>
                        <input class="input" name="pseudo" id="pseudo" type="text" value='${pseudo}' pattern="^[a-zA-Z0-9]*$"
                        autofocus title="caractères alphanumériques uniquement" size="30" required > <br>
                    </div>
                    <div><label for="prenom" > Prénom: : </label>
                        <input  class="input" name="prenom" id="prenom" type="text" value='${prenom}' size="30" required> <br>
                    </div>
                    <div><label for="telephone"> Téléphone : </label>
                        <input  class="input" name="telephone" id="telephone" type="text" value='${telephone}' pattern="[0-9]{10}"
                        title="sur 10 chiffres" placeholder="sur 10 chiffres" size="30" required> <br>
                    </div>
                    <div><label for="codepostal" > Code postal: </label>
                         <input class="input" name="codepostal" id="codepostal" type="text" value='${codepostal}'
                         pattern="[0-9]{5}" title="sur 5 chiffres" placeholder="sur 5 chiffres" size="30" required> <br>
                    </div>
                    <div><label for="password" > Mot de passe: </label>
                         <input class="input" name="password"  id="password" type="password" value="" pattern="\w.{8,30}" title="8 caractères minimum, 30 maximum" size="31" required>  <br>
                    </div>
                </div>
               
                <div style="float:left;width:50%;">
                    <div><label for="nom"> Nom :</label>
                        <input class="input" name="nom"  id="nom" type="text" value='${nom}' size="30" required>
                    </div>
                    <div><label for="email"> Email : </label>
                        <input class="input" name="email" id="email" type="email" value='${email}' title="de type: nomprenom@domaine.fr"
                        placeholder="nomprenom@domaine.fr" size="30" required>
                    </div>
                    <div><label for="rue" > N°/Rue : </label>
                        <input class="input" name="rue" id="rue" type="text" value='${rue}' size="30" required>
                    </div>
                     <div><label for="ville" > Ville : </label>
                        <input class="input" name="ville" id="ville" type="text" value='${ville}' size="30" required>
                    </div>
                    <div><label for="confirmation" > Confirmation :  </label>
                        <input class="input" name="confirmation" id="confirmation" type="password" value="" pattern="\w.{8,30}" title="8 caractères minimum, 30 maximum" size="31" required>
                    </div>
                    <br><br>
                </div>
					<div class="btnActions">
                	<button type= "submit" name="action" id="creer" value="creer">Créer</button>
                	<a href="accueil"> <input type="button" value="Annuler"> </a>
                	</div>
           </div>
           		<div>
           		<p style="color:red"> ${erreurPseudo}</p>
				<p style="color:red"> ${erreurEmail}</p>
				<p style="color:red"> ${erreurMotDePasse}</p>
				</div>
</form>
</article>
</div>
</body>
</html>