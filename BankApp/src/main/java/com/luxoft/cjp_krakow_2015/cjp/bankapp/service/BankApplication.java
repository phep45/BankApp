package com.luxoft.cjp_krakow_2015.cjp.bankapp.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.CheckingAccount;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Gender;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.SavingAccount;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.BankException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.BankClient;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.BankRemoteOffice;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.BankServer;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.BankServerThreaded;

public class BankApplication {

//	private static final String LOGGING_PROPERTIES = "./src/main/resources/logger_all.properties";
	private Bank bank;
	private BankServiceImpl bankService;
	
	public Logger logger = Logger.getLogger("exceptions." + this.getClass().getName());

	public BankApplication() {
		bank = new Bank();
		bankService = new BankServiceImpl();
		
	}
	
	public void initialize() {
		try{
			bankService.addClient(bank, new Client("Adam Adamski", Gender.MALE, "adam@mail.pl", "Krakow", 100f));
			bankService.addClient(bank, new Client("Ala MaKota", Gender.FEMALE, "ala@kot.com", "Katowice", 500f));
			bankService.addClient(bank, new Client("Ola Olinska", Gender.FEMALE, "ola@mail.pl", "Katowice", 200f));
		}catch(ClientExistsException e) {
			logger.log(Level.FINE, e.getMessage());
		} catch (Exception e) {
			logger.log(Level.FINE, e.getMessage());
		}
		
		Set<Client> clientsList = bank.getClients();
		for(Client client : clientsList) {
			bankService.addAccount(client, new SavingAccount());
			bankService.addAccount(client, new CheckingAccount(client.getInitialOverdraft()));
		}
	}
	
	public Bank getBank() {
		return bank;
	}

	public void modify() {
		Set<Client> clientsList = bank.getClients();
		Iterator<Client> iterator = clientsList.iterator();
		Client currentClient = iterator.next();
		Account curentAccount = currentClient.getAccountsList().get(0);
		currentClient.setActiveAccount(curentAccount);
		currentClient.deposit(500);
		try {
			currentClient.withdraw(300);
		} catch (BankException e) {
			logger.log(Level.FINE, e.getMessage());
		}
		
		curentAccount = currentClient.getAccountsList().get(1);
		currentClient.setActiveAccount(curentAccount);
		currentClient.deposit(500);
		try {
			currentClient.withdraw(389);
		} catch (BankException e) {
			logger.log(Level.FINE, e.getMessage());
		}
		
		currentClient = iterator.next();
		curentAccount = currentClient.getAccountsList().get(0);
		currentClient.setActiveAccount(curentAccount);
		currentClient.deposit(1500);
		try {
			currentClient.withdraw(900);
		} catch (BankException e) {
			logger.log(Level.FINE, e.getMessage());
		}
		
		curentAccount = currentClient.getAccountsList().get(1);
		currentClient.setActiveAccount(curentAccount);
		((CheckingAccount) curentAccount).setOverdraft(150);
		currentClient.deposit(100);
		try {
			currentClient.withdraw(20);
		} catch (BankException e) {
			logger.log(Level.FINE, e.getMessage());
		} 
	}
	
	public void printBankReport() {
//		bank.printReport();
		BankReport.getAccountsNumber(bank);
		BankReport.getBankCreditSum(bank);
		BankReport.getClientsByCity(bank);
		BankReport.getClientsSorted(bank);
		BankReport.getNumberOfClients(bank);
	}

	public void printBalance() {
		float sum = 0;
		for(Client c : bank.getClients()) {
			for(Account a : c.getAccountsList()) {
				sum += a.decimalValue();
			}
		}
		
		System.out.println("Total bank balance: $" + sum);
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		BankApplication bankApp = new BankApplication();
		Logger.getLogger("db." + bankApp.getClass().getName()).log(Level.ALL, "TEEEEEST");
//		LogManager.getLogManager().readConfiguration(new FileInputStream(LOGGING_PROPERTIES));
		
		if(args.length == 0) {
			bankApp.logger.log(Level.INFO, "Application started in standard mode");
			BankCommander.main(args);
		}
		else if(args[0].equals("-report")) {
			bankApp.logger.log(Level.INFO, "Application started in report mode");
			FileInputStream fin = new FileInputStream(".\\bank.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			bankApp.bank = (Bank) ois.readObject();
			bankApp.printBankReport();
			ois.close();
		}
		else if(args[0].equals("-demo")) {
			bankApp.logger.log(Level.INFO, "Application started in demonstration mode");
			bankApp.initialize();
			bankApp.printBankReport();
			bankApp.modify();
			System.out.println("===========================");
			bankApp.printBankReport();
			FileOutputStream fin = new FileOutputStream(".\\bank.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fin);
			oos.writeObject(bankApp.bank);
			oos.close();
		}
		else if(args[0].equals("-server")) {
			System.out.println("Bank server");
			bankApp.initialize();
			bankApp.modify();
			bankApp.printBankReport();
			BankServer server = new BankServer(bankApp.bank, 2004);
			bankApp.logger.log(Level.INFO, "Application started as server");
			server.run();
		}
		else if(args[0].equals("-remote")) {
			System.out.println("Bank remote office");
			bankApp.initialize();
			bankApp.modify();
			BankRemoteOffice server = new BankRemoteOffice(bankApp.bank, 2005);
			bankApp.logger.log(Level.INFO, "Application started as remote office");
			server.run();
		}
		else if(args[0].equals("-client")) {
			bankApp.logger.log(Level.INFO, "Application started as client");
			BankClient.main(args);
		}
		else if(args[0].equals("-threads")) {
			bankApp.initialize();
			bankApp.modify();
			BankServerThreaded bst = new BankServerThreaded(bankApp.bank, 2004);
			bankApp.logger.log(Level.INFO, "Application started as multithreaded server");
			bst.run();
		}
		else {
			bankApp.logger.log(Level.INFO, "Unknown option");
		}
	}


}
