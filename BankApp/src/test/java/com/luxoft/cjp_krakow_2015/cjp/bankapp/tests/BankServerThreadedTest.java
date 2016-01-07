package com.luxoft.cjp_krakow_2015.cjp.bankapp.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.BankServerThreaded;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankApplication;

public class BankServerThreadedTest {

	private final int NMBR_OF_MOCKS = 100;
	private final float AMOUNT = NMBR_OF_MOCKS;
	private final float DELTA = 0.00001f;
	
/*	
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
*/	
	@Test
	public void testWithCallable() throws InterruptedException, IOException, ExecutionException {
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
		
		ExecutorService executor = Executors.newFixedThreadPool(NMBR_OF_MOCKS);
		
		List<Future<Long>> clientsList = new ArrayList<Future<Long>>(NMBR_OF_MOCKS);
		for(int i = 0; i < NMBR_OF_MOCKS; i++) {
			Future<Long> future = executor.submit(new BankClientMock());
			clientsList.add(future);
		}
		
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Long time = 0l;
		for(Future<Long> future : clientsList) {
			time += future.get();
		}
		
		System.out.println("average time: " + time/NMBR_OF_MOCKS + "ms");
		assertEquals(initialBalance - AMOUNT, client.getBalance() - AMOUNT, DELTA);
			
	}

}
