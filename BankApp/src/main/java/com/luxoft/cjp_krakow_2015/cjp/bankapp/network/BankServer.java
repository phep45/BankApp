package com.luxoft.cjp_krakow_2015.cjp.bankapp.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	protected int serverPort;
	
	protected ServerSocket providerSocket;
	protected Socket connection = null;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	protected Request request;
	
	protected Bank activeBank;//= BankCommander.bank;
	protected Client loggedClient;
	
	private Lock lock = new ReentrantLock();
	
	Logger log = Logger.getLogger("clients." + this.getClass().getName());
	
	public BankServer(Bank bank) {
		activeBank = bank;
	}
			
	
	public BankServer(Bank bank, int port) {
		activeBank = bank;
		serverPort = port;
	}
	
	public void run() {
		activeBank.printReport();
		
//		System.out.println("==============");
//		System.out.println("localhost:" + serverPort);
//		System.out.println("==============");
		try {
			providerSocket = new ServerSocket(serverPort, 10);
//			System.out.println("Waiting for connection");
			connection = providerSocket.accept();
//			System.out.println("Connection recived from " + connection.getInetAddress().getHostName());
			log.log(Level.INFO, "Connection recived from " + connection.getInetAddress().getHostName());
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			sendMessage("Connection successful");

			do {
				try {
					
					request = (Request) in.readObject();
//					request.printInfo();
					sendMessage(handleRequest(request));
					
				} catch (ClassNotFoundException e) {
					System.err.println("Data recived in unknown format");
				}
			} while(!(request instanceof EndTransactionRequest));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				providerSocket.close();
				log.log(Level.INFO, "Connection closed");
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected String handleRequest(Request request) {
		//Login request
		if(request.getClass() == LoginRequest.class) {
			log.log(Level.INFO, "Log in request recieved");
			return handleLogin(request);
		}
		//My accounts request
		else if(request.getClass() == MyAccountRequest.class) {
			log.log(Level.INFO, "My accounts request recieved");
			return handleMyAccounts();
			
		}
		//Withdrawn request
		else if(request.getClass() == WithdrawRequest.class) {
			log.log(Level.INFO, "withdraw request recieved");
			return handleWithdraw(request);
		}
		//Change account request
		else if(request.getClass() == ChangeAccountRequest.class) {
			log.log(Level.INFO, "change account request recieved");
			return handleChangeAccount(request);
		}
		//End transaction request
		else if(request.getClass() == EndTransactionRequest.class) {
			log.log(Level.INFO, "end transaction request recieved");
			return "bye";
		}
		
		return "Incorrect request";
	}


	private String handleChangeAccount(Request request) {
		Account activeAccount = loggedClient.searchAccount(((ChangeAccountRequest)request).getAccountID());
		loggedClient.setActiveAccount(activeAccount);
		return "Acitve account is: " + activeAccount;
	}


	private String handleLogin(Request request) {
//		System.out.println("++++++++");
//		System.out.println(activeBank.getClient(((LoginRequest) request).getLogin()));
//		System.out.println("++++++++");
		if(activeBank.getClient(((LoginRequest) request).getLogin()) != null) {
			loggedClient = activeBank.getClient(((LoginRequest) request).getLogin());
			return "Logged in";
		}
		else {
			return "Username incorrect";
		}
	}


	private String handleMyAccounts() {
		StringBuilder response = new StringBuilder();
		List<Account> accounts = loggedClient.getAccountsList();
		for(Account account : accounts) {
			response.append(account.toString()).append("\n");
		}
		return response.toString();
	}


	private String handleWithdraw(Request request) {
		if(loggedClient.getActiveAccount() != null) {
			try {
				lock.lock();
				loggedClient.withdraw(((WithdrawRequest)request).getAmount());
				lock.unlock();
				Thread.yield();
			} catch (BankException e) {
				return e.getMessage();
			}
			return "Withdrawn: " + ((WithdrawRequest)request).getAmount();
		}
		else 
			return "No account set as active";
	}

	protected void sendMessage(final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
//			System.out.println("server>> " + msg);
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
