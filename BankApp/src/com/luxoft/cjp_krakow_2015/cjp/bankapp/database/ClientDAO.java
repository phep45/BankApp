package com.luxoft.cjp_krakow_2015.cjp.bankapp.database;

import java.util.List;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;

public interface ClientDAO {

	Client findClientByName(Bank bank, String name) throws DAOException;
	List<Client> getAllClients(Bank bank) throws DAOException;
	void save(Client client) throws DAOException;
	void remove(Client client) throws DAOException;
	
}
