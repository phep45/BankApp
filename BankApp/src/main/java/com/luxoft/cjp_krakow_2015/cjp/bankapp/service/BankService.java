package com.luxoft.cjp_krakow_2015.cjp.bankapp.service;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;

public interface BankService {
	public void addClient(Bank bank, Client client) throws ClientExistsException;
	public void removeClient(Bank bank,Client client);
    public void addAccount(Client client, Account account);
    public void setActiveAccount(Client client, Account account);
    public Client getClient(Bank bank, String clientName);
    public Client createClient();
}
