<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp">
	<jsp:param value="./css/accueil.css" name="style" />
</jsp:include>
<body>
	<%@ include file="/WEB-INF/fragments/navbar.jspf"%>
	<div class="container text-center">
		<h1 class="d-none d-sm-block">Gestion de l'administration</h1>
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item" role="presentation">
				<button class="nav-link active" id="utilisateurs-tab" data-bs-toggle="tab" data-bs-target="#utilisateurs" type="button" role="tab" aria-controls="utilisateurs" aria-selected="true">Utilisateurs</button>
			</li>
			<li class="nav-item" role="presentation">
				<button class="nav-link" id="categories-tab" data-bs-toggle="tab" data-bs-target="#categories" type="button" role="tab" aria-controls="categories" aria-selected="false">Categories</button>
			</li>
		</ul>
		<div class="tab-content" id="myTabContent">
			<div class="tab-pane fade show active" id="utilisateurs" role="tabpanel" aria-labelledby="utilisateurs-tab">
				<form action="administration" method="post">
					<table class="table table-striped">
						<thead>
							<tr>
								<th scope="col">No.</th>
								<th scope="col">Pseudo</th>
								<th scope="col">Nom</th>
								<th scope="col">Prénom</th>
								<th scope="col">Email</th>
								<th scope="col">Téléphone</th>
								<th scope="col">Adresse</th>
								<th scope="col">Crédit</th>
								<th scope="col">Admin</th>
								<th scope="col">Suppression</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="utilisateur" items="${utilisateurs}">
								<tr>
									<th scope="row">${utilisateur.no_utilisateur}</th>
									<td>${utilisateur.pseudo}</td>
									<td>${utilisateur.nom}</td>
									<td>${utilisateur.prenom}</td>
									<td>${utilisateur.email}</td>
									<td>${utilisateur.telephone}</td>
									<td>${utilisateur.rue}&#8239;${utilisateur.code_postal}&#8239;${utilisateur.ville}</td>
									<td>${utilisateur.credit}</td>
									<td>${utilisateur.administrateur}</td>
									<td><c:choose>
											<c:when test="${!utilisateur.administrateur}">
												<button type="submit" class="btn btn-danger" name="no_suppression" value="${utilisateur.no_utilisateur}">X</button>
											</c:when>
										</c:choose></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form>
			</div>
			<div class="tab-pane fade" id="categories" role="tabpanel" aria-labelledby="categories-tab">En construction !</div>
		</div>
	</div>
</body>
<%@ include file="/WEB-INF/fragments/script.html"%>
</html>