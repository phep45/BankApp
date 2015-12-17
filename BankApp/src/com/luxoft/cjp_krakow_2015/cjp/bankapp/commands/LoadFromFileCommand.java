package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.IOException;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankFeedService;

public class LoadFromFileCommand implements Command {

	@Override
	public void execute() throws IOException {
		
		BankFeedService.setActiveBank(BankCommander.bank);
		BankFeedService.loadFeed("data.feed");
		System.out.println("Data loaded");
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Load from file");
	}

}
