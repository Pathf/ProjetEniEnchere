<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
</head>
<body>
<%@ include file="/WEB-INF/fragments/navbar.jspf" %>
<div class="container">
	<div class="row justify-content-center">
		<div class="col-md-6 justify-content-center">
			<table class="table mt-5">
			    <tbody>
			        <tr class="row justify-content-center pt-5">
			            <th class="col-4 offset-1 offset-md-2" scope="row">Pseudo</th>
			            <td class="col-4 offset-1 offset-md-2">${ utilisateur.getPseudo() }</td>
			        </tr>
			        <tr class="row justify-content-center">
			            <th class="col-4 offset-1 offset-md-2" scope="row">Nom</th>
			            <td class="col-4 offset-1 offset-md-2">${ utilisateur.getNom() }</td>
			        </tr>
			        <tr class="row justify-content-center">
			            <th class="col-4 offset-1 offset-md-2" scope="row">Prénom</th>
			            <td class="col-4 offset-1 offset-md-2">${ utilisateur.getPrenom() }</td>
			        </tr>
			        <tr class="row justify-content-center">
			            <th class="col-4 offset-1 offset-md-2" scope="row">Email</th>
			            <td class="col-4 offset-1 offset-md-2">${ utilisateur.getEmail() }</td>
			        </tr>
			        <tr class="row justify-content-center">
			            <th class="col-4 offset-1 offset-md-2" scope="row">Téléphone</th>
			            <td class="col-4 offset-1 offset-md-2">${ utilisateur.getTelephone() }</td>
			        </tr>
			        <tr class="row justify-content-center">
			            <th class="col-4 offset-1 offset-md-2" scope="row">Rue</th>
			            <td class="col-4 offset-1 offset-md-2">${ utilisateur.getRue() }</td>
			        </tr>
			        <tr class="row justify-content-center">
			            <th class="col-4 offset-1 offset-md-2" scope="row">Code&nbsp;Postal</th>
			            <td class="col-4 offset-1 offset-md-2">${ utilisateur.getCode_postal() }</td>
			        </tr>
			        <tr class="row justify-content-center">
			            <th class="col-4 offset-1 offset-md-2" scope="row">Ville</th>
			            <td class="col-4 offset-1 offset-md-2">${ utilisateur.getVille() }</td>
			        </tr>
			    </tbody>
			</table>
			<c:if test="${ isMonProfil }">
				<div class="row">
				    <a href="${pageContext.request.contextPath}/monProfil" class="btn btn-primary mx-auto" role="button">Modifier</a>
			    </div>
			</c:if>
		</div>
	</div>
</div>
</body>
<%@ include file="/WEB-INF/fragments/script.html" %>
</html>