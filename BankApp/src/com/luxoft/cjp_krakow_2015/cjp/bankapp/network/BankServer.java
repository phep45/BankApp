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
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.ChangeAccountRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.EndTransactionRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.LoginRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.MyAccountRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.Request;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.WithdrawRequest;

public class BankServer {

	private ServerSocket providerSocket;
	private Socket connection = null;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message = "";
	private Request request;
	
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
					
					request = (Request) in.readObject();
					request.printInfo();
					sendMessage(handleRequest(request));
					
					if(message.equals("bye")) {
						sendMessage("bye");
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
	
	private String handleRequest(Request request) {
		//Login request
		if(request.getClass() == LoginRequest.class) {
			System.out.println("++++++++");
			System.out.println(activeBank.getClient(((LoginRequest) request).getLogin()));
			System.out.println("++++++++");
			if(activeBank.getClient(((LoginRequest) request).getLogin()) != null) {
				loggedClient = activeBank.getClient(((LoginRequest) request).getLogin());
				return "Logged in";
			}
			else {
				return "Username incorrect";
			}
		}
		//My accounts request
		else if(request.getClass() == MyAccountRequest.class) {
			StringBuilder response = new StringBuilder();
			List<Account> accounts = loggedClient.getAccountsList();
			for(Account account : accounts) {
				response.append(account.toString()).append("\n");
			}
			return response.toString();
			
		}
		//Withdrawn request
		else if(request.getClass() == WithdrawRequest.class) {
			if(loggedClient.getActiveAccount() != null) {
				try {
					loggedClient.withdraw(((WithdrawRequest)request).getAmount());
				} catch (BankException e) {
					return e.getMessage();
				}
				return "Withdrawn: " + ((WithdrawRequest)request).getAmount();
			}
			else 
				return "No account set as active";
		}
		//Change account request
		else if(request.getClass() == ChangeAccountRequest.class) {
			Account activeAccount = loggedClient.searchAccount(((ChangeAccountRequest)request).getAccountID());
			loggedClient.setActiveAccount(activeAccount);
			return "Acitve account is: " + activeAccount;
		}
		//End transaction request
		else if(request.getClass() == EndTransactionRequest.class) {
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
