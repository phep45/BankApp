package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class LoadBankStateCommand implements Command {

	@Override
	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Filename: ");
		String filename = reader.readLine();
		FileInputStream fin = new FileInputStream(filename+".dat");
		ObjectInputStream ois = new ObjectInputStream(fin);
		try {
			BankCommander.bank = (Bank) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ois.close();
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Load bank state");
	}

}
