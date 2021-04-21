package org.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.encheres.bo.Enchere;
import org.encheres.dal.ConstantesSQL;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.dao.EnchereDAO;

public class EnchereDAOImpl implements EnchereDAO {

	private static final String TABLE = "ENCHERES";
	private static final String[] IDS = new String[]{"no_article"};
	private static final String[] CHAMPS = new String[]{"date_enchere","montant_enchere","no_article","no_utilisateur"};

	//TODO : GROSSE REQUETE POUR ALLER TOUT CHERCHER
	//"SELECT e.date_enchere, e.montant_enchere, a.no_article, u.no_utilisateur FROM ENCHERES as e INNER JOIN ARTICLES_VENDUS as a ON e.no_article = a.no_article INNER JOIN UTILISATEURS as u ON e.no_utilisateur = u.no_utilisateur WHERE e.no_enchere=?"
	private static final String SQLSELECT_ID = ConstantesSQL.requeteSelect(TABLE, null, IDS);
	//"SELECT * FROM ENCHERES as e INNER JOIN ARTICLES_VENDUS as a ON e.no_article = a.no_article INNER JOIN UTILISATEURS as u ON e.no_utilisateur = u.no_utilisateur WHERE no_utilisateur=?"
	private static final String SQLSELECT_UTILISATEUR = ConstantesSQL.requeteSelect(TABLE, null, new String[]{"no_utilisateur"});
	private static final String SQLINSERT = ConstantesSQL.requeteInsert(TABLE, CHAMPS);
	private static final String SQLUPDATE = ConstantesSQL.requeteUpdate(TABLE, CHAMPS, IDS);

	//TODO : Opti eventuelle
	@Override
	public Enchere selectById(Integer id) throws DALException {
		Enchere enchere = null;
		int no_article = -1;
		int no_utilisateur = -1;
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_ID);
				) {
			preparedStatement.setInt(1, id);

			try (ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()){
					enchere = new Enchere(
							id,
							rs.getDate("date_enchere"),
							rs.getInt("montant_enchere"),
							null,
							null
							);
					no_article = rs.getInt("no_article");
					no_utilisateur = rs.getInt("no_utilisateur");
				}
			}catch (SQLException e) {
				throw new DALException("Select BYID failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed - ", e);
		}

		try {
			if(no_article != -1 && no_utilisateur != -1) {
				ArticleVenduDAOImpl articleVenduDAOImpl = new ArticleVenduDAOImpl();
				enchere.setArticle(articleVenduDAOImpl.selectById(no_article));
				UtilisateurDAOImpl utilisateurDAOImpl = new UtilisateurDAOImpl();
				enchere.setUtilisateur(utilisateurDAOImpl.selectById(no_utilisateur));
			} else {
				throw new DALException("Select BYID failed - le no_article ou le no_utilisateur n'est pas référencé");
			}
		} catch (Exception e) {
			throw new DALException("Select BYID failed - close failed for rs -  ", e);
		}

		return enchere;
	}

	// TODO : a voir pour les -1 (si un plante on faite quoi) et pour l'opti
	@Override
	public List<Enchere> selectUtilisateur(Integer id_utilisateur) throws DALException {
		List<Enchere> encheres = new ArrayList<>();
		List<Integer> no_articles = new ArrayList<>();
		List<Integer> no_utilisateurs = new ArrayList<>();
		try (	Connection connection = DAOTools.getConnection();
				Statement statement = connection.createStatement();
				) {
			statement.execute(SQLSELECT_UTILISATEUR);

			try (ResultSet rs = statement.getResultSet();){
				while(rs.next()){
					encheres.add(new Enchere(
							rs.getInt("no_enchere"),
							rs.getDate("date_enchere"),
							rs.getInt("montant_enchere"),
							null,
							null
							));
					no_articles.add((rs.getInt("no_article") != 0) ? rs.getInt("no_article") : -1);
					no_utilisateurs.add((rs.getInt("no_utilisateur") != 0) ? rs.getInt("no_utilisateur") : -1);
				}
			}catch (SQLException e) {
				throw new DALException("Select utilisateur failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select utilisateur failed - ", e);
		}

		try {
			ArticleVenduDAOImpl articleVenduDAOImpl = new ArticleVenduDAOImpl();
			for(int i=0; i < no_articles.size(); i++) {
				if(no_articles.get(i) != -1) {
					encheres.get(i).setArticle(articleVenduDAOImpl.selectById(no_articles.get(i)));
				} else {
					throw new DALException("Select BYID failed - le no_article n'est pas référencé");
				}
			}
			UtilisateurDAOImpl utilisateurDAOImpl = new UtilisateurDAOImpl();
			for(int i=0; i < no_utilisateurs.size(); i++) {
				if(no_utilisateurs.get(i) != -1) {
					encheres.get(i).setUtilisateur(utilisateurDAOImpl.selectById(no_utilisateurs.get(i)));
				} else {
					throw new DALException("Select BYID failed - le no_utilisateur n'est pas référencé");
				}
			}
		} catch (Exception e) {
			throw new DALException("Select BYID failed - close failed for rs -  ", e);
		}

		return encheres;
	}

	@Override
	public void insert(Enchere enchere) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
				){
			preparedStatement.setDate(1, enchere.getDate_enchere());
			preparedStatement.setInt(2, enchere.getMontant_enchere());
			preparedStatement.setInt(3, enchere.getArticle().getNo_article());
			preparedStatement.setInt(4, enchere.getUtilisateur().getNo_utilisateur());

			preparedStatement.executeUpdate();

			try(ResultSet rs = preparedStatement.getGeneratedKeys()){
				if(rs.next()) {
					enchere.setNo_enchere(rs.getInt(1));
				}
			} catch (SQLException e) {
				throw new DALException("Insert enchere return key failed - " + enchere + " - ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Insert enchere failed - " + enchere + " - ", e);
		}
	}

	@Override
	public void update(Enchere enchere) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLUPDATE);
				){
			preparedStatement.setDate(1, enchere.getDate_enchere());
			preparedStatement.setInt(2, enchere.getMontant_enchere());
			preparedStatement.setInt(3, enchere.getArticle().getNo_article());
			preparedStatement.setInt(4, enchere.getUtilisateur().getNo_utilisateur());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Update enchere failed - " + enchere + " - ", e);
		}
	}
}
