package org.encheres.ihm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deconnexion")
public class DeconnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("pseudo", "");
		request.getSession().removeAttribute("pseudo");
		request.getSession().setAttribute("id", "");
		request.getSession().removeAttribute("id");
		request.getSession().setAttribute("utilisateur", null);
		request.getSession().removeAttribute("utilisateur");
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath());
	}

}
