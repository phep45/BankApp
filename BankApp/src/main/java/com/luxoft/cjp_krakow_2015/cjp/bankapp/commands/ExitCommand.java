package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.IOException;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class ExitCommand implements Command {

	@Override
	public void execute() throws IOException {
		BankCommander.isRunning = false;
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Exit");
	}

}
