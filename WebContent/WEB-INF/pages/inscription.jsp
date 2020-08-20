<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="accueil.css">
<title>Inscription encheres</title>
</head>
<body>
<header>
<h1>ENI Encheres</h1>
</header>
<br>
<article>
<h2>Mon profil</h2>
<form method="post" name="inscription" action="${pageContext.request.contextPath}/inscription">
             <div class="data">
                <div style="float:left;width:50%;">
  				    <div><label for="pseudo"> Pseudo : </label>
                        <input class="input" name="pseudo" id="pseudo" type="text" value="${pseudo}" pattern="^[a-zA-Z0-9]*$"
                        autofocus title="caractères alphanumériques uniquement" required >
                    </div>
                    <div><label for="prenom" > Prénom: : </label>
                        <input  class="input" name="prenom" id="prenom" type="text" value="${prenom}" required>
                    </div>
                    <div><label for="telephone"> Téléphone : </label>
                        <input  class="input" name="telephone" id="telelphone" type="text" value="${telephone}" pattern="[0-9]{10}" title="sur 10 chiffres" placeholder="sur 10 chiffres" required>
                    </div>
                    <div><label for="codepostal" > Code postal: </label>
                         <input class="input" name="codepostal" id="codepostal" type="text" value="${codepostal}" pattern="[0-9]{5}" title="sur 5 chiffres" placeholder="sur 5 chiffres" required>
                    </div>
                    <div><label for="password" > Mot de passe: </label>
                         <input class="input" name="password"  id="password" type="password" pattern="\w{8}" title="8 caractères" placeholder="8 caractères impératif"  required>  
                    </div>
                </div>
               
                <div style="float:left;width:50%;">
                    <div><label for="nom"> Nom :</label>
                        <input class="input" name="nom"  id="nom" type="text" value="${nom}" required>
                    </div>
                    <div><label for="email"> Email : </label>
                        <input class="input" name="email" id="email" type="email" value="${email}" title="de type: nomprenom@domaine.fr" placeholder="nomprenom@domaine.fr" required>
                    </div>
                    <div><label for="rue" > N°/Rue : </label>
                        <input class="input" name="rue" id="rue" type="text" value="${rue}" required>
                    </div>
                     <div><label for="ville" > Ville : </label>
                        <input class="input" name="ville" id="ville" type="text" value="${ville}" required>
                    </div>
                    <div><label for="confirmation" > Confirmation :  </label>
                        <input class="input" name="confirmation" id="confirmation" type="password" pattern="\w{8}" title="8 caractères" placeholder="8 caractères impératif" required>
                    </div>
                </div>
     			
                	<div><button type= "submit" name="creer" id="creer" value="1">Créer</button>
                	<a href="encheres"> <input type="button" value="Annuler"> </a>
					</div>
           </div>
</form>
</article>
<p>${erreurPseudo}</p>
<p>${erreurEmail}</p>
<p>${erreurMotDePasse}</p>


</body>
</html>