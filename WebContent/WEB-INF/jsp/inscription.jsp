<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Création de compte</title>
</head>
<body>
	<div class="container text-center text-black">
		<h1>Mon profil</h1>
</div>
	<form action="connexion" method="post">
		<label for="identifiant" >Identifiant :</label> <input id="identifiant" type="text" name="identifiant">
		<br />
		<label for="mdp" >Mot de passe :</label> <input id="mdp" type="text" name="mdp">
		<br />
		<button>Connexion</button>
	</form>
</body>
</html>