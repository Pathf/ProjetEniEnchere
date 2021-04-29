package org.encheres.dal.dao;

import java.util.List;

import org.encheres.bo.Enchere;
import org.encheres.dal.DALException;

public interface EnchereDAO {
	Enchere selectById(Integer id) throws DALException;

	List<Enchere> selectUtilisateur(Integer id_utilisateur) throws DALException;

	void insert(Enchere enchere) throws DALException;

	void update(Enchere enchere) throws DALException;

	Enchere selectMeilleurByArticle(Integer id_article) throws DALException;

	void remove(Enchere enchere) throws DALException;

	List<Enchere> selectByArticle(Integer no_article) throws DALException;
}
