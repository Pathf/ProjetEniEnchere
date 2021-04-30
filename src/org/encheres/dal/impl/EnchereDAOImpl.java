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
import org.encheres.bo.Enchere;
import org.encheres.bo.Retrait;
import org.encheres.bo.Utilisateur;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.dao.EnchereDAO;
import org.encheres.dal.sql.SQLRequete;

public class EnchereDAOImpl implements EnchereDAO {
	//TODO : GROSSE REQUETE POUR ALLER TOUT CHERCHER
	//"SELECT e.date_enchere, e.montant_enchere, a.no_article, u.no_utilisateur FROM ENCHERES as e INNER JOIN ARTICLES_VENDUS as a ON e.no_article = a.no_article INNER JOIN UTILISATEURS as u ON e.no_utilisateur = u.no_utilisateur WHERE e.no_enchere=?"
	private static final String SQLSELECT_ID = SQLRequete.select(null, BDD.ENCHERES_TABLENOM, BDD.ENCHERES_IDS);
	//private static final String SQLSELECT_UTILISATEUR = SQLRequete.select(null, TABLE, new String[]{"no_utilisateur"});
	private static final String SQLSELECT_UTILISATEUR = "SELECT * FROM ENCHERES as e INNER JOIN ARTICLES_VENDUS as a ON e.no_article = a.no_article INNER JOIN UTILISATEURS as u ON e.no_utilisateur = u.no_utilisateur WHERE e.no_utilisateur=?";
	private static final String SQLSELECT_ARTICLE = SQLRequete.select(null, BDD.ENCHERES_TABLENOM, BDD.ARTICLESVENDUS_IDS);
	private static final String SQLSELECT_MEILLEUR_ARTICLE = "SELECT e.no_enchere, e.date_enchere, MAX(e.montant_enchere) as montant_enchere, e.no_article, e.no_utilisateur, \r\n" +
			"u.no_utilisateur, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur\r\n" +
			"FROM ENCHERES as e\r\n" +
			"INNER JOIN UTILISATEURS as u ON e.no_utilisateur = u.no_utilisateur\r\n" +
			"WHERE e.no_article=? AND e.montant_enchere = (SELECT MAX(montant_enchere) FROM ENCHERES WHERE ENCHERES.no_article=?)\r\n" +
			"GROUP BY e.no_enchere, e.date_enchere, e.montant_enchere, e.no_article, e.no_utilisateur, \r\n" +
			"u.no_utilisateur , u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur";
	private static final String SQLINSERT = SQLRequete.insert(BDD.ENCHERES_TABLENOM, BDD.ENCHERES_CHAMPS);
	private static final String SQLUPDATE = SQLRequete.update(BDD.ENCHERES_TABLENOM, BDD.ENCHERES_CHAMPS, BDD.ENCHERES_IDS);
	private static final String SQLDELETE = SQLRequete.delete(BDD.ENCHERES_TABLENOM, BDD.ENCHERES_IDS);

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
				throw new DALException("Select BYID failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed\n" + e);
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
			throw new DALException("Select BYID failed - close failed for rs\n" + e);
		}

		return enchere;
	}

	@Override
	public Enchere selectMeilleurByArticle(Integer id_article) throws DALException {
		Enchere enchere = null;
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_MEILLEUR_ARTICLE);
				) {
			preparedStatement.setInt(1, id_article);
			preparedStatement.setInt(2, id_article);
			try (ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()){
					enchere = new Enchere(
							rs.getInt(BDD.ENCHERES_IDS[0]),
							rs.getDate(BDD.ENCHERES_CHAMPS[0]),
							rs.getInt(BDD.ENCHERES_CHAMPS[1]),
							new ArticleVendu(
									rs.getInt(BDD.ENCHERES_CHAMPS[2])
									),
							new Utilisateur(
									rs.getInt(BDD.ENCHERES_CHAMPS[3]),
									rs.getString(BDD.UTILISATEURS_CHAMPS[0]),
									rs.getString(BDD.UTILISATEURS_CHAMPS[1]),
									rs.getString(BDD.UTILISATEURS_CHAMPS[2]),
									rs.getString(BDD.UTILISATEURS_CHAMPS[3]),
									rs.getString(BDD.UTILISATEURS_CHAMPS[4]),
									rs.getString(BDD.UTILISATEURS_CHAMPS[5]),
									rs.getString(BDD.UTILISATEURS_CHAMPS[6]),
									rs.getString(BDD.UTILISATEURS_CHAMPS[7]),
									rs.getString(BDD.UTILISATEURS_CHAMPS[8]),
									rs.getInt(BDD.UTILISATEURS_CHAMPS[9]),
									rs.getBoolean(BDD.UTILISATEURS_CHAMPS[10])
									)
							);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYID_ARTICLE failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID_ARTICLE failed\n" + e);
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
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_UTILISATEUR);
				) {
			preparedStatement.setInt(1, id_utilisateur);
			try (ResultSet rs = preparedStatement.executeQuery();){
				while(rs.next()){
					encheres.add(new Enchere(
							rs.getInt(BDD.ENCHERES_IDS[0]),
							rs.getDate(BDD.ENCHERES_CHAMPS[0]),
							rs.getInt(BDD.ENCHERES_CHAMPS[1]),
							new ArticleVendu(
									rs.getInt(BDD.ENCHERES_CHAMPS[3]),
									rs.getString(BDD.ARTICLESVENDUS_CHAMPS[0]).trim(),
									rs.getString(BDD.ARTICLESVENDUS_CHAMPS[1]),
									rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[2]),
									rs.getDate(BDD.ARTICLESVENDUS_CHAMPS[3]),
									rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[4]),
									rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[5]),
									rs.getString(BDD.ARTICLESVENDUS_CHAMPS[6]),
									rs.getBytes(BDD.ARTICLESVENDUS_CHAMPS[7]),
									new Utilisateur(
											null,
											null,
											null,
											null,
											null,
											null,
											null,
											null,
											null,
											null,
											null,
											false
											),
									new Categorie(
											rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[9]),
											null
											),
									new Retrait(
											rs.getInt(BDD.ARTICLESVENDUS_CHAMPS[10]),
											null,
											null,
											null
											)
									),
							new Utilisateur(id_utilisateur)
							)
							);
					no_articles.add((rs.getInt("no_article") != 0) ? rs.getInt("no_article") : -1);
					no_utilisateurs.add((rs.getInt("no_utilisateur") != 0) ? rs.getInt("no_utilisateur") : -1);
				}
			}catch (SQLException e) {
				throw new DALException("Select utilisateur failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select utilisateur failed\n" + e);
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
			throw new DALException("Select BYID failed - close failed for rs\n" + e);
		}
		return encheres;
	}

	@Override
	public List<Enchere> selectByArticle(Integer no_article) throws DALException {
		List<Enchere> encheres = new ArrayList<>();
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_ARTICLE);
				) {
			preparedStatement.setInt(1, no_article);
			try (ResultSet rs = preparedStatement.executeQuery();){
				while(rs.next()){
					encheres.add(new Enchere(
							rs.getInt(BDD.ENCHERES_IDS[0]),
							rs.getDate(BDD.ENCHERES_CHAMPS[0]),
							rs.getInt(BDD.ENCHERES_CHAMPS[1]),
							new ArticleVendu(no_article),
							new Utilisateur(rs.getInt(BDD.ENCHERES_CHAMPS[3]))
							));
				}
			} catch (SQLException e) {
				throw new DALException("Select utilisateur failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select utilisateur failed\n" + e);
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
				throw new DALException("Insert enchere return key failed - " + enchere + "\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Insert enchere failed - " + enchere + "\n" + e);
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
			preparedStatement.setInt(5, enchere.getNo_enchere());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Update enchere failed - " + enchere + "\n" + e);
		}
	}

	@Override
	public void remove(Enchere enchere) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLDELETE);
				){
			preparedStatement.setInt(1, enchere.getNo_enchere());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Delete enchere failed - " + enchere + "\n" + e);
		}
	}
}
