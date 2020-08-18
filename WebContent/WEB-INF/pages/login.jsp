<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login encheres</title>
</head>
<body>
<h1>ENI-Enchères</h1>

<form action="${pageContext.request.contextPath}/login" method="post">
	<label for="identifiant">Identifiant : </label>
	<input type="text" name="identifiant" id="identifiant" autofocus><br><br>
	<label for="password">Mot de Passe : </label>
	<input type="password" name="password" id="password" ><br><br>
	<button type="submit" name="connexion" id="connexion">Connexion</button>
	<input type="checkbox" name="rememberme" id="rememberme">
	<label for="rememberme">Se souvenir de moi</label><br><br>
	<a href="${pageContext.request.contextPath}/encheres?action=inscription">Mot de passe oublié ?</a><br><br>
<!-- 	<button type="submit" name="creercompte" id="creercompte" value="1">Créer un compte ?</button> -->
  	<button name="creercompte" id="creercompte" value="1">Créer un compte ?</button>
</form>
</body>
</html>