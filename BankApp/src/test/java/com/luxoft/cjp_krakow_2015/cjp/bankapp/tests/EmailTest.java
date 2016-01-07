package com.luxoft.cjp_krakow_2015.cjp.bankapp.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Email;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.EmailException;

public class EmailTest {

	private final String PROPEREMAIL = "abcd@mail.com";
	private final String INVALID = "abcd";
	
	@Test
	public void testCreateEmail() throws EmailException {
		Email email = new Email(PROPEREMAIL);
		assertEquals(PROPEREMAIL, email.toString());
	}
	
	@Test(expected=EmailException.class)
	public void testCreateEmailWithInvalidInput() throws EmailException {
		new Email(INVALID);
	}

}
