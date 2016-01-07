package com.luxoft.cjp_krakow_2015.cjp.bankapp.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.AccountDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankNotFoundException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.DAOException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.SavingAccount;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.EmailException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.InvalidClientNameException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.TestService;

public class BankDAOTest {

	private final String BANK_NAME = "Bankkk";
	
	Bank bank;
	
	@Before
	public void initBank() throws InvalidClientNameException, EmailException, ClientExistsException {
		bank = new Bank();
		bank.setName(BANK_NAME);
		bank.setId(100);
		Client client = new Client(100, "A B", "f", "a@b.c", 100f, 100);
		
		client.addAccount(new SavingAccount());
		bank.addClient(client);
	}
	
	@Test
	public void test() throws DAOException, BankNotFoundException, ClientExistsException {
		BankDAOImpl bankDao = new BankDAOImpl();
		ClientDAOImpl clientDao = new ClientDAOImpl();
		AccountDAOImpl accountDao = new AccountDAOImpl();
		bankDao.save(bank);
		
		Bank bank2 = bankDao.getBankByName(BANK_NAME);
		for(Client client : clientDao.getAllClients(bank2)) {
			for(Account account : accountDao.getClientAccounts(client.getID())) {
				System.out.println(client);
				client.addAccount(account);
			}
			bank2.addClient(client);
		}
		
		assertTrue(TestService.isEquals(bank, bank2));
	}
	
}
