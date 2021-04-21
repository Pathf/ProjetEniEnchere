<%@page import="java.util.ArrayList"%>
<%@page import="org.encheres.bo.ArticleVendu"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Accueil</title>
</head>
<body>

	<p>C'est l'accueil !</p>
	<h1>Liste Enchères</h1>
	<Form>
	<label>Filtres</label>
	  <input name="filtres" type="search" class="form-control rounded" placeholder="Search" aria-label="Search"
    aria-describedby="search-addon" />
  <span class="input-group-text border-0" id="search-addon">
    <i class="fas fa-search"></i>
  </span>
	<label for="categorie" >Catégorie</label>
	
	<select name="categorie" class="custom-select custom-select-lg mb-3">
	<option selected>Choisir une categorie</option>
		
		<c:choose>
	    		<c:when test="${categories.size()>0}">
	 				<c:forEach var="cat" items="${categories}">
	  
	 					<option value="${cat.libelle}">${cat.libelle}</option>
	   	</c:forEach>
	   	 </c:when>
	   <c:otherwise>
		      	  <option value="null" >pas de categorie</option>
		        </c:otherwise>
	
		</c:choose>
	</select>
	
	</Form>
      
		<c:choose>
    		<c:when test="${articlesVendus.size()>0}">
		        <ul class="list-group col-12">
		         <div class="card-deck">
		         
		        	<c:forEach var="c" items="${articlesVendus}">
			            	         
				  <div class="card">
				   <img class="card-img-top" src="https://via.placeholder.com/150
				C/O https://placeholder.com/" alt="Card image cap"> 
				    <div class="card-body">
				      <h5 class="card-title">${c.nom_article}</h5>
				      <p class="card-text">Prix :${c.categorie.libelle}</p>
				      <p class="card-text"><small class="text-muted"> <a href="#" class="btn btn-primary">Vendeur :${c.utilisateur.pseudo}</a></small></p>
				    </div>
				  </div>
				  
			      	</c:forEach>
			      	</div>
		        </ul>
	       </c:when>
	        <c:otherwise>
	      	  	<p>Pas de liste actuellement.<p>
	        </c:otherwise>
        </c:choose>

				      	
<%-- 	<% String pseudo = (String)session.getAttribute("pseudo"); %>
	<%=pseudo%> --%>
</body>
</html>