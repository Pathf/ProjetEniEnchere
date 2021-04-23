<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<%
	String erreur = (String) request.getAttribute("erreur");
%>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp">
	<jsp:param value="#" name="style" />
</jsp:include>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="<%=request.getContextPath()%>">DUH
				- Encheres</a>
		</div>
	</nav>
	<div class="container">
		<div class="mx-auto text-center">
			<h1>Détail vente</h1>
		</div>
		<c:if test="${ erreur != null }">
			<div
				class="alert alert-danger alert-dismissible fade show mx-auto col-md-10 offset-md-1 mt-3"
				role="alert">
				<strong>Erreur !</strong>
				<%=erreur%>
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:if>
		<div class="row mt-5">
			<div class="photo offset-md-1 col-md-4">
				<img alt="photo de l'objet" src="https://via.placeholder.com/300">
			</div>
			<div class="infos col-md-6 ml-3">
				<table class="table">
					<tbody>
					<tr class="row">
							<th class="col-12 mb-3 w-50" scope="row"><h4 class="font-weight-bold">PC Gamer pour teletravail</h4></th>
						</tr>
						<tr class="row">
							<th class="col-5 col-md-4" scope="row">Description :</th>
							<td class="col-6">gros pavé de description aaaa
								aaaaaaaaa aa aaaaaaaaaa aa a aaaa aaaaaaaaa aaaa a aaa aaaa
								aaaaaa aaaaaaa aaaa</td>
						</tr>
						<tr class="row">
							<th class="col-5 col-md-4" scope="row">Catégorie :</th>
							<td class="col-7">Informatique</td>
						</tr>
						<tr class="row">
							<th class="col-5 col-md-4" scope="row">Meilleure offre :</th>
							<td class="col-7">210 pts par Bob</td>
						</tr>
						<tr class="row">
							<th class="col-5 col-md-4" scope="row">Mise à prix :</th>
							<td class="col-7">185 points</td>
						</tr>
						<tr class="row">
							<th class="col-5 col-md-4" scope="row">Fin de l'enchère :</th>
							<td class="col-7">09/10/2018</td>
						</tr>
						<tr class="row">
							<th class="col-5 col-md-4" scope="row">Retrait :</th>
							<td class="col-7">10 allée des Alouettes</td>
							<th class="col-5 col-md-4" scope="row"></th>
							<td class="col-7">44800 Saint Herblain</td>
						</tr>
						<tr class="row">
							<th class="col-5 col-md-4" scope="row">Vendeur :</th>
							<td class="col-7">jojo44</td>
						</tr>
					</tbody>
				</table>
				<form action="detail-enchere" method="post" class="form-inline">
					<div class="form-group mb-2 col-6 pl-0">
						 <label for="proposition" class="col-7 col-form-label pl-0 font-weight-bold justify-content-start">Ma proposition :</label>
					    <div class="col-5">
				      		<input type="number" id="proposition" class="form-control col-12" name="proposition" />
					    </div>
					</div>
					<button type="submit" class="btn btn-primary mb-2">Enchérir</button>
				</form>
			</div>
		</div>

	</div>
</body>
<%@ include file="/WEB-INF/fragments/footer.html"%>
</html>