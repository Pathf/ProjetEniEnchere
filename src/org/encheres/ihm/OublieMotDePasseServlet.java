package org.encheres.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.UtilisateurManager;
import org.encheres.bll.UtilisateurManagerException;
import org.encheres.bo.Utilisateur;

@WebServlet("/oubliemdp")
public class OublieMotDePasseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if("ProjetEniEnchere_connexion_login".equals(cookie.getName())) {
				request.setAttribute("login", cookie.getValue());
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/oubliemdp.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bouton = request.getParameter("bouton");
		if(bouton != null) {
			if("demande".equals(bouton)) {
				String email = request.getParameter("email");
				request.setAttribute("isModif", email);
				// envoie l'email
			} else {
				boolean erreur = false;
				String mot_de_passe = request.getParameter("mot_de_passe");
				String confirmation = request.getParameter("confirmation");
				if(mot_de_passe == null || !mot_de_passe.matches("^[a-zA-Z0-9@$+=]+$")) {
					erreur = true;
				}
				if(confirmation == null || !confirmation.matches("^[a-zA-Z0-9@$+=]+$")) {
					erreur = true;
				}
				if(!mot_de_passe.equals(confirmation)) {
					erreur = true;
				}
				try {
					if(!erreur) {
						Utilisateur utilisateur = this.utilisateurManager.getUtilisateurByEmail(bouton);
						System.out.println(utilisateur);
						utilisateur.setMot_de_passe(mot_de_passe);
						this.utilisateurManager.updateUtilisateur(utilisateur);
					} else {
						throw new UtilisateurManagerException("Le mot de passe n'est pas correct !");
					}
				} catch (UtilisateurManagerException e) {
					System.err.println(e);
				}
			}
		}
		this.doGet(request, response);
	}
}
