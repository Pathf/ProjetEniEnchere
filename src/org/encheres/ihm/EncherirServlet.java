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

@WebServlet("/detail-enchere")
public class EncherirServlet extends HttpServlet {
	private ArticleVenduManager articleVenduManager = ArticleVenduManager.getInstance();
	private EnchereManager enchereManager = EnchereManager.getInstance();
	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
	private Calendar calendar = Calendar.getInstance();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		boolean isConnect = false;
		boolean articleValide = false;
		boolean meilleurEnchereNotNull = false;
		boolean isMeilleurEncherisseur = false;
		boolean isEnCour = false;
		boolean isGagnant = false;
		boolean isTerminee = false;
		boolean vendeur = false;
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

					Date date = new Date(this.calendar.getTime().getTime());
					if(date.getTime() >= debutEnchere && date.getTime() < finEnchere) {
						isEnCour = true;
					}
					if (isConnect) {
						try {
							vendeur = this.utilisateurManager.getUtilisateur(pseudo).equals(article.getUtilisateur());
						} catch (UtilisateurManagerException e) {
							System.err.println(e);
						}
					}
				}
			} catch (ArticleVenduManagerException e) {
				System.err.println(e);
			}
			try {
				meilleurEnchere = this.enchereManager.getMeilleurEnchereByArticle(articleId);
				if(meilleurEnchere != null) {
					meilleurEnchereNotNull = true;
					isMeilleurEncherisseur = meilleurEnchere.getUtilisateur().equals(this.utilisateurManager.getUtilisateur(pseudo));
					isGagnant = this.isGagnant(this.utilisateurManager.getUtilisateur(pseudo), article, meilleurEnchere);
					isTerminee = this.isTerminee(article);
				}
			} catch (EnchereManagerException | UtilisateurManagerException e) {
				System.err.println(e);
			}
		}
		request.setAttribute("isMeilleurEncherisseur", isMeilleurEncherisseur);
		request.setAttribute("meilleurEnchereNotNull", meilleurEnchereNotNull);
		request.setAttribute("isGagnant", isGagnant);
		request.setAttribute("isTerminee", isTerminee);
		request.setAttribute("isEnCour", isEnCour);
		request.setAttribute("isConnect", isConnect);
		request.setAttribute("vendeur", vendeur);
		request.setAttribute("articleValide", articleValide);
		request.setAttribute("article", article);
		request.setAttribute("enchere", meilleurEnchere);
		request.getRequestDispatcher("/WEB-INF/jsp/detailEnchere.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
					utilisateur = this.utilisateurManager.getUtilisateur(pseudo);
					articleVendu = this.articleVenduManager.getArticleVendu(articleId);
					
					if (this.isCreditable(proposition, utilisateur)) {
						Date date = new Date(this.calendar.getTime().getTime());
						Enchere nouvelleEnchere = new Enchere(null, date, proposition, articleVendu, utilisateur);
						
						if (meilleurEnchereNotNull) {
							try {
								this.enchereManager.updateEncher(nouvelleEnchere);
							} catch (EnchereManagerException e) {
								erreur ="Votre proposition n'est pas valable !";
							}
						} else {
							try {
								this.enchereManager.addEnchere(nouvelleEnchere);
							} catch (EnchereManagerException e) {
								erreur ="Votre proposition n'est pas valable !";
							}
						}
					} else {
						erreur ="Vous n'avez pas assez de crédits !";
					}							
				} catch (EnchereManagerException e) {
					System.err.println(e);
				} catch (UtilisateurManagerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArticleVenduManagerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				
			} else {
				erreur = "Veuillez faire une proposition !";
			}
			request.setAttribute("erreur", erreur);
			this.doGet(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}

	private Boolean isCreditable(Integer proposition, Utilisateur utilisateur) {
		Boolean isCreditable = proposition <= utilisateur.getCredit();
		return isCreditable;
	}

	private Boolean isGagnant(Utilisateur utilisateur, ArticleVendu articleVendu, Enchere enchere) {
		Boolean isGagnant = false;
		if (this.isTerminee(articleVendu)) {
			isGagnant = enchere.getUtilisateur().equals(utilisateur);
		}
		return isGagnant;
	}

	private Boolean isTerminee(ArticleVendu articleVendu) {
		Date date = new Date(this.calendar.getTime().getTime());
		return articleVendu.getDate_fin_encheres().getTime() <= date.getTime();
	}
}
