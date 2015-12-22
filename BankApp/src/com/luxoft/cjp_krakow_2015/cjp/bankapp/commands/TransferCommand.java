package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.AccountDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.AccountDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.DAOException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.BankException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class TransferCommand implements Command {

	@Override
	public void execute() throws IOException {
		System.out.println("Select client");
		BankCommander.bank.printReport();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			int answer = Integer.parseInt(reader.readLine());
			Client client = BankCommander.bank.getClient(answer);
			if(client == null) {
				System.err.println("Client " + answer + " doesn't exist");
				return;
			}
			System.out.println("Select account");
			int id = Integer.parseInt(reader.readLine());
			System.out.println("Ammount to transfer");
			float amount = (float) Double.parseDouble(reader.readLine());
			BankCommander.currentClient.withdraw(amount);
			Account account = client.searchAccount(id);
			if(account == null) {
				System.err.println("Account " + id + " doesn't exist on client " + client.getID());
				return;
			}
			account.deposit(amount);
			ClientDAO clientDAO = new ClientDAOImpl();
			AccountDAO accountDAO = new AccountDAOImpl();
			clientDAO.save(BankCommander.currentClient);
			accountDAO.save(BankCommander.currentClient.getActiveAccount());
			accountDAO.save(account);
		} catch (BankException e) {
			System.err.println(e.getMessage());
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Transfer");
	}

}
