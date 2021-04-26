package org.encheres.ihm;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

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

/**
 * Servlet implementation class EncherirServlet
 */
@WebServlet("/detail-enchere")
public class EncherirServlet extends HttpServlet {
	private ArticleVenduManager articleVenduManager = ArticleVenduManager.getInstance();
	private EnchereManager enchereManager = EnchereManager.getInstance();
	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Boolean isConnect = false;
		Boolean articleValide = false;
		Boolean meilleurEnchereNotNull = false;
		Boolean isMeilleurEncherisseur = false;
		Boolean isEnCour = false;
		String pseudo = (String) session.getAttribute("pseudo");
		ArticleVendu article = null;
		Enchere meilleurEnchere = null;
		Integer articleId = null;
		if (request.getParameter("id") != null) {
			articleId = Integer.parseInt(request.getParameter("id"));

			isConnect = (pseudo != null);
			
			try {
				article = this.articleVenduManager.getArticleVendu(articleId);
				if(article != null) {
					articleValide = true;
					long debutEnchere = article.getDate_debut_encheres().getTime();
					long finEnchere = article.getDate_fin_encheres().getTime();
					Calendar calendar = Calendar.getInstance();
				    Date date = new Date(calendar.getTime().getTime());
				    if(date.getTime() >= debutEnchere && date.getTime() < finEnchere) {
				    	isEnCour = true;
				    }
					
				}
			} catch (ArticleVenduManagerException e) {
				System.out.println(e);
			} 
			try {
				meilleurEnchere = this.enchereManager.getMeilleurEnchereByArticle(articleId);
				if(meilleurEnchere != null) {
					meilleurEnchereNotNull = true;
					isMeilleurEncherisseur = meilleurEnchere.getUtilisateur().equals(this.utilisateurManager.getUtilisateur(pseudo));
				}
			} catch (EnchereManagerException e) {
				System.out.println(e);
			} catch (UtilisateurManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		request.setAttribute("isMeilleurEncherisseur", isMeilleurEncherisseur);
		request.setAttribute("meilleurEnchereNotNull", meilleurEnchereNotNull);
		request.setAttribute("isEnCour", isEnCour);
		request.setAttribute("isConnect", isConnect);
		request.setAttribute("articleValide", articleValide);
		request.setAttribute("article", article);
		request.setAttribute("enchere", meilleurEnchere);
		request.getRequestDispatcher("/WEB-INF/jsp/detailEnchere.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("pseudo") != null) {
			String pseudo = (String) session.getAttribute("pseudo");
			Utilisateur utilisateur = null;
			Boolean meilleurEnchereNotNull = false;
			String erreur = null;
			String propositionString = request.getParameter("proposition");
			Integer articleId = Integer.parseInt(request.getParameter("id"));
			if (propositionString != null && !propositionString.isEmpty()) {
				Integer proposition = Integer.parseInt(propositionString);
				Enchere meilleurEnchere = null;
				ArticleVendu articleVendu = null;
				
				try {
					meilleurEnchere = this.enchereManager.getMeilleurEnchereByArticle(articleId);
					if(meilleurEnchere != null) {
						meilleurEnchereNotNull = true;
					}
				} catch (EnchereManagerException e) {
				}
				
				try {
					articleVendu = this.articleVenduManager.getArticleVendu(articleId);
				} catch (ArticleVenduManagerException e) {
				}
				
				try {
					utilisateur = this.utilisateurManager.getUtilisateur(pseudo);
				} catch (UtilisateurManagerException e) {
				}
				if (meilleurEnchereNotNull) {
					if (proposition > meilleurEnchere.getMontant_enchere()) {
						if (isCreditable(proposition, utilisateur)) {
							Calendar calendar = Calendar.getInstance();
						    Date date = new Date(calendar.getTime().getTime());
							Enchere nouvelleEnchere = new Enchere(null, date, proposition, articleVendu, utilisateur);
							try {
								this.enchereManager.addEnchere(nouvelleEnchere);
								Utilisateur acheteurPrecedent = meilleurEnchere.getUtilisateur();
								acheteurPrecedent.setCredit(acheteurPrecedent.getCredit() + meilleurEnchere.getMontant_enchere());
								utilisateur.setCredit(utilisateur.getCredit() - proposition);
								this.utilisateurManager.updateUtilisateur(utilisateur);
								this.utilisateurManager.updateUtilisateur(acheteurPrecedent);
							} catch (EnchereManagerException e) {
								e.printStackTrace();
							} catch (UtilisateurManagerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							erreur = "Vous n'avez pas assez de points !";
						}
					} else {
						erreur = "La proposition doit être supérieure à l'offre en cours !";
					}
				} else {
					if (proposition > articleVendu.getPrix_initial()) {
						if (isCreditable(proposition, utilisateur)) {
							Calendar calendar = Calendar.getInstance();
						    Date date = new Date(calendar.getTime().getTime());
							Enchere nouvelleEnchere = new Enchere(null, date, proposition, articleVendu, utilisateur);
							try {
								this.enchereManager.addEnchere(nouvelleEnchere);
								utilisateur.setCredit(utilisateur.getCredit() - proposition);
							} catch (EnchereManagerException e) {
								e.printStackTrace();
							}
						} else {
							erreur = "Vous n'avez pas assez de points !";
						}
					} else {
						erreur = "La proposition doit être supérieure à l'offre en cours !";
					}
				}
			} else {
				erreur = "La proposition est incorrect !";
			}			
			request.setAttribute("erreur", erreur);
			doGet(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}
	
	private Boolean isCreditable(Integer proposition, Utilisateur utilisateur) {
		Boolean isCreditable = proposition <= utilisateur.getCredit();
		return isCreditable;	
	}
}
