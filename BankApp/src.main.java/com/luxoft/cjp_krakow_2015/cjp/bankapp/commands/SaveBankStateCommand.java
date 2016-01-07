package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class SaveBankStateCommand implements Command {

	@Override
	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Filename: ");
		String filename = reader.readLine();
		FileOutputStream fout = new FileOutputStream(filename+".dat");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(BankCommander.bank);
		oos.close();
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Save bank state");
	}

}
