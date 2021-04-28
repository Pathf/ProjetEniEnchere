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
	</div>
	<ul class="nav nav-tabs" id="myTab" role="tablist">
		<li class="nav-item" role="presentation">
			<button class="nav-link active" id="utilisateurs-tab" data-bs-toggle="tab" data-bs-target="#utilisateurs" type="button" role="tab" aria-controls="utilisateurs" aria-selected="true">Utilisateurs</button>
		</li>
		<li class="nav-item" role="presentation">
			<button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile" type="button" role="tab" aria-controls="profile" aria-selected="false">Profile</button>
		</li>
	</ul>
	<div class="tab-content" id="myTabContent">
		<div class="tab-pane fade show active" id="utilisateurs" role="tabpanel" aria-labelledby="utilisateurs-tab">utilisateurs : 1 , 2 , 3</div>
		<div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">Teste</div>
	</div>
</body>
<%@ include file="/WEB-INF/fragments/script.html"%>
</html>