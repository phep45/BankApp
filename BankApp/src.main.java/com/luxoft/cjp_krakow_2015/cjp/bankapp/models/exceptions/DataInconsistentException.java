package com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions;

public class DataInconsistentException extends BankException {

	private static final long serialVersionUID = 826922419174525522L;
	
	public DataInconsistentException(String msg) {
		super(msg);
	}
}
