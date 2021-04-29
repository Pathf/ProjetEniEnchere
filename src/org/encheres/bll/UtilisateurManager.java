package org.encheres.bll;

import java.util.List;

import org.encheres.bo.Utilisateur;
import org.encheres.dal.DALException;
import org.encheres.dal.FactoryDAO;
import org.encheres.dal.dao.UtilisateurDAO;

public class UtilisateurManager {
	private static UtilisateurManager utilisateurManager;
	private UtilisateurDAO utilisateurDAO;

	private UtilisateurManager() {
		this.utilisateurDAO = FactoryDAO.getUtilisateur();
	}

	public static UtilisateurManager getInstance() {
		if(utilisateurManager == null) {
			utilisateurManager = new UtilisateurManager();
		}
		return utilisateurManager;
	}

	public Utilisateur getUtilisateur(Integer id) throws UtilisateurManagerException {
		Utilisateur utilisateur = null;
		try {
			utilisateur = this.utilisateurDAO.selectById(id);
		} catch (DALException e) {
			throw new UtilisateurManagerException("getUtilisateur failed\n" + e);
		}
		return utilisateur;
	}

	public Utilisateur getUtilisateur(String pseudo) throws UtilisateurManagerException {
		Utilisateur utilisateur = null;
		try {
			utilisateur = this.utilisateurDAO.selectByPseudo(pseudo);
		} catch (DALException e) {
			throw new UtilisateurManagerException("getUtilisateur failed\n" + e);
		}
		return utilisateur;
	}

	public Utilisateur getUtilisateurConnexion(String identifiant, String mdp) throws UtilisateurManagerException {
		Utilisateur utilisateur = null;
		try {
			if(identifiant.contains("@") && identifiant.contains(".")) {
				utilisateur = this.utilisateurDAO.selectByEmailEtMdp(identifiant, mdp);
			} else {
				if(!"Inconnu".equals(identifiant) && !"inconnu".equals(identifiant)) {
					utilisateur = this.utilisateurDAO.selectByPseudoEtMdp(identifiant, mdp);
				} else {
					throw new UtilisateurManagerException("getUtilisateurConnexion failed : pseudo \"Inconnu\" interdit");
				}
			}

			if(utilisateur == null) {
				throw new UtilisateurManagerException("getUtilisateurConnexion failed: pas d'utilisateur\n");
			}
		} catch (DALException e) {
			throw new UtilisateurManagerException("getUtilisateurConnexion failed\n" + e);
		}

		return utilisateur;
	}

	public List<Utilisateur> getListeUtilisateur() throws UtilisateurManagerException {
		List<Utilisateur> utilisateurs = null;

		try {
			utilisateurs = this.utilisateurDAO.selectAll();
		} catch (DALException e) {
			throw new UtilisateurManagerException("getListeUtilisateur failed\n" + e);
		}

		return utilisateurs;
	}

	public void addUtilisateur(Utilisateur utilisateur) throws UtilisateurManagerException {
		try {
			this.utilisateurDAO.insert(utilisateur);
		} catch (DALException e) {
			throw new UtilisateurManagerException("addUtilisateur failed\n" + e);
		}
	}

	public void updateUtilisateur(Utilisateur utilisateur) throws UtilisateurManagerException {
		try {
			this.utilisateurDAO.update(utilisateur);
		} catch (DALException e) {
			throw new UtilisateurManagerException("update failed\n" + e);
		}
	}

	public void delete(Utilisateur utilisateur) throws UtilisateurManagerException {
		try {
			this.utilisateurDAO.remove(utilisateur);
		} catch (DALException e) {
			throw new UtilisateurManagerException("Delete failed\n" + e);
		}
	}
}
