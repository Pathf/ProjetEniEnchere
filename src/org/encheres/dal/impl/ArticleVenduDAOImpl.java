package org.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.encheres.bo.ArticleVendu;
import org.encheres.dal.ConstantesSQL;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.dao.ArticleVenduDAO;

public class ArticleVenduDAOImpl implements ArticleVenduDAO {

	private static final String TABLE = "ARTICLES_VENDUS";
	private static final String[] IDS = new String[]{"no_article"};
	private static final String[] CHAMPS = new String[]{"nom_article","description","date_debut_encheres","date_fin_encheres","prix_initial","prix_vente","no_utilisateur","no_categorie","no_retrait"};

	private static final String SQLSELECT_ID = ConstantesSQL.requeteSelect(TABLE, null, IDS);
	private static final String SQLSELECT_ALL = ConstantesSQL.requeteSelect(TABLE);
	private static final String SQLINSERT = ConstantesSQL.requeteInsert(TABLE, CHAMPS);
	private static final String SQLUPDATE = ConstantesSQL.requeteUpdate(TABLE, CHAMPS);
	// TODO : A voir
	@Override
	public ArticleVendu selectById(Integer id) throws DALException {
		ArticleVendu article = null;
		int no_utilisateur = -1;
		int no_categorie = -1;
		int no_retrait = -1;

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
							null,
							null,
							null
							);
					no_utilisateur = rs.getInt("no_utilisateur");
					no_categorie = rs.getInt("no_categorie");
					no_retrait = rs.getInt("no_retrait");
				}
			}catch (SQLException e) {
				throw new DALException("Select BYID failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed - ", e);
		}
		try {
			if(no_utilisateur != -1 && no_categorie != -1 && no_retrait != -1) {
				UtilisateurDAOImpl utilisateurDAOImpl = new UtilisateurDAOImpl();
				article.setUtilisateur(utilisateurDAOImpl.selectById(no_utilisateur));
				CategorieDAOImpl categorieDAOImpl = new CategorieDAOImpl();
				article.setCategorie(categorieDAOImpl.selectById(no_categorie));
				RetraitDAOImpl retraitDAOImpl = new RetraitDAOImpl();
				article.setRetrait(retraitDAOImpl.selectById(no_retrait));
			} else {
				throw new DALException("Select BYID failed - le no_utilisateur et/ou le no_categorie et/ou le no_retrait n'est/ne sont pas référencé");
			}
		} catch (Exception e) {
			throw new DALException("Select BYID failed - close failed for rs -  ", e);
		}

		return article;
	}

	// TODO: A optimiser
	@Override
	public List<ArticleVendu> selectAll() throws DALException {
		List<ArticleVendu> articles = new ArrayList<>();
		List<Integer> no_utilisateurs = new ArrayList<>();
		List<Integer> no_categories = new ArrayList<>();
		List<Integer> no_retraits = new ArrayList<>();
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
							null,
							null,
							null
							));
					no_utilisateurs.add((rs.getInt("no_utilisateur") != 0) ? rs.getInt("no_utilisateur") : -1);
					no_categories.add((rs.getInt("no_categorie") != 0) ? rs.getInt("no_categorie") : -1);
					no_retraits.add((rs.getInt("no_retrait") != 0) ? rs.getInt("no_retrait") : -1);
				}
			}catch (SQLException e) {
				throw new DALException("Select ALL failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select All failed - ", e);
		}

		try {
			UtilisateurDAOImpl utilisateurDAOImpl = new UtilisateurDAOImpl();
			for(int i=0; i < no_utilisateurs.size(); i++) {
				if(no_utilisateurs.get(i) != -1) {
					articles.get(i).setUtilisateur(utilisateurDAOImpl.selectById(no_utilisateurs.get(i)));
				} else {
					throw new DALException("Select BYID failed - le no_utilisateur n'est pas référencé");
				}
			}
			CategorieDAOImpl categorieDAOImpl = new CategorieDAOImpl();
			for(int i=0; i < no_categories.size(); i++) {
				if(no_categories.get(i) != -1) {
					articles.get(i).setCategorie(categorieDAOImpl.selectById(no_categories.get(i)));
				} else {
					throw new DALException("Select BYID failed - le no_categorie n'est pas référencé");
				}
			}
			RetraitDAOImpl retraitDAOImpl = new RetraitDAOImpl();
			for(int i=0; i < no_retraits.size(); i++) {
				if(no_retraits.get(i) != -1) {
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
				articleVendu.setNo_article(rs.getInt(1));
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

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Update articleVendu failed - " + articleVendu + " - ", e);
		}
	}
}
