package com.luxoft.cjp_krakow_2015.cjp.bankapp.database;

import java.util.List;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;

public interface AccountDAO {

	void save(Account account) throws DAOException;
	void add(Account account) throws DAOException;
	void removeByClientId(int clientId) throws DAOException;
	List<Account> getClientAccounts(int clientId) throws DAOException;
	
}
