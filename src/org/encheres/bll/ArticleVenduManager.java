package org.encheres.bll;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import org.encheres.bo.ArticleVendu;
import org.encheres.dal.DALException;
import org.encheres.dal.FactoryDAO;
import org.encheres.dal.dao.ArticleVenduDAO;
import org.encheres.dal.dao.RetraitDAO;

public class ArticleVenduManager {
	private static ArticleVenduManager articleVenduManager;
	private ArticleVenduDAO articleVenduDAO;
	private RetraitDAO retraitDAO;


	private ArticleVenduManager() {
		this.articleVenduDAO = FactoryDAO.getArticleVendu();
		this.retraitDAO = FactoryDAO.getRetrait();
	}

	public static ArticleVenduManager getInstance() {
		if(articleVenduManager == null) {
			articleVenduManager = new ArticleVenduManager();
		}
		return articleVenduManager;
	}

	public ArticleVendu getArticleVendu(Integer id) throws ArticleVenduManagerException {
		ArticleVendu articleVendu = null;
		try {
			articleVendu = this.articleVenduDAO.selectById(id);
		} catch (DALException e) {
			throw new ArticleVenduManagerException("getArticleVendu failed \n"+ e);
		}
		return articleVendu;
	}

	public List<ArticleVendu> getListeArticleVendu() throws ArticleVenduManagerException {
		List<ArticleVendu> articleVendus = null;
		try {
			articleVendus = this.articleVenduDAO.selectAll();
		} catch (DALException e) {
			throw new ArticleVenduManagerException("getListeArticleVendu failed \n"+ e);
		}
		return articleVendus;
	}

	public List<ArticleVendu> getListeArticleVenduByUtilisateur(Integer no_utilisateur) throws ArticleVenduManagerException {
		List<ArticleVendu> articleVendus = null;
		try {
			articleVendus = this.articleVenduDAO.selectByUtilisateur(no_utilisateur);
		} catch (DALException e) {
			throw new ArticleVenduManagerException("getListeArticleVenduByUtilisateur failed \n"+ e);
		}
		return articleVendus;
	}
	
	public List<ArticleVendu> getListeArticleVenduByCategorie(Integer no_categorie) throws ArticleVenduManagerException {
		List<ArticleVendu> articleVendus = null;

		try {
			articleVendus = this.articleVenduDAO.selectByCategorie(no_categorie);
		} catch (DALException e) {
			throw new ArticleVenduManagerException("getListeArticleVenduByCategorie failed - ", e);
		}

		return articleVendus;
	}
	
	
	public List<ArticleVendu> selectByFiltre(Integer no_categorie , String nom, Boolean date,String no_utilisateur, Boolean process, Boolean start, Boolean finish) throws ArticleVenduManagerException {
		List<ArticleVendu> articleVendus = null;

		try {
			articleVendus = this.articleVenduDAO.selectByFiltre(no_categorie,nom, date,no_utilisateur, process, start, finish );
		} catch (DALException e) {
			throw new ArticleVenduManagerException("selectByFiltre failed \n " + e);
		}

		return articleVendus;
	}
	
	public List<ArticleVendu> listByWinBid(String no_utilisateur) throws ArticleVenduManagerException{
			List<ArticleVendu> articleVendus = null;

			try {
				articleVendus = this.articleVenduDAO.listByWinBid(no_utilisateur);
			} catch (DALException e) {
				throw new ArticleVenduManagerException("listByWinBid failed - ", e);
			}

			return articleVendus;
		}
	
	
	public List<ArticleVendu> selectByCategorieAndNom(Integer no_categorie, String nom) throws ArticleVenduManagerException {
		List<ArticleVendu> articleVendus = null;

		try {
			articleVendus = this.articleVenduDAO.selectByCategorieAndNom(no_categorie, nom);
		} catch (DALException e) {
			throw new ArticleVenduManagerException("selectByCategorieAndNom failed - ", e);
		}

		return articleVendus;
	}
	
	public List<ArticleVendu> selectBydNom(String nom) throws ArticleVenduManagerException {
		List<ArticleVendu> articleVendus = null;

		try {
			articleVendus = this.articleVenduDAO.selectBydNom(nom);
		} catch (DALException e) {
			throw new ArticleVenduManagerException("selectBydNom failed - ", e);
		}

		return articleVendus;
	}
	
	public void addArticleVendu(ArticleVendu articleVendu) throws ArticleVenduManagerException {
		try {
			this.retraitDAO.insert(articleVendu.getRetrait());
			this.articleVenduDAO.insert(articleVendu);
		} catch (DALException e) {
			throw new ArticleVenduManagerException("addArticleVendu failed \n"+ e);
		}
	}

	public void updateArticleVendu(ArticleVendu articleVendu) throws ArticleVenduManagerException {
		try {
			this.articleVenduDAO.update(articleVendu);
			this.retraitDAO.update(articleVendu.getRetrait());
		} catch (DALException e) {
			throw new ArticleVenduManagerException("updateArticleVendu failed \n"+ e);
		}
	}
}
