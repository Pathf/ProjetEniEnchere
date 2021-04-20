package org.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.encheres.bo.Enchere;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.dao.EnchereDAO;

public class EnchereDAOImpl implements EnchereDAO {

	private static String SQLSELECT_ID = "Select * from ENCHERES WHERE no_enchere=?";
	private static String SQLSELECT_UTILISATEUR = "Select * from ENCHERES WHERE no_utilisateur=?";
	private static String SQLINSERT = "INSERT INTO ENCHERES (date_enchere, montant_enchere, no_article, no_utilisateur) VALUES (?, ?, ?, ?)";
	private static String SQLUPDATE = "UPDATE ENCHERES SET date_enchere=?, montant_enchere=?, no_article=?, no_utilisateur=?";

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
		List<Enchere> encheres = new ArrayList<>();
		try (	Connection connection = DAOTools.getConnection();
				Statement statement = connection.createStatement();
				) {
			statement.execute(SQLSELECT_UTILISATEUR);

			try (ResultSet rs = statement.getResultSet();){
				if(rs.next()){
					encheres.add(new Enchere(
							rs.getInt("no_enchere"),
							rs.getDate("date_enchere"),
							rs.getInt("montant_enchere"),
							rs.getInt("no_article"),
							rs.getInt("no_utilisateur")
							));
				}
			}catch (SQLException e) {
				throw new DALException("Select utilisateur failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select utilisateur failed - ", e);
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
			preparedStatement.setInt(3, enchere.getNo_article());
			preparedStatement.setInt(4, enchere.getNo_utilisateur());

			preparedStatement.executeUpdate();

			try(ResultSet rs = preparedStatement.getGeneratedKeys()){
				enchere.setNo_article(rs.getInt(1));
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
			preparedStatement.setInt(3, enchere.getNo_article());
			preparedStatement.setInt(4, enchere.getNo_utilisateur());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Update enchere failed - " + enchere + " - ", e);
		}
	}
}
