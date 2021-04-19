package org.encheres.dal.dao;

import java.util.List;

import org.encheres.bo.Retrait;
import org.encheres.dal.DALException;

public interface RetraitDAO {
	Retrait selectById(Integer id) throws DALException;

	List<Retrait> selectAll() throws DALException;

	void insert(Retrait object) throws DALException;

	void update(Retrait object) throws DALException;

	void remove(Retrait article) throws DALException;
}
