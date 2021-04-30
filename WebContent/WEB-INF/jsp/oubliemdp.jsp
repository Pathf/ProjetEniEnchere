<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
	<%@ include file="/WEB-INF/fragments/navbar.jspf"%>
	<div class="container">
		<div class="mx-auto text-center">
			<h1>Oublie du mot de passe</h1>
		</div>
		<c:choose>
			<c:when test="${ isModif == null }">
				<form action="oubliemdp" method="post">
					<div class="row mt-5 col-12 m-0 p-0">
						<label for="email" class="col-md-2 offset-md-3">Email :</label> <input id="email" class="col-md-4" type="email" name="email" placeholder="Email" required>
					</div>
					<div class="mt-4 row justify-content-center col-12 m-0 p-0">
						<div class="col-6 col-md-3 m-0 p-0 text-center">
							<button class="btn btn-lg btn-primary justify-content-md-end" name="bouton" value="demande">Demande de nouveau mot de passe</button>
						</div>
					</div>
				</form>
			</c:when>
			<c:otherwise>
				<form action="oubliemdp" method="post">
					<div class="row mt-5 col-12 m-0 p-0">
						<label for="mot_de_passe" class="col-md-2 offset-md-3">Mot de passe :</label> <input id="mot_de_passe" class="col-md-4" type="text" name="mot_de_passe" placeholder="*******" required>
					</div>
					<div class="row mt-5 col-12 m-0 p-0">
						<label for="confirmation" class="col-md-2 offset-md-3">Confirmation :</label> <input id="confirmation" class="col-md-4" type="text" name="confirmation" placeholder="*******" required>
					</div>
					<div class="mt-4 row justify-content-center col-12 m-0 p-0">
						<div class="col-6 col-md-3 m-0 p-0 text-center">
							<button class="btn btn-lg btn-primary justify-content-md-end" name="bouton" value="${isModif}">Valider</button>
						</div>
					</div>
				</form>
			</c:otherwise>
		</c:choose>
	</div>
</body>
<%@ include file="/WEB-INF/fragments/script.html"%>
</html>