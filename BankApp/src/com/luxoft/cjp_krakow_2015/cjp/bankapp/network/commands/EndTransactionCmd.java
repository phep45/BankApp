package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands;

public class EndTransactionCmd implements NetCommand {

	private static final long serialVersionUID = 8395604587268751321L;

	@Override
	public void printInfo() {
		System.out.println("End transaction");
	}

}
