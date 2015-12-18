package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests;

public class ChangeAccountRequest implements Request {
	
	private static final long serialVersionUID = -2160066748701010012L;

	private final int accountID;
	
	public ChangeAccountRequest(int accountID) {
		this.accountID = accountID;
	}
	
	public int getAccountID() {
		return accountID;
	}
	
	@Override
	public void printInfo() {
		System.out.println("Change account");
	}

}
