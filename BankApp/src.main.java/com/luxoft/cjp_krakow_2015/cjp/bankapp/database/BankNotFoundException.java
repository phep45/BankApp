package com.luxoft.cjp_krakow_2015.cjp.bankapp.database;

public class BankNotFoundException extends Exception {

	private static final long serialVersionUID = 8384611217627447802L;
	
	public BankNotFoundException(String msg) {
		super(msg);
	}

}
