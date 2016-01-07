package com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions;

public class InvalidClientNameException extends BankException {

	private static final long serialVersionUID = -7477312608808198027L;

	public InvalidClientNameException(String msg) {
		super(msg);
	}

}
