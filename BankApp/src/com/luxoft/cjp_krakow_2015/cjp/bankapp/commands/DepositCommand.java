package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
			} catch(NumberFormatException e) {
				System.err.println("Invalid input");
			} 
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Deposit");
	}

}
