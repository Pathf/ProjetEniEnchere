<%@page import="java.util.ArrayList"%>
<%@page import="org.encheres.bo.ArticleVendu"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
	<%-- <%		String isConnecte = (String) request.getSession().getAttribute("pseudo");	%> --%>
	<%@ include file="/WEB-INF/fragments/navbar.jspf"%>
	<div class="container text-center">
		<h1>Liste des enchères</h1>

		<Form method="get" action="encheres">
			<label for="filtres">Filtres</label> <input name="filtres"
				type="search" class="form-control rounded mb-3"
				placeholder="${defaultFiltresPlaceHolder!= null ? defaultFiltresPlaceHolder:"
				le nom de l'article contient" }" aria-label="Search"
				aria-describedby="search-addon" /> <span
				class="input-group-text border-0" id="search-addon"> <i
				class="fas fa-search"></i>
			</span> <label for="categorie">Catégorie </label> <select name="categorie"
				class="custom-select custom-select-lg mb-3">
				<option value="0">Toutes</option>
				<c:choose>
					<c:when test="${categories.size()>0}">
						<c:forEach var="cat" items="${categories}">
							<option ${defaultCategorie == cat.no_categorie?"selected":""}
								value="${cat.no_categorie}">${cat.libelle}</option>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<option value="null">pas de categorie</option>
					</c:otherwise>
				</c:choose>
			</select>

			<c:choose>
				<c:when test="${pseudo != null}">
					<div class="d-flex justify-content-around">
						<div class="form-check">
							<input class="form-check-input" type="radio" name="radioAchatVente"
								id="exampleRadios1" value="achat" > <label
								class="form-check-label" for="exampleRadios1"> Achat </label>
							<div class="form-check">
								<input class="form-check-input" name="checkboxAchat" type="checkbox" value="open"
									id="defaultCheck1"> <label class="form-check-label"
									for="defaultCheck1"> enchères ouvertes </label>
							</div>
							<div class="form-check">
								<input class="form-check-input"  name="checkboxAchat" type="checkbox" value="mine"
									id="defaultCheck2"> <label class="form-check-label"
									for="defaultCheck2"> mes enchères </label>
							</div>
							<div class="form-check">
								<input class="form-check-input" name="checkboxAchat" type="checkbox" value="win"
									id="defaultCheck3"> <label class="form-check-label"
									for="defaultCheck3"> mes enchères remportées </label>
							</div>
						</div>

						<div class="form-check">
							<input class="form-check-input" type="radio" name="radioAchatVente"
								id="exampleRadios2" value="vente"> <label
								class="form-check-label" for="exampleRadios2"> Vente </label>
							<div class="form-check">
								<input class="form-check-input" name="checkboxVente" type="checkbox" value="process"
									id="defaultCheck4"> <label class="form-check-label"
									for="defaultCheck4"> mes ventes en cours </label>
							</div>
							<div class="form-check">
								<input class="form-check-input" name="checkboxVente" type="checkbox" value="start"
									id="defaultCheck5"> <label class="form-check-label"
									for="defaultCheck5"> ventes non débutées </label>
							</div>
							<div class="form-check">
								<input class="form-check-input" name="checkboxVente" type="checkbox" value="finish"
									id="defaultCheck6"> <label class="form-check-label"
									for="defaultCheck6"> ventes terminées </label>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<p>fonctionnalité supplémentaire non accessible</p>
				        </c:otherwise>
			</c:choose>

			<button>Rechercher</button>
		</Form>


		<%-- <c:choose>
			<c:when test="${pseudo == null}"> --%>

				<c:choose>
					<c:when test="${articlesVendus.size()>0}">
						<ul class="list-group col-12">
							<div class="card-deck cardFlex flex-column">

								<c:forEach var="c" items="${articlesVendus}">
									<div class="card border border-dark flex-sm-row">
										<img class="card-img-top"
											src="https://via.placeholder.com/150
							C/O https://placeholder.com/"
											alt="Card image cap">
										<div class="card-body">
											<h5 class="card-title">${c.nom_article}</h5>
											<p class="card-text">Prix :${c.prix_initial}</p>
											<p class="card-text">Categorie :${c.categorie.libelle}</p>
											<p class="card-text">Fin de l'enchère
												:${c.date_fin_encheres}</p>
											<p class="card-text">
												<small class="text-muted">Vendeur : <a href="#"
													class="btn btn-primary">Vendeur
														:${c.utilisateur.pseudo}</a></small>
											</p>
										</div>
									</div>
								</c:forEach>

							</div>
						</ul>
					</c:when>
					<c:otherwise>
						<p>Pas de liste actuellement.
						<p>
					</c:otherwise>
				</c:choose>
			<%-- </c:when>
			<c:otherwise>
				<p>Un utilisateur est connecté afficher liste plus complète</p>
			</c:otherwise>
		</c:choose> --%>





		<c:choose>
			<c:when test="${pseudo != null}">
				<p>connecté comme ${pseudo}
				<p>
			</c:when>


			<c:otherwise>
				<p>non connecté
				<p>
			</c:otherwise>
		</c:choose>

		<%-- <% String pseudo = (String)session.getAttribute("pseudo"); %>
	<%=pseudo%> --%>
	</div>
</body>
<%@ include file="/WEB-INF/fragments/script.html" %>
</html>