<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="<%=request.getContextPath()%>">DUH - Encheres</a>
  </div>
</nav>
<table class="table text-center mt-5">
    <tbody>
        <tr class="row pt-5">
            <th class="offset-1 col-5 offset-md-3 col-md-3" scope="row">Pseudo</th>
            <td class="col-5 col-md-3">${ utilisateur.getPseudo() }</td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-5 offset-md-3 col-md-3" scope="row">Nom</th>
            <td class="col-5 col-md-3">${ utilisateur.getNom() }</td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-5 offset-md-3 col-md-3" scope="row">Prénom</th>
            <td class="col-5 col-md-3">${ utilisateur.getPrenom() }</td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-5 offset-md-3 col-md-3" scope="row">Email</th>
            <td class="col-5 col-md-3">${ utilisateur.getEmail() }</td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-5 offset-md-3 col-md-3" scope="row">Téléphone</th>
            <td class="col-5 col-md-3">${ utilisateur.getTelephone() }</td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-5 offset-md-3 col-md-3" scope="row">Rue</th>
            <td class="col-5 col-md-3">${ utilisateur.getRue() }</td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-5 offset-md-3 col-md-3" scope="row">Code&nbsp;Postal</th>
            <td class="col-5 col-md-3">${ utilisateur.getCode_postal() }</td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-5 offset-md-3 col-md-3" scope="row">Ville</th>
            <td class="col-5 col-md-3">${ utilisateur.getVille() }</td>
        </tr>
    </tbody>
</table>
<c:if test="${ isMonProfil }">
	<div class="row">
	    <a href="${pageContext.request.contextPath}/monProfil" class="btn btn-primary mx-auto" role="button">Modifier</a>
    </div>
</c:if>
</body>
<%@ include file="/WEB-INF/fragments/footer.html" %>
</html>