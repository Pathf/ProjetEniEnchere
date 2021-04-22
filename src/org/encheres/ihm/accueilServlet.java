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

		String filtres = request.getParameter("filtres");
		String categorie = request.getParameter("categorie");
		Integer categorieInt = 0;

		if(categorie != null) {
			categorieInt = Integer.parseInt(categorie);
		}

		request.setAttribute("defaultCategorie", categorieInt);
		request.setAttribute("defaultFiltresPlaceHolder", filtres);

		//si uniquement filtres sur nom
		if (categorieInt == 0 & !"".equals(filtres) & filtres != null){
			try {
				articlesVendus = this.articleVenduManager.selectBydNom(filtres) ;
				request.setAttribute("articlesVendus", articlesVendus);
			} catch (ArticleVenduManagerException e) {
				System.out.println(e);
			}
		}
		//si on filtre avec categorie
		else if (categorieInt != 0 ) {
			try {
				articlesVendus = this.articleVenduManager.selectByCategorieAndNom(categorieInt, filtres) ;
				request.setAttribute("articlesVendus", articlesVendus);
			} catch (ArticleVenduManagerException e) {
				System.out.println(e);
			}
		}
		// par default on renvois tous les articlevendus
		else {
			try {
				articlesVendus = this.articleVenduManager.getListeArticleVendu() ;
				request.setAttribute("articlesVendus", articlesVendus);
			} catch (ArticleVenduManagerException e) {
				System.out.println(e);
			}
		}
		//on liste l'ensemble des catégories pour générer le select correspondant
		request.setAttribute("categories", this.listCategorie());

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
		rd.forward(request, response);
	}

	private List<Categorie> listCategorie(){
		List<Categorie> categories = null;
		try {
			categories = this.categorieManager.getListeCategorie() ;

		} catch (CategorieManagerException e) {
			System.out.println(e);
		}
		return categories;
	}
}