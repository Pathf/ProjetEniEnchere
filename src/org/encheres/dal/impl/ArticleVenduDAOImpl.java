package org.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Categorie;
import org.encheres.bo.Retrait;
import org.encheres.bo.Utilisateur;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.dao.ArticleVenduDAO;
import org.encheres.dal.sql.SQLRequete;

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

	// SQLRequete.selectLeftJoin(TABLES, CHAMPALLTABLES, BDD.ARTICLESVENDUS_IDS);
	private static final String SQLSELECT_ID = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.photo_nom, a.photo_data, a.no_utilisateur, a.no_categorie, a.no_retrait, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue as rueUTILISATEURS, u.code_postal as code_postalUTILISATEURS, u.ville as villeUTILISATEURS, u.mot_de_passe, u.credit, u.administrateur, r.rue as rueRETRAITS, r.code_postal as code_postalRETRAITS, r.ville as villeRETRAITS, c.libelle FROM ARTICLES_VENDUS AS a LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie WHERE a.no_article=?";
	// SQLRequete.selectLeftJoin(TABLES, CHAMPALLTABLES, BDD.UTILISATEURS_IDS);
	private static final String SQLSELECT_UTILISATEUR = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.photo_nom, a.photo_data, a.no_utilisateur, a.no_categorie, a.no_retrait, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue as rueUTILISATEURS, u.code_postal as code_postalUTILISATEURS, u.ville as villeUTILISATEURS, u.mot_de_passe, u.credit, u.administrateur, r.rue as rueRETRAITS, r.code_postal as code_postalRETRAITS, r.ville as villeRETRAITS, c.libelle FROM ARTICLES_VENDUS AS a LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie WHERE a.no_utilisateur=?";
	// SQLRequete.selectLeftJoin(TABLES, CHAMPALLTABLES, null);
	private static final String SQLSELECT_ALL = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.photo_nom, a.photo_data, a.no_utilisateur, a.no_categorie, a.no_retrait, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue as rueUTILISATEURS, u.code_postal as code_postalUTILISATEURS, u.ville as villeUTILISATEURS, u.mot_de_passe, u.credit, u.administrateur, r.rue as rueRETRAITS, r.code_postal as code_postalRETRAITS, r.ville as villeRETRAITS, c.libelle FROM ARTICLES_VENDUS AS a LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie";
	private static final String SQLINSERT = SQLRequete.insert(BDD.ARTICLESVENDUS_TABLENOM, BDD.ARTICLESVENDUS_CHAMPS);
	private static final String SQLUPDATE = SQLRequete.update(BDD.ARTICLESVENDUS_TABLENOM, BDD.ARTICLESVENDUS_CHAMPS, BDD.ARTICLESVENDUS_IDS);
	private static final String SQLDELETE = SQLRequete.delete(BDD.ARTICLESVENDUS_TABLENOM, BDD.ARTICLESVENDUS_IDS);

	@Override
	public ArticleVendu selectById(Integer id) throws DALException {
		ArticleVendu article = null;
		try (Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_ID);) {
			preparedStatement.setInt(1, id);
			try (ResultSet rs = preparedStatement.executeQuery();) {
				if (rs.next()) {
					article = this.buildArticleVendu(rs, true, true, true);
				}
			} catch (SQLException e) {
				throw new DALException("Select BYID failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed\n" + e);
		}

		return article;
	}

	@Override
	public List<ArticleVendu> selectByFiltre(Integer no_categorie, String nom, Boolean date, Integer no_utilisateur,
			Boolean process, Boolean start, Boolean finish, Integer firstRow, Integer lastRow, Boolean mine) throws DALException {
		List<ArticleVendu> articles = new ArrayList<>();

		Integer position = 1;
		Integer positionNom = null;
		Integer positionCategorie = null;
		Integer positionNoUtilisateur = null;
		String nomByDefault = null;
		Integer positionFirstRow = null;
		Integer positionLastRow = null;
		Integer positionMine = null;
	
		String query = "WITH Results_CTE AS ( SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.photo_nom, a.photo_data, a.no_utilisateur, a.no_categorie, a.no_retrait, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue as rueUTILISATEURS, u.code_postal as code_postalUTILISATEURS, u.ville as villeUTILISATEURS, u.mot_de_passe, u.credit, u.administrateur, r.rue as rueRETRAITS, r.code_postal as code_postalRETRAITS, r.ville as villeRETRAITS, c.libelle , ROW_NUMBER() OVER (ORDER BY a.date_debut_encheres ) AS RowNum FROM ARTICLES_VENDUS AS a LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie WHERE ";

		if (nom == null) {
			query += " nom_article LIKE ?";
			nomByDefault = "%";
			positionNom = position;
			position++;
		}
		if (nom != null) {
			query += " nom_article LIKE ?";
			positionNom = position;
			position++;
		}
		if (no_categorie != null) {
			query += " AND a.no_categorie = ?";
			positionCategorie = position;
			position++;
		}
		if (date == true) {
			query += " AND date_debut_encheres <= getDate() AND date_fin_encheres > getdate()";
		}
		if (no_utilisateur != null) {
			query += " AND a.no_utilisateur = ?";
			positionNoUtilisateur = position;
			position++;
		}
		if (process) {
			query += " AND date_debut_encheres < getDate() AND date_fin_encheres > getdate() ";
		}
		if (start) {
			query += " AND date_debut_encheres >= getDate()";
		}
		if (finish) {
			query += " AND date_fin_encheres <= getDate()";
		}
		if(mine) {
			query += " AND no_article IN (select e.no_article from ENCHERES as e where e.no_utilisateur = ?)";
			positionMine = position;
			position++;
		}
		if (firstRow != null && lastRow != null) {
			query += " ) SELECT * FROM Results_CTE WHERE RowNum > ? AND RowNum <= ?";
			positionFirstRow = position;
			position++;
			positionLastRow = position;
			position++;
		}
System.out.println(query);
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
				preparedStatement.setInt(positionNoUtilisateur, no_utilisateur);
			}
			if (mine) {
				preparedStatement.setInt(positionMine, no_utilisateur);
			}
			if (firstRow != null) {
				preparedStatement.setInt(positionFirstRow, firstRow);
			}
			if (lastRow != null) {
				preparedStatement.setInt(positionLastRow, lastRow);
			}

			try (ResultSet rs = preparedStatement.executeQuery();) {
				while (rs.next()) {
					articles.add(this.buildArticleVendu(rs, true, true, true));
				}
			} catch (SQLException e) {
				throw new DALException("SelectByFiltre failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("SelectByFiltre All failed\n" + e);
		}

		return articles;
	}

	@Override
	public Integer countSelectByFilter(Integer no_categorie, String nom, Boolean date, Integer no_utilisateur,
			Boolean process, Boolean start, Boolean finish, Boolean mine) throws DALException {
		List<ArticleVendu> articles = new ArrayList<>();
		Integer number = null;
		Integer position = 1;
		Integer positionNom = null;
		Integer positionCategorie = null;
		Integer positionNoUtilisateur = null;
		String nomByDefault = null;
		Integer positionFirstRow = null;
		Integer positionLastRow = null;
		Integer positionMine = null;

		String query = "SELECT Count(*) FROM ARTICLES_VENDUS as a WHERE";
		
		if (nom == null) {
			query += " nom_article LIKE ?";
			nomByDefault = "%";
			positionNom = position;
			position++;
		}
		if (nom != null) {
			query += " nom_article LIKE ?";
			positionNom = position;
			position++;
		}
		if (no_categorie != null) {
			query += " AND a.no_categorie = ?";
			positionCategorie = position;
			position++;
		}
		if (date) {
			query += " AND date_debut_encheres <= getDate() AND date_fin_encheres > getdate()";
		}
		if (no_utilisateur != null) {
			query += " AND a.no_utilisateur = ?";
			positionNoUtilisateur = position;
			position++;
		}
		if(mine) {
			query += " AND no_article in (select no_article from ENCHERES where no_utilisateur = ?)";
			positionMine = position;
			position++;
		}
		if (process) {
			query += " AND date_debut_encheres < getDate() AND date_fin_encheres > getdate() ";
		}
		if (start) {
			query += " AND date_debut_encheres >= getDate()";
		}
		if (finish) {
			query += " AND date_fin_encheres <= getDate()";
		}
		
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
				preparedStatement.setInt(positionNoUtilisateur, no_utilisateur);
			}
			if (mine) {
				preparedStatement.setInt(positionMine, no_utilisateur);
			}
			

			try (ResultSet rs = preparedStatement.executeQuery();) {
				if (rs.next()) {
					number = rs.getInt(1);
				}
			} catch (SQLException e) {
				throw new DALException("CountSelect failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("CounSelectfailed\n" + e);
		}

		return number;
	}

	@Override
	public List<ArticleVendu> listByWinBid(Integer no_utilisateur) throws DALException {
		List<ArticleVendu> articles = new ArrayList<>();
		List<Integer> no_utilisateurs = new ArrayList<>();
		List<Integer> no_categories = new ArrayList<>();
		List<Integer> no_retraits = new ArrayList<>();

		String query = "SELECT e2.no_article,a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres,a.prix_initial,a.prix_vente, e2.no_utilisateur, a.no_categorie, a.no_retrait , a.photo_nom , a.photo_data FROM ENCHERES AS e2 LEFT JOIN ARTICLES_VENDUS AS a ON e2.no_article = a.no_article\r\n"
				+ "WHERE a.date_fin_encheres <= GETDATE()\r\n" + "AND e2.no_utilisateur = ?\r\n"
				+ "AND e2.no_enchere IN (SELECT no_enchere\r\n"
				+ "FROM ENCHERES AS e LEFT JOIN (SELECT no_article, max(montant_enchere)\r\n"
				+ "AS montant FROM ENCHERES GROUP BY no_article) AS tmp ON e.no_article = tmp.no_article\r\n"
				+ "WHERE tmp.montant = e.montant_enchere)";

		try (Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setInt(1, no_utilisateur);

			try (ResultSet rs = preparedStatement.executeQuery();) {
				while (rs.next()) {
					articles.add(this.buildArticleVendu(rs, false, false, false));
					no_utilisateurs.add((rs.getInt("no_utilisateur") != 0) ? rs.getInt("no_utilisateur") : -1);
					no_categories.add((rs.getInt("no_categorie") != 0) ? rs.getInt("no_categorie") : -1);
					no_retraits.add((rs.getInt("no_retrait") != 0) ? rs.getInt("no_retrait") : -1);
				}
			} catch (SQLException e) {
				throw new DALException("listByWinBid failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("listByWinBid All failed\n" + e);
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
			throw new DALException("listByWinBid failed - close failed for rs\n" + e);
		}

		return articles;
	}

	// TODO: A optimiser
	@Override
	public List<ArticleVendu> selectAll() throws DALException {
		List<ArticleVendu> articles = new ArrayList<>();
		try (Connection connection = DAOTools.getConnection();
				Statement statement = connection.createStatement();) {
			statement.execute(SQLSELECT_ALL);
			try (ResultSet rs = statement.getResultSet();) {
				while (rs.next()) {
					articles.add(this.buildArticleVendu(rs, true, true, true));
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
					articles.add(this.buildArticleVendu(rs, true, true, true));
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
			preparedStatement.setNull(6, Types.INTEGER);
			if(articleVendu.getPhotoNom() != null) {
				preparedStatement.setString(7, articleVendu.getPhotoNom());
				preparedStatement.setBytes(8, articleVendu.getPhotoData());
			} else {
				preparedStatement.setNull(7, Types.VARCHAR);
				preparedStatement.setNull(8, Types.BLOB);
			}
			preparedStatement.setInt(9, articleVendu.getUtilisateur().getNo_utilisateur());
			preparedStatement.setInt(10, articleVendu.getCategorie().getNo_categorie());
			preparedStatement.setInt(11, articleVendu.getRetrait().getNo_retrait());
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
			preparedStatement.setString(7, articleVendu.getPhotoNom());
			preparedStatement.setBytes(8, articleVendu.getPhotoData());
			preparedStatement.setInt(9, articleVendu.getUtilisateur().getNo_utilisateur());
			preparedStatement.setInt(10, articleVendu.getCategorie().getNo_categorie());
			preparedStatement.setInt(11, articleVendu.getRetrait().getNo_retrait());
			preparedStatement.setInt(12, articleVendu.getNo_article());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Update articleVendu failed - " + articleVendu + "\n" + e);
		}
	}

	@Override
	public void remove(ArticleVendu articleVendu) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLDELETE);
				){
			preparedStatement.setInt(1, articleVendu.getNo_article());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Remove article failed - " + articleVendu + "\n" + e);
		}
	}

	private ArticleVendu buildArticleVendu(ResultSet rs, boolean avecUtilisateur, boolean avecCategorie, boolean avecRetrait) throws SQLException {
		Utilisateur utilisateur = new Utilisateur();
		Categorie categorie = new Categorie();
		Retrait retrait = new Retrait();

		if(avecUtilisateur) {
			utilisateur = new Utilisateur(
					null,
					rs.getString(BDD.UTILISATEURS_CHAMPS[0]),
					rs.getString(BDD.UTILISATEURS_CHAMPS[1]),
					rs.getString(BDD.UTILISATEURS_CHAMPS[2]),
					rs.getString(BDD.UTILISATEURS_CHAMPS[3]),
					rs.getString(BDD.UTILISATEURS_CHAMPS[4]),
					rs.getString(BDD.UTILISATEURS_CHAMPS[5] + BDD.UTILISATEURS_TABLENOM),
					rs.getString(BDD.UTILISATEURS_CHAMPS[6] + BDD.UTILISATEURS_TABLENOM),
					rs.getString(BDD.UTILISATEURS_CHAMPS[7] + BDD.UTILISATEURS_TABLENOM),
					rs.getString(BDD.UTILISATEURS_CHAMPS[8]),
					rs.getInt(BDD.UTILISATEURS_CHAMPS[9]),
					rs.getBoolean(BDD.UTILISATEURS_CHAMPS[10]));
		}
		utilisateur.setNo_utilisateur(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[8]));

		if(avecCategorie) {
			categorie = new Categorie(
					null,
					rs.getString(BDD.CATEGORIES_CHAMPS[0]));
		}
		categorie.setNo_categorie(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[9]));

		if(avecRetrait) {
			retrait = new Retrait(
					null,
					rs.getString(BDD.RETRAIT_CHAMPS[0] + BDD.RETRAITS_TABLENOM),
					rs.getString(BDD.RETRAIT_CHAMPS[1] + BDD.RETRAITS_TABLENOM),
					rs.getString(BDD.RETRAIT_CHAMPS[2] + BDD.RETRAITS_TABLENOM)
					);
		}
		retrait.setNo_retrait(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[10]));

		return new ArticleVendu(
				rs.getInt(BDD.ARTICLESVENDUS_IDS[0]),
				rs.getString(BDD.ARTICLESVENDUS_CHAMPS[0]).trim(),
				rs.getString(BDD.ARTICLESVENDUS_CHAMPS[1]),
				rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[2]),
				rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[3]),
				rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[4]),
				rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[5]),
				rs.getString(BDD.ARTICLESVENDUS_CHAMPS[6]),
				rs.getBytes(BDD.ARTICLESVENDUS_CHAMPS[7]),
				utilisateur,
				categorie,
				retrait
				);
	}
}
