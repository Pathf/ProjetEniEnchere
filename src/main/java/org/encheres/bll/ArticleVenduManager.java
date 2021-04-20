package org.encheres.bll;

import java.util.List;

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
			throw new ArticleVenduManagerException("getArticleVendu failed - ", e);
		}

		return articleVendu;
	}

	public List<ArticleVendu> getListeArticleVendu() throws ArticleVenduManagerException {
		List<ArticleVendu> articleVendus = null;

		try {
			articleVendus = this.articleVenduDAO.selectAll();
		} catch (DALException e) {
			throw new ArticleVenduManagerException("getListeArticleVendu failed - ", e);
		}

		return articleVendus;
	}

	public void addArticleVendu(ArticleVendu articleVendu) throws ArticleVenduManagerException {
		try {
			this.articleVenduDAO.insert(articleVendu);
			this.retraitDAO.insert(articleVendu.getRetrait());
		} catch (DALException e) {
			throw new ArticleVenduManagerException("addArticleVendu failed - ", e);
		}
	}

	public void updateArticleVendu(ArticleVendu articleVendu) throws ArticleVenduManagerException {
		try {
			this.articleVenduDAO.update(articleVendu);
			this.retraitDAO.update(articleVendu.getRetrait());
		} catch (DALException e) {
			throw new ArticleVenduManagerException("updateArticleVendu failed - ", e);
		}
	}
}
