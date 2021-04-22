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
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.SQLRequete;
import org.encheres.dal.dao.ArticleVenduDAO;

public class ArticleVenduDAOImpl implements ArticleVenduDAO {
	/* Select ALL :
	SELECT 	a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.no_retrait,
			u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur,
			r.rue as retraitRue, r.code_postal as code_postalRue, r.ville as retraitVille,
			c.libelle
	FROM ARTICLES_VENDUS AS a	LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur
								LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait
								LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie;
	 */

	/* Select ID :
	 SELECT 	a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.no_retrait,
			u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur,
			r.rue, r.code_postal, r.ville,
			c.libelle
	FROM ARTICLES_VENDUS AS a	LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur
								LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait
								LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie
	WHERE a.no_article=?;
	 */
	//SQLRequete.selectLeftJoin(TABLES, CHAMPALLTABLES, BDD.ARTICLESVENDUS_IDS);
	private static final String SQLSELECT_ID = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.no_retrait, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue as rueUTILISATEURS, u.code_postal as code_postalUTILISATEURS, u.ville as villeUTILISATEURS, u.mot_de_passe, u.credit, u.administrateur, r.rue as rueRETRAITS, r.code_postal as code_postalRETRAITS, r.ville as villeRETRAITS, c.libelle FROM ARTICLES_VENDUS AS a LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie WHERE a.no_article=?";
	//SQLRequete.selectLeftJoin(TABLES, CHAMPALLTABLES, BDD.UTILISATEURS_IDS);
	private static final String SQLSELECT_UTILISATEUR ="SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.no_retrait, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue as rueUTILISATEURS, u.code_postal as code_postalUTILISATEURS, u.ville as villeUTILISATEURS, u.mot_de_passe, u.credit, u.administrateur, r.rue as rueRETRAITS, r.code_postal as code_postalRETRAITS, r.ville as villeRETRAITS, c.libelle FROM ARTICLES_VENDUS AS a LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie WHERE a.no_utilisateur=?";
	//SQLRequete.selectLeftJoin(TABLES, CHAMPALLTABLES, null);
	private static final String SQLSELECT_ALL = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.no_retrait, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue as rueUTILISATEURS, u.code_postal as code_postalUTILISATEURS, u.ville as villeUTILISATEURS, u.mot_de_passe, u.credit, u.administrateur, r.rue as rueRETRAITS, r.code_postal as code_postalRETRAITS, r.ville as villeRETRAITS, c.libelle FROM ARTICLES_VENDUS AS a LEFT JOIN UTILISATEURS AS u ON a.no_utilisateur = u.no_utilisateur LEFT JOIN RETRAITS AS r ON a.no_retrait = r.no_retrait LEFT JOIN CATEGORIES AS c ON a.no_categorie = c.no_categorie";
	private static final String SQLINSERT = SQLRequete.insert(BDD.ARTICLESVENDUS_TABLENOM, BDD.ARTICLESVENDUS_CHAMPS);
	private static final String SQLUPDATE = SQLRequete.update(BDD.ARTICLESVENDUS_TABLENOM, BDD.ARTICLESVENDUS_CHAMPS, BDD.ARTICLESVENDUS_IDS);

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
							rs.getString(BDD.ARTICLESVENDUS_CHAMPS[0]).trim(),
							rs.getString(BDD.ARTICLESVENDUS_CHAMPS[1]),
							rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[2]),
							rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[3]),
							rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[4]),
							rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[5]),
							new Utilisateur(
									rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[6]),
									rs.getString("pseudo"),
									rs.getString("nom"),
									rs.getString("prenom"),
									rs.getString("email"),
									rs.getString("telephone"),
									rs.getString("rueUTILISATEURS"),
									rs.getString("code_postalUTILISATEURS"),
									rs.getString("villeUTILISATEURS"),
									rs.getString("mot_de_passe"),
									rs.getInt("credit"),
									rs.getBoolean("administrateur")
									),
							new Categorie(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[7]), rs.getString("libelle")),
							new Retrait(
									rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[8]),
									rs.getString("rueRETRAITS"),
									rs.getString("code_postalRETRAITS"),
									rs.getString("villeRETRAITS")
									)
							);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYID failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed\n" + e);
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
							rs.getInt(BDD.ARTICLESVENDUS_IDS[0]),
							rs.getString(BDD.ARTICLESVENDUS_CHAMPS[0]).trim(),
							rs.getString(BDD.ARTICLESVENDUS_CHAMPS[1]),
							rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[2]),
							rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[3]),
							rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[4]),
							rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[5]),
							new Utilisateur(
									rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[6]),
									rs.getString("pseudo"),
									rs.getString("nom"),
									rs.getString("prenom"),
									rs.getString("email"),
									rs.getString("telephone"),
									rs.getString("rueUTILISATEURS"),
									rs.getString("code_postalUTILISATEURS"),
									rs.getString("villeUTILISATEURS"),
									rs.getString("mot_de_passe"),
									rs.getInt("credit"),
									rs.getBoolean("administrateur")
									),
							new Categorie(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[7]), rs.getString("libelle")),
							new Retrait(
									rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[8]),
									rs.getString("rueRETRAITS"),
									rs.getString("code_postalRETRAITS"),
									rs.getString("villeRETRAITS")
									)
							));
				}
			}catch (SQLException e) {
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
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_UTILISATEUR);
				) {
			preparedStatement.setInt(1, no_utilisateur);
			try (ResultSet rs = preparedStatement.executeQuery();){
				while(rs.next()){
					articles.add(new ArticleVendu(
							rs.getInt(BDD.ARTICLESVENDUS_IDS[0]),
							rs.getString(BDD.ARTICLESVENDUS_CHAMPS[0]).trim(),
							rs.getString(BDD.ARTICLESVENDUS_CHAMPS[1]),
							rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[2]),
							rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[3]),
							rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[4]),
							rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[5]),
							new Utilisateur(
									rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[6]),
									rs.getString("pseudo"),
									rs.getString("nom"),
									rs.getString("prenom"),
									rs.getString("email"),
									rs.getString("telephone"),
									rs.getString("rueUTILISATEURS"),
									rs.getString("code_postalUTILISATEURS"),
									rs.getString("villeUTILISATEURS"),
									rs.getString("mot_de_passe"),
									rs.getInt("credit"),
									rs.getBoolean("administrateur")
									),
							new Categorie(rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[7]), rs.getString("libelle")),
							new Retrait(
									rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[8]),
									rs.getString("rueRETRAITS"),
									rs.getString("code_postalRETRAITS"),
									rs.getString("villeRETRAITS")
									)
							));
				}
			}catch (SQLException e) {
				throw new DALException("Select UTILISATEUR failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select UTILISATEUR failed\n" + e);
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
				throw new DALException("Insert articleVendu return key failed - " + articleVendu + "\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Insert articleVendu failed - " + articleVendu + "\n" + e);
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
			throw new DALException("Update articleVendu failed - " + articleVendu + "\n" + e);
		}
	}
}
