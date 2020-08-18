<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="styles.css">
<title>Inscription encheres</title>
</head>
<body>
<header>
<h1>ENI Encheres</h1>
</header>
<br>
<article>
<h2>Mon profil</h2>
<form method="post" name="inscription">
             <div class="data">
                <div style="float:left;width:50%;">
  				    <div><label for="pseudo"> Pseudo : </label>
                        <input class="input" name="pseudo"  id="pseudo" type="text" required>
                    </div>
                    <div><label for="prenom" > Prénom: : </label>
                        <input  class="input" name="prenom" id="prenom" type="text" required>
                    </div>
                    <div><label for="telephone"> Téléphone : </label>
                        <input  class="input" name="telephone" id="telelphone" type="text" required>
                    </div>
                    <div><label for="codepostal" > Code postal: </label>
                         <input class="input" name="codepostal" id="codepostal" type="text" required>
                    </div>
                    <div><label for="password" > Mot de passe: </label>
                         <input class="input" name="password"  id="password" type="password" required>  
                    </div>
                </div>
               
                <div style="float:left;width:50%;">
                    <div><label for="nom"> Nom :</label>
                        <input class="input" name="nom"  id="nom" type="text" required>
                    </div>
                    <div><label for="email"> Email : </label>
                        <input class="input" name="email" id="email" type="email" required>
                    </div>
                    <div><label for="rue" > Rue : </label>
                        <input class="input" name="rue" id="rue" type="text" required>
                    </div>
                     <div><label for="ville" > Ville : </label>
                        <input class="input" name="ville" id="ville" type="text" required>
                    </div>
                    <div><label for="confirmation" > Confirmation :  </label>
                        <input class="input" name="confirmation" id="confirmation" type="password" required>
                    </div>
                </div>
           </div>
           <p>
           		<input type="submit" name="creer" value="Creer"/>
           		<a href="encheres"> <input type="button" value="Annuler"> </a>
           </p>
</form>
</article>
</body>
</html>