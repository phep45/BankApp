package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.DAOException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.BankException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class WithdrawCommand implements Command {

	@Override
	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Amount to withdraw:");
		String userInput = reader.readLine();
		if(BankCommander.currentClient != null)
			try {
				double amount = Double.parseDouble(userInput);
				BankCommander.currentClient.withdraw((float) amount);
				ClientDAO clientDAO = new ClientDAOImpl();
				clientDAO.save(BankCommander.currentClient);
			} catch(NumberFormatException e) {
				System.err.println("Invalid input");
			} catch (BankException e) {
				System.err.println("Limit exceeded");
			} catch (DAOException e) {
				System.err.println(e.getMessage());
			} 
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Withdraw");
	}

}
