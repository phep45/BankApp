package com.luxoft.cjp_krakow_2015.cjp.bankapp.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Gender;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.EmailException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.InvalidClientNameException;

public class BankTest {

	private Bank bank;
	
	@Before
	public void setUp() {
		bank = new Bank();
	}
	
	@Test
	public void testAddOneCliet() throws InvalidClientNameException, ClientExistsException, EmailException {
		bank.addClient(new Client("name surname", Gender.FEMALE, "a@b.c", "a", 0));
		assertEquals(1, bank.getClients().size());
	}
	
	@Test
	public void testAddLotOfCLients() throws InvalidClientNameException, ClientExistsException, EmailException {
		int numberOfClients = 10000;
		for(int i = 0; i < numberOfClients; i++) {
			bank.addClient(new Client("name surname"+i, Gender.FEMALE, "a@b.c", "a", 0));
		}
		assertEquals(numberOfClients, bank.getClients().size());
	}

}
