<%@page import="org.encheres.bo.ArticleVendu"%>
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
	<%@ include file="/WEB-INF/fragments/navbar.jspf" %>
	<div class="container">
		<c:choose>
		    <c:when test="${!articleValide}">
		        <div class="alert alert-danger mt-5" role="alert">
			  		<strong>Erreur !</strong> L'article sélectionné n'a pas été trouvé, <a href="<%=request.getContextPath()%>" class="alert-link">Merci de vous rediriger vers l'accueil</a>.
				</div>
		    </c:when>    
		    <c:otherwise>	
				<div class="mx-auto text-center">
					<h1>Détail vente</h1>
				</div>
				<c:if test="${ erreur != null }">
					<div
						class="alert alert-danger alert-dismissible fade show mx-auto col-md-10 offset-md-1 mt-3"
						role="alert">
						<strong>Erreur !</strong><%=erreur%>
						<button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>
				<c:if test="${ isGagnant }">
					<div
						class="alert alert-success alert-dismissible fade show mx-auto col-md-10 offset-md-1 mt-3"
						role="alert">
						<strong>Bravo !</strong> Vous avez remporté la vente.
						<button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>
				<c:if test="${ isTerminee && !isGagnant }">
					<div class="mb-2 col-12 mt-5 text-center">
						<h3><strong>${enchere.getUtilisateur().getPseudo() }</strong> a remporté l'enchère.</h3>	 
					</div>
				</c:if>
				<div class="row mt-5">
					<div class="photo offset-md-1 col-md-4">
					<c:choose>
						<c:when test="${article.getPhotoNom() != null}">
							<a href="images?id=${article.getNo_article()}"><img alt="Pas de photo disponible." class="img-thumbnail" src="images?id=${article.getNo_article()}"></a>
						</c:when>
						<c:otherwise>
							<img alt="Pas de photo disponible." class="img-thumbnail" src="https://via.placeholder.com/150	C/O https://placeholder.com/">
						</c:otherwise>
					</c:choose>
					</div>
					<div class="infos col-10 offset-1 col-md-6 ml-md-3">
						<table class="table">
							<tbody>
							<tr class="row">
									<th class="col-12 mb-3" scope="row"><h4 class="font-weight-bold">${ article.getNom_article() }</h4></th>
								</tr>
								<tr class="row">
									<th class="col-5 col-md-4" scope="row">Description :</th>
									<td class="col-6">${ article.getDescription() }</td>
								</tr>
								<tr class="row">
									<th class="col-5 col-md-4" scope="row">Catégorie :</th>
									<td class="col-7">${ article.getCategorie().getLibelle() }</td>
								</tr>
								<tr class="row">
									<th class="col-5 col-md-4" scope="row">Meilleure offre :</th>
									<c:choose>
					    				<c:when test="${!meilleurEnchereNotNull}">
					    					<td class="col-7">Aucune enchère actuellement</td>
										</c:when>    
					   					 <c:otherwise>
											<td class="col-7">${enchere.getMontant_enchere() } pts par ${enchere.getUtilisateur().getPseudo() }</td>
							   			</c:otherwise>
									</c:choose>
									
								</tr>
								<tr class="row">
									<th class="col-5 col-md-4" scope="row">Mise à prix :</th>
									<td class="col-7">${ article.getPrix_initial() } points</td>
								</tr>
								<tr class="row">
									<th class="col-5 col-md-4" scope="row">Fin de l'enchère :</th>
									<td class="col-7"><fmt:formatDate pattern="dd-MM-yyyy" value="${article.getDate_fin_encheres()}" /></td>
								</tr>
								<tr class="row">
									<th class="col-5 col-md-4" scope="row">Retrait :</th>
									<td class="col-7">${ article.getRetrait().getRue() }</td>
									<th class="col-5 col-md-4" scope="row"></th>
									<td class="col-7">${ article.getRetrait().getCode_postal() } ${ article.getRetrait().getVille() }</td>
								</tr>
								<tr class="row">
									<th class="col-5 col-md-4" scope="row">Vendeur :</th>
									<td class="col-7">${ article.getUtilisateur().getPseudo() }</td>
								</tr>
							</tbody>
						</table>
						<c:choose>
		    				<c:when test="${!isConnect}">
		    					<div class="form-group mb-2 col-8 pl-0">
									<p><em>Vous devez être <a href="connexion" >connecté</a> pour pouvoir enchérir</em></p>	 
								</div>
							</c:when>
							<c:when test="${isEnCour && isMeilleurEncherisseur }">
		    					<div class="form-group mb-2 col-8 pl-0">
									<p><em>Vous êtes actuellement le meilleur enchérisseur !</em></p>	 
								</div>
							</c:when>     
							<c:when test="${vendeur && !isEnCour && !isTerminee}">
				   				<a href="${pageContext.request.contextPath}/modifier-vente?id=${article.getNo_article() }" class="btn btn-secondary p-3 col-md-4" role="button">Modifier l'article</a>
				   			</c:when>
		   					 <c:when test="${isEnCour && !isMeilleurEncherisseur && !vendeur}">
								<form action="detail-enchere?id=${article.getNo_article() }" method="post" class="form-inline">
									<div class="form-group mb-2 col-7 pl-0">
										 <label for="proposition" class="col-7 col-form-label pl-0 font-weight-bold justify-content-start">Ma proposition :</label>
									    <div class="col-5">
									    <c:choose>
		    								<c:when test="${meilleurEnchereNotNull}">
								      			<input type="number" id="proposition" class="form-control col-12" name="proposition" value="${enchere.getMontant_enchere() +1}"/>
									    	</c:when>    
		   					 				<c:otherwise>
		   					 					<input type="number" id="proposition" class="form-control col-12" name="proposition" value="${ article.getPrix_initial() +1}"/>
		   					 				</c:otherwise>
										</c:choose>
									    </div>
									</div>
									<button type="submit" class="btn btn-secondary mb-2">Enchérir</button>
								</form>
				   			</c:when>
				   			<c:when test="${isGagnant}">
				   				<a href="${pageContext.request.contextPath}" class="btn btn-secondary p-3 col-md-4" role="button">Retour</a>
				   			</c:when>
				   			<c:otherwise>
			 					
			 				</c:otherwise>
						</c:choose>
					</div>
				</div>   
   			</c:otherwise>
		</c:choose>
	</div>
</body>
<%@ include file="/WEB-INF/fragments/script.html"%>
</html>