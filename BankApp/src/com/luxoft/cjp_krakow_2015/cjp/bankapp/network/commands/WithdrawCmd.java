package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands;

public class WithdrawCmd implements NetCommand {

	private static final long serialVersionUID = 3986581540656030622L;

	private final float amount;
	
	public WithdrawCmd(float amount) {
		this.amount = amount;
	}
	
	public float getAmount() {
		return amount;
	}
	
	@Override
	public void printInfo() {
		System.out.println("Withdraw");
	}

}
