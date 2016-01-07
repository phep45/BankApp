package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class CreateAccount implements Command {

	private final String SAVING = "saving";
	private final String CHECKING = "checking";
	
	@Override
	public void execute() throws IOException {
		if(BankCommander.currentClient != null) {
			System.out.println("Account type: [S]aving/[C]hecking");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String answer = reader.readLine();
			if(answer.toLowerCase().equals("s") || answer.toLowerCase().equals("saving"))
				BankCommander.currentClient.createAccount(SAVING);
			else if(answer.toLowerCase().equals("c") || answer.toLowerCase().equals("checking")) {
				BankCommander.currentClient.createAccount(CHECKING);
			}
			else {
				System.err.println("invalid account type");
				return;
			}
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Create account");
	}

}
