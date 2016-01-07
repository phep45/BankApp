package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class SaveClientCommand implements Command {

	@Override
	public void execute() throws IOException {
		FileOutputStream fout = new FileOutputStream(BankCommander.currentClient.getName()+".dat");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(BankCommander.currentClient);
		oos.close();
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Save client");
	}

}
