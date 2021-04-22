package org.encheres.bll;

import java.util.List;

import org.encheres.bo.Categorie;
import org.encheres.dal.DALException;
import org.encheres.dal.FactoryDAO;
import org.encheres.dal.dao.CategorieDAO;

public class CategoriesManager {
	private static CategoriesManager categorieManager;
	private CategorieDAO categorieDAO;

	private CategoriesManager() {
		this.categorieDAO = FactoryDAO.getCategorie();
	}

	public static CategoriesManager getInstance() {
		if(categorieManager == null) {
			categorieManager = new CategoriesManager();
		}
		return categorieManager;
	}

	public Categorie getCategorie(Integer id) throws CategorieManagerException {
		Categorie categorie = null;
		try {
			categorie = this.categorieDAO.selectById(id);
		} catch (DALException e) {
			throw new CategorieManagerException("getCategorie failed - \n" + e);
		}
		return categorie;
	}
	
	public List<Categorie> getListeCategorie() throws CategorieManagerException {
		List<Categorie> categories = null;
		try {
			categories = this.categorieDAO.selectAll();
		} catch (DALException e) {
			throw new CategorieManagerException("getListeCategorie failed - ", e);
		}
		return categories;
	}
}
