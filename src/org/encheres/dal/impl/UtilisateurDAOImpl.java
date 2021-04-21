package org.encheres.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.encheres.bo.Utilisateur;
import org.encheres.dal.ConstantesSQL;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOTools;
import org.encheres.dal.dao.UtilisateurDAO;

public class UtilisateurDAOImpl implements UtilisateurDAO {

	private static final String TABLE = "UTILISATEURS";
	private static final String[] IDS = new String[]{"no_utilisateur"};
	private static final String[] CHAMPS = new String[]{"pseudo","nom","prenom","email","telephone","rue","code_postal","ville","mot_de_passe","credit","administrateur"};

	private static final String SQLSELECT_ID = ConstantesSQL.requeteSelect(TABLE, null, IDS);
	private static final String SQLSELECT_PSEUDO = ConstantesSQL.requeteSelect(TABLE, null, new String[] {"pseudo"} );
	private static final String SQLSELECT_PSEUDO_MDP = ConstantesSQL.requeteSelect(TABLE, null, new String[] {"pseudo","mot_de_passe"});
	private static final String SQLSELECT_EMAIL_MDP = ConstantesSQL.requeteSelect(TABLE, null, new String[] {"email","mot_de_passe"});
	private static final String SQLSELECT_ALL = ConstantesSQL.requeteSelect(TABLE);
	private static final String SQLINSERT = ConstantesSQL.requeteInsert(TABLE, CHAMPS);
	private static final String SQLUPDATE = ConstantesSQL.requeteUpdate(TABLE, CHAMPS, IDS);
	private static final String SQLREMOVE = ConstantesSQL.requeteDelete(TABLE, IDS);

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
				throw new DALException("Select BYID failed - close failed for rs -  \n"+ e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed - \n"+ e);
		}
		return utilisateur;
	}
	
	@Override
	public Utilisateur selectByPseudo(String pseudo) throws DALException {
		Utilisateur utilisateur = null;
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_PSEUDO);
				) {
			preparedStatement.setString(1, pseudo);

			try (ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()){
					utilisateur = new Utilisateur(
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
							);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYPSEUDO failed - close failed for rs -  ", e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYPSEUDO failed - ", e);
		}
		return utilisateur;
	}

	@Override
	public Utilisateur selectByEmailEtMdp(String identifiant, String mdp) throws DALException {
		Utilisateur utilisateur = null;
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_EMAIL_MDP);
				) {
			preparedStatement.setString(1, identifiant);
			preparedStatement.setString(2, mdp);

			try (ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()){
					utilisateur = new Utilisateur(
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
							);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYEMAIL failed - close failed for rs -  \n"+ e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYEMAIL failed - \n"+ e);
		}
		return utilisateur;
	}

	@Override
	public Utilisateur selectByPseudoEtMdp(String identifiant, String mdp) throws DALException {
		Utilisateur utilisateur = null;
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_PSEUDO_MDP);
				) {
			preparedStatement.setString(1, identifiant);
			preparedStatement.setString(2, mdp);

			try (ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()){
					utilisateur = new Utilisateur(
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
							);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYPSEUDO failed - close failed for rs -  \n"+ e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYPSEUDO failed - \n"+ e);
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
				while(rs.next()){
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
				throw new DALException("Select ALL failed - close failed for rs -  \n"+ e);
			}
		} catch (SQLException e) {
			throw new DALException("Select All failed - \n"+ e);
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
				if(rs.next()) {
					utilisateur.setNo_utilisateur(rs.getInt(1));
				}
			} catch (SQLException e) {
				throw new DALException("Insert utilisateur return key failed - " + utilisateur + " - \n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Insert utilisateur failed - " + utilisateur + " - \n" + e);
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
			preparedStatement.setInt(12, utilisateur.getNo_utilisateur());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Update utilisateur failed - " + utilisateur + " - \n" + e);
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
			throw new DALException("Delete utilisateur failed - " + utilisateur + " - \n" + e);
		}
	}
}
