package com.luxoft.cjp_krakow_2015.cjp.bankapp.database.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.Command;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.AccountDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.AccountDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.DAOException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankApplication;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class DBSelectClientCommander implements Command {

//	private ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
	
	private ClientDAO clientDAO = null;// (ClientDAO) BankApplication.context.getBean("clientDAO");
	private AccountDAO accountDAO = null;// (AccountDAO) BankApplication.context.getBean("accountDAO");
	
	@Override
	public void execute() {
		Client client = null;
		
		System.out.println("Name of client:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String name = reader.readLine();
			client = clientDAO.findClientByName(BankCommander.bank, name);
			for(Account account : accountDAO.getClientAccounts(client.getID()))
				client.addAccount(account);
			BankCommander.currentClient = client;
		} catch(IOException e) {
			System.err.println(e.getMessage());
		} catch(DAOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("DB Select client");
	}
	
}
