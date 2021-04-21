<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<% String erreur = (String)request.getAttribute("erreur"); %>

<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
	<div class="container text-center">
		<h1>Mon profil</h1>
  		<form action="creercompte" method="post">
  		<div class="row">
    		<div class="col-sm">
      			<label for="pseudo" >Pseudo :</label> <input id="pseudo" type="text" name="pseudo">
      			<br />
      			<label for="prenom" >Prénom :</label> <input id="prenom" type="text" name="prenom">
      			<br />
      			<label for="telephone" >Téléphone :</label> <input id="telephone" type="text" name="telephone">
      			<br />
      			<label for="code_postal" >Code postal :</label> <input id="code_postal" type="text" name="code_postal">
      			<br />
      			<label for="mot_de_passe" >Mot de passe :</label> <input id="mot_de_passe" type="text" name="mot_de_passe">	
    		</div>
    		<div class="col-sm">
      			<label for="nom" >Nom :</label> <input id="nom" type="text" name="nom">
      			<br />
      			<label for="email" >Email :</label> <input id="email" type="text" name="email">
      			<br />
      			<label for="rue" >Rue :</label> <input id="rue" type="text" name="rue">
      			<br />
      			<label for="ville" >Ville :</label> <input id="ville" type="text" name="ville">
      			<br />
      			<label for="confirmation" >Confirmation :</label> <input id="confirmation" type="text" name="confirmation">
    		</div>
		</div>
		<div class="row">
			<div class="col-sm">
				<button name="bouton" value="creer">Créer</button>
			</div>
			<div class="col-sm">
				<button name="bouton" value="annuler">Annuler</button>
			</div>
		</div>
		</form>
		<c:if test="${ erreur != null }">
			<p class="text-danger"><%=erreur%></p>
		</c:if>
	</div>
</body>
</html>