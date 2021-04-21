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

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet("/creercompte")
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bouton = request.getParameter("bouton");
		if("creer".equals(bouton)) {
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
							System.out.println(utilisateur);
							try {
								this.utilisateurManager.addUtilisateur(utilisateur);
							} catch (UtilisateurManagerException e) {
								if(e.toString().contains("Impossible d'insérer une clé en double dans l'objet")) {
									System.out.println("L'utilisateur exista déjà !");
								} else {
									// autres erreurs
									System.out.println("Une erreur est survenue :");
									System.out.println(e);
								}
							}
						} else {
							System.out.println("Vous avez oublié de remplir un champ !");
						}
					} else {
						System.out.println("pseudo incorrect");
					}
				} else {
					System.out.println("email incorrect");
				}
			} else {
				System.out.println("mot de passe incorrect");
			}
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}

}
