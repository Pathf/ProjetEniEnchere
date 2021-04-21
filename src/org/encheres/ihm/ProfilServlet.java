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
 * Servlet implementation class ProfilServlet
 */
@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {
	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		HttpSession session = request.getSession();
		String pseudo = request.getParameter("pseudo");	
		Utilisateur utilisateur = null;
		Boolean isMonProfil = false;
		try {
			utilisateur = this.utilisateurManager.getUtilisateur(pseudo);			
		} catch (UtilisateurManagerException e) {
			// TODO: handle exception
		}
		if (session.getAttribute("pseudo") != null) {
		isMonProfil= session.getAttribute("pseudo").equals(pseudo);
		}
		request.setAttribute("utilisateur", utilisateur);
		request.setAttribute("isMonProfil", isMonProfil);
		request.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(request, response);
	}

}
