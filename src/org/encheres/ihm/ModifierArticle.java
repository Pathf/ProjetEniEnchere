package org.encheres.ihm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.ArticleVenduManagerException;
import org.encheres.bll.CategorieManagerException;
import org.encheres.bll.CategoriesManager;
import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Categorie;

@WebServlet("/modifier-vente")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class ModifierArticle extends HttpServlet {
	private ArticleVenduManager articleVenduManager = ArticleVenduManager.getInstance();
	private CategoriesManager categoriesManager = CategoriesManager.getInstance();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String pseudo = (String) session.getAttribute("pseudo");
		Integer articleId = Integer.parseInt(request.getParameter("id"));
		ArticleVendu articleVendu = null;
		List<Categorie> categories = null;
		Date dateActuelle = new Date(new java.util.Date().getTime());

		try {
			articleVendu = this.articleVenduManager.getArticleVendu(articleId);
		} catch (ArticleVenduManagerException e) {
			System.err.println(e);
		}
		if (pseudo.equals(articleVendu.getUtilisateur().getPseudo()) && dateActuelle.before(articleVendu.getDate_debut_encheres())) {
			try {
				categories = this.categoriesManager.getListeCategorie();
				request.setAttribute("categories", categories);
				request.setAttribute("article", articleVendu);
			} catch (CategorieManagerException e) {
				System.err.println(e);
			}

			request.getRequestDispatcher("/WEB-INF/jsp/modifierArticle.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String pseudo = (String) session.getAttribute("pseudo");
		ArticleVendu articleVendu = null;
		Date dateActuelle = new Date(new java.util.Date().getTime());
		Integer articleId = Integer.parseInt(request.getParameter("id"));

		try {
			articleVendu = this.articleVenduManager.getArticleVendu(articleId);
		} catch (ArticleVenduManagerException e) {
			System.err.println(e);
		}
		if (pseudo.equals(articleVendu.getUtilisateur().getPseudo()) && dateActuelle.before(articleVendu.getDate_debut_encheres())) {
			Categorie categorie = null;
			String erreur = null;
			Date debutEnchere = null;
			Date finEnchere = null;
			String article = request.getParameter("article");
			String description = request.getParameter("description");
			Integer categorieId = Integer.parseInt(request.getParameter("categorie"));
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("codePostal");
			String ville = request.getParameter("ville");
			String miseAPrixString = request.getParameter("miseAPrix");
			Part filePart = request.getPart("photoArticle");
			String photoNom = null;
			byte[] photoData = null;
			if(filePart != null) {
				photoNom = filePart.getSubmittedFileName();
				photoData = this.toByteArray(filePart.getInputStream());
			}
			try {
				debutEnchere = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("debutEnchere")).getTime());
			} catch (ParseException e) {
				System.err.println(e);
			}
			try {
				finEnchere = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("finEnchere")).getTime());
			} catch (ParseException e) {
				System.err.println(e);
			}

			if (miseAPrixString != null && !miseAPrixString.isEmpty()) {
				Integer miseAPrix = Integer.parseInt(miseAPrixString);
				if(		article != null && !article.isEmpty() &&
						description != null && !description.isEmpty() &&
						categorieId != null &&
						debutEnchere != null &&
						rue != null && !rue.isEmpty() &&
						codePostal != null && !codePostal.isEmpty() &&
						ville != null && !ville.isEmpty()
						) {
					if(finEnchere != null && debutEnchere != null) {
						if (finEnchere.after(debutEnchere) && (debutEnchere.after(dateActuelle) || debutEnchere.compareTo(dateActuelle)==0)) {
							try {
								categorie = this.categoriesManager.getCategorie(categorieId);
							} catch (CategorieManagerException e) {
								System.err.println(e);
							}

							articleVendu.setNom_article(article);
							articleVendu.setDescription(description);
							articleVendu.setCategorie(categorie);
							articleVendu.setPrix_initial(miseAPrix);
							articleVendu.setDate_debut_encheres(debutEnchere);
							articleVendu.setDate_fin_encheres(finEnchere);
							articleVendu.getRetrait().setRue(rue);
							articleVendu.getRetrait().setCode_postal(codePostal);
							articleVendu.getRetrait().setVille(ville);
							if (photoNom != null && !photoNom.isEmpty()) {
								articleVendu.setPhotoNom(photoNom);
								articleVendu.setPhotoData(photoData);
							}

							try {
								this.articleVenduManager.updateArticleVendu(articleVendu);
								response.sendRedirect(request.getContextPath()+"/detail-enchere?id="+articleVendu.getNo_article());
								return;
							} catch (ArticleVenduManagerException e) {
								System.err.println(e);
								erreur = "Une erreur est survenue !";
							}
						} else {
							erreur = "Veuillez enregistrer des dates correctes !";
						}
					} else {
						erreur = "Les dates de l'enchère sont obligatoires !";
					}
				} else {
					erreur = "Vous avez oublié de remplir un champ !";
				}
			} else {
				erreur = "La mise à prix est obligatoire !";
			}
			request.setAttribute("erreur", erreur);
			this.doGet(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}

	private byte[] toByteArray(InputStream inputStream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[1024];
		while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		return buffer.toByteArray();
	}
}
