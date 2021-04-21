<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
</head>
<body>

<div class="container text-center text-black">
		<h1>Connexion</h1>
</div>

	<form action="connexion" method="post">
		<label for="identifiant" >Identifiant :</label> <input id="identifiant" type="text" name="identifiant">
		<br />
		<label for="mdp" >Mot de passe :</label> <input id="mdp" type="text" name="mdp">
		<br />
		<button>Connexion</button>
		<input type="checkbox" name="souvenir" id="souvenir"> <label for="souvenir">Se souvenir de moi</label>
		<a href="">Mot de passe oublié</a> 
	</form>
	<form action="inscription" method="get">
		<button>Créer un compte</button>
	</form>
</body>
<%@ include file="/WEB-INF/fragments/footer.html" %>
</html>