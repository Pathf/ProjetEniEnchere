<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<% String erreur = (String)request.getAttribute("erreur"); %>

<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
	<%@ include file="/WEB-INF/fragments/navbar.jspf" %>
	<div class="container w-75 p-3">
		<div class="mx-auto text-center ">
			<h1>Mon profil</h1>
		</div>
		
		<c:if test="${ erreur != null }">
			<div class="alert alert-danger alert-dismissible fade show mx-auto col-md-10 offset-md-1 mt-3" role="alert">
			  <strong>Erreur !</strong> ${erreur}
			  <button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
		</c:if>
		
  		<form action="creercompte" method="post">
  			<div class="row mt-5">
				<label for="pseudo" class="col-md-2 mb-3 offset-md-1">Pseudo :</label>
				<input id="pseudo" class="col-md-3 mb-3" type="text" name="pseudo" placeholder="Jules">
	   			<label for="nom" class="col-md-2 mb-3 ml-3">Nom :</label> 
	   			<input id="nom" class="col-md-3 mb-3" type="text" name="nom" placeholder="Verne">
	   		</div>
	   		<div class="row">
	   			<label for="prenom" class="col-md-2 mb-3 offset-md-1">Prénom :</label> 
	   			<input id="prenom" class="col-md-3 mb-3" type="text" name="prenom" placeholder="Gabriel">
	   			<label for="email" class="col-md-2 mb-3 ml-3">Email :</label> 
	   			<input id="email" class="col-md-3 mb-3" type="email" name="email" placeholder="jules.verne@nantes.fr">
	   		</div>
	   		<div class="row">
	   			<label for="telephone" class="col-md-2 mb-3 offset-md-1">Téléphone :</label> 
	   			<input id="telephone" class="col-md-3 mb-3" type="text" name="telephone" placeholder="08.02.18.28.44">
	   			<label for="rue" class="col-md-2 mb-3 ml-3">Rue :</label> 
	   			<input id="rue" class="col-md-3 mb-3" type="text" name="rue" placeholder="4 de la rue Olivier-de-Clisson">
	   		</div>
	   		<div class="row">
	   			<label for="code_postal" class="col-md-2 mb-3 offset-md-1">Code postal :</label> 
	   			<input id="code_postal" class="col-md-3 mb-3" type="text" name="code_postal" placeholder="44000">
	   			<label for="ville" class="col-md-2 mb-3 ml-3">Ville :</label> 
	   			<input id="ville" class="col-md-3 mb-3" type="text" name="ville" placeholder="Nantes">
	   		</div>
	   		<div class="row">
	   			<label for="mot_de_passe" class="col-md-2 mb-3 offset-md-1">Mot de passe :</label> 
	   			<input id="mot_de_passe" class="col-md-3 mb-3" type="password" name="mot_de_passe" placeholder="**********">
	  			<label for="confirmation" class="col-md-2 mb-3 ml-3">Confirmation :</label> 
	  			<input id="confirmation" class="col-md-3 mb-3" type="password" name="confirmation" placeholder="**********">
	   		</div>
	   		<div class="row justify-content-center">
				<button name="bouton" class="btn btn-lg btn-primary mr-1 " value="creer">Créer profil</button>
				<button name="bouton" class="btn btn-lg btn-danger ml-1" value="annuler">Annuler</button>
			</div>
		</form>
	</div>
</body>
<%@ include file="/WEB-INF/fragments/script.html" %>
</html>