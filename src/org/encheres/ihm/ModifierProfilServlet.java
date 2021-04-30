package org.encheres.ihm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.encheres.bll.UtilisateurManager;
import org.encheres.bll.UtilisateurManagerException;
import org.encheres.bo.Utilisateur;

@WebServlet("/monProfil")
public class ModifierProfilServlet extends HttpServlet {
	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur utilisateur = null;
		if (session.getAttribute("pseudo") != null) {
			try {
				utilisateur = this.utilisateurManager.getUtilisateur((String) session.getAttribute("pseudo"));
				request.setAttribute("utilisateur", utilisateur);
			} catch (UtilisateurManagerException e) {
				System.err.println(e);
			}
			request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("pseudo") != null) {
			StringBuffer erreur = new StringBuffer();
			String mot_de_passe = request.getParameter("mot_de_passe");
			String confirmation = request.getParameter("confirmation");
			String email = request.getParameter("email");
			String pseudo = request.getParameter("pseudo");
			String prenom = request.getParameter("prenom");
			String telephone = request.getParameter("telephone");
			String code_postal = request.getParameter("code_postal");
			String nom = request.getParameter("nom");
			String rue = request.getParameter("rue");
			String ville = request.getParameter("ville");
			if(mot_de_passe == null || !mot_de_passe.matches("^[a-zA-Z0-9@$+=]+$") || !mot_de_passe.equals(confirmation)) {
				erreur.append("Le mot de passe incorrect ! ");
			}
			if(email == null || !email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
				erreur.append("L'email est incorrect ! ");
			}
			if(pseudo == null || !pseudo.matches("^[a-zA-Z0-9]+$")) {
				erreur.append("Le pseudo est incorrect ! ");
			}
			if(prenom == null || !prenom.matches("^[a-zA-Z0-9]+$")) {
				erreur.append("Le prenom est incorrect ! ");
			}
			if(telephone == null || !telephone.matches("^[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}$")) {
				erreur.append("Le telephone est incorrect ! ");
			}
			if(code_postal == null || !code_postal.matches("^[0-9]{2,5}$")) {
				erreur.append("Le code_postal est incorrect ! ");
			}
			if(nom == null || !nom.matches("^[a-zA-Z0-9]+$")) {
				erreur.append("Le nom est incorrect ! ");
			}
			if(rue == null || !rue.matches("^[a-zA-Z0-9]+$")) {
				erreur.append("La rue est incorrect ! ");
			}
			if(ville == null || !ville.matches("^[a-zA-Z]+$")) {
				erreur.append("La ville est incorrect ! ");
			}
			try {
				if(erreur.toString().isEmpty()) {
					Utilisateur utilisateur = this.utilisateurManager.getUtilisateur(pseudo);
					utilisateur.setPseudo(pseudo);
					utilisateur.setEmail(email);
					utilisateur.setNom(nom);
					utilisateur.setPrenom(prenom);
					utilisateur.setTelephone(telephone);
					utilisateur.setRue(rue);
					utilisateur.setCode_postal(code_postal);
					utilisateur.setVille(ville);
					utilisateur.setMot_de_passe(mot_de_passe);
					this.utilisateurManager.updateUtilisateur(utilisateur);
					response.sendRedirect(request.getContextPath() + "/profil?pseudo=" + utilisateur.getPseudo());
					return;
				}else {
					throw new UtilisateurManagerException(erreur.toString());
				}
			} catch (UtilisateurManagerException e) {
				if(e.toString().contains("Impossible d'insérer une clé en double dans l'objet")) {
					erreur = new StringBuffer().append("L'utilisateur exista déjà !");
				}
				// System.err.println(e.toString()); TODO : log
				request.setAttribute("erreur", erreur.toString());
				this.doGet(request, response);
			}
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}
}
