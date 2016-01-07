package com.luxoft.cjp_krakow_2015.cjp.bankapp.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.CheckingAccount;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Gender;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.SavingAccount;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.BankException;

public class ClientTest {

	private Client client;
	
	private final String SAVING = "saving";
	private final String CHECKING = "checking";
	private final double DELTA = 0.001;
	
	@Before
	public void setUp() throws Exception {
		client = new Client("name surname", Gender.FEMALE);
	}
	
	@Test
	public void testInvalidAccountType() {
		Account account = client.createAccount("aaa");
		assertNull(account);
	}
	
	@Test
	public void testCreateSavingAccount() {
		Account account = client.createAccount(SAVING);
		assertTrue(account.getClass().equals(SavingAccount.class));
	}
	
	@Test
	public void testCreateCheckingAccount() {
		Account account = client.createAccount(CHECKING);
		assertTrue(account.getClass().equals(CheckingAccount.class));
	}
	
	@Test
	public void testDeposit() {
		Account account = client.createAccount(SAVING);
		float moneyToDeposit = 1000;
		
		client.setActiveAccount(account);
		client.deposit(moneyToDeposit);
		assertEquals(moneyToDeposit, client.getBalance(), DELTA);
	}
	
	@Test
	public void testWithdraw() throws BankException {
		Account account = client.createAccount(SAVING);
		float moneyToDeposit = 1000;
		float moneyToWithdraw = 340;
		
		client.setActiveAccount(account);
		client.deposit(moneyToDeposit);
		client.withdraw(moneyToWithdraw);
		
		assertEquals(moneyToDeposit - moneyToWithdraw, client.getBalance(), DELTA);
	}
	
	@Test
	public void testCreateLotOfAccounts() {
		int numberOfAccounts = 1000;
		
		for(int i = 0; i < numberOfAccounts; i++) {
			if(i % 2 == 0) {
				client.createAccount(SAVING);
			}
			else {
				client.createAccount(CHECKING);
			}
		}
		
		assertEquals(numberOfAccounts, client.getAccountsList().size());
	}

}
