package com.luxoft.cjp_krakow_2015.cjp.bankapp.service;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;

public class BankServiceImpl implements BankService {

	@Override
	public void addClient(Bank bank, Client client) throws ClientExistsException {
		bank.addClient(client);
	}

	@Override
	public void removeClient(Bank bank, Client client) {
		bank.removeClient(client);
	}

	@Override
	public void addAccount(Client client, Account account) {
		client.addAccount(account);
	}

	@Override
	public void setActiveAccount(Client client, Account account) {
		client.setActiveAccount(account);
	}

	@Override
	public Client getClient(Bank bank, String clientName) {
		return bank.getClient(clientName);
	}

}
