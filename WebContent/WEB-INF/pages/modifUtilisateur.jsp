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
<form method="post" name="inscription" action="${pageContext.request.contextPath}/modifUtilisateur">
             <div class="data">
                <div style="float:left;width:50%;">
  				    <div><label for="pseudo"> Pseudo : </label>
                        <input class="input" name="pseudo" id="pseudo" type="text" value="${connectedUser.getPseudo()}" pattern="^[a-zA-Z0-9]*$" autofocus title="caractères alphanumériques uniquement" required>
                    </div>
                    <div><label for="prenom" > Prénom: : </label>
                        <input  class="input" name="prenom" id="prenom" type="text" value="${connectedUser.getPrenom()}" required>
                    </div>
                    <div><label for="telephone"> Téléphone : </label>
                        <input  class="input" name="telephone" id="telephone" type="text" value="${connectedUser.getTelephone()}" pattern="[0-9]{10}" title="sur 10 chiffres" required>
                    </div>
                    <div><label for="codepostal" > Code postal: </label>
                         <input class="input" name="codepostal" id="codepostal" type="text" value="${connectedUser.getCodePostal()}" pattern="[0-9]{5}" title="sur 5 chiffres" required>
                    </div>
                    <div><label for="oldPassword" > Mot de passe actuel: </label>
                         <input class="input" name="oldPassword"  id="oldPassword" type="password" pattern="\w.{8,30}" title="8 caractères minimum, 30 maximum">  
                    </div>
                    <div><label for="newPassword" > Nouveau mot de passe : </label>
                         <input class="input" name="newPassword"  id="newPassword" type="password" pattern="\w.{8,30}" title="8 caractères minimum, 30 maximum">  
                    </div>
                </div>
               
                <div style="float:left;width:50%;">
                    <div><label for="nom"> Nom :</label>
                        <input class="input" name="nom"  id="nom" type="text" value="${connectedUser.getNom()}" required>
                    </div>
                    <div><label for="email"> Email : </label>
                        <input class="input" name="email" id="email" type="email" value="${connectedUser.getEmail()}" title="de type: nomprenom@domaine.fr" required>
                    </div>
                    <div><label for="rue" > N°/Rue : </label>
                        <input class="input" name="rue" id="rue" type="text" value="${connectedUser.getRue()}" >
                    </div>
                     <div><label for="ville" > Ville : </label>
                        <input class="input" name="ville" id="ville" type="text" value="${connectedUser.getVille()}" required>
                    </div>
                    <br>
                    <div><label for="newPasswordConfirm" > Confirmation :  </label>
                        <input class="input" name="newPasswordConfirm" id="newPasswordConfirm" type="password" pattern="\w.{8,30}" title="8 caractères minimum, 30 maximum" >
                    </div>
                    <div>
                    <p>Crédit : '${connectedUser.getCredit()}'</p><br><br>
                    </div>
                </div>
					<div id="btnActions">
	                	<button type= "submit" name="btnAction" id="btnModifier" value="modifier">Modifier</button>
	                	<button type= "submit" name="btnAction" id="btnSupprimer" value="supprimer">Supprimer</button>
	                	${modifEffectuee ? '
	                	<button type= "submit" name="btnAction" id="btnRetour" value="retour">Retour</button>':
	                	'<button type= "submit" name="btnAction" id="btnAnnuler" value="annuler">Annuler</button>'}
					</div>
     			
</form>

					
</div>
</article>
<p>${erreurMotDePasse}</p>
<p>${msgModif}</p>

</body>
</html>