package com.luxoft.cjp_krakow_2015.cjp.bankapp.database;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.BankInfo;

public interface BankDAO {

	Bank getBankByName(String name) throws DAOException, BankNotFoundException;
	void save(Bank bank) throws DAOException;
	void remove(Bank bank) throws DAOException;
	BankInfo getBankInfo(Bank bank);
	
}
