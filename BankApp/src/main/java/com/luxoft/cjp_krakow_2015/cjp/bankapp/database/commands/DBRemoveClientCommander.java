package com.luxoft.cjp_krakow_2015.cjp.bankapp.database.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.Command;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.DAOException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class DBRemoveClientCommander implements Command {

	private ClientDAO clientDAO;// (ClientDAO) BankApplication.context.getBean("clientDAO");
	
	@Override
	public void execute() {
		System.out.println("Client name:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String name = reader.readLine();
			Client client = clientDAO.findClientByName(BankCommander.bank, name);
			clientDAO.remove(client);
		} catch(IOException e) {
			System.err.println(e.getMessage());
		} catch(DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("DB Remove Client");
	}

	public ClientDAO getClientDAO() {
		return clientDAO;
	}

	public void setClientDAO(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}
	
}
