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

@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {
	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String pseudo = request.getParameter("pseudo");

		Boolean isMonProfil = false;
		try {
			if(session.getAttribute("pseudo") != null) {
				Utilisateur utilisateur = this.utilisateurManager.getUtilisateur(pseudo);
				request.setAttribute("utilisateur", utilisateur);
				isMonProfil= session.getAttribute("pseudo").equals(pseudo);
			}
		} catch (UtilisateurManagerException e) {
			System.err.println(e);
		}

		request.setAttribute("isMonProfil", isMonProfil);
		request.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(request, response);
	}
}
