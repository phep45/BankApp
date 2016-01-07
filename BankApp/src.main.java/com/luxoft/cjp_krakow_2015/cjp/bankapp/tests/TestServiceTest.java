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
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.TestService;

public class TestServiceTest {

	Bank bank1, bank2;
	Client client;
	
	@Before
	public void initBanks() throws InvalidClientNameException, ClientExistsException, EmailException {
		client = new Client("Ala D", Gender.FEMALE, "a@s.p", "kk", 100f);
		
		bank1 = new Bank();
		bank1.setId(100);
		bank1.setName("Bank");
		bank1.addClient(client);
		
		bank2 = new Bank();
		bank2.setId(200);
		bank2.setName("Bank");
		bank2.addClient(client);
	}
	
	@Test
	public void test() {
		assertTrue(TestService.isEquals(bank1, bank2));
	}

}
