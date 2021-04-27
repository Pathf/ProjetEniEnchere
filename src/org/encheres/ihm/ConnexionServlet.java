package org.encheres.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.encheres.bll.UtilisateurManager;
import org.encheres.bll.UtilisateurManagerException;
import org.encheres.bo.Utilisateur;

@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String identifiant = request.getParameter("identifiant");
		String mdp = request.getParameter("mot_de_passe");
		Utilisateur utilisateur = null;
		try {
			utilisateur = this.utilisateurManager.getUtilisateurConnexion(identifiant, mdp);
			HttpSession session = request.getSession();
			session.setAttribute("pseudo", utilisateur.getPseudo());
			session.setAttribute("id", utilisateur.getNo_utilisateur());
			response.sendRedirect("encheres");
		} catch (UtilisateurManagerException e) {
			String erreur = (e.toString().contains("pas d'utilisateur")) ? "Identifiant ou mot de passe incorrect." : "Une erreur est survenue.";
			request.setAttribute("erreur", erreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
			rd.forward(request, response);
		}
	}
}
