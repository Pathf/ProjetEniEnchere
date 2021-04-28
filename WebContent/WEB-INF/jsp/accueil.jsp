<%@page import="java.util.ArrayList"%>
<%@page import="org.encheres.bo.ArticleVendu"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp">
	<jsp:param value="./css/accueil.css" name="style" />
</jsp:include>
<body>
	<%-- <%		String isConnecte = (String) request.getSession().getAttribute("pseudo");	%> --%>
	<%@ include file="/WEB-INF/fragments/navbar.jspf"%>
	<div class="container text-center">
		<h1 class="d-none d-sm-block">Liste des enchères</h1>
		<div class="d-flex flex-column flex-column-reverse-xs">
			<h4 class="container text-left">Filtres&#8239;:</h4>
			<Form class="d-flex" method="get" action="encheres">
				<div class="text-left w-100 searchModule">
					<input name="filtres" type="search" class="form-control rounded mb-3 rounded" placeholder="${defaultFiltresPlaceHolder!= null ? defaultFiltresPlaceHolder:
						'&#x1F50D; le nom de l\'article contient'}" aria-label="Search" aria-describedby="search-addon" /> <span class="input-group-text border-0" id="search-addon"> <i class="fas fa-search"></i>
					</span>
					<div class="block">
						<label for="categorie">Catégorie </label> <select name="categorie" class="custom-select custom-select-lg mb-3 w-75 float-right">
							<option value="0">Toutes</option>
							<c:choose>
								<c:when test="${categories.size()>0}">
									<c:forEach var="cat" items="${categories}">
										<option ${defaultCategorie == cat.no_categorie?"selected":""} value="${cat.no_categorie}">${cat.libelle}</option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<option value="null">pas de categorie</option>
								</c:otherwise>
							</c:choose>
						</select>
					</div>
					<c:choose>
						<c:when test="${pseudo != null}">
							<div class="d-flex justify-content-around mt-3 float-left blockRadioButton">
								<div class="form-check">
									<input class="form-check-input ml-0" type="radio" name="radioAchatVente" id="exampleRadios1" value="achat"> <label class="form-check-label" for="exampleRadios1"> Achat </label>
									<div class="ml-5">
										<div class="form-check">
											<input class="form-check-input" name="checkboxAchat" type="checkbox" value="open" id="defaultCheck1"> <label class="form-check-label" for="defaultCheck1"> enchères ouvertes </label>
										</div>
										<div class="form-check">
											<input class="form-check-input" name="checkboxAchat" type="checkbox" value="mine" id="defaultCheck2"> <label class="form-check-label" for="defaultCheck2"> mes enchères </label>
										</div>
										<div class="form-check">
											<input class="form-check-input" name="checkboxAchat" type="checkbox" value="win" id="defaultCheck3"> <label class="form-check-label" for="defaultCheck3"> mes enchères remportées </label>
										</div>
									</div>
								</div>
								<div class="form-check">
									<input class="form-check-input ml-0" type="radio" name="radioAchatVente" id="exampleRadios2" value="vente"> <label class="form-check-label" for="exampleRadios2"> Vente </label>
									<div class="ml-5">
										<div class="form-check">
											<input class="form-check-input" name="checkboxVente" type="checkbox" value="process" id="defaultCheck4"> <label class="form-check-label" for="defaultCheck4"> mes ventes en cours </label>
										</div>
										<div class="form-check">
											<input class="form-check-input" name="checkboxVente" type="checkbox" value="start" id="defaultCheck5"> <label class="form-check-label" for="defaultCheck5"> ventes non débutées </label>
										</div>
										<div class="form-check">
											<input class="form-check-input" name="checkboxVente" type="checkbox" value="finish" id="defaultCheck6"> <label class="form-check-label" for="defaultCheck6"> ventes terminées </label>
										</div>
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<p>fonctionnalité supplémentaire non accessible</p>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="m-auto col-sm-6">
					<button class="btn btn-primary mx-auto searchButton">Rechercher</button>
				</div>
			</Form>
		</div>
		<c:choose>
			<c:when test="${articlesVendus.size()>0}">
				<div class="list-group col-12 ">
					<div class="card-deck cardFlex flex-wrap flex-row col-12 m-1 p-0">
						<c:forEach var="c" items="${articlesVendus}">
							<div class="card border border-dark flex-sm-row col col-lg-6 m-1 p-0 ">
								<img class="card-img-top col-4 m-1 p-0" src="https://via.placeholder.com/150
							C/O https://placeholder.com/" alt="Card image cap">
								<div class="card-body col-8 m-1 p-0">
									<h5 class="card-title col-12">
										<a href="${pageContext.request.contextPath}/detail-enchere?id=${c.no_article}" class="text-decoration-none">${c.nom_article}</a>
									</h5>
									<p class="card-text col-12">Prix&#8239;: ${c.prix_initial}</p>
									<p class="card-text col-12">Categorie&#8239;: ${c.categorie.libelle}</p>
									<p class="card-text col-12">Fin de l'enchère&#8239;: ${c.date_fin_encheres}</p>
									<p class="card-text col-12">
										<small class="text-muted"><a href="${pageContext.request.contextPath}/profil?pseudo=${c.utilisateur.pseudo}" class="btn btn-primary">Vendeur&#8239;: ${c.utilisateur.pseudo}</a></small>
									</p>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<p>Pas de liste actuellement.</p>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${pseudo != null}">
				<p>connecté comme ${pseudo}</p>
			</c:when>
			<c:otherwise>
				<p>non connecté</p>
			</c:otherwise>
		</c:choose>
	</div>
</body>
<%@ include file="/WEB-INF/fragments/script.html"%>
</html>
