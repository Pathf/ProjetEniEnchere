package org.encheres.dal.dao;

import java.util.List;

import org.encheres.bo.ArticleVendu;
import org.encheres.dal.DALException;


public interface ArticleVenduDAO {
	ArticleVendu selectById(Integer id) throws DALException;

	List<ArticleVendu> selectAll() throws DALException;

	void insert(ArticleVendu object) throws DALException;

	void update(ArticleVendu object) throws DALException;

	List<ArticleVendu> selectByUtilisateur(Integer no_utilisateur) throws DALException;

	List<ArticleVendu> selectByFiltre(Integer no_categorie, String nom, Boolean date, Integer no_utilisateur, Boolean process, Boolean start, Boolean finish,Integer firstRow, Integer lastRow) throws DALException;

	List<ArticleVendu> listByWinBid(Integer no_utilisateur) throws DALException;
	
	Integer countSelectByFilter(Integer no_categorie, String nom, Boolean date, Integer no_utilisateur,
			Boolean process, Boolean start, Boolean finish)throws DALException;

	void remove(ArticleVendu articleVendu) throws DALException;

}
