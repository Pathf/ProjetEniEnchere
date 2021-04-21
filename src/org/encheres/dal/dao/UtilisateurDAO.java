package org.encheres.dal.dao;

import java.util.List;

import org.encheres.bo.Utilisateur;
import org.encheres.dal.DALException;

public interface UtilisateurDAO {
	Utilisateur selectById(Integer id) throws DALException;

	List<Utilisateur> selectAll() throws DALException;

	void insert(Utilisateur utilisateur) throws DALException;

	void update(Utilisateur utilisateur) throws DALException;

	void remove(Utilisateur utilisateur) throws DALException;

	Utilisateur selectByEmailEtMdp(String identifiant, String mdp) throws DALException;

	Utilisateur selectByPseudoEtMdp(String identifiant, String mdp) throws DALException;
}
