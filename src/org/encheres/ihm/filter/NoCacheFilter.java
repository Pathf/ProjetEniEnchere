package org.encheres.ihm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class NoCacheFilter implements Filter {
	@Override
	public void init(FilterConfig config) throws ServletException {}

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			if (response instanceof HttpServletResponse) {
				HttpServletResponse httpresponse = (HttpServletResponse)response ;
				// Supprimer la possibilit√© de mettre le cahce avec "Cache-Control", "Pragma" et "Expires"
				httpresponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate") ;
				httpresponse.setHeader("Pragma", "no-cache");
				httpresponse.setHeader("Expires", "0") ;
				// TODO : a mettre en log
				//System.out.println("Filtrage du cache : " + ((HttpServletRequest)request).getRequestURI()) ;
			}
			chain.doFilter (request, response);
		} catch (IOException | ServletException e) {
			System.err.println ("Exception dans NoCacheFilter :\n" + e);
		}
	}
}
