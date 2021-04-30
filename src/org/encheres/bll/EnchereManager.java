package org.encheres.bll;

import java.util.ArrayList;
import java.util.List;

import org.encheres.bo.Enchere;
import org.encheres.bo.Utilisateur;
import org.encheres.dal.DALException;
import org.encheres.dal.FactoryDAO;
import org.encheres.dal.dao.EnchereDAO;

public class EnchereManager {
	private static EnchereManager enchereManager;
	private EnchereDAO enchereDAO;

	private EnchereManager() {
		this.enchereDAO = FactoryDAO.getEnchere();
	}

	public static EnchereManager getInstance() {
		if(enchereManager == null) {
			enchereManager = new EnchereManager();
		}
		return enchereManager;
	}

	public Enchere getEnchere(Integer id) throws EnchereManagerException {
		Enchere enchere = null;
		try {
			enchere = this.enchereDAO.selectById(id);
		} catch (DALException e) {
			throw new EnchereManagerException("getEnchere failed\n" + e);
		}
		return enchere;
	}

	public Enchere getMeilleurEnchereByArticle(Integer id_article) throws EnchereManagerException {
		Enchere enchere = null;
		try {
			enchere = this.enchereDAO.selectMeilleurByArticle(id_article);
		} catch (DALException e) {
			throw new EnchereManagerException("getEnchere failed\n" + e);
		}
		return enchere;
	}

	public List<Enchere> getListeEnchere(Integer id_utilisateur) throws EnchereManagerException {
		List<Enchere> encheres = null;
		try {
			encheres = this.enchereDAO.selectUtilisateur(id_utilisateur);
		} catch (DALException e) {
			throw new EnchereManagerException("getListeEnchere failed\n" + e);
		}
		return encheres;
	}

	public List<Enchere> getListeEnchereByArticle(Integer no_article) throws EnchereManagerException {
		List<Enchere> encheres = new ArrayList<>();
		try {
			encheres = this.enchereDAO.selectByArticle(no_article);
		} catch (DALException e) {
			throw new EnchereManagerException("getListeEnchereByArticle failed article " + no_article + "\n" + e);
		}
		return encheres;
	}

	public void addEnchere(Enchere nouvelleEnchere) throws EnchereManagerException {
		try {
			if(nouvelleEnchere.getMontant_enchere() >= nouvelleEnchere.getArticle().getPrix_initial() &&
					nouvelleEnchere.getUtilisateur().getCredit() >= nouvelleEnchere.getMontant_enchere()) {
				UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
				// decredite le nouveau encherisseur
				Utilisateur utilisateurNouvelleEnchere = nouvelleEnchere.getUtilisateur();
				utilisateurNouvelleEnchere.setCredit(utilisateurNouvelleEnchere.getCredit() - nouvelleEnchere.getMontant_enchere());
				utilisateurManager.updateUtilisateur(utilisateurNouvelleEnchere);
				this.enchereDAO.insert(nouvelleEnchere);
			}
		} catch (DALException e) {
			throw new EnchereManagerException("addEnchere failed\n" + e);
		} catch (UtilisateurManagerException e) {
			throw new EnchereManagerException("updateEncher failed - updateUtilisateur failed\n" + e);
		}
	}

	public void updateEncher(Enchere nouvelleEnchere) throws EnchereManagerException {
		try {
			// select encheractuelle
			Enchere encherePrecedente = this.enchereDAO.selectMeilleurByArticle(nouvelleEnchere.getArticle().getNo_article());
			// verifie si l'enchere proposé est strictement supérieur a l'actuelle
			if(nouvelleEnchere.getMontant_enchere() > encherePrecedente.getMontant_enchere() &&
					nouvelleEnchere.getUtilisateur().getCredit() >= nouvelleEnchere.getMontant_enchere()) {
				UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
				// recredite l'ancien encherisseur
				Utilisateur utilisateurPrecedenteEnchere = encherePrecedente.getUtilisateur();
				utilisateurPrecedenteEnchere.setCredit(utilisateurPrecedenteEnchere.getCredit() + encherePrecedente.getMontant_enchere());
				utilisateurManager.updateUtilisateur(utilisateurPrecedenteEnchere);

				// decredite le nouveau encherisseur
				Utilisateur utilisateurNouvelleEnchere = nouvelleEnchere.getUtilisateur();
				utilisateurNouvelleEnchere.setCredit(utilisateurNouvelleEnchere.getCredit() - nouvelleEnchere.getMontant_enchere());
				utilisateurManager.updateUtilisateur(utilisateurNouvelleEnchere);

				// update => insert d'une nouvelle enchere qui devient l'actuel
				this.enchereDAO.insert(nouvelleEnchere);
			}
		} catch (DALException e) {
			throw new EnchereManagerException("updateEncher failed\n" + e);
		} catch (UtilisateurManagerException e) {
			throw new EnchereManagerException("updateEncher failed - updateUtilisateur failed\n" + e);
		}
	}

	public void suppressionDesEncheres(List<Enchere> enchereASupprimers) throws EnchereManagerException {
		UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
		// recredite l'ancien encherisseur
		Enchere meilleureEnchere = this.getMeilleurEnchereByArticle(enchereASupprimers.get(0).getArticle().getNo_article());
		Utilisateur utilisateurARecredite = meilleureEnchere.getUtilisateur();
		utilisateurARecredite.setCredit(utilisateurARecredite.getCredit() + meilleureEnchere.getMontant_enchere());
		try {
			utilisateurManager.updateUtilisateur(utilisateurARecredite);
		} catch (UtilisateurManagerException e) {
			throw new EnchereManagerException("SuppressionDesEncheres failed - " + e);
		}

		for(Enchere enchere : enchereASupprimers) {
			this.suppressionDeLaProposition(enchere);
		}
	}

	public void suppressionDeLaMeilleureEnchere(Enchere enchere) throws EnchereManagerException {
		// Suppression de la meilleure enchere
		this.suppressionDeLaProposition(enchere);

		List<Enchere> encheres = this.getListeEnchereByArticle(enchere.getArticle().getNo_article());
		encheres.sort((enchere1, enchere2) -> (enchere2.getMontant_enchere() - enchere1.getMontant_enchere()));

		//TODO : ameliorer la suppression en une seul requete
		for(Enchere enchereTmp : encheres) {
			try {
				UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
				Utilisateur utilisateur = utilisateurManager.getUtilisateur(enchereTmp.getUtilisateur().getNo_utilisateur());
				Integer montantEnchere = enchereTmp.getMontant_enchere();
				if(montantEnchere <= utilisateur.getCredit()) {
					utilisateur.setCredit(utilisateur.getCredit() - montantEnchere);
					utilisateurManager.updateUtilisateur(utilisateur);
					return;
				} else {
					this.suppressionDeLaProposition(enchereTmp);
				}
			} catch (UtilisateurManagerException e) {
				throw new EnchereManagerException("suppressionDeLaMeilleureEnchere failed - " + e);
			}
		}
	}

	public void suppressionDeLaProposition(Enchere enchere) throws EnchereManagerException {
		try {
			this.enchereDAO.remove(enchere);
		} catch (DALException e) {
			throw new EnchereManagerException("suppressionDesEncheres failed - failed de la suppression de l'enchere : " + enchere + "\n" + e);
		}
	}
}
