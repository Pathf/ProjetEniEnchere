package org.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.encheres.bo.Retrait;
import org.encheres.dal.ConstantesSQL;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.dao.RetraitDAO;

public class RetraitDAOImpl implements RetraitDAO {

	private static final String TABLE = "RETRAITS";
	private static final String[] IDS = new String[]{"no_retrait"};
	private static final String[] CHAMPS = new String[]{"rue","code_postal","ville"};

	private static final String SQLSELECT_ID = ConstantesSQL.requeteSelect(TABLE, null, IDS);
	private static final String SQLSELECT_ALL = ConstantesSQL.requeteSelect(TABLE);
	private static final String SQLINSERT = ConstantesSQL.requeteInsert(TABLE, CHAMPS);
	private static final String SQLUPDATE = ConstantesSQL.requeteUpdate(TABLE, CHAMPS, IDS);

	@Override
	public Retrait selectById(Integer id) throws DALException {
		Retrait retrait = null;
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_ID);
				) {
			preparedStatement.setInt(1, id);

			try (ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()){
					retrait = new Retrait(
							id,
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville")
							);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYID failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed - ", e);
		}
		return retrait;
	}

	@Override
	public List<Retrait> selectAll() throws DALException {
		List<Retrait> retrait = new ArrayList<>();
		try (	Connection connection = DAOTools.getConnection();
				Statement statement = connection.createStatement();
				) {
			statement.execute(SQLSELECT_ALL);

			try (ResultSet rs = statement.getResultSet();){
				while(rs.next()){
					retrait.add(new Retrait(
							rs.getInt("no_retrait"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville")
							));
				}
			}catch (SQLException e) {
				throw new DALException("Select ALL failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select All failed - ", e);
		}
		return retrait;
	}

	@Override
	public void insert(Retrait retrait) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
				){
			preparedStatement.setString(1, retrait.getRue());
			preparedStatement.setString(2, retrait.getCode_postal());
			preparedStatement.setString(3, retrait.getVille());
			preparedStatement.executeUpdate();

			try(ResultSet rs = preparedStatement.getGeneratedKeys()){
				if(rs.next()) {
					retrait.setNo_retrait(rs.getInt(1));
				}
			} catch (SQLException e) {
				throw new DALException("Insert retrait return key failed - " + retrait + " - ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Insert retrait failed - " + retrait + " - ", e);
		}
	}

	@Override
	public void update(Retrait retrait) throws DALException {
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLUPDATE);
				){
			preparedStatement.setString(1, retrait.getRue());
			preparedStatement.setString(2, retrait.getCode_postal());
			preparedStatement.setString(3, retrait.getVille());
			preparedStatement.setInt(4, retrait.getNo_retrait());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Update retrait failed - " + retrait + " - ", e);
		}
	}
}
