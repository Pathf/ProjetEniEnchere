package org.encheres.ihm.administration;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.UtilisateurManager;
import org.encheres.bll.UtilisateurManagerException;
import org.encheres.bo.Utilisateur;

@WebServlet("/administration")
public class AdministationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
		if(utilisateur != null && utilisateur.getAdministrateur()) {
			List<Utilisateur> utilisateurs = null;
			try {
				utilisateurs = this.utilisateurManager.getListeUtilisateur();
			} catch (UtilisateurManagerException e) {
				System.err.println(e);
			}
			request.setAttribute("utilisateurs", utilisateurs);
			request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer no_utilisateurASupprimer = Integer.valueOf(request.getParameter("no_suppression"));
		System.out.println("test " + no_utilisateurASupprimer);
		this.doGet(request, response);
	}

}
