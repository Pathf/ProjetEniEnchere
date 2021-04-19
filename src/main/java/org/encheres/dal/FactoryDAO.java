package org.encheres.dal;

import org.encheres.dal.dao.ArticleVenduDAO;
import org.encheres.dal.dao.CategorieDAO;
import org.encheres.dal.dao.EnchereDAO;
import org.encheres.dal.dao.RetraitDAO;
import org.encheres.dal.dao.UtilisateurDAO;
import org.encheres.dal.impl.ArticleVenduDAOImpl;
import org.encheres.dal.impl.CategorieDAOImpl;
import org.encheres.dal.impl.EnchereDAOImpl;
import org.encheres.dal.impl.RetraitDAOImpl;
import org.encheres.dal.impl.UtilisateurDAOImpl;

public class FactoryDAO {
	public static ArticleVenduDAO getArticleVendu() {
		return new ArticleVenduDAOImpl();
	}

	public static CategorieDAO getCategorie() {
		return new CategorieDAOImpl();
	}

	public static EnchereDAO getEnchere() {
		return new EnchereDAOImpl();
	}

	public static RetraitDAO getRetrait() {
		return new RetraitDAOImpl();
	}

	public static UtilisateurDAO getUtilisateur() {
		return new UtilisateurDAOImpl();
	}
}
