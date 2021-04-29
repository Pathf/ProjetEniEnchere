package org.encheres.ihm;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.ArticleVenduManagerException;
import org.encheres.bo.ArticleVendu;

/**
 * Servlet implementation class SuppressionArticleServlet
 */
@WebServlet("/suppression-article")
public class SuppressionArticleServlet extends HttpServlet {
	private ArticleVenduManager articleVenduManager = ArticleVenduManager.getInstance();
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String pseudo = (String) session.getAttribute("pseudo");
		Integer articleId = Integer.parseInt(request.getParameter("id"));
		ArticleVendu articleVendu = null;
		Date dateActuelle = new Date(new java.util.Date().getTime());
		String erreur = null;
		
		try {
			articleVendu = this.articleVenduManager.getArticleVendu(articleId);
		} catch (ArticleVenduManagerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (pseudo.equals(articleVendu.getUtilisateur().getPseudo()) && dateActuelle.before(articleVendu.getDate_debut_encheres())) {
			try {
				this.articleVenduManager.delete(articleVendu);
				response.sendRedirect(request.getContextPath());
			} catch (ArticleVenduManagerException e) {
				e.printStackTrace();
				erreur = "Une erreur est survenue !";
			}
		} else {
			erreur = "L'enchère ne peut être supprimer";
			request.setAttribute("erreur", erreur);
			response.sendRedirect(request.getContextPath()+"/detail-enchere?id="+articleVendu.getNo_article());
		}
	}
}
