package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands;

public class MyAccountsCmd implements NetCommand {

	private static final long serialVersionUID = 9007979910383617570L;

	@Override
	public void printInfo() {
		System.out.println("My accounts");
	}

}
