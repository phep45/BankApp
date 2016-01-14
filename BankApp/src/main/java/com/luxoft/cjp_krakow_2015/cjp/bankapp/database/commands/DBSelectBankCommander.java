package com.luxoft.cjp_krakow_2015.cjp.bankapp.database.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankApplication;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class DBSelectBankCommander implements Command {

//	private ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
	
	private BankDAO bankDAO = null;//(BankDAO) BankApplication.context.getBean("bankDAO");
	private ClientDAO clientDAO = null;//(ClientDAO) BankApplication.context.getBean("clientDAO");
	private AccountDAO accountDAO = null;//(AccountDAO) BankApplication.context.getBean("accountDAO");
	
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
