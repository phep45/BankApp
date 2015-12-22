package com.luxoft.cjp_krakow_2015.cjp.bankapp.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.BankInfo;

public class BankDAOImpl extends BaseDAOImpl implements BankDAO {

	@Override
	public Bank getBankByName(String name) throws DAOException, BankNotFoundException {
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
			throw new DAOException(e.getMessage());
		} finally {
			closeConnection();
		}
		return bank;
	}

	@Override
	public void save(Bank bank) throws DAOException {
		String sql ="INSERT INTO BANK (NAME) VALUES('" + bank.getName() +"');";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			if(!stmt.execute())
				System.out.println("Bank saved");
		} catch(SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public void remove(Bank bank) throws DAOException {
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
