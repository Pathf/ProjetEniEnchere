package org.encheres.dal.dao;

import java.util.List;

import org.encheres.bo.Categorie;
import org.encheres.dal.DALException;

public interface CategorieDAO {
	Categorie selectById(Integer id) throws DALException;

	List<Categorie> selectAll() throws DALException;

	void insert(Categorie object) throws DALException;

	void update(Categorie object) throws DALException;

	void remove(Categorie article) throws DALException;
}
