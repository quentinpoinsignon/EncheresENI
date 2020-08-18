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

<form action="${pageContext.request.contextPath}/encheres" method="post">
	<label for="identifiant">Identifiant : </label>
	<input type="text" name="identifiant" id="identifiant" required autofocus><br><br>
	<label for="password">Mot de Passe : </label>
	<input type="text" name="password" id="password" required><br><br>
	<button type="submit" name="connexion" id="connexion">Connexion</button>
	<input type="checkbox" name="rememberme" id="rememberme">
	<label for="rememberme">Se souvenir de moi</label><br><br>
	<a href="${pageContext.request.contextPath}/encheres?action=inscription">Mot de passe oublié ?</a><br><br>
	<button type="submit" name="creercompte" id="creercompte">Créer un compte</button>
</form>
</body>
</html>