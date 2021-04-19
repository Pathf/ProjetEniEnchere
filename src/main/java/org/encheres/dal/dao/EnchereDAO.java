package org.encheres.dal.dao;

import java.util.List;

import org.encheres.bo.Enchere;
import org.encheres.dal.DALException;

public interface EnchereDAO {
	Enchere selectById(Integer id) throws DALException;

	List<Enchere> selectAll() throws DALException;

	void insert(Enchere object) throws DALException;

	void update(Enchere object) throws DALException;

	void remove(Enchere article) throws DALException;
}
