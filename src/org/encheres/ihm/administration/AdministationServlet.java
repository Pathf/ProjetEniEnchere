package org.encheres.ihm.administration;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.EnchereManager;
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
			Integer no_utilisateurASupprimer = Integer.valueOf(request.getParameter("no_suppression"));
			Utilisateur utilisateur = this.utilisateurManager.getUtilisateur(no_utilisateurASupprimer);
			if(utilisateur.getAdministrateur()) {
				System.err.println("On ne peut pas supprimer un administrateur !");
				this.doGet(request, response);
				return;
			}
			// Proposition :
			List<Enchere> encheresPosteParUtilisateurS = this.enchereManager.getListeEnchere(utilisateur.getNo_utilisateur());
			for(Enchere enchere : encheresPosteParUtilisateurS) {
				Date dateNow = new Date(System.currentTimeMillis());
				Date dateFinEnchere = enchere.getArticle().getDate_fin_encheres();
				if(dateNow.before(dateFinEnchere) || dateNow.toString().equals(dateFinEnchere.toString())) {
					Enchere meilleureEnchere = this.enchereManager.getMeilleurEnchereByArticle(enchere.getArticle().getNo_article());
					if(meilleureEnchere.getNo_enchere() == enchere.getNo_enchere()) {
						this.enchereManager.suppressionDeLaMeilleureEnchere(enchere);
					} else {
						this.enchereManager.suppressionDeLaProposition(enchere);
					}
				}
			}
			// Vend : Recredité le dernier à avoir proposé > supprime les enchere > Supprime les articles
			List<ArticleVendu> utilisateurArticleVendus = this.articleVenduManager.getListeArticleVenduByUtilisateur(utilisateur.getNo_utilisateur());
			for(ArticleVendu articleVendu : utilisateurArticleVendus) {
				Date dateNow = new Date(System.currentTimeMillis());
				Date dateFinEnchere = articleVendu.getDate_fin_encheres();
				if(dateNow.before(dateFinEnchere)) {
					this.articleVenduManager.suppression(articleVendu);
				}
			}
			// modifier tous les article possedant le no_utilisateur correspondant à l'utilisateur
			Utilisateur utilisateurInconnu = this.utilisateurManager.getUtilisateur("inconnu");
			List<ArticleVendu> articleVendus = this.articleVenduManager.getListeArticleVenduByUtilisateur(utilisateur.getNo_utilisateur());
			for(ArticleVendu articleVendu : articleVendus) {
				articleVendu.setUtilisateur(utilisateurInconnu);
				this.articleVenduManager.updateArticleVendu(articleVendu);
			}
			// modifier toutes les encheres possedant le no_utilisateur correspondant à l'utilisateur
			encheresPosteParUtilisateurS = this.enchereManager.getListeEnchere(utilisateur.getNo_utilisateur());
			for(Enchere enchere : encheresPosteParUtilisateurS) {
				enchere.setUtilisateur(utilisateurInconnu);
				this.enchereManager.updateEncher(enchere);
			}
			// suppression utilisateur
			this.utilisateurManager.delete(utilisateur);
		} catch (Exception e) {
			System.err.println("doPost - administration\n" + e);
		}
		this.doGet(request, response);
	}
}
