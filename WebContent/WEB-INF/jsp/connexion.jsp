<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
<%@ include file="/WEB-INF/fragments/navbar.jspf" %>
	<div class="container w-75 p-3">
		<div class="mx-auto text-center">
			<h1>Connexion</h1>
		</div>
		
		<c:if test="${ erreur != null }">
			<div class="alert alert-danger alert-dismissible fade show mx-auto col-md-10 offset-md-1 mt-3" role="alert">
			  <strong>Erreur !</strong> ${erreur}
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
		</c:if>
		
		<form action="connexion" method="post">
			<div class="row mt-5">
				<label for="identifiant" class="col-md-2 offset-md-3">Identifiant :</label>
				<input id="identifiant" class="col-md-4" type="text" name="identifiant" placeholder=" Pseudo ou Email" required>
			</div>
			<div class="row mt-2">
				<label for="mot_de_passe" class="col-md-2 offset-md-3 ">Mot de passe :</label>
				<input id="mot_de_passe" class="col-md-4" type="password" name="mot_de_passe" placeholder=" **********" required>
			</div>
			<div class="mt-4 d-flex flex-row">
				<div class="col-md-2 offset-md-4">
					<button class="btn btn-lg btn-primary justify-content-md-end">Connexion</button>
				</div>
				<div class="col-md-3">
					<div class="row"> 
						<input class="mr-1" type="checkbox" name="souvenir" id="souvenir">
						<label class="mr-1" for="souvenir"> Se souvenir de moi</label>
					</div>
					<div class="row">
						<a href="">Mot de passe oublié</a>
					</div>
				</div>
			</div> 
		</form>
		<form action="creercompte" method="get">
			<div class="row justify-content-center">
				<button class="btn btn-lg btn-secondary mt-5 w-50 p-3">Créer un compte</button>
			</div>
		</form>
	</div>
</body>
<%@ include file="/WEB-INF/fragments/script.html" %>
</html>