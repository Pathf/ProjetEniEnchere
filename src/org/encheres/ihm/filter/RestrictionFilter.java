package org.encheres.ihm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RestrictionFilter implements Filter {
	public static final String SESSION_ID = "id";

	@Override
	public void init(FilterConfig config) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		// Récupération de la session depuis la requête
		HttpSession session = request.getSession();

		/**
		 * Si l'utilisateur n'est pas connecté.
		 */
		if (session.getAttribute(SESSION_ID) == null) {
			// Redirection vers la page d'accueil
			response.sendRedirect(request.getContextPath());
		} else {
			// Affichage de la page ou prochain filtre
			chain.doFilter(request, response);
		}
	}

}