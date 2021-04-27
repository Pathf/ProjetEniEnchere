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
		List<ArticleVendu> articlesVendus = null;
		HttpSession session = request.getSession();

		// recuperation des param de filtration
		String filtres = request.getParameter("filtres");
		String categorie = request.getParameter("categorie");
		Integer categorieInt = null;
		String radioAchat = request.getParameter("radioAchatVente");
		Boolean filtreByDateDebut = false;
		String noUtilisateur = request.getParameter("pseudo");
		Boolean winBid = false;
		Boolean process = false;
		Boolean start = false;
		Boolean finish = false;

		if ("achat".equals(radioAchat)) {
			String[] checkboxAchat =null;
			if (request.getParameterValues("checkboxAchat") != null) {
				checkboxAchat = request.getParameterValues("checkboxAchat");
				if (Arrays.stream(checkboxAchat).anyMatch("open"::equals)) {
					filtreByDateDebut = true;
				}
				if (Arrays.stream(checkboxAchat).anyMatch("mine"::equals)) {
					if (session.getAttribute("pseudo") != null) {
						noUtilisateur = (String) session.getAttribute("pseudo");
					}
				}
				if (Arrays.stream(checkboxAchat).anyMatch("win"::equals)) {
					winBid = true;
					if (session.getAttribute("pseudo") != null) {
						noUtilisateur = (String) session.getAttribute("pseudo");
					}
				}
			}
		}
		if ("vente".equals(radioAchat)) {
			String[] checkboxVente = null;
			if (request.getParameterValues("checkboxVente") != null) {
				checkboxVente = request.getParameterValues("checkboxVente");
				if (Arrays.stream(checkboxVente).anyMatch("process"::equals)) {
					process = true;
					if (session.getAttribute("pseudo") != null) {
						noUtilisateur = (String) session.getAttribute("pseudo");
					}
				}
				if (Arrays.stream(checkboxVente).anyMatch("start"::equals)) {
					start = true;
				}
				if (Arrays.stream(checkboxVente).anyMatch("finish"::equals)) {
					finish = true;
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
			articlesVendus = this.articleVenduManager.selectByFiltre(categorieInt, filtres, filtreByDateDebut, noUtilisateur, process, start, finish);
			request.setAttribute("articlesVendus", articlesVendus);
		} catch (ArticleVenduManagerException e) {
			System.err.println(e);
		}

		// FILTRE SUR ENCHERE REMPORTE UNIQUEMENT
		if (winBid == true) {
			try {
				List<ArticleVendu> articlesVendusGagné = null;
				articlesVendusGagné = this.articleVenduManager.listByWinBid(noUtilisateur);

				// ON COMPARE AVEC LA LISTE DE FILTRE PRECEDENTE
				articlesVendus.retainAll(articlesVendusGagné);
				request.setAttribute("articlesVendus", articlesVendus);
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
