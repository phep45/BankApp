package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands;

public class ChangeAccountCmd implements NetCommand {
	
	private static final long serialVersionUID = -2160066748701010012L;

	private final int accountID;
	
	public ChangeAccountCmd(int accountID) {
		this.accountID = accountID;
	}
	
	public int getAccountID() {
		return accountID;
	}
	
	@Override
	public void printInfo() {
		// TODO Auto-generated method stub

	}

}
