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
public class connexionServlet extends HttpServlet {
	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String identifiant = request.getParameter("identifiant");
		String mdp = request.getParameter("mdp");
		// Gestion de la connexion
		// 'utilisateur1', 'un@test.com', 'password'
		Utilisateur utilisateur = null;
		try {
			utilisateur = this.utilisateurManager.getUtilisateurConnexion(identifiant, mdp);
			HttpSession session = request.getSession();
			session.setAttribute("pseudo", utilisateur.getPseudo());
		} catch (UtilisateurManagerException e) {
			System.out.println(e);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
			rd.forward(request, response);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
		response.sendRedirect("encheres");  
//		rd.forward(request, response);
	}

}
