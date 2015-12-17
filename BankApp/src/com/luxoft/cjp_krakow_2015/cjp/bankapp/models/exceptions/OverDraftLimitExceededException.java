package com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;

public class OverDraftLimitExceededException extends NotEnoughFundException {

	private static final long serialVersionUID = -6746271541356382757L;
	
	private Account account;
	
	public OverDraftLimitExceededException(String msg, float amount, Account account) {
		super(msg, amount);
		this.account = account;
	}
	
	@Override
	public String getMessage() {
		return account.toString() + " " + super.getMessage();
	}

}
