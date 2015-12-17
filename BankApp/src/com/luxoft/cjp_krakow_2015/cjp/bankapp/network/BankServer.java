package com.luxoft.cjp_krakow_2015.cjp.bankapp.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.BankException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands.ChangeAccountCmd;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands.EndTransactionCmd;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands.LoginCmd;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands.MyAccountsCmd;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands.NetCommand;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands.WithdrawCmd;

public class BankServer {

	private ServerSocket providerSocket;
	private Socket connection = null;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message = "";
	private NetCommand command;
	
	private Bank activeBank;//= BankCommander.bank;
	private Client loggedClient;
	
	public BankServer(Bank bank) {
		activeBank = bank;
	}
	
	public void run() {
		
		activeBank.printReport();
		
		System.out.println();
		System.out.println();
		System.out.println();
		try {
			providerSocket = new ServerSocket(2004, 10);
			System.out.println("Waiting for connection");
			connection = providerSocket.accept();
			System.out.println("Connection recived from " + connection.getInetAddress().getHostName());
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			sendMessage("Connection successful");
			
			do {
				try {
					
					command = (NetCommand) in.readObject();
					command.printInfo();
					sendMessage(handleRequest(command));
					
					if(message.equals("bye")) {
						sendMessage("bye");
						return;
					}
					
				} catch (ClassNotFoundException e) {
					System.err.println("Data recived in unknown format");
				}
			} while(!message.equals("bye"));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				providerSocket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String handleRequest(NetCommand command) {
		//Login command
		if(command.getClass() == LoginCmd.class) {
			System.out.println("++++++++");
			System.out.println(activeBank.getClient(((LoginCmd) command).getLogin()));
			System.out.println("++++++++");
			if(activeBank.getClient(((LoginCmd) command).getLogin()) != null) {
				loggedClient = activeBank.getClient(((LoginCmd) command).getLogin());
				return "Logged in";
			}
			else {
				return "Username incorrect";
			}
		}
		//My accounts command
		else if(command.getClass() == MyAccountsCmd.class) {
			StringBuilder response = new StringBuilder();
			List<Account> accounts = loggedClient.getAccountsList();
			for(Account account : accounts) {
				response.append(account.toString()).append("\n");
			}
			return response.toString();
			
		}
		//Withdrawn command
		else if(command.getClass() == WithdrawCmd.class) {
			if(loggedClient.getActiveAccount() != null) {
				try {
					loggedClient.withdraw(((WithdrawCmd)command).getAmount());
				} catch (BankException e) {
					return e.getMessage();
				}
				return "Withdrawn: " + ((WithdrawCmd)command).getAmount();
			}
			else 
				return "No account set as active";
		}
		//Change account command
		else if(command.getClass() == ChangeAccountCmd.class) {
			Account activeAccount = loggedClient.searchAccount(((ChangeAccountCmd)command).getAccountID());
			loggedClient.setActiveAccount(activeAccount);
			return "Acitve account is: " + activeAccount;
		}
		//End transaction command
		else if(command.getClass() == EndTransactionCmd.class) {
			message = "bye";
			return message;
		}
		
		return "Incorrect command";
	}

	private void sendMessage(final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("server>> " + msg);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setActiveBank(Bank activeBank) {
		this.activeBank = activeBank;
	}
	
	public static void main(String[] args) {
		BankServer server = new BankServer(new Bank());
		server.run();
	}
	
}
