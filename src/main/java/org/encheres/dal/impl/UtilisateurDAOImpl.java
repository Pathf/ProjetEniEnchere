package org.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.encheres.bo.Utilisateur;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.dao.UtilisateurDAO;

public class UtilisateurDAOImpl implements UtilisateurDAO {

	private static String SQLSELECT_ID = "Select * from UTILISATEURS WHERE no_utilisateur=?";
	private static String SQLSELECT_ALL = "Select * from UTILISATEURS";
	private static String SQLINSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static String SQLUPDATE = "UPDATE UTILISATEURS SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=?, credit=?, administrateur=?";
	private static String SQLREMOVE = "DELETE FROM UTILISATEURS WHERE no_utilisateur=?";

	@Override
	public Utilisateur selectById(Integer id) throws DALException {
		Utilisateur utilisateur = null;
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_ID);
				) {
			preparedStatement.setInt(1, id);

			try (ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()){
					utilisateur = new Utilisateur(
							id,
							rs.getString("pseudo"),
							rs.getString("nom"),
							rs.getString("prenom"),
							rs.getString("email"),
							rs.getString("telephone"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville"),
							rs.getString("mot_de_passe"),
							rs.getInt("credit"),
							rs.getBoolean("administrateur")
							);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYID failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed - ", e);
		}
		return utilisateur;
	}

	@Override
	public List<Utilisateur> selectAll() throws DALException {
		List<Utilisateur> utilisateur = new ArrayList<>();
		try (	Connection connection = DAOTools.getConnection();
				Statement statement = connection.createStatement();
				) {
			statement.execute(SQLSELECT_ALL);

			try (ResultSet rs = statement.getResultSet();){
				if(rs.next()){
					utilisateur.add(new Utilisateur(
							rs.getInt("no_utilisateur"),
							rs.getString("pseudo"),
							rs.getString("nom"),
							rs.getString("prenom"),
							rs.getString("email"),
							rs.getString("telephone"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville"),
							rs.getString("mot_de_passe"),
							rs.getInt("credit"),
							rs.getBoolean("administrateur")
							));
				}
			}catch (SQLException e) {
				throw new DALException("Select ALL failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select All failed - ", e);
		}
		return utilisateur;
	}

	@Override
	public void insert(Utilisateur utilisateur) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
				){
			preparedStatement.setString(1, utilisateur.getPseudo());
			preparedStatement.setString(2, utilisateur.getNom());
			preparedStatement.setString(3, utilisateur.getPrenom());
			preparedStatement.setString(4, utilisateur.getEmail());
			preparedStatement.setString(5, utilisateur.getTelephone());
			preparedStatement.setString(6, utilisateur.getRue());
			preparedStatement.setString(7, utilisateur.getCode_postal());
			preparedStatement.setString(8, utilisateur.getVille());
			preparedStatement.setString(9, utilisateur.getMot_de_passe());
			preparedStatement.setInt(10, utilisateur.getCredit());
			preparedStatement.setBoolean(11, utilisateur.getAdministrateur());

			preparedStatement.executeUpdate();

			try(ResultSet rs = preparedStatement.getGeneratedKeys()){
				utilisateur.setNo_utilisateur(rs.getInt(1));
			} catch (SQLException e) {
				throw new DALException("Insert utilisateur return key failed - " + utilisateur + " - ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Insert utilisateur failed - " + utilisateur + " - ", e);
		}
	}

	@Override
	public void update(Utilisateur utilisateur) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLUPDATE);
				){
			preparedStatement.setString(1, utilisateur.getPseudo());
			preparedStatement.setString(2, utilisateur.getNom());
			preparedStatement.setString(3, utilisateur.getPrenom());
			preparedStatement.setString(4, utilisateur.getEmail());
			preparedStatement.setString(5, utilisateur.getTelephone());
			preparedStatement.setString(6, utilisateur.getRue());
			preparedStatement.setString(7, utilisateur.getCode_postal());
			preparedStatement.setString(8, utilisateur.getVille());
			preparedStatement.setString(9, utilisateur.getMot_de_passe());
			preparedStatement.setInt(10, utilisateur.getCredit());
			preparedStatement.setBoolean(11, utilisateur.getAdministrateur());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Update utilisateur failed - " + utilisateur + " - ", e);
		}
	}

	@Override
	public void remove(Utilisateur utilisateur) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLREMOVE);
				){
			preparedStatement.setInt(1, utilisateur.getNo_utilisateur());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Delete utilisateur failed - " + utilisateur + " - ", e);
		}
	}

}
