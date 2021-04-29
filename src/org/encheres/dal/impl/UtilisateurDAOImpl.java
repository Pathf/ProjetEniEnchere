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
import org.encheres.dal.sql.SQLRequete;

public class UtilisateurDAOImpl implements UtilisateurDAO {
	private static final String SQLSELECT_ID = SQLRequete.select(null, BDD.UTILISATEURS_TABLENOM, BDD.UTILISATEURS_IDS);
	private static final String SQLSELECT_PSEUDO = SQLRequete.select(null, BDD.UTILISATEURS_TABLENOM, new String[] {"pseudo"} );
	private static final String SQLSELECT_PSEUDO_MDP = SQLRequete.select(null, BDD.UTILISATEURS_TABLENOM, new String[] {"pseudo","mot_de_passe"});
	private static final String SQLSELECT_EMAIL_MDP = SQLRequete.select(null, BDD.UTILISATEURS_TABLENOM, new String[] {"email","mot_de_passe"});
	private static final String SQLSELECT_ALL = SQLRequete.select(BDD.UTILISATEURS_TABLENOM);
	private static final String SQLINSERT = SQLRequete.insert(BDD.UTILISATEURS_TABLENOM, BDD.UTILISATEURS_CHAMPS);
	private static final String SQLUPDATE = SQLRequete.update(BDD.UTILISATEURS_TABLENOM, BDD.UTILISATEURS_CHAMPS, BDD.UTILISATEURS_IDS);
	private static final String SQLREMOVE = SQLRequete.delete(BDD.UTILISATEURS_TABLENOM, BDD.UTILISATEURS_IDS);

	@Override
	public Utilisateur selectById(Integer id) throws DALException {
		Utilisateur utilisateur = null;
		try (	Connection connection = DAOTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLSELECT_ID);
				) {
			preparedStatement.setInt(1, id);

			try (ResultSet rs = preparedStatement.executeQuery();){
				if(rs.next()){
					utilisateur = this.buildUtilisateur(rs);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYID failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYID failed\n" + e);
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
					utilisateur = this.buildUtilisateur(rs);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYPSEUDO failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYPSEUDO failed\n" + e);
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
					utilisateur = this.buildUtilisateur(rs);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYEMAIL failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYEMAIL failed\n" + e);
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
					utilisateur = this.buildUtilisateur(rs);
				}
			}catch (SQLException e) {
				throw new DALException("Select BYPSEUDO failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select BYPSEUDO failed\n" + e);
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
					utilisateur.add(this.buildUtilisateur(rs));
				}
			}catch (SQLException e) {
				throw new DALException("Select ALL failed - close failed for rs\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Select All failed\n" + e);
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
			preparedStatement.setBoolean(12, utilisateur.isActiver());

			preparedStatement.executeUpdate();

			try(ResultSet rs = preparedStatement.getGeneratedKeys()){
				if(rs.next()) {
					utilisateur.setNo_utilisateur(rs.getInt(1));
				}
			} catch (SQLException e) {
				throw new DALException("Insert utilisateur return key failed - " + utilisateur + "\n" + e);
			}
		} catch (SQLException e) {
			throw new DALException("Insert utilisateur failed - " + utilisateur + "\n" + e);
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
			preparedStatement.setBoolean(12, utilisateur.isActiver());
			preparedStatement.setInt(13, utilisateur.getNo_utilisateur());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Update utilisateur failed - " + utilisateur + "\n" + e);
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
			throw new DALException("Delete utilisateur failed - " + utilisateur + "\n" + e);
		}
	}

	private Utilisateur buildUtilisateur(ResultSet rs) throws SQLException {
		return new Utilisateur(
				rs.getInt(BDD.UTILISATEURS_IDS[0]),
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
				rs.getBoolean(BDD.UTILISATEURS_CHAMPS[10]),
				rs.getBoolean(BDD.UTILISATEURS_CHAMPS[11])
				);
	}
}
