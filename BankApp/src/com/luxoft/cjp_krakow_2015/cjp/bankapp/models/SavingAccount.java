package com.luxoft.cjp_krakow_2015.cjp.bankapp.models;

import java.util.Map;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.BankException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.NotEnoughFundException;

public class SavingAccount extends AbstractAccount {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5967377366696302893L;

	@Override
	public void withdraw(float amount) throws BankException {
		if(balance - amount < 0) {
			throw new NotEnoughFundException("insufficient funds");
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
		
	}
	
	@Override
	public String toString() {
		return  new StringBuilder("Account ID: ")
				.append(id)
				.append(", Saving account, balance: ")
				.append(balance)
				.toString(); 
	}
	
}
