package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class LoadClient implements Command {

	@Override
	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Client name: ");
		String filename = reader.readLine();
		FileInputStream fin = new FileInputStream(filename+".dat");
		ObjectInputStream ois = new ObjectInputStream(fin);
		try {
			Client client = (Client) ois.readObject();
			BankCommander.bank.addClient(client);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ClientExistsException e) {
			System.err.println(e.getMessage());
		}
		ois.close();
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Load client");
	}

}
