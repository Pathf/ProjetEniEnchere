package org.encheres.bll;

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

	public void addEnchere(Enchere enchere) throws EnchereManagerException {
		try {
			this.enchereDAO.insert(enchere);
		} catch (DALException e) {
			throw new EnchereManagerException("addEnchere failed\n" + e);
		}
	}

	public void updateEncher(Enchere nouvelleEnchere) throws EnchereManagerException {
		try {
			// select encheractuelle
			Enchere encherePrecedente = this.enchereDAO.selectById(nouvelleEnchere.getNo_enchere());
			// verifie si l'enchere proposé est strictement supérieur a l'actuelle
			if(nouvelleEnchere.getMontant_enchere() > encherePrecedente.getMontant_enchere() &&
					nouvelleEnchere.getUtilisateur().getCredit() >= nouvelleEnchere.getMontant_enchere()) {
				// recredite l'ancien encherisseur
				Utilisateur utilisateurPrecedenteEnchere = encherePrecedente.getUtilisateur();
				utilisateurPrecedenteEnchere.setCredit(utilisateurPrecedenteEnchere.getCredit() + encherePrecedente.getMontant_enchere());
				UtilisateurManager.getInstance().updateUtilisateur(utilisateurPrecedenteEnchere);

				// decredite le nouveau encherisseur
				Utilisateur utilisateurNouvelleEnchere = nouvelleEnchere.getUtilisateur();
				utilisateurNouvelleEnchere.setCredit(utilisateurNouvelleEnchere.getCredit() - nouvelleEnchere.getMontant_enchere());
				UtilisateurManager.getInstance().updateUtilisateur(utilisateurNouvelleEnchere);

				// update => insert d'une nouvelle enchere qui devient l'actuel
				this.enchereDAO.insert(nouvelleEnchere);
			}
		} catch (DALException e) {
			throw new EnchereManagerException("updateEncher failed\n" + e);
		} catch (UtilisateurManagerException e) {
			throw new EnchereManagerException("updateEncher failed - updateUtilisateur failed\n" + e);
		}
	}
}
