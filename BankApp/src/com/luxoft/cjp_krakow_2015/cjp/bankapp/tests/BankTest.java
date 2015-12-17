package com.luxoft.cjp_krakow_2015.cjp.bankapp.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Gender;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.InvalidClientNameException;

public class BankTest {

	private Bank bank;
	
	@Before
	public void setUp() {
		bank = new Bank();
	}
	
	@Test
	public void testAddOneCliet() throws InvalidClientNameException, ClientExistsException {
		bank.addClient(new Client("name", Gender.FEMALE));
		assertEquals(1, bank.getClients().size());
	}
	
	@Test
	public void testAddLotOfCLients() throws InvalidClientNameException, ClientExistsException {
		int numberOfClients = 10000;
		for(int i = 0; i < numberOfClients; i++) {
			bank.addClient(new Client("name"+i, Gender.FEMALE));
		}
		assertEquals(numberOfClients, bank.getClients().size());
	}

}
