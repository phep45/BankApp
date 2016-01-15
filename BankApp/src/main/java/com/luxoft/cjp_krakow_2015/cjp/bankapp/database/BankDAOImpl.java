package com.luxoft.cjp_krakow_2015.cjp.bankapp.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.annotation.Resource;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.BankInfo;

@Resource
public class BankDAOImpl extends BaseDAOImpl implements BankDAO {

	@Override
	public Bank getBankByName(String name) throws DAOException, BankNotFoundException {
		log.log(Level.INFO, "getting bank " + name);
		Bank bank = new Bank(name);
		String sql = "SELECT ID, NAME FROM BANK WHERE NAME=?";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt("ID");
				bank.setId(id);
			}
			else {
				throw new BankNotFoundException("Bank " + name +" not found in database!");
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			closeConnection();
		}
		return bank;
	}

	@Override
	public void save(Bank bank) throws DAOException {
		log.log(Level.INFO, "saving bank state " + bank.getName());
		String sql ="INSERT INTO BANK (NAME) VALUES('" + bank.getName() +"');";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			for(Client c : bank.getClients()) {
				ClientDAOImpl clDaoImpl = new ClientDAOImpl();
				clDaoImpl.save(c);
			}
			if(!stmt.execute())
				System.out.println("Bank saved");
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public void remove(Bank bank) throws DAOException {
		log.log(Level.INFO, "removing bank " + bank.getName());
		String sql = "DELETE FROM BANK WHERE NAME='" + bank.getName() + "');";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			if(!stmt.execute())
				System.out.println("Bank deleted");
		} catch(SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public BankInfo getBankInfo(Bank bank) {
		return new BankInfo(bank);
	}

}
