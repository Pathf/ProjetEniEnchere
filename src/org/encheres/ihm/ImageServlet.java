package org.encheres.ihm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.ArticleVenduManagerException;
import org.encheres.bo.ArticleVendu;

@WebServlet("/images")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleVenduManager articleVenduManager = ArticleVenduManager.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idImage = request.getParameter("id");
		byte[] tmp = null;
		try {
			ArticleVendu articleVendu = this.articleVenduManager.getArticleVendu(Integer.valueOf(idImage));
			tmp = articleVendu.getPhotoData();
		} catch (ArticleVenduManagerException | NumberFormatException e) {
			System.err.println(e);
		}

		if(tmp != null && tmp.length > 0) {
			response.getOutputStream().write(tmp);
		} else {
			response.getWriter().append("Il n'y a pas d'image pour cette article");
		}
	}

}
