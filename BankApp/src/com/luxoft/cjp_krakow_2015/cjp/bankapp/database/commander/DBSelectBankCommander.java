package com.luxoft.cjp_krakow_2015.cjp.bankapp.database.commander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.Command;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.AccountDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.AccountDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankNotFoundException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.DAOException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class DBSelectBankCommander implements Command {

	private BankDAO bankDAO = new BankDAOImpl();
	private ClientDAO clientDAO = new ClientDAOImpl();
	private AccountDAO accountDAO = new AccountDAOImpl();
	
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
	
}
