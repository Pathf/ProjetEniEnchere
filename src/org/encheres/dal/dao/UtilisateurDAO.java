package org.encheres.dal.dao;

import java.util.List;

import org.encheres.bo.Utilisateur;
import org.encheres.dal.DALException;

public interface UtilisateurDAO {
	Utilisateur selectById(Integer id) throws DALException;

	List<Utilisateur> selectAll() throws DALException;

	void insert(Utilisateur object) throws DALException;

	void update(Utilisateur object) throws DALException;

	void remove(Utilisateur article) throws DALException;
}
