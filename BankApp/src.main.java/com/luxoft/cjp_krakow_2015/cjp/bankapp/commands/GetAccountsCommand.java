package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class GetAccountsCommand implements Command {

	@Override
	public void execute() {
		System.out.println("Bank report:");
		BankCommander.bank.printReport();
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Get accounts");
	}

}
