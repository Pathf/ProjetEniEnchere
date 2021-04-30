package org.encheres.ihm.administration;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.ArticleVenduManagerException;
import org.encheres.bll.EnchereManager;
import org.encheres.bll.EnchereManagerException;
import org.encheres.bll.UtilisateurManager;
import org.encheres.bll.UtilisateurManagerException;
import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Enchere;
import org.encheres.bo.Utilisateur;

@WebServlet("/administration")
public class AdministationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
	private EnchereManager enchereManager = EnchereManager.getInstance();
	private ArticleVenduManager articleVenduManager = ArticleVenduManager.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
		if(utilisateur != null && utilisateur.getAdministrateur()) {
			List<Utilisateur> utilisateurs = null;
			try {
				utilisateurs = this.utilisateurManager.getListeUtilisateur();
			} catch (UtilisateurManagerException e) {
				System.err.println(e);
			}
			request.setAttribute("utilisateurs", utilisateurs);
			request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String no_suppression = request.getParameter("no_suppression");
			String no_desactivation = request.getParameter("no_desactivation");
			if(no_suppression != null) {
				this.suppression(no_suppression, request, response);
			} else {
				this.desactivation(no_desactivation, request, response);
			}
		} catch (Exception e) {
			System.err.println("doPost - administration\n" + e);
		}
		this.doGet(request, response);
	}

	private void desactivation(String no_desactivation, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UtilisateurManagerException, EnchereManagerException, ArticleVenduManagerException {
		Integer no_utilisateurADesactiver = Integer.valueOf(no_desactivation);

		Utilisateur utilisateur = this.utilisateurManager.getUtilisateur(no_utilisateurADesactiver);
		if(utilisateur.getAdministrateur()) {
			System.err.println("On ne peut pas désactiver un administrateur !");
		} else {
			this.utilisateurManager.inverseActiver(utilisateur);
			this.enchereManager.annulerLesPropositionsDEnchereEnCours(utilisateur.getNo_utilisateur());
			this.articleVenduManager.annuleLesVentesEnCoursDUnUtilisateur(utilisateur.getNo_utilisateur());
		}
	}

	private void suppression(String no_suppression, HttpServletRequest request, HttpServletResponse response) throws UtilisateurManagerException, EnchereManagerException, ServletException, IOException, ArticleVenduManagerException {
		Integer no_utilisateurASupprimer = Integer.valueOf(no_suppression);

		Utilisateur utilisateur = this.utilisateurManager.getUtilisateur(no_utilisateurASupprimer);
		if(utilisateur.getAdministrateur()) {
			System.err.println("On ne peut pas supprimer un administrateur !");
			this.doGet(request, response);
			return;
		}
		this.enchereManager.annulerLesPropositionsDEnchereEnCours(utilisateur.getNo_utilisateur());
		this.articleVenduManager.annuleLesVentesEnCoursDUnUtilisateur(utilisateur.getNo_utilisateur());

		// modifier tous les article possedant le no_utilisateur correspondant à l'utilisateur
		Utilisateur utilisateurInconnu = this.utilisateurManager.getUtilisateur("inconnu");
		List<ArticleVendu> articleVendus = this.articleVenduManager.getListeArticleVenduByUtilisateur(utilisateur.getNo_utilisateur());
		for(ArticleVendu articleVendu : articleVendus) {
			articleVendu.setUtilisateur(utilisateurInconnu);
			this.articleVenduManager.updateArticleVendu(articleVendu);
		}
		// modifier toutes les encheres possedant le no_utilisateur correspondant à l'utilisateur
		List<Enchere> encheresPosteParUtilisateurS = this.enchereManager.getListeEnchere(utilisateur.getNo_utilisateur());
		for(Enchere enchere : encheresPosteParUtilisateurS) {
			enchere.setUtilisateur(utilisateurInconnu);
			this.enchereManager.updateEncher(enchere);
		}
		// suppression utilisateur
		this.utilisateurManager.delete(utilisateur);
	}
}
