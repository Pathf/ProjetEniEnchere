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
import org.encheres.bll.UtilisateurManager;
import org.encheres.bll.UtilisateurManagerException;
import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Categorie;
import org.encheres.bo.Retrait;
import org.encheres.bo.Utilisateur;

@WebServlet("/nouvelle-vente")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class VendreUnArticleServlet extends HttpServlet {
	private UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
	private ArticleVenduManager articleVenduManager = ArticleVenduManager.getInstance();
	private CategoriesManager categoriesManager = CategoriesManager.getInstance();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String pseudo = (String) session.getAttribute("pseudo");
		Utilisateur utilisateur = null;
		List<Categorie> categories = null;
		if (session.getAttribute("pseudo") != null) {
			try {
				utilisateur = this.utilisateurManager.getUtilisateur(pseudo);
				request.setAttribute("utilisateur", utilisateur);
			} catch (UtilisateurManagerException e) {
				System.err.println(e);
			}
			try {
				categories = this.categoriesManager.getListeCategorie();
				request.setAttribute("categories", categories);
			} catch (CategorieManagerException e) {
				System.err.println(e);
			}

			request.getRequestDispatcher("/WEB-INF/jsp/vendreUnArticle.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("pseudo") != null) {
			String erreur = null;
			boolean isActiver = ((Utilisateur)session.getAttribute("utilisateur")).isActiver();
			if(isActiver) {
				Utilisateur utilisateur = null;
				Categorie categorie = null;
				String pseudo = (String) session.getAttribute("pseudo");
				Date debutEnchere = null;
				Date finEnchere = null;
				String article = request.getParameter("article");
				String description = request.getParameter("description");
				Integer categorieId = Integer.parseInt(request.getParameter("categorie"));
				Part filePart = request.getPart("photoArticle");
				String photoNom = null;
				byte[] photoData = null;
				if(filePart != null) {
					photoNom = filePart.getSubmittedFileName();
					photoData = this.toByteArray(filePart.getInputStream());
				}
				try {
					debutEnchere = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("debutEnchere")).getTime());
				} catch (ParseException e) {
					System.err.println(e);
				}
				try {
					finEnchere = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("finEnchere")).getTime());
				} catch (ParseException e) {
					System.err.println(e);
				}
				String rue = request.getParameter("rue");
				String codePostal = request.getParameter("codePostal");
				String ville = request.getParameter("ville");
				String miseAPrixString = request.getParameter("miseAPrix");
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
						if(finEnchere != null) {
							if (finEnchere.after(debutEnchere)) {
								try {
									utilisateur = this.utilisateurManager.getUtilisateur(pseudo);
								} catch (UtilisateurManagerException e) {
									System.err.println(e);
								}
								try {
									categorie = this.categoriesManager.getCategorie(categorieId);
								} catch (CategorieManagerException e) {
									System.err.println(e);
								}
								Retrait retrait = new Retrait(null, rue, codePostal, ville);
								ArticleVendu articleVendu = new ArticleVendu(null, article, description, debutEnchere, finEnchere, miseAPrix, null, photoNom, photoData, utilisateur, categorie, retrait);
								try {
									this.articleVenduManager.addArticleVendu(articleVendu);
									response.sendRedirect(request.getContextPath());
									return;
								} catch (ArticleVenduManagerException e) {
									System.err.println(e);
									erreur = "Une erreur est survenue !";
								}
							} else {
								erreur = "La date de fin de l'enchère ne peut être inferieur à la date de début !";
							}
						} else {
							erreur = "La date de fin de l'enchère est obligatoire !";
						}
					} else {
						erreur = "Vous avez oublié de remplir un champ !";
					}
				} else {
					erreur = "La mise à prix est obligatoire !";
				}
			} else {
				erreur = "Vous avez été désactiver veuillez contacter l'admin !";
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
