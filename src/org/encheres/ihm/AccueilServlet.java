package org.encheres.ihm;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.ArticleVenduManagerException;
import org.encheres.bll.CategorieManagerException;
import org.encheres.bll.CategoriesManager;
import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Categorie;

@WebServlet("/encheres")
public class AccueilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleVenduManager articleVenduManager = ArticleVenduManager.getInstance();
	private CategoriesManager categorieManager = CategoriesManager.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<ArticleVendu> articlesVendus = null;

		// Verification du champ filtre
		String filtres = request.getParameter("filtres");
		String categorie = request.getParameter("categorie");
		String radioAchat = request.getParameter("radioAchatVente");

		Integer categorieInt = null;
		Boolean winBid = false;
		Boolean process = false;
		Boolean start = false;
		Boolean finish = false;
		Boolean filtreByDateDebut = false;
		boolean mine = false;
		boolean win = false;
		Integer idUtilisateur = null;
		Integer numberResult = null;
		
		// PAGINATION
		Integer firstRow = 0;
		Integer rowPerPage = 6 ; // nbre de retour par page
		Integer lastRow = 6;

		if (request.getParameter("page") != null) {
			firstRow = Integer.parseInt(request.getParameter("page"))*rowPerPage ;
			lastRow = Integer.parseInt(request.getParameter("page"))*rowPerPage + rowPerPage;
		}

		// verification succinte du champs de saisi libre pour eviter injection SQL
		if (filtres != null) {
			if (!filtres.matches("[a-zA-Z0-9]+")) {
				filtres = null;
			}
			;
		}

		if ("achat".equals(radioAchat)) {
			String[] checkboxAchat = null;
			if (request.getParameterValues("checkboxAchat") != null) {
				request.setAttribute("achat", true);
				checkboxAchat = request.getParameterValues("checkboxAchat");
				if (Arrays.stream(checkboxAchat).anyMatch("open"::equals)) {
					filtreByDateDebut = true;
					request.setAttribute("open", filtreByDateDebut);
				}
				if (Arrays.stream(checkboxAchat).anyMatch("mine"::equals)) {
					if (session.getAttribute("id") != null) {
						idUtilisateur = (Integer) session.getAttribute("id");
					}
					mine = true;
					request.setAttribute("mine", mine);
				}
				if (Arrays.stream(checkboxAchat).anyMatch("win"::equals)) {
					winBid = true;

					if (session.getAttribute("id") != null) {
						idUtilisateur = (Integer) session.getAttribute("id");
					}
					win = true;
					request.setAttribute("win", win);
				}
			}
		}
		if ("vente".equals(radioAchat)) {
			String[] checkboxVente = null;
			if (request.getParameterValues("checkboxVente") != null) {
				checkboxVente = request.getParameterValues("checkboxVente");
				request.setAttribute("vente", true);
				if (Arrays.stream(checkboxVente).anyMatch("process"::equals)) {
					process = true;
					request.setAttribute("process", process);
					if (session.getAttribute("id") != null) {
						idUtilisateur = (Integer) session.getAttribute("id");
					}
				}
				if (Arrays.stream(checkboxVente).anyMatch("start"::equals)) {
					start = true;
					request.setAttribute("start", start);
				}
				if (Arrays.stream(checkboxVente).anyMatch("finish"::equals)) {
					finish = true;
					request.setAttribute("finish", finish);
				}
			}
		}

		// on verifie la catégorie choisie sinon si 0 on laisse à null
		if (categorie != null && !"0".equals(categorie)) {
			categorieInt = Integer.parseInt(categorie);
		}

		request.setAttribute("defaultCategorie", categorieInt);
		request.setAttribute("defaultFiltresPlaceHolder", filtres);

		// TEST LES FILTRES DEMANDE//
		try {
			articlesVendus = this.articleVenduManager.selectByFiltre(categorieInt, filtres, filtreByDateDebut,
					idUtilisateur, process, start, finish, firstRow, lastRow);
			request.setAttribute("articlesVendus", articlesVendus);

			numberResult = this.articleVenduManager.countSelectByFilter(categorieInt, filtres, filtreByDateDebut,
					idUtilisateur, process, start, finish);
			
			// calcul du nombre de page pour l'affichage
			int noOfPages = (int) Math.ceil(numberResult * 1.0 / rowPerPage);
			request.setAttribute("nbreDePage", noOfPages);
			
		} catch (ArticleVenduManagerException e) {
			System.err.println(e);
		}

		// FILTRE SUR ENCHERE REMPORTE UNIQUEMENT
		if (winBid) {
			try {
				List<ArticleVendu> articlesVendusGagné = null;
				articlesVendusGagné = this.articleVenduManager.listByWinBid(idUtilisateur);

				// ON COMPARE AVEC LA LISTE DE FILTRE PRECEDENTE
				articlesVendus.retainAll(articlesVendusGagné);
				request.setAttribute("articlesVendus", articlesVendus);
				
				// calcul du nombre de page pour l'affichage
				int noOfPages = (int) Math.ceil(articlesVendus.size() * 1.0 / rowPerPage);
				request.setAttribute("nbreDePage", noOfPages);
				
			} catch (ArticleVenduManagerException e) {
				System.err.println(e);
			}
		}

		// on liste l'ensemble des catégories pour générer le select correspondant
		request.setAttribute("categories", this.listCategorie());

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
		rd.forward(request, response);
	}

	private List<Categorie> listCategorie() {
		List<Categorie> categories = null;
		try {
			categories = this.categorieManager.getListeCategorie();
		} catch (CategorieManagerException e) {
			System.err.println(e);
		}
		return categories;
	}

}
