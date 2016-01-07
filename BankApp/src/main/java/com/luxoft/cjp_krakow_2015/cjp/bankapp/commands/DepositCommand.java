package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.AccountDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.AccountDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.DAOException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class DepositCommand implements Command {

	@Override
	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Amount to deposit:");
		String userInput = reader.readLine();
		if(BankCommander.currentClient != null)
			try {
				double amount = Double.parseDouble(userInput);
				BankCommander.currentClient.deposit((float) amount);
				ClientDAO clientDAO = new ClientDAOImpl();
				AccountDAO accountDAO = new AccountDAOImpl();
				clientDAO.save(BankCommander.currentClient);
				accountDAO.save(BankCommander.currentClient.getActiveAccount());
			} catch(NumberFormatException e) {
				System.err.println("Invalid input");
			} catch (DAOException e) {
				System.err.println(e.getMessage());
			} 
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Deposit");
	}

}
