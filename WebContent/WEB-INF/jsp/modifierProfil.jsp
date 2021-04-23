<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<% String erreur = (String)request.getAttribute("erreur"); %>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp">
	<jsp:param value="./css/monProfil.css" name="style"/>
</jsp:include>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	  <div class="container-fluid">
	    <a class="navbar-brand" href="<%=request.getContextPath()%>">DUH - Encheres</a>
	  </div>
	</nav>
	<div class="container">
		<div class="mx-auto text-center">
			<h1>Mon profil</h1>
		</div>
		
		<c:if test="${ erreur != null }">
			<div class="alert alert-danger alert-dismissible fade show mx-auto col-md-10 offset-md-1 mt-3" role="alert">
			  <strong>Erreur !</strong> <%=erreur%>
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
		</c:if>
		
  		<form action="monProfil" method="post">
	   		<div class="row mt-5">
				<label for="pseudo" class="col-md-2 mb-3 offset-md-1">Pseudo :</label>
				<input id="pseudo" class="col-md-3 mb-3" type="text" name="pseudo" value="${ utilisateur.getPseudo() }">
	   			<label for="nom" class="col-md-2 mb-3 ml-3">Nom :</label> 
	   			<input id="nom" class="col-md-3 mb-3" type="text" name="nom" value="${ utilisateur.getNom() }">
	   		</div>
	   		<div class="row">
	   			<label for="prenom" class="col-md-2 mb-3 offset-md-1">Prénom :</label> 
	   			<input id="prenom" class="col-md-3 mb-3" type="text" name="prenom" value="${ utilisateur.getPrenom() }">
	   			<label for="email" class="col-md-2 mb-3 ml-3">Email :</label> 
	   			<input id="email" class="col-md-3 mb-3" type="email" name="email" value="${ utilisateur.getEmail() }">
	   		</div>
	   		<div class="row">
	   			<label for="telephone" class="col-md-2 mb-3 offset-md-1">Téléphone :</label> 
	   			<input id="telephone" class="col-md-3 mb-3" type="text" name="telephone" value="${ utilisateur.getTelephone() }">
	   			<label for="rue" class="col-md-2 mb-3 ml-3">Rue :</label> 
	   			<input id="rue" class="col-md-3 mb-3" type="text" name="rue" value="${ utilisateur.getRue() }">
	   		</div>
	   		<div class="row">
	   			<label for="code_postal" class="col-md-2 mb-3 offset-md-1">Code postal :</label> 
	   			<input id="code_postal" class="col-md-3 mb-3" type="text" name="code_postal" value="${ utilisateur.getCode_postal() }">
	   			<label for="ville" class="col-md-2 mb-3 ml-3">Ville :</label> 
	   			<input id="ville" class="col-md-3 mb-3" type="text" name="ville" value="${ utilisateur.getVille() }">
	   		</div>
	   		<div class="row">
	   			<label for="mot_de_passe" class="col-md-2 mb-3 offset-md-1">Nouveau mot de passe :</label> 
	   			<input id="mot_de_passe" class="col-md-3 mb-3" type="password" name="mot_de_passe">
	  			<label for="confirmation" class="col-md-2 mb-3 ml-3">Confirmation du mot de passe:</label> 
	  			<input id="confirmation" class="col-md-3 mb-3" type="password" name="confirmation">
	   		</div>
	   		<div class="row">
	  			<label class="col-md-2 mb-3 mt-3 offset-md-1">Crédit&nbsp;&nbsp;&nbsp;&nbsp; ${ utilisateur.getCredit() }</label>	
	   		</div>	
			<div class="row justify-content-center">
					<button type="submit" class="btn btn-primary mr-1">Enregistrer</button>
					<a href="${pageContext.request.contextPath}/suppression" class="btn btn-danger ml-1" role="button">Supprimer mon compte</a>
			</div>
		</form>
	</div>
</body>
<%@ include file="/WEB-INF/fragments/footer.html" %>
</html>