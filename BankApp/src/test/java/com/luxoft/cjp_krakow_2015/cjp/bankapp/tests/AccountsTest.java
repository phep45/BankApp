package com.luxoft.cjp_krakow_2015.cjp.bankapp.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.CheckingAccount;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.SavingAccount;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.BankException;

public class AccountsTest {

	private final double DELTA = 0.001;
	private float moneyToDeposit = 1000;
	private float moneyToWithdraw = 450;
	private float overdraft = 450;
	
	@Test
	public void testSavingAccount() throws BankException {
		float moneyToDeposit = 1000;
		float moneyToWithdraw = 450;
		
		Account account = new SavingAccount();
		
		account.deposit(moneyToDeposit);
		account.withdraw(moneyToWithdraw);
		
		assertEquals(moneyToDeposit - moneyToWithdraw, account.getBalance(), DELTA);
	}
	
	@Test(expected=BankException.class)
	public void testSavingAccountWithdrawOverLimit() throws BankException {
		Account account = new SavingAccount();
		
		account.deposit(moneyToDeposit);
		account.withdraw(moneyToWithdraw * 3);
		
	}
	
	@Test
	public void testCheckingAccount() throws BankException {
		Account account = new CheckingAccount();
		
		account.deposit(moneyToDeposit);
		account.withdraw(moneyToWithdraw);
		
		assertEquals(moneyToDeposit - moneyToWithdraw, account.getBalance(), DELTA);
	}
	
	@Test
	public void testCheckingAccountWithdrawBelowLimit() throws BankException {
		Account account = new CheckingAccount();
		((CheckingAccount) account).setOverdraft(overdraft);
		
		account.deposit(moneyToDeposit);
		account.withdraw(moneyToWithdraw);
		
		assertEquals(moneyToDeposit - moneyToWithdraw, account.getBalance(), DELTA);
	}
	
	@Test(expected=BankException.class)
	public void testCheckingAccountWithdrawOverLimit() throws BankException {
		Account account = new CheckingAccount();
		((CheckingAccount) account).setOverdraft(overdraft);
		
		account.deposit(moneyToDeposit);
		account.withdraw(moneyToWithdraw * 10);
		
	}
	
	@Test
	public void testDecimalValue() {
		float moneyToDeposit = 1.43f - 0.67f;
		Account account = new SavingAccount();
		account.deposit(moneyToDeposit);
		assertEquals(0.76f, account.decimalValue(), 0);
	}

}
