<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%
String erreur = (String) request.getAttribute("erreur");
%>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp">
	<jsp:param value="./css/monProfil.css" name="style" />
</jsp:include>
<body>
	<%@ include file="/WEB-INF/fragments/navbar.jspf"%>
	<div class="container">
		<div class="mx-auto text-center">
			<h1 class="mb-5">Mon profil</h1>
		</div>
		<c:if test="${ erreur != null }">
			<div class="alert alert-danger alert-dismissible fade show mx-auto col-md-10 offset-md-1 mt-3" role="alert">
				<strong>Erreur !</strong>
				<%=erreur%>
				<button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:if>
		<form action="monProfil" method="post">
			<div class="form-row justify-content-center">
				<div class="form-group row col-md-6">
					<label for="pseudo" class="col-5 col-md-4">Pseudo :</label> <input id="pseudo" class="col-7 col-md-6" type="text" name="pseudo" value="${ utilisateur.getPseudo() }">
				</div>
				<div class="form-group row col-md-6">
					<label for="nom" class="col-5 col-md-4">Nom :</label> <input id="nom" class="col-7 col-md-6" type="text" name="nom" value="${ utilisateur.getNom() }">
				</div>
			</div>
			<div class="form-row justify-content-center">
				<div class="form-group row col-md-6">
					<label for="prenom" class="col-5 col-md-4">Prénom :</label> <input id="prenom" class="col-7 col-md-6" type="text" name="prenom" value="${ utilisateur.getPrenom() }">
				</div>
				<div class="form-group row col-md-6">
					<label for="email" class="col-5 col-md-4">Email :</label> <input id="email" class="col-7 col-md-6" type="email" name="email" value="${ utilisateur.getEmail() }">
				</div>
			</div>
			<div class="form-row justify-content-center">
				<div class="form-group row col-md-6">
					<label for="telephone" class="col-5 col-md-4">Téléphone :</label> <input id="telephone" class="col-7 col-md-6" type="text" name="telephone" value="${ utilisateur.getTelephone() }">
				</div>
				<div class="form-group row col-md-6">
					<label for="rue" class="col-5 col-md-4">Rue :</label> <input id="rue" class="col-7 col-md-6" type="text" name="rue" value="${ utilisateur.getRue() }">
				</div>
			</div>
			<div class="form-row justify-content-center">
				<div class="form-group row col-md-6">
					<label for="code_postal" class="col-5 col-md-4">Code postal :</label> <input id="code_postal" class="col-7 col-md-6" type="text" name="code_postal" value="${ utilisateur.getCode_postal() }">
				</div>
				<div class="form-group row col-md-6">
					<label for="ville" class="col-5 col-md-4">Ville :</label> <input id="ville" class="col-7 col-md-6" type="text" name="ville" value="${ utilisateur.getVille() }">
				</div>
			</div>
			<div class="form-row justify-content-center">
				<div class="form-group row col-md-6">
					<label for="mot_de_passe" class="col-5 col-md-4">Nouveau mot de passe :</label> <input id="mot_de_passe" class="col-7 col-md-6" type="password" name="mot_de_passe">
				</div>
				<div class="form-group row col-md-6">
					<label for="confirmation" class="col-5 col-md-4">Confirmation du mot de passe:</label> <input id="confirmation" class="col-7 col-md-6" type="password" name="confirmation">
				</div>
			</div>
			<div class="form-row justify-content-center">
				<div class="form-group row col-12 ml-md-2">
					<label class="col-12">Crédit : &nbsp;&nbsp;&nbsp;&nbsp; ${ utilisateur.getCredit() }</label>
				</div>
			</div>
			<div class="row justify-content-center">
				<button type="submit" class="btn btn-primary mr-1">Enregistrer</button>
				<button type="button" class="btn btn-danger ml-1" data-bs-toggle="modal" data-bs-target="#suppression">Supprimer mon compte</button>
			</div>
		</form>
	</div>
	<div class="modal fade" id="suppression" tabindex="-1" role="dialog" aria-labelledby="suppressionLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="suppressionLabel">Suppression du compte</h5>
					<button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					Êtes-vous sur de vouloir supprimer votre compte ?<br /> (Cette action est irreversible)
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal">Anuller</button>
					<form action="suppression" method="get">
						<button type="submit" class="btn btn-danger">Supprimer</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<%@ include file="/WEB-INF/fragments/script.html"%>
</html>