package com.luxoft.cjp_krakow_2015.cjp.bankapp.models;

import java.util.Map;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.BankException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.OverDraftLimitExceededException;

public class CheckingAccount extends AbstractAccount {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3473444313409334447L;
	private float overdraft;
	
	public CheckingAccount() {
		overdraft = 0;
	}
	
	public void setOverdraft(float overdraft) {
		this.overdraft = overdraft;
	}
	
	public float getOverdraft() {
		return overdraft;
	}
	
	public CheckingAccount(float overdraft) {
		this.overdraft = overdraft;
	}
	
	@Override
	public void withdraw(float amount) throws BankException {
		if(balance - amount < -overdraft) {
			throw new OverDraftLimitExceededException("OverDraftLimitExceeded", amount - balance - overdraft, this);
		}
		else {
			super.withdraw(amount);
		}
	}
	
	@Override
	public void printReport() {
		System.out.println(this);
	}
	
	public void parseFeed(Map<String, String> feed) {
		balance = (float) Double.parseDouble(feed.get("balance"));
		overdraft = (float) Double.parseDouble(feed.get("overdraft"));
	}
	
	@Override
	public String toString() {
		return  new StringBuilder("Account ID: ")
				.append(id)
				.append(", Checking account, balance: ")
				.append(balance)
				.append(", overdraft: ")
				.append(overdraft)
				.toString(); 
	}
	
}
