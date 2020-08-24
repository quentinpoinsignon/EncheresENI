<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login encheres</title>
<link rel="stylesheet" type="text/css" href="login.css">
</head>
<body>
	<div class="divPage">
	<header>
	<img src="${pageContext.request.contextPath}/resources/logo-eni.png" alt="logo-eni">
	<h1>ENI-Enchères</h1>
	</header>
	<br><br><br><br>
	
	<form action="${pageContext.request.contextPath}/login" method="post">
		<h2>Login</h2>
		<br>
		<label for="identifiant">Identifiant : </label><br>
		<input type="text" name="identifiant" id="identifiant autofocus title="Entrez votre pseudo ou email"><br><br>
		
		<label for="password">Mot de Passe : </label><br>
		<input type="password" name="password" id="password" ><br><br>
		
		<button type= "submit" name="action" value="connexion">Connexion</button>
		
		<input type="checkbox" name="rememberme" id="rememberme">
		<label for="rememberme">Se souvenir de moi</label><br><br>
		
		<a href="">Mot de passe oublié ?</a><br><br>
		
		<button type= "submit" name="action" value="creerCompte">Créer un compte ?</button>
		
		<button type="submit" name="action" id="btnAnnuler" value="annuler">Annuler</button>
		<br>
	</form>
 
	<p>${erreurLogin}</p>
	</div>  	
</body>
</html>