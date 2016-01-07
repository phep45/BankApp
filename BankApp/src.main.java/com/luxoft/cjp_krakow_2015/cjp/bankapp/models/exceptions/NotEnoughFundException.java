package com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions;

public class NotEnoughFundException extends BankException {

	private static final long serialVersionUID = 3006018508040249792L;

	private float amount;
	
	public NotEnoughFundException(String msg) {
		super(msg);
	}
	
	public NotEnoughFundException(String msg, float amount) {
		this(msg);
		this.amount = amount;
	}
	
	public float getAmount() {
		return amount;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + ": " + amount;
	}

}
