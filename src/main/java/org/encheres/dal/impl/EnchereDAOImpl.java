package org.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.encheres.bo.Enchere;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.dao.EnchereDAO;

public class EnchereDAOImpl implements EnchereDAO {

	private static String SQLSELECT_ID = "Select * from ENCHERES WHERE no_enchere=?";
	private static String SQLSELECT_UTILISATEUR = "Select * from ENCHERES WHERE no_utilisateur=?";
	//TODO
	private static String SQLINSERT = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, no_retrait) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static String SQLUPDATE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_encheres=?, date_fin_encheres=?, prix_initial=?, prix_vente=?, no_utilisateur=?, no_categorie=?, no_retrait=?";

	@Override
	public Enchere selectById(Integer id) throws DALException {
		Enchere enchere = null;
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
							rs.getInt("no_article"),
							rs.getInt("no_utilisateur")
							);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYID failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed - ", e);
		}
		return enchere;
	}

	@Override
	public List<Enchere> selectUtilisateur(Integer id_utilisateur) throws DALException {
		/*List<ArticleVendu> articles = new ArrayList<>();
		try (	Connection connection = DAOTools.getConnection();
				Statement statement = connection.createStatement();
				) {
			statement.execute(SQLSELECT_UTILISATEUR);

			try (ResultSet rs = statement.getResultSet();){
				if(rs.next()){
					articles.add(new ArticleVendu(
							rs.getInt("id"),
							rs.getString("nom_article").trim(),
							rs.getString("description"),
							rs.getDate("date_debut_encheres"),
							rs.getDate("date_fin_encheres"),
							rs.getInt("prix_initial"),
							rs.getInt("prix_vente"),
							rs.getInt("no_utilisateur"),
							rs.getInt("no_categorie"),
							rs.getInt("no_retrait")
							));
				}
			}catch (SQLException e) {
				throw new DALException("Select ALL failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select All failed - ", e);
		}*/
		return null;
	}

	@Override
	public void insert(Enchere articleVendu) throws DALException {
		/*try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
				){
			preparedStatement.setString(1, articleVendu.getNom_article());
			preparedStatement.setString(2, articleVendu.getDescription());
			preparedStatement.setDate(3, articleVendu.getDate_debut_encheres());
			preparedStatement.setDate(4, articleVendu.getDate_fin_encheres());
			preparedStatement.setInt(5, articleVendu.getPrix_initial());
			preparedStatement.setInt(6, articleVendu.getPrix_vente());
			preparedStatement.setInt(7, articleVendu.getNo_utilisateur());
			preparedStatement.setInt(8, articleVendu.getNo_categorie());
			preparedStatement.setInt(9, articleVendu.getNo_retrait());

			preparedStatement.executeUpdate();

			try(ResultSet rs = preparedStatement.getGeneratedKeys()){
				articleVendu.setNo_article(rs.getInt(1));
			} catch (SQLException e) {
				throw new DALException("Insert articleVendu return key failed - " + articleVendu + " - ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Insert articleVendu failed - " + articleVendu + " - ", e);
		}*/
	}

	@Override
	public void update(Enchere articleVendu) throws DALException {
		/*try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLUPDATE);
				){
			preparedStatement.setString(1, articleVendu.getNom_article());
			preparedStatement.setString(2, articleVendu.getDescription());
			preparedStatement.setDate(3, articleVendu.getDate_debut_encheres());
			preparedStatement.setDate(4, articleVendu.getDate_fin_encheres());
			preparedStatement.setInt(5, articleVendu.getPrix_initial());
			preparedStatement.setInt(6, articleVendu.getPrix_vente());
			preparedStatement.setInt(7, articleVendu.getNo_utilisateur());
			preparedStatement.setInt(8, articleVendu.getNo_categorie());
			preparedStatement.setInt(9, articleVendu.getNo_retrait());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Update articleVendu failed - " + articleVendu + " - ", e);
		}*/
	}
}
