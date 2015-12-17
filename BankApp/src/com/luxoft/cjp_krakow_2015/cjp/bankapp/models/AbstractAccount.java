package com.luxoft.cjp_krakow_2015.cjp.bankapp.models;

import java.io.Serializable;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.BankException;

public abstract class AbstractAccount implements Account, Serializable {
	private static final long serialVersionUID = 7481480330145179010L;
	protected float balance;
	private static int idGenerator;
	protected int id;
	
	public AbstractAccount() {
		balance = 0;
		id = idGenerator++;
	}
	
	public float getBalance() {
		return balance;
	}
	
	public void deposit(float amount) {
		balance += amount;
	}
	
	public void withdraw(float amount) throws BankException {
		balance -= amount;
	}
	
	public void printReport() {
		System.out.println("Account id:" + id + "Balance of " + this.getClass() + ": " + balance);
	}
	
	public float decimalValue() {
		float out = balance;
		out *= 100;
		out = Math.round(out);
		out /= 100;
		return out;
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return "AbstractAccount [balance=" + balance + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAccount other = (AbstractAccount) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
