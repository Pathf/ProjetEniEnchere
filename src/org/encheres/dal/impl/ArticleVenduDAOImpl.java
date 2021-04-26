package org.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Categorie;
import org.encheres.bo.Retrait;
import org.encheres.bo.Utilisateur;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.SQLRequete;
import org.encheres.dal.dao.ArticleVenduDAO;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class ArticleVenduDAOImpl implements ArticleVenduDAO {
	/*
	 * Select ALL : SELECT a.no_article, a.nom_article, a.description,
	 * a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente,
	 * a.no_utilisateur, a.no_categorie, a.no_retrait, u.pseudo, u.nom, u.prenom,
	 * u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe,
	 * u.credit, u.administrateur, r.rue as retraitRue, r.code_postal as
	 * code_postalRue, r.ville as retraitVille, c.libelle FROM ARTICLES_VENDUS AS a
	 * LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur LEFT JOIN
	 * RETRAITS AS r ON a.no_retrait = r.no_retrait LEFT JOIN CATEGORIES AS c ON
	 * a.no_categorie = c.no_categorie;
	 */

	/*
	 * Select ID : SELECT a.no_article, a.nom_article, a.description,
	 * a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente,
	 * a.no_utilisateur, a.no_categorie, a.no_retrait, u.pseudo, u.nom, u.prenom,
	 * u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe,
	 * u.credit, u.administrateur, r.rue, r.code_postal, r.ville, c.libelle FROM
	 * ARTICLES_VENDUS AS a LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur =
	 * u.no_utilisateur LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait LEFT
	 * JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie WHERE a.no_article=?;
	 */

//	SELECT * FROM ARTICLES_VENDUS WHERE no_categorie IN (
//			SELECT no_categorie
//			FROM CATEGORIES
//			);

//	SELECT * FROM ARTICLES_VENDUS WHERE no_categorie IN (
//			SELECT no_categorie
//			FROM CATEGORIES
//			WHERE no_categorie = ?
//			);

	private static final String SQLSELECT_FILTRE = "SELECT * FROM ARTICLES_VENDUS WHERE  no_categorie IN (1,2,3,4) AND  nom_article LIKE ? AND date_debut_encheres >= ?  ";
	private static final String SQLSELECT_LIKE = "SELECT * FROM ARTICLES_VENDUS WHERE NOM_ARTICLE LIKE ? ";
	private static final String SQLSELECT_WHERE_LIKE = "SELECT * FROM ARTICLES_VENDUS WHERE NO_CATEGORIE = ? AND NOM_ARTICLE LIKE ? ";
	private static final String SQLSELECT_WHERE = SQLRequete.select(null, BDD.ARTICLESVENDUS_TABLENOM,
			BDD.CATEGORIES_IDS);
	// SQLRequete.selectLeftJoin(TABLES, CHAMPALLTABLES, BDD.ARTICLESVENDUS_IDS);
	private static final String SQLSELECT_ID = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.no_retrait, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue as rueUTILISATEURS, u.code_postal as code_postalUTILISATEURS, u.ville as villeUTILISATEURS, u.mot_de_passe, u.credit, u.administrateur, r.rue as rueRETRAITS, r.code_postal as code_postalRETRAITS, r.ville as villeRETRAITS, c.libelle FROM ARTICLES_VENDUS AS a LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie WHERE a.no_article=?";
	// SQLRequete.selectLeftJoin(TABLES, CHAMPALLTABLES, BDD.UTILISATEURS_IDS);
	private static final String SQLSELECT_UTILISATEUR = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.no_retrait, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue as rueUTILISATEURS, u.code_postal as code_postalUTILISATEURS, u.ville as villeUTILISATEURS, u.mot_de_passe, u.credit, u.administrateur, r.rue as rueRETRAITS, r.code_postal as code_postalRETRAITS, r.ville as villeRETRAITS, c.libelle FROM ARTICLES_VENDUS AS a LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie WHERE a.no_utilisateur=?";
	// SQLRequete.selectLeftJoin(TABLES, CHAMPALLTABLES, null);
	private static final String SQLSELECT_ALL = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.no_retrait, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue as rueUTILISATEURS, u.code_postal as code_postalUTILISATEURS, u.ville as villeUTILISATEURS, u.mot_de_passe, u.credit, u.administrateur, r.rue as rueRETRAITS, r.code_postal as code_postalRETRAITS, r.ville as villeRETRAITS, c.libelle FROM ARTICLES_VENDUS AS a LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie";
	private static final String SQLINSERT = SQLRequete.insert(BDD.ARTICLESVENDUS_TABLENOM, BDD.ARTICLESVENDUS_CHAMPS);
	private static final String SQLUPDATE = SQLRequete.update(BDD.ARTICLESVENDUS_TABLENOM, BDD.ARTICLESVENDUS_CHAMPS,
			BDD.ARTICLESVENDUS_IDS);

	@Override
	public ArticleVendu selectById(Integer id) throws DALException {
		ArticleVendu article = null;
		try (Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_ID);) {
			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery();) {
				if (rs.next()) {
					article = new ArticleVendu(id, rs.getString(BDD.ARTICLESVENDUS_CHAMPS[0]).trim(),
							rs.getString(BDD.ARTICLESVENDUS_CHAMPS[1]), rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[2]),
							rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[3]), rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[4]),
							rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[5]),
							new Utilisateur(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[6]), rs.getString("pseudo"),
									rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
									rs.getString("telephone"), rs.getString("rueUTILISATEURS"),
									rs.getString("code_postalUTILISATEURS"), rs.getString("villeUTILISATEURS"),
									rs.getString("mot_de_passe"), rs.getInt("credit"), rs.getBoolean("administrateur")),
							new Categorie(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[7]), rs.getString("libelle")),
							new Retrait(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[8]), rs.getString("rueRETRAITS"),
									rs.getString("code_postalRETRAITS"), rs.getString("villeRETRAITS")));
				}
			} catch (SQLException e) {
				throw new DALException("Select BYID failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed\n" + e);
		}

		return article;
	}

//	@Override
//	public List<ArticleVendu> selectByCategorie(Integer no_categorie) throws DALException {
//		List<ArticleVendu> articles = new ArrayList<>();
//		List<Integer> no_utilisateurs = new ArrayList<>();
//		List<Integer> no_categories = new ArrayList<>();
//		List<Integer> no_retraits = new ArrayList<>();
//
//		try (Connection connection = DAOTools.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_WHERE);) {
//			preparedStatement.setInt(1, no_categorie);
//
//			try (ResultSet rs = preparedStatement.executeQuery();) {
//
//				while (rs.next()) {
//					articles.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article").trim(),
//							rs.getString("description"), rs.getDate("date_debut_encheres"),
//							rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), null,
//							null, null));
//					no_utilisateurs.add((rs.getInt("no_utilisateur") != 0) ? rs.getInt("no_utilisateur") : -1);
//					no_categories.add((rs.getInt("no_categorie") != 0) ? rs.getInt("no_categorie") : -1);
//					no_retraits.add((rs.getInt("no_retrait") != 0) ? rs.getInt("no_retrait") : -1);
//				}
//			} catch (SQLException e) {
//				throw new DALException("SelectByCategorie failed - close failed for rs -  ", e);
//			}
//		} catch (SQLException e) {
//			throw new DALException("SelectByCategorie All failed - ", e);
//		}
//
//		try {
//			UtilisateurDAOImpl utilisateurDAOImpl = new UtilisateurDAOImpl();
//			for (int i = 0; i < no_utilisateurs.size(); i++) {
//				if (no_utilisateurs.get(i) != -1) {
//					articles.get(i).setUtilisateur(utilisateurDAOImpl.selectById(no_utilisateurs.get(i)));
//				} else {
//					throw new DALException("Select BYID failed - le no_utilisateur n'est pas référencé");
//				}
//			}
//			CategorieDAOImpl categorieDAOImpl = new CategorieDAOImpl();
//			for (int i = 0; i < no_categories.size(); i++) {
//				if (no_categories.get(i) != -1) {
//					articles.get(i).setCategorie(categorieDAOImpl.selectById(no_categories.get(i)));
//				} else {
//					throw new DALException("Select BYID failed - le no_categorie n'est pas référencé");
//				}
//			}
//			RetraitDAOImpl retraitDAOImpl = new RetraitDAOImpl();
//			for (int i = 0; i < no_retraits.size(); i++) {
//				if (no_retraits.get(i) != -1) {
//					articles.get(i).setRetrait(retraitDAOImpl.selectById(no_retraits.get(i)));
//				} else {
//					throw new DALException("Select BYID failed - le no_retrait n'est pas référencé");
//				}
//			}
//		} catch (Exception e) {
//			throw new DALException("Select BYID failed - close failed for rs -  ", e);
//		}
//
//		return articles;
//	}
//
//	@Override
//	public List<ArticleVendu> selectByCategorieAndNom(Integer no_categorie, String nom) throws DALException {
//		List<ArticleVendu> articles = new ArrayList<>();
//		List<Integer> no_utilisateurs = new ArrayList<>();
//		List<Integer> no_categories = new ArrayList<>();
//		List<Integer> no_retraits = new ArrayList<>();
//
//		try (Connection connection = DAOTools.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_WHERE_LIKE);) {
//			preparedStatement.setInt(1, no_categorie);
//			preparedStatement.setString(2, "%" + nom + "%");
//
//			try (ResultSet rs = preparedStatement.executeQuery();) {
//
//				while (rs.next()) {
//
//					articles.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article").trim(),
//							rs.getString("description"), rs.getDate("date_debut_encheres"),
//							rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), null,
//							null, null));
//					no_utilisateurs.add((rs.getInt("no_utilisateur") != 0) ? rs.getInt("no_utilisateur") : -1);
//					no_categories.add((rs.getInt("no_categorie") != 0) ? rs.getInt("no_categorie") : -1);
//					no_retraits.add((rs.getInt("no_retrait") != 0) ? rs.getInt("no_retrait") : -1);
//				}
//			} catch (SQLException e) {
//				throw new DALException("SelectByCategorie failed - close failed for rs -  ", e);
//			}
//		} catch (SQLException e) {
//			throw new DALException("SelectByCategorie All failed - ", e);
//		}
//
//		try {
//			UtilisateurDAOImpl utilisateurDAOImpl = new UtilisateurDAOImpl();
//			for (int i = 0; i < no_utilisateurs.size(); i++) {
//				if (no_utilisateurs.get(i) != -1) {
//					articles.get(i).setUtilisateur(utilisateurDAOImpl.selectById(no_utilisateurs.get(i)));
//				} else {
//					throw new DALException("Select BYID failed - le no_utilisateur n'est pas référencé");
//				}
//			}
//			CategorieDAOImpl categorieDAOImpl = new CategorieDAOImpl();
//			for (int i = 0; i < no_categories.size(); i++) {
//				if (no_categories.get(i) != -1) {
//					articles.get(i).setCategorie(categorieDAOImpl.selectById(no_categories.get(i)));
//				} else {
//					throw new DALException("Select BYID failed - le no_categorie n'est pas référencé");
//				}
//			}
//			RetraitDAOImpl retraitDAOImpl = new RetraitDAOImpl();
//			for (int i = 0; i < no_retraits.size(); i++) {
//				if (no_retraits.get(i) != -1) {
//					articles.get(i).setRetrait(retraitDAOImpl.selectById(no_retraits.get(i)));
//				} else {
//					throw new DALException("Select BYID failed - le no_retrait n'est pas référencé");
//				}
//			}
//		} catch (Exception e) {
//			throw new DALException("Select BYID failed - close failed for rs -  ", e);
//		}
//
//		return articles;
//	}

	@Override
	public List<ArticleVendu> selectByFiltre(Integer no_categorie, String nom, Boolean date, String no_utilisateur, Boolean process, Boolean start, Boolean finish)
			throws DALException {
		List<ArticleVendu> articles = new ArrayList<>();
		List<Integer> no_utilisateurs = new ArrayList<>();
		List<Integer> no_categories = new ArrayList<>();
		List<Integer> no_retraits = new ArrayList<>();

		Integer position = 1;
		Integer positionNom = null;
		Integer positionCategorie = null;
		Integer positionNoUtilisateur = null;
		String nomByDefault = null;

		String query = "SELECT * FROM ARTICLES_VENDUS WHERE ";

		if (nom == null) {
			query += " nom_article LIKE ?";
			nomByDefault = "%%";
			positionNom = position;
			position++;
		}
		if (nom != null) {
			query += " nom_article LIKE ?";
			positionNom = position;
			position++;
		}
		if (no_categorie != null) {
			query += " AND no_categorie = ?";
			positionCategorie = position;
			position++;
		}
		if (date == true) {
			query += " AND date_debut_encheres <= getDate() AND date_fin_encheres > getdate()";
		}
		// TODO a revoir lorsque l'on aura l'id en session
		if (no_utilisateur != null) {
//			query += " AND no_utilisateur = ?";// query += " AND no_utilisateur = (SELECTED no_utilisateur WHERE pseudo
			query += " AND no_utilisateur = (SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo = ?)";
			positionNoUtilisateur = position;
			position++;
		}
		if (process == true) {
			query += " AND date_debut_encheres < getDate() AND date_fin_encheres > getdate() ";
		}
		if (start == true) {
			query += " AND date_debut_encheres >= getDate()";
		}
		if (finish == true) {
			query += " AND date_fin_encheres <= getDate()";
		}
		System.out.println(query);
		System.out.println(no_utilisateur + " " + positionNoUtilisateur);

		try (Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			if (nom != null) {
				preparedStatement.setString(positionNom, "%" + nom + "%");
			} else {
				preparedStatement.setString(positionNom, nomByDefault);
			}
			if (no_categorie != null) {
				preparedStatement.setInt(positionCategorie, no_categorie);
			}
			if (no_utilisateur != null) {
				preparedStatement.setString(positionNoUtilisateur, no_utilisateur);
			}

			try (ResultSet rs = preparedStatement.executeQuery();) {

				while (rs.next()) {

					articles.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article").trim(),
							rs.getString("description"), rs.getDate("date_debut_encheres"),
							rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), null,
							null, null));
					no_utilisateurs.add((rs.getInt("no_utilisateur") != 0) ? rs.getInt("no_utilisateur") : -1);
					no_categories.add((rs.getInt("no_categorie") != 0) ? rs.getInt("no_categorie") : -1);
					no_retraits.add((rs.getInt("no_retrait") != 0) ? rs.getInt("no_retrait") : -1);
				}
			} catch (SQLException e) {
				throw new DALException("SelectByFiltre failed - close failed for rs \n  " + e);
			}
		} catch (SQLException e) {
			throw new DALException("SelectByFiltre All failed \n " + e);
		}

		try {
			UtilisateurDAOImpl utilisateurDAOImpl = new UtilisateurDAOImpl();
			for (int i = 0; i < no_utilisateurs.size(); i++) {
				if (no_utilisateurs.get(i) != -1) {
					articles.get(i).setUtilisateur(utilisateurDAOImpl.selectById(no_utilisateurs.get(i)));
				} else {
					throw new DALException("Select BYID failed - le no_utilisateur n'est pas référencé");
				}
			}
			CategorieDAOImpl categorieDAOImpl = new CategorieDAOImpl();
			for (int i = 0; i < no_categories.size(); i++) {
				if (no_categories.get(i) != -1) {
					articles.get(i).setCategorie(categorieDAOImpl.selectById(no_categories.get(i)));
				} else {
					throw new DALException("Select BYID failed - le no_categorie n'est pas référencé");
				}
			}
			RetraitDAOImpl retraitDAOImpl = new RetraitDAOImpl();
			for (int i = 0; i < no_retraits.size(); i++) {
				if (no_retraits.get(i) != -1) {
					articles.get(i).setRetrait(retraitDAOImpl.selectById(no_retraits.get(i)));
				} else {
					throw new DALException("Select BYID failed - le no_retrait n'est pas référencé");
				}
			}
		} catch (Exception e) {
			throw new DALException("Select BYID failed - close failed for rs -  ", e);
		}

		return articles;
	}

	public List<ArticleVendu> listByWinBid(String no_utilisateur) throws DALException {
		List<ArticleVendu> articles = new ArrayList<>();
		List<Integer> no_utilisateurs = new ArrayList<>();
		List<Integer> no_categories = new ArrayList<>();
		List<Integer> no_retraits = new ArrayList<>();

		String query = "SELECT e2.no_article,a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres,a.prix_initial,a.prix_vente, e2.no_utilisateur, a.no_categorie, a.no_retrait FROM ENCHERES AS e2 LEFT JOIN ARTICLES_VENDUS AS a ON e2.no_article = a.no_article\r\n"
				+ "WHERE a.date_fin_encheres <= GETDATE()\r\n"
				+ "and e2.no_utilisateur = (SELECT u.no_utilisateur FROM UTILISATEURS as u WHERE pseudo = ?)\r\n"
				+ "AND e2.no_enchere IN (SELECT no_enchere\r\n"
				+ "FROM ENCHERES AS e LEFT JOIN (SELECT no_article, max(montant_enchere)\r\n"
				+ "AS montant FROM ENCHERES GROUP BY no_article) AS tmp ON e.no_article = tmp.no_article\r\n"
				+ "WHERE tmp.montant = e.montant_enchere)";

		try (Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setString(1, no_utilisateur);

			try (ResultSet rs = preparedStatement.executeQuery();) {

				while (rs.next()) {
					articles.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article").trim(),
							rs.getString("description"), rs.getDate("date_debut_encheres"),
							rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), null,
							null, null));
					no_utilisateurs.add((rs.getInt("no_utilisateur") != 0) ? rs.getInt("no_utilisateur") : -1);
					no_categories.add((rs.getInt("no_categorie") != 0) ? rs.getInt("no_categorie") : -1);
					no_retraits.add((rs.getInt("no_retrait") != 0) ? rs.getInt("no_retrait") : -1);
				}
			} catch (SQLException e) {
				throw new DALException("listByWinBid failed - close failed for rs -  ", e);
			}
		} catch (

		SQLException e) {
			throw new DALException("listByWinBid All failed - ", e);
		}

		try {
			UtilisateurDAOImpl utilisateurDAOImpl = new UtilisateurDAOImpl();
			for (int i = 0; i < no_utilisateurs.size(); i++) {
				if (no_utilisateurs.get(i) != -1) {
					articles.get(i).setUtilisateur(utilisateurDAOImpl.selectById(no_utilisateurs.get(i)));
				} else {
					throw new DALException("listByWinBid failed - le no_utilisateur n'est pas référencé");
				}
			}
			CategorieDAOImpl categorieDAOImpl = new CategorieDAOImpl();
			for (int i = 0; i < no_categories.size(); i++) {
				if (no_categories.get(i) != -1) {
					articles.get(i).setCategorie(categorieDAOImpl.selectById(no_categories.get(i)));
				} else {
					throw new DALException("listByWinBid failed - le no_categorie n'est pas référencé");
				}
			}
			RetraitDAOImpl retraitDAOImpl = new RetraitDAOImpl();
			for (int i = 0; i < no_retraits.size(); i++) {
				if (no_retraits.get(i) != -1) {
					articles.get(i).setRetrait(retraitDAOImpl.selectById(no_retraits.get(i)));
				} else {
					throw new DALException("listByWinBid failed - le no_retrait n'est pas référencé");
				}
			}
		} catch (Exception e) {
			throw new DALException("listByWinBid failed - close failed for rs -  ", e);
		}

		return articles;
	}

	@Override
	public List<ArticleVendu> selectBydNom(String nom) throws DALException {
		List<ArticleVendu> articles = new ArrayList<>();
		List<Integer> no_utilisateurs = new ArrayList<>();
		List<Integer> no_categories = new ArrayList<>();
		List<Integer> no_retraits = new ArrayList<>();

		try (Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_LIKE);) {

			preparedStatement.setString(1, "%" + nom + "%");

			try (ResultSet rs = preparedStatement.executeQuery();) {

				while (rs.next()) {
					articles.add(new ArticleVendu(rs.getInt("no_article"), rs.getString("nom_article").trim(),
							rs.getString("description"), rs.getDate("date_debut_encheres"),
							rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), null,
							null, null));
					no_utilisateurs.add((rs.getInt("no_utilisateur") != 0) ? rs.getInt("no_utilisateur") : -1);
					no_categories.add((rs.getInt("no_categorie") != 0) ? rs.getInt("no_categorie") : -1);
					no_retraits.add((rs.getInt("no_retrait") != 0) ? rs.getInt("no_retrait") : -1);
				}
			} catch (SQLException e) {
				throw new DALException("SelectByCategorie failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("SelectByCategorie All failed - ", e);
		}

		try {
			UtilisateurDAOImpl utilisateurDAOImpl = new UtilisateurDAOImpl();
			for (int i = 0; i < no_utilisateurs.size(); i++) {
				if (no_utilisateurs.get(i) != -1) {
					articles.get(i).setUtilisateur(utilisateurDAOImpl.selectById(no_utilisateurs.get(i)));
				} else {
					throw new DALException("Select BYID failed - le no_utilisateur n'est pas référencé");
				}
			}
			CategorieDAOImpl categorieDAOImpl = new CategorieDAOImpl();
			for (int i = 0; i < no_categories.size(); i++) {
				if (no_categories.get(i) != -1) {
					articles.get(i).setCategorie(categorieDAOImpl.selectById(no_categories.get(i)));
				} else {
					throw new DALException("Select BYID failed - le no_categorie n'est pas référencé");
				}
			}
			RetraitDAOImpl retraitDAOImpl = new RetraitDAOImpl();
			for (int i = 0; i < no_retraits.size(); i++) {
				if (no_retraits.get(i) != -1) {
					articles.get(i).setRetrait(retraitDAOImpl.selectById(no_retraits.get(i)));
				} else {
					throw new DALException("Select BYID failed - le no_retrait n'est pas référencé");
				}
			}
		} catch (Exception e) {
			throw new DALException("Select BYID failed - close failed for rs -  ", e);
		}

		return articles;
	}

	// TODO: A optimiser
	@Override
	public List<ArticleVendu> selectAll() throws DALException {
		List<ArticleVendu> articles = new ArrayList<>();
		try (Connection connection = DAOTools.getConnection(); Statement statement = connection.createStatement();) {
			statement.execute(SQLSELECT_ALL);
			try (ResultSet rs = statement.getResultSet();) {
				while (rs.next()) {
					articles.add(new ArticleVendu(rs.getInt(BDD.ARTICLESVENDUS_IDS[0]),
							rs.getString(BDD.ARTICLESVENDUS_CHAMPS[0]).trim(),
							rs.getString(BDD.ARTICLESVENDUS_CHAMPS[1]), rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[2]),
							rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[3]), rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[4]),
							rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[5]),
							new Utilisateur(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[6]), rs.getString("pseudo"),
									rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
									rs.getString("telephone"), rs.getString("rueUTILISATEURS"),
									rs.getString("code_postalUTILISATEURS"), rs.getString("villeUTILISATEURS"),
									rs.getString("mot_de_passe"), rs.getInt("credit"), rs.getBoolean("administrateur")),
							new Categorie(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[7]), rs.getString("libelle")),
							new Retrait(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[8]), rs.getString("rueRETRAITS"),
									rs.getString("code_postalRETRAITS"), rs.getString("villeRETRAITS"))));
				}
			} catch (SQLException e) {
				throw new DALException("Select ALL failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select All failed\n" + e);
		}

		return articles;
	}

	@Override
	public List<ArticleVendu> selectByUtilisateur(Integer no_utilisateur) throws DALException {
		List<ArticleVendu> articles = new ArrayList<>();
		try (Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_UTILISATEUR);) {
			preparedStatement.setInt(1, no_utilisateur);
			try (ResultSet rs = preparedStatement.executeQuery();) {
				while (rs.next()) {
					articles.add(new ArticleVendu(rs.getInt(BDD.ARTICLESVENDUS_IDS[0]),
							rs.getString(BDD.ARTICLESVENDUS_CHAMPS[0]).trim(),
							rs.getString(BDD.ARTICLESVENDUS_CHAMPS[1]), rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[2]),
							rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[3]), rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[4]),
							rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[5]),
							new Utilisateur(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[6]), rs.getString("pseudo"),
									rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
									rs.getString("telephone"), rs.getString("rueUTILISATEURS"),
									rs.getString("code_postalUTILISATEURS"), rs.getString("villeUTILISATEURS"),
									rs.getString("mot_de_passe"), rs.getInt("credit"), rs.getBoolean("administrateur")),
							new Categorie(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[7]), rs.getString("libelle")),
							new Retrait(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[8]), rs.getString("rueRETRAITS"),
									rs.getString("code_postalRETRAITS"), rs.getString("villeRETRAITS"))));
				}
			} catch (SQLException e) {
				throw new DALException("Select UTILISATEUR failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select UTILISATEUR failed\n" + e);
		}

		return articles;
	}

	@Override
	public void insert(ArticleVendu articleVendu) throws DALException {
		try (Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLINSERT,
						Statement.RETURN_GENERATED_KEYS);) {
			preparedStatement.setString(1, articleVendu.getNom_article());
			preparedStatement.setString(2, articleVendu.getDescription());
			preparedStatement.setDate(3, articleVendu.getDate_debut_encheres());
			preparedStatement.setDate(4, articleVendu.getDate_fin_encheres());
			preparedStatement.setInt(5, articleVendu.getPrix_initial());
			preparedStatement.setNull(6, java.sql.Types.INTEGER);
			preparedStatement.setInt(7, articleVendu.getUtilisateur().getNo_utilisateur());
			preparedStatement.setInt(8, articleVendu.getCategorie().getNo_categorie());
			preparedStatement.setInt(9, articleVendu.getRetrait().getNo_retrait());

			preparedStatement.executeUpdate();

			try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
				if (rs.next()) {
					articleVendu.setNo_article(rs.getInt(1));
				}
			} catch (SQLException e) {
				throw new DALException("Insert articleVendu return key failed - " + articleVendu + "\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Insert articleVendu failed - " + articleVendu + "\n" + e);
		}
	}

	@Override
	public void update(ArticleVendu articleVendu) throws DALException {
		try (Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLUPDATE);) {
			preparedStatement.setString(1, articleVendu.getNom_article());
			preparedStatement.setString(2, articleVendu.getDescription());
			preparedStatement.setDate(3, articleVendu.getDate_debut_encheres());
			preparedStatement.setDate(4, articleVendu.getDate_fin_encheres());
			preparedStatement.setInt(5, articleVendu.getPrix_initial());
			preparedStatement.setInt(6, articleVendu.getPrix_vente());
			preparedStatement.setInt(7, articleVendu.getUtilisateur().getNo_utilisateur());
			preparedStatement.setInt(8, articleVendu.getCategorie().getNo_categorie());
			preparedStatement.setInt(9, articleVendu.getRetrait().getNo_retrait());
			preparedStatement.setInt(10, articleVendu.getNo_article());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Update articleVendu failed - " + articleVendu + "\n" + e);
		}
	}

}
