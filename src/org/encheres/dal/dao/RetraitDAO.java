package org.encheres.dal.dao;

import java.util.List;

import org.encheres.bo.Retrait;
import org.encheres.dal.DALException;

public interface RetraitDAO {
	Retrait selectById(Integer id) throws DALException;

	List<Retrait> selectAll() throws DALException;

	void insert(Retrait retrait) throws DALException;

	void update(Retrait retrait) throws DALException;
}
