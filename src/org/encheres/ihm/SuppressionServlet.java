package org.encheres.ihm;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.ArticleVenduManagerException;
import org.encheres.bll.EnchereManager;
import org.encheres.bll.EnchereManagerException;
import org.encheres.bll.UtilisateurManager;
import org.encheres.bll.UtilisateurManagerException;
import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Enchere;
import org.encheres.bo.Utilisateur;

@WebServlet("/suppression")
public class SuppressionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EnchereManager enchereManager = EnchereManager.getInstance();
	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
	private ArticleVenduManager articleVenduManager = ArticleVenduManager.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String erreur = "Une erreur est survenue !";
		// suppression du compte utilisateur :
		HttpSession session = request.getSession();
		String pseudo = (String)session.getAttribute("pseudo");
		if(pseudo != null && !pseudo.isEmpty()) {
			// suppression que si pas d'encheres en cours
			try {
				Utilisateur utilisateur = this.utilisateurManager.getUtilisateur(pseudo);
				// acheteur (pas postulé sur une enchère actuelle)
				List<Enchere> encheres = this.enchereManager.getListeEnchere(utilisateur.getNo_utilisateur());
				boolean enchereEnCour = false;
				for(Enchere enchere : encheres) {
					Date dateNow = new Date(System.currentTimeMillis());
					Date dateFinEnchere = enchere.getArticle().getDate_fin_encheres();
					if(dateNow.before(dateFinEnchere)) {
						enchereEnCour = true;
						break;
					}
				}
				//vendeur(article ou la date de fin de l'enchere n'est ps inferieur à now)
				List<ArticleVendu> utilisateurArticleVendus = this.articleVenduManager.getListeArticleVenduByUtilisateur(utilisateur.getNo_utilisateur());
				for(ArticleVendu articleVendu : utilisateurArticleVendus) {
					Date dateNow = new Date(System.currentTimeMillis());
					Date dateFinEnchere = articleVendu.getDate_fin_encheres();
					if(dateNow.before(dateFinEnchere)) {
						enchereEnCour = true;
						break;
					}
				}
				if(!enchereEnCour) {
					// Déconnexion
					request.getSession().removeAttribute("pseudo");
					request.getSession().invalidate();
					// modifier tous les article possedant le no_utilisateur correspondant à l'utilisateur
					Utilisateur utilisateurInconnu = this.utilisateurManager.getUtilisateur("inconnu");
					List<ArticleVendu> articleVendus = this.articleVenduManager.getListeArticleVenduByUtilisateur(utilisateur.getNo_utilisateur());
					for(ArticleVendu articleVendu : articleVendus) {
						articleVendu.setUtilisateur(utilisateurInconnu);
						this.articleVenduManager.updateArticleVendu(articleVendu);
					}
					// modifier toutes les encheres possedant le no_utilisateur correspondant à l'utilisateur
					for(Enchere enchere : encheres) {
						enchere.setUtilisateur(utilisateurInconnu);
						this.enchereManager.updateEncher(enchere);
					}
					// suppression utilisateur
					this.utilisateurManager.delete(utilisateur);
					response.sendRedirect(request.getContextPath());
					return;
				} else {
					erreur = "Il reste des encheres en cours";
				}
			} catch (UtilisateurManagerException | EnchereManagerException | ArticleVenduManagerException e) {
				System.out.println(e); //TODO : Log
			}
		}
		request.setAttribute("erreur", erreur);
		request.getRequestDispatcher("monProfil").forward(request, response);
	}
}
