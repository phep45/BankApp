package com.luxoft.cjp_krakow_2015.cjp.bankapp.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.CheckingAccount;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.SavingAccount;

public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO {

	@Override
	public void save(Account account) throws DAOException {
		log.log(Level.INFO, "saving account: " + account);
		String accountType = "";
		float overdraft = 0f;
		if(account instanceof CheckingAccount) {
			accountType = "c";
			overdraft = ((CheckingAccount)account).getOverdraft();
		}
		else if(account instanceof SavingAccount) {
			accountType = "s"; 
			overdraft = 0f;
		}
		String sql = "UPDATE ACCOUNTS SET " +
				"CLIENT_ID=" + account.getClientId() + "," +
				"TYPE=" + "'" + accountType + "'," +
				"BALANCE=" + account.getBalance() + "," +
				"OVERDRAFT=" + overdraft +
				"WHERE ID=" + account.getID() + ";";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			if(!stmt.execute())
				System.out.println("Account saved");
			else
				System.err.println("Problems during save");
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public void add(Account account) throws DAOException {
		log.log(Level.INFO, "adding account " + account);
		String accountType = "";
		float overdraft = 0f;
		if(account instanceof CheckingAccount) {
			accountType = "c";
			overdraft = ((CheckingAccount)account).getOverdraft();
		}
		else if(account instanceof SavingAccount) {
			accountType = "s"; 
			overdraft = 0f;
		}
		String sql = "INSERT INTO ACCOUNTS VALUES(" +
				account.getID() + "," +
				account.getClientId() + "," +
				"'" + accountType + "'," +
				account.getBalance() + "," +
				overdraft + ");";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			if(stmt.execute())
				System.out.println("Account saved");
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public void removeByClientId(int clientId) throws DAOException {
		log.log(Level.INFO, "removing by client id = " + clientId);
		String sql = "DELETE FROM ACCOUNTS WHERE CLIENT_ID=" + clientId + ";";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			if(stmt.execute())
				System.out.println("Accounts deleted");
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public List<Account> getClientAccounts(int clientId) throws DAOException {
		log.log(Level.INFO, "getting accounts list of client: " + clientId);
		List<Account> clientAccountsList = new ArrayList<Account>();
		String sql = "SELECT * FROM ACCOUNTS WHERE CLIENT_ID=" + clientId +";";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("ID");
				int client_id = rs.getInt("CLIENT_ID");
				String type = rs.getString("TYPE");
				float balance = rs.getFloat("BALANCE");
				float overdraft = rs.getFloat("OVERDRAFT");
				
				if(type.toLowerCase().equals("s")) {
					SavingAccount account = new SavingAccount();
					account.setClientId(client_id);
					account.deposit(balance);
					account.setId(id);
					clientAccountsList.add(account);
				}
				else if(type.toLowerCase().equals("c")) {
					CheckingAccount account = new CheckingAccount(overdraft);
					account.deposit(balance);
					account.setClientId(client_id);
					account.setId(id);
					clientAccountsList.add(account);
				}
				else {
					System.err.println("wrong account type " + type);
				}
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			closeConnection();
		}
		return clientAccountsList;
	}

}
