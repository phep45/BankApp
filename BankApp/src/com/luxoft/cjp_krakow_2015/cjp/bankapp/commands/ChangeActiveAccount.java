package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class ChangeActiveAccount implements Command {

	@Override
	public void execute() throws IOException {
		if(BankCommander.currentClient.getAccountsList().isEmpty()){
			System.out.println("Client has no accounts");
			return;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		for(Account account : BankCommander.currentClient.getAccountsList()) {
			account.printReport();
		}
		try {
			int answer = Integer.parseInt(reader.readLine());
			Account account = BankCommander.currentClient.searchAccount(answer);
			if(account != null)
				BankCommander.currentClient.setActiveAccount(account);
			else
				System.err.println("Inccorect accout ID");
		} catch(NumberFormatException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Change active account");
	}

}
