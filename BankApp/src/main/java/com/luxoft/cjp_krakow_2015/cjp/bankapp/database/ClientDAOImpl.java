package com.luxoft.cjp_krakow_2015.cjp.bankapp.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.EmailException;

public class ClientDAOImpl extends BaseDAOImpl implements ClientDAO {

	@Override
	public Client findClientByName(Bank bank, String name) throws DAOException {
		String sql = "SELECT * FROM CLIENTS WHERE NAME='" + name + "';";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt("ID");
				String clientName =  rs.getString("NAME");
				String gender = rs.getString("GENDER");
				String email = rs.getString("EMAIL");
				float initialOverdraft = rs.getFloat("INITIALOVERDRAFT");
				int bankId = rs.getInt("BANK_ID");
				return new Client(id, clientName, gender, email, initialOverdraft, bankId);
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (EmailException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			System.err.println(e.getMessage());
		} finally {
			closeConnection();
		}
		return null;
	}

	@Override
	public List<Client> getAllClients(Bank bank) throws DAOException {
		String sql = "SELECT * FROM CLIENTS WHERE BANK_ID=" + bank.getId() + ";";
		PreparedStatement stmt;
		List<Client> clientsList = new ArrayList<Client>();
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("ID");
				String clientName =  rs.getString("NAME");
				String gender = rs.getString("GENDER");
				String email = rs.getString("EMAIL");
				float initialOverdraft = rs.getFloat("INITIALOVERDRAFT");
				int bankId = rs.getInt("BANK_ID");
				Client client = new Client(id, clientName, gender, email, initialOverdraft, bankId);
				clientsList.add(client);
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (EmailException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			System.err.println(e.getMessage());
		} finally {
			closeConnection();
		}
		return clientsList;
	}

	@Override
	public void save(Client client) throws DAOException {
		String sql = "";
		if(clientExistsInDB(client)){
			sql = "UPDATE CLIENTS SET " +
					"GENDER='" + client.getGender().toSqlString() + "'," +
					"EMAIL='" + client.getEmail().toString() + "'," +
					"INITIALOVERDRAFT=" + client.getInitialOverdraft() +
					"WHERE ID=" + client.getID() + ";";
		}
		else {
			sql = "INSERT INTO CLIENTS VALUES(" +
					client.getID() + "," +
					"'" + client.getName() + "'," +
					"'" + client.getGender().toSqlString() + "'," +
					"'" + client.getEmail().toString() + "'," +
					client.getInitialOverdraft() + "," +
					client.getBankId() + ");";
		}
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			if(!stmt.execute())
				System.out.println("Client saved");
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			closeConnection();
		}

	}

	@Override
	public void remove(Client client) throws DAOException {
		String sql = "DELETE FROM CLIENTS WHERE ID=" + client.getID() + ";";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			if(!stmt.execute())
				System.out.println("Client removed");
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			closeConnection();
		}
	}
	
	private boolean clientExistsInDB(Client client) throws DAOException {
		String sql = "SELECT * FROM CLIENTS WHERE ID=" + client.getID() + ";";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			closeConnection();
		}
		return false;
	}

}
