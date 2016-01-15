package com.luxoft.cjp_krakow_2015.cjp.bankapp.database.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.Command;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.AccountDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankNotFoundException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.DAOException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class DBSelectBankCommander implements Command {

	private BankDAO bankDAO;//(BankDAO) BankApplication.context.getBean("bankDAO");
	private ClientDAO clientDAO;//(ClientDAO) BankApplication.context.getBean("clientDAO");
	private AccountDAO accountDAO;//(AccountDAO) BankApplication.context.getBean("accountDAO");
	
	@Override
	public void execute() {
		Bank bank = null;
		
		System.out.println("Name of bank:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String name = reader.readLine();
			bank = bankDAO.getBankByName(name);
			for(Client client : clientDAO.getAllClients(bank)) {
				for(Account account : accountDAO.getClientAccounts(client.getID())) {
					client.addAccount(account);
				}
				bank.addClient(client);
			}
			BankCommander.bank = bank;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (BankNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (ClientExistsException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("DB Select Bank");
	}

	public BankDAO getBankDAO() {
		return bankDAO;
	}

	@Autowired
	public void setBankDAO(BankDAO bankDAO) {
		this.bankDAO = bankDAO;
	}

	public ClientDAO getClientDAO() {
		return clientDAO;
	}

	@Autowired
	public void setClientDAO(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	@Autowired
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	
}
