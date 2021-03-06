package org.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.encheres.bo.Categorie;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.dao.CategorieDAO;
import org.encheres.dal.sql.SQLRequete;

public class CategorieDAOImpl implements CategorieDAO {

	private static final String TABLE = "CATEGORIES";
	private static final String[] IDS = new String[]{"no_categorie"};
	private static final String[] CHAMPS = new String[]{"libelle"};

	private static final String SQLSELECT_ID = SQLRequete.select(null, TABLE, IDS);
	private static final String SQLSELECT_ALL = SQLRequete.select(TABLE);
	private static final String SQLINSERT = SQLRequete.insert(TABLE, CHAMPS);
	private static final String SQLUPDATE = SQLRequete.update(TABLE, CHAMPS, IDS);
	private static final String SQLREMOVE = SQLRequete.delete(TABLE, IDS);

	@Override
	public Categorie selectById(Integer id) throws DALException {
		Categorie categorie = null;
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_ID);
				) {
			preparedStatement.setInt(1, id);

			try (ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()){
					categorie = new Categorie(id, rs.getString("libelle"));
				}
			}catch (SQLException e) {
				throw new DALException("Select BYID failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed\n" + e);
		}
		return categorie;
	}

	@Override
	public List<Categorie> selectAll() throws DALException {
		List<Categorie> categories = new ArrayList<>();
		try (	Connection connection = DAOTools.getConnection();
				Statement statement = connection.createStatement();
				) {
			statement.execute(SQLSELECT_ALL);

			try (ResultSet rs = statement.getResultSet();){
				while(rs.next()){
					categories.add(new Categorie(rs.getInt("no_categorie"), rs.getString("libelle")));
				}
			}catch (SQLException e) {
				throw new DALException("Select ALL failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select All failed\n" + e);
		}
		return categories;
	}

	@Override
	public void insert(Categorie categorie) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
				){
			preparedStatement.setString(1, categorie.getLibelle());
			preparedStatement.executeUpdate();

			try(ResultSet rs = preparedStatement.getGeneratedKeys()){
				if(rs.next()) {
					categorie.setNo_categorie(rs.getInt(1));
				}
			} catch (SQLException e) {
				throw new DALException("Insert categorie return key failed - " + categorie + "\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Insert categorie failed - " + categorie + "\n" + e);
		}
	}

	@Override
	public void update(Categorie categorie) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLUPDATE);
				){
			preparedStatement.setString(1, categorie.getLibelle());
			preparedStatement.setInt(2, categorie.getNo_categorie());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Update categorie failed - " + categorie + "\n" + e);
		}
	}

	@Override
	public void remove(Categorie categorie) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLREMOVE);
				){
			preparedStatement.setInt(1, categorie.getNo_categorie());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Delete categorie failed - " + categorie + "\n" + e);
		}
	}
}
