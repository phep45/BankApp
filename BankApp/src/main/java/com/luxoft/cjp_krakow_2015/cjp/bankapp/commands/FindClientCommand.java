package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankServiceImpl;

public class FindClientCommand implements Command {

	@Override
	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Name or ID:");
		String userInput = reader.readLine();
		try {
			int clientID = Integer.parseInt(userInput);
			BankCommander.currentClient = BankCommander.bank.getClient(clientID);
		} catch(NumberFormatException e) {
			BankCommander.currentClient = new BankServiceImpl().getClient(BankCommander.bank, userInput);
//			BankCommander.currentClient = BankCommander.bank.getClient(userInput);
		} 
		System.out.println(BankCommander.currentClient);
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Find client");
	}

}
