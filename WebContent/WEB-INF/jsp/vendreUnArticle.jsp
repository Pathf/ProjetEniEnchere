<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<% String erreur = (String)request.getAttribute("erreur"); %>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp">
	<jsp:param value="./css/vendreUnArticle.css" name="style"/>
</jsp:include>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	  <div class="container-fluid">
	    <a class="navbar-brand" href="<%=request.getContextPath()%>">DUH - Encheres</a>
	  </div>
	</nav>
	<div class="container">
		<div class="mx-auto text-center">
			<h1>Nouvelle vente</h1>
		</div>
		<c:if test="${ erreur != null }">
			<div class="alert alert-danger alert-dismissible fade show mx-auto col-md-10 offset-md-1 mt-3" role="alert">
			  <strong>Erreur !</strong> <%=erreur%>
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
		</c:if>
		<div class="row mt-5">
			<div class="photo offset-md-1 col-md-4">
				<img alt="photo de l'objet" src="https://via.placeholder.com/300">
			</div>
			<div class="formulaire col-md-6 ml-3">
				<form action="nouvelle-vente" method="post">
			   		<div class="form-group row">
					    <label for="article" class="col-sm-3 col-form-label">Article :</label>
					    <div class="col-sm-8">
					      <input type="text" class="form-control" id="article" name="article">
					    </div>
				  	</div>
				  	<div class="form-group row">
					    <label for="description" class="col-sm-3 col-form-label">Description :</label>
					    <div class="col-sm-8">
					      <textarea class="form-control" id="description" rows="3" name="description"></textarea>
					    </div>
			  		</div>
			  		<div class="form-group row">
					    <label for="categorie" class="col-sm-4 col-form-label">Catégorie :</label>
					    <div class="col-sm-7">
				      		<select class="form-control" id="exampleFormControlSelect1" name="categorie">
						      <c:forEach var="c" items="${categories}">
						      	<option value="${ c.getNo_categorie() }">${ c.getLibelle() }</option>
						      </c:forEach>
					    	</select>
					    </div>
			  		</div>
			  		<div class="form-group row">
					    <label for="photoArticle" class="col-sm-4 col-form-label">Photo de l'article :</label>
					    <div class="col-sm-7">
				      		<input type="file" class="form-control-file" id="photoArticle" name="photoArticle">
					    </div>
			  		</div>
			  		<div class="form-group row">
					    <label for="miseAPrix" class="col-sm-4 col-form-label">Mise à prix :</label>
					    <div class="col-sm-7">
				      		<input type="number" id="miseAPrix" class="form-control" name="miseAPrix" />
					    </div>
			  		</div>
			  		<div class="form-group row">
					    <label for="debutEnchere" class="col-sm-4 col-form-label">Début de l'enchère :</label>
					    <div class="col-sm-7">
				      		<input class="form-control" type="date" id="debutEnchere" name="debutEnchere">
					    </div>
			  		</div>
			  		<div class="form-group row">
					    <label for="finEnchere" class="col-sm-4 col-form-label">Fin de l'enchère :</label>
					    <div class="col-sm-7">
				      		<input class="form-control" type="date" id="finEnchere" name="finEnchere">
					    </div>
			  		</div>
			  		<fieldset class="scheduler-border">
			  			<legend class="scheduler-border">Retrait</legend>
				  		<div class="form-group row">
						    <label for="rue" class="col-sm-4 col-form-label">Rue :</label>
						    <div class="col-sm-7">
						      <input type="text" class="form-control" id="rue" name="rue" value="${ utilisateur.getRue() }">
						    </div>
				  		</div>
				  		<div class="form-group row">
						    <label for="codePostal" class="col-sm-4 col-form-label">Code postal :</label>
						    <div class="col-sm-7">
						      <input type="text" class="form-control" id="codePostal" name="codePostal" value="${ utilisateur.getCode_postal() }">
						    </div>
				  		</div>
				  		<div class="form-group row">
						    <label for="ville" class="col-sm-4 col-form-label">Ville :</label>
						    <div class="col-sm-7">
						      <input type="text" class="form-control" id="ville" name="ville" value="${ utilisateur.getVille() }">
						    </div>
				  		</div>
			  		</fieldset>
			  		<div class="mb-5">
						<button type="submit" class="btn btn-primary mr-3 p-3 col-md-4">Enregistrer</button>
						<a href="${pageContext.request.contextPath}" class="btn btn-danger ml-3 p-3 col-md-4" role="button">Annuler</a>
					</div>
				</form>
			</div>
		</div>

	</div>
</body>
<%@ include file="/WEB-INF/fragments/footer.html" %>
</html>