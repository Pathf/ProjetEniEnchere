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
	
List<ArticleVendu> selectByCategorie(Integer no_categorie) throws DALException;
	
	List<ArticleVendu> selectByCategorieAndNom(Integer no_categorie, String nom) throws DALException;
	
	List<ArticleVendu> selectBydNom(String nom) throws DALException;
}
