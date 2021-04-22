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

/**
 * Servlet implementation class ModifierProfilServlet
 */
@WebServlet("/monProfil")
public class ModifierProfilServlet extends HttpServlet {
	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierProfilServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		HttpSession session = request.getSession();
		Utilisateur utilisateur = null;
		
		if (session.getAttribute("pseudo") != null) {
			try {
				utilisateur = this.utilisateurManager.getUtilisateur((String) session.getAttribute("pseudo"));			
			} catch (UtilisateurManagerException e) {
				
			}

			request.setAttribute("utilisateur", utilisateur);
			request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("pseudo") != null) {
			Utilisateur utilisateur = null;
			String erreur = null;
			String pseudo = request.getParameter("pseudo");
			String email = request.getParameter("email");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String telephone = request.getParameter("telephone");
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("code_postal");
			String ville = request.getParameter("ville");
			String motDePasse = request.getParameter("mot_de_passe");
			String confirmation = request.getParameter("confirmation");
			
			
			try {
				utilisateur = this.utilisateurManager.getUtilisateur((String) session.getAttribute("pseudo"));
				
			} catch (UtilisateurManagerException e) {
				
			}
			
			
			if(pseudo.matches("[a-zA-Z0-9]+")) {
				utilisateur.setPseudo(pseudo);
			} else {
				erreur = "Le pseudo est incorrect !";
			}
			if(email.contains("@") && email.contains(".")) {
				utilisateur.setEmail(email);
			} else {
				erreur = "L'email est incorrect !";
			}
			if(		prenom != null && !prenom.isEmpty() &&
					telephone != null && !telephone.isEmpty() &&
					codePostal != null && !codePostal.isEmpty() &&
					nom != null && !nom.isEmpty() &&
					rue != null && !rue.isEmpty() &&
					ville != null && !ville.isEmpty()
					) {
				utilisateur.setNom(nom);
				utilisateur.setPrenom(prenom);
				utilisateur.setTelephone(telephone);
				utilisateur.setRue(rue);
				utilisateur.setCode_postal(codePostal);
				utilisateur.setVille(ville);
			}
			if(motDePasse != null && !motDePasse.isEmpty()) {
				if (motDePasse.equals(confirmation)) {
					utilisateur.setMot_de_passe(request.getParameter("mot_de_passe"));
				} else {
					erreur = "Le nouveau mot de passe et la confirmation du nouveau mot de passe sont différents !";
				}
			}
			
			try {
				utilisateurManager.updateUtilisateur(utilisateur);
			} catch (UtilisateurManagerException e) {
				
				if(e.toString().contains("Impossible d'insérer une clé en double dans l'objet")) {
					erreur = "L'utilisateur ou l'email sont déjà utilisés !";
					System.out.println(erreur);
				} else {
					erreur = "Une erreur est survenue !";
					System.out.println(e);
				}
			}
			
			if (erreur != null) {
				request.setAttribute("erreur", erreur);
				doGet(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/profil?pseudo=" + utilisateur.getPseudo());
			}
		} else {
			response.sendRedirect(request.getContextPath());
		}		
	}
}
