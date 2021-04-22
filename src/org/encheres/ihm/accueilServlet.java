package org.encheres.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.ArticleVenduManagerException;
import org.encheres.bll.CategorieManagerException;
import org.encheres.bll.CategoriesManager;
import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Categorie;

@WebServlet("/encheres")
public class accueilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleVenduManager articleVenduManager = ArticleVenduManager.getInstance();
	private CategoriesManager categorieManager = CategoriesManager.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ArticleVendu> articlesVendus = null;

		try {
			articlesVendus = this.articleVenduManager.getListeArticleVendu() ;
			request.setAttribute("articlesVendus", articlesVendus);
		} catch (ArticleVenduManagerException e) {
			System.out.println(e);
		}
		//
		List<Categorie> categories = null;

		try {
			categories = this.categorieManager.getListeCategorie() ;
			request.setAttribute("categories", categories);
		} catch (CategorieManagerException e) {
			System.out.println(e);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
		rd.forward(request, response);
		//
	}
}
