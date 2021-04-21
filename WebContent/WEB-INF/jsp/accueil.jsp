<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
<% String isConnecte = "1"; %>
<%@include file="/WEB-INF/fragments/navbar.jspf"%>
	<p>C'est l'accueil !</p>
	<% String pseudo = (String)session.getAttribute("pseudo"); %>
	<%=pseudo%>
</body>
</html>