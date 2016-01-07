package com.luxoft.cjp_krakow_2015.cjp.bankapp.models;

import java.util.Map;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.BankException;

public interface Account extends Report{
	public float getBalance();
	public void deposit(float amount);
	public void withdraw(float amount) throws BankException;
	public float decimalValue();
	public int getID();
	public void parseFeed(Map<String, String> feed);
	public int getClientId();
	public void setClientId(int clientId);
}
