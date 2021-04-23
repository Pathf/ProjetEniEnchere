<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<% String erreur = (String)request.getAttribute("erreur"); %>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp">
	<jsp:param value="#" name="style"/>
</jsp:include>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	  <div class="container-fluid">
	    <a class="navbar-brand" href="<%=request.getContextPath()%>">DUH - Encheres</a>
	  </div>
	</nav>
	<div class="container">
		<div class="mx-auto text-center">
			<h1>Détail vente</h1>
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
				
			</div>
		</div>

	</div>
</body>
<%@ include file="/WEB-INF/fragments/footer.html" %>
</html>