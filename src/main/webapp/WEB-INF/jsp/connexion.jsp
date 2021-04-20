<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connexion</title>
</head>
<body>
	<form action="connexion" method="post">
		<label for="identifiant" >Identifiant :</label> <input id="identifiant" type="text" name="identifiant">
		<br />
		<label for="mdp" >Mot de passe :</label> <input id="mdp" type="text" name="mdp">
		<button>Connexion</button>
		<input type="checkbox" name="souvenir" id="souvenir"> <label for="souvenir">Se souvenir de moi</label>
		<a href="">Mot de passe oublié</a> 
	</form>
</body>
</html>