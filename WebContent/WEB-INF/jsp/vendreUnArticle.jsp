<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

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
			<h1>Nouvelle vente</h1>
		</div>

	</div>
</body>
<%@ include file="/WEB-INF/fragments/footer.html" %>
</html>