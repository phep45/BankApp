package com.luxoft.cjp_krakow_2015.cjp.bankapp.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.BankServerThreaded;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankApplication;

public class BankServerThreadedTest {

	private final int NMBR_OF_MOCKS = 1000;
	private final float AMOUNT = NMBR_OF_MOCKS;
	private final float DELTA = 0.00001f;
	
	
	@Test
	public void test() throws InterruptedException, IOException {
		BankApplication bankApp = new BankApplication();
		bankApp.initialize();
		bankApp.modify();
		
		Bank bank = bankApp.getBank();
		Client client = bank.getClient("Ala MaKota");
		Account account = client.searchAccount(2);
		float initialBalance = account.getBalance();

		client.setActiveAccount(account);
		client.deposit(AMOUNT);

		BankServerThreaded bst = new BankServerThreaded(bank, 2004);
		Thread server = new Thread(bst);
		server.start();
		
		
		List<Thread> mocks = new ArrayList<Thread>();
		for(int i = 0; i < NMBR_OF_MOCKS; i++) {
			mocks.add(new Thread(new BankClientMock()));
		}
		for(int i = 0; i < NMBR_OF_MOCKS; i++) {
			mocks.get(i).start();
		}
		for(int i = 0; i < NMBR_OF_MOCKS; i++) {
			mocks.get(i).join();
		}
		
//		server.join();
		
		assertEquals(initialBalance - AMOUNT, client.getBalance() - AMOUNT, DELTA);
			
	}

}
