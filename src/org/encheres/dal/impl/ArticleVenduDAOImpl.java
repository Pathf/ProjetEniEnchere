package org.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Categorie;
import org.encheres.bo.Retrait;
import org.encheres.bo.Utilisateur;
import org.encheres.dal.ConstantesSQL;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.dao.ArticleVenduDAO;

public class ArticleVenduDAOImpl implements ArticleVenduDAO {

	private static final String TABLE = "ARTICLES_VENDUS";
	private static final String[] IDS = new String[]{"no_article"};
	private static final String[] CHAMPS = new String[]{"nom_article","description","date_debut_encheres","date_fin_encheres","prix_initial","prix_vente","no_utilisateur","no_categorie","no_retrait"};

	private static final String[] TABLES = new String[]{"ARTICLES_VENDUS", "UTILISATEURS no_utilisateur", "RETRAITS no_retrait","CATEGORIES no_categorie"};
	private static final String[] CHAMPALLTABLES = new String[] {
			"no_article nom_article description date_debut_encheres date_fin_encheres prix_initial prix_vente no_utilisateur no_categorie no_retrait",
			"pseudo nom prenom email telephone rue code_postal ville mot_de_passe credit administrateur",
			"rue code_postal ville",
			"libelle"
	};
	/* Select ALL :
	SELECT 	a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.no_retrait,
			u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur,
			r.rue, r.code_postal, r.code_postal,
			c.libelle
	FROM ARTICLES_VENDUS AS a	LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur
								LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait
								LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie;
	 */

	/* Select ID :
	 SELECT 	a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.no_retrait,
			u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur,
			r.rue, r.code_postal, r.code_postal,
			c.libelle
	FROM ARTICLES_VENDUS AS a	LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur
								LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait
								LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie
	WHERE a.no_article=?;
	 */
	private static final String SQLSELECT_ID = ConstantesSQL.requeteSelectLeftJoin(TABLES, CHAMPALLTABLES, IDS);
	private static final String SQLSELECT_UTILISATEUR = ConstantesSQL.requeteSelectLeftJoin(TABLES, CHAMPALLTABLES, new String[] {"no_utilisateur"});
	private static final String SQLSELECT_ALL = ConstantesSQL.requeteSelectLeftJoin(TABLES, CHAMPALLTABLES, null);
	private static final String SQLINSERT = ConstantesSQL.requeteInsert(TABLE, CHAMPS);
	private static final String SQLUPDATE = ConstantesSQL.requeteUpdate(TABLE, CHAMPS, IDS);

	@Override
	public ArticleVendu selectById(Integer id) throws DALException {
		ArticleVendu article = null;

		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_ID);
				) {
			preparedStatement.setInt(1, id);

			try (ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()){
					article = new ArticleVendu(
							id,
							rs.getString("nom_article").trim(),
							rs.getString("description"),
							rs.getDate("date_debut_encheres"),
							rs.getDate("date_fin_encheres"),
							rs.getInt("prix_initial"),
							rs.getInt("prix_vente"),
							new Utilisateur(
									rs.getInt("no_utilisateur"),
									rs.getString("pseudo"),
									rs.getString("nom"),
									rs.getString("prenom"),
									rs.getString("email"),
									rs.getString("telephone"),
									rs.getString("UTILISATEURS.rue"),
									rs.getString("UTILISATEURS.code_postal"),
									rs.getString("UTILISATEURS.ville"),
									rs.getString("mot_de_passe"),
									rs.getInt("credit"),
									rs.getBoolean("administrateur")
									),
							new Categorie(rs.getInt("no_categorie"), rs.getString("libelle")),
							new Retrait(
									rs.getInt("no_retrait"),
									rs.getString("RETRAITS.rue"),
									rs.getString("RETRAITS.code_postal"),
									rs.getString("RETRAITS.ville")
									)
							);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYID failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed - ", e);
		}

		return article;
	}

	// TODO: A optimiser
	@Override
	public List<ArticleVendu> selectAll() throws DALException {
		List<ArticleVendu> articles = new ArrayList<>();
		try (	Connection connection = DAOTools.getConnection();
				Statement statement = connection.createStatement();
				) {
			statement.execute(SQLSELECT_ALL);
			try (ResultSet rs = statement.getResultSet();){
				while(rs.next()){
					articles.add(new ArticleVendu(
							rs.getInt("no_article"),
							rs.getString("nom_article").trim(),
							rs.getString("description"),
							rs.getDate("date_debut_encheres"),
							rs.getDate("date_fin_encheres"),
							rs.getInt("prix_initial"),
							rs.getInt("prix_vente"),
							new Utilisateur(
									rs.getInt("no_utilisateur"),
									rs.getString("pseudo"),
									rs.getString("nom"),
									rs.getString("prenom"),
									rs.getString("email"),
									rs.getString("telephone"),
									rs.getString("UTILISATEURS.rue"),
									rs.getString("UTILISATEURS.code_postal"),
									rs.getString("UTILISATEURS.ville"),
									rs.getString("mot_de_passe"),
									rs.getInt("credit"),
									rs.getBoolean("administrateur")
									),
							new Categorie(rs.getInt("no_categorie"), rs.getString("libelle")),
							new Retrait(
									rs.getInt("no_retrait"),
									rs.getString("RETRAITS.rue"),
									rs.getString("RETRAITS.code_postal"),
									rs.getString("RETRAITS.ville")
									)
							));
				}
			}catch (SQLException e) {
				throw new DALException("Select ALL failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select All failed - ", e);
		}

		return articles;
	}

	@Override
	public List<ArticleVendu> selectByUtilisateur(Integer no_utilisateur) throws DALException {
		List<ArticleVendu> articles = new ArrayList<>();
		try (	Connection connection = DAOTools.getConnection();
				Statement statement = connection.createStatement();
				) {
			statement.execute(SQLSELECT_UTILISATEUR);
			try (ResultSet rs = statement.getResultSet();){
				while(rs.next()){
					articles.add(new ArticleVendu(
							rs.getInt("no_article"),
							rs.getString("nom_article").trim(),
							rs.getString("description"),
							rs.getDate("date_debut_encheres"),
							rs.getDate("date_fin_encheres"),
							rs.getInt("prix_initial"),
							rs.getInt("prix_vente"),
							new Utilisateur(
									rs.getInt("no_utilisateur"),
									rs.getString("pseudo"),
									rs.getString("nom"),
									rs.getString("prenom"),
									rs.getString("email"),
									rs.getString("telephone"),
									rs.getString("UTILISATEURS.rue"),
									rs.getString("UTILISATEURS.code_postal"),
									rs.getString("UTILISATEURS.ville"),
									rs.getString("mot_de_passe"),
									rs.getInt("credit"),
									rs.getBoolean("administrateur")
									),
							new Categorie(rs.getInt("no_categorie"), rs.getString("libelle")),
							new Retrait(
									rs.getInt("no_retrait"),
									rs.getString("RETRAITS.rue"),
									rs.getString("RETRAITS.code_postal"),
									rs.getString("RETRAITS.ville")
									)
							));
				}
			}catch (SQLException e) {
				throw new DALException("Select UTILISATEUR failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select UTILISATEUR failed - ", e);
		}

		return articles;
	}

	@Override
	public void insert(ArticleVendu articleVendu) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
				){
			preparedStatement.setString(1, articleVendu.getNom_article());
			preparedStatement.setString(2, articleVendu.getDescription());
			preparedStatement.setDate(3, articleVendu.getDate_debut_encheres());
			preparedStatement.setDate(4, articleVendu.getDate_fin_encheres());
			preparedStatement.setInt(5, articleVendu.getPrix_initial());
			preparedStatement.setInt(6, articleVendu.getPrix_vente());
			preparedStatement.setInt(7, articleVendu.getUtilisateur().getNo_utilisateur());
			preparedStatement.setInt(8, articleVendu.getCategorie().getNo_categorie());
			preparedStatement.setInt(9, articleVendu.getRetrait().getNo_retrait());

			preparedStatement.executeUpdate();

			try(ResultSet rs = preparedStatement.getGeneratedKeys()){
				if(rs.next()) {
					articleVendu.setNo_article(rs.getInt(1));
				}
			} catch (SQLException e) {
				throw new DALException("Insert articleVendu return key failed - " + articleVendu + " - ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Insert articleVendu failed - " + articleVendu + " - ", e);
		}
	}

	@Override
	public void update(ArticleVendu articleVendu) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLUPDATE);
				){
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
			throw new DALException("Update articleVendu failed - " + articleVendu + " - ", e);
		}
	}
}
