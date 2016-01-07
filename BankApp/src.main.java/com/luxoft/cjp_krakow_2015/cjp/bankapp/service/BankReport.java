package com.luxoft.cjp_krakow_2015.cjp.bankapp.service;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;

public class BankReport {

	static void getNumberOfClients(Bank bank) {
		System.out.println("Number of bank clients: " + bank.getClients().size());
	}
	
	static void getAccountsNumber(Bank bank) {
		int accountsNum = 0;
		for(Client client : bank.getClients()) {
			accountsNum += client.getAccountsList().size();
		}
		System.out.println("Number of accounts: " + accountsNum);
	}
	
	static void getClientsSorted(Bank bank) {
		//Clients are stored in TreeSet, so they are already sorted
		for(Client client : bank.getClients()) {
			System.out.println(client);
		}
	}
	
	static void getBankCreditSum(Bank bank) {
		int sum = 0;
		for(Client client : bank.getClients()) {
			for(Account account : client.getAccountsList()) {
				sum += account.decimalValue();
			}
		}
		System.out.println("Credit sum: " + sum);
	}
	
	static void getClientsByCity(Bank bank) {
		for(String city : bank.getCities().keySet()) {
			System.out.println(city + " => " + bank.getCities().get(city));
		}
	}
	
	public static String getFullReport(Bank bank) {
		int sum = 0;
		for(Client client : bank.getClients()) {
			for(Account account : client.getAccountsList()) {
				sum += account.decimalValue();
			}
		}
		
		int accountsNum = 0;
		for(Client client : bank.getClients()) {
			accountsNum += client.getAccountsList().size();
		}
		
		return "Amount of clients: " + bank.getClients().size() + ", Amount of accounts: " + accountsNum + ", total balance: " + sum;
	}
	
}
