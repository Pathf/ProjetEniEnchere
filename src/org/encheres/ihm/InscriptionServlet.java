package org.encheres.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.UtilisateurManager;
import org.encheres.bll.UtilisateurManagerException;
import org.encheres.bo.Utilisateur;

@WebServlet("/creercompte")
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bouton = request.getParameter("bouton");
		if("creer".equals(bouton)) {
			String erreur = null;
			String mot_de_passe = request.getParameter("mot_de_passe");
			String confirmation = request.getParameter("confirmation");
			String email = request.getParameter("email");
			String pseudo = request.getParameter("pseudo");
			if(mot_de_passe != null && !mot_de_passe.isEmpty() && mot_de_passe.equals(confirmation)) {
				if(email.contains("@") && email.contains(".")) {
					if(pseudo.matches("[a-zA-Z0-9]+")) {
						String prenom = request.getParameter("prenom");
						String telephone = request.getParameter("telephone");
						String code_postal = request.getParameter("code_postal");
						String nom = request.getParameter("nom");
						String rue = request.getParameter("rue");
						String ville = request.getParameter("ville");
						if(		prenom != null && !prenom.isEmpty() &&
								telephone != null && !telephone.isEmpty() &&
								code_postal != null && !code_postal.isEmpty() &&
								nom != null && !nom.isEmpty() &&
								rue != null && !rue.isEmpty() &&
								ville != null && !ville.isEmpty()
								) {
							Utilisateur utilisateur = new Utilisateur(null, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, 100, false);
							try {
								this.utilisateurManager.addUtilisateur(utilisateur);
								response.sendRedirect(request.getContextPath());
								return ;
							} catch (UtilisateurManagerException e) {
								if(e.toString().contains("Impossible d'insérer une clé en double dans l'objet")) {
									erreur = "L'utilisateur exista déjà !";
								} else {
									erreur = "Une erreur est survenue !";
								}
								System.err.println(e);
							}
						} else {
							erreur = "Vous avez oublié de remplir un champ !";
						}
					} else {
						erreur = "Le pseudo est incorrect !";
					}
				} else {
					erreur = "L'email est incorrect !";
				}
			} else {
				erreur = "Le mot de passe incorrect !";
			}
			request.setAttribute("erreur", erreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp");
			rd.forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}
}
