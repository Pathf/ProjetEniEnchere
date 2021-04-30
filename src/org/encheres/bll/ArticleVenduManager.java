package org.encheres.bll;

import java.sql.Date;
import java.util.List;

import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Enchere;
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
			throw new ArticleVenduManagerException("getArticleVendu failed\n" + e);
		}
		return articleVendu;
	}

	public List<ArticleVendu> getListeArticleVendu() throws ArticleVenduManagerException {
		List<ArticleVendu> articleVendus = null;
		try {
			articleVendus = this.articleVenduDAO.selectAll();
		} catch (DALException e) {
			throw new ArticleVenduManagerException("getListeArticleVendu failed\n" + e);
		}
		return articleVendus;
	}

	public List<ArticleVendu> getListeArticleVenduByUtilisateur(Integer no_utilisateur) throws ArticleVenduManagerException {
		List<ArticleVendu> articleVendus = null;
		try {
			articleVendus = this.articleVenduDAO.selectByUtilisateur(no_utilisateur);
		} catch (DALException e) {
			throw new ArticleVenduManagerException("getListeArticleVenduByUtilisateur failed\n" + e);
		}
		return articleVendus;
	}

	public List<ArticleVendu> selectByFiltre(Integer no_categorie , String nom, Boolean date,Integer no_utilisateur, Boolean process, Boolean start, Boolean finish) throws ArticleVenduManagerException {
		List<ArticleVendu> articleVendus = null;

		try {
			articleVendus = this.articleVenduDAO.selectByFiltre(no_categorie,nom, date,no_utilisateur, process, start, finish );
		} catch (DALException e) {
			throw new ArticleVenduManagerException("selectByFiltre failed \n " + e);
		}

		return articleVendus;
	}

	public List<ArticleVendu> listByWinBid(Integer no_utilisateur) throws ArticleVenduManagerException{
		List<ArticleVendu> articleVendus = null;

		try {
			articleVendus = this.articleVenduDAO.listByWinBid(no_utilisateur);
		} catch (DALException e) {
			throw new ArticleVenduManagerException("listByWinBid failed\n" + e);
		}

		return articleVendus;
	}

	public void addArticleVendu(ArticleVendu articleVendu) throws ArticleVenduManagerException {
		try {
			this.retraitDAO.insert(articleVendu.getRetrait());
			this.articleVenduDAO.insert(articleVendu);
		} catch (DALException e) {
			throw new ArticleVenduManagerException("addArticleVendu failed\n" + e);
		}
	}

	public void updateArticleVendu(ArticleVendu articleVendu) throws ArticleVenduManagerException {
		try {
			this.articleVenduDAO.update(articleVendu);
			this.retraitDAO.update(articleVendu.getRetrait());
		} catch (DALException e) {
			throw new ArticleVenduManagerException("updateArticleVendu failed\n" + e);
		}
	}

	public void delete(ArticleVendu articleVendu) throws ArticleVenduManagerException {
		try {
			this.articleVenduDAO.remove(articleVendu);
		} catch (DALException e) {
			throw new ArticleVenduManagerException("delete failed\n" +e);
		}

	}

	public void suppression(ArticleVendu articleVendu) throws ArticleVenduManagerException {
		EnchereManager enchereManager = EnchereManager.getInstance();
		List<Enchere> enchereASupprimer;
		try {
			enchereASupprimer = enchereManager.getListeEnchereByArticle(articleVendu.getNo_article());
			enchereManager.suppressionDesEncheres(enchereASupprimer);
		} catch (EnchereManagerException e) {
			throw new ArticleVenduManagerException("suppression failed - " + e);
		}
		try {
			this.articleVenduDAO.remove(articleVendu);
		} catch (DALException e) {
			throw new ArticleVenduManagerException("suppression failed\n" + e);
		}
	}

	// Vend : Recredité le dernier à avoir proposé > supprime les enchere > Supprime les articles
	public void annuleLesVentesEnCoursDUnUtilisateur(Integer no_utilisateur) throws ArticleVenduManagerException {
		List<ArticleVendu> utilisateurArticleVendus = this.getListeArticleVenduByUtilisateur(no_utilisateur);
		for(ArticleVendu articleVendu : utilisateurArticleVendus) {
			Date dateNow = new Date(System.currentTimeMillis());
			Date dateFinEnchere = articleVendu.getDate_fin_encheres();
			if(dateNow.before(dateFinEnchere)) {
				this.suppression(articleVendu);
			}
		}
	}
}
