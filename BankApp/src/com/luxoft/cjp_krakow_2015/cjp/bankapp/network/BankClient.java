package com.luxoft.cjp_krakow_2015.cjp.bankapp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.ChangeAccountRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.EndTransactionRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.LoginRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.MyAccountRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.Request;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.WithdrawRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.extended.AddClientRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.extended.FindClientRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.extended.GetStatisticsRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.extended.RemoveClientRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankServiceImpl;

public class BankClient {

	protected Socket requestSocket;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	protected String message;
	protected static final String SERVER = "localhost";
	protected final int port;
	protected String user = null;
	
	protected BankServiceImpl bankService = new BankServiceImpl();
	
	public BankClient(int port) {
		this.port = port;
	}
	
	public void run() {
		try {
			requestSocket = new Socket(SERVER, port);
			System.out.println("Connected to localhost on port " + port);
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			do {
				
				try {
					message = (String) in.readObject();
					System.out.println("server>> " + message);
					if(message.equals("Username incorrect")){
						user = null;
					}
					sendRequest(action());
					
				} catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
				
			} while(!message.equals("bye"));
		} catch(UnknownHostException e) {
			System.err.println("You are trying to connect to unknown host");
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void sendRequest(Request request) {
		try {
			out.writeObject(request);
			out.flush();
			request.printInfo();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private Request action() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		if(user == null) {
			System.out.println("Username: ");
			try {
				user = reader.readLine();
				return new LoginRequest(user);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//superuser
		else if(user.equals("superuser")) {
			System.out.println("1) Add client");
			System.out.println("2) Remove client");
			System.out.println("3) Find client");
			System.out.println("4) Get statistics");
			System.out.println("5) End transaction");
			String option = reader.readLine();
			if(option.equals("1")) {
				return new AddClientRequest(bankService.createClient());
			}
			else if(option.equals("2")) {
				System.out.println("Client name: ");
				String name = reader.readLine();
				return new RemoveClientRequest(name);
			}
			else if(option.equals("3")) {
				System.out.println("Client name: ");
				String name = reader.readLine();
				return new FindClientRequest(name);
			}
			else if(option.equals("4")) {
				return new GetStatisticsRequest();
			}
			else if(option.equals("5")) {
				return new EndTransactionRequest();
			}
			else {
				action();
			}
		}
		else {
			System.out.println("1) My Accounts");
			System.out.println("2) Change active accout");
			System.out.println("3) Withdraw");
			System.out.println("4) End transaction");
			String option = reader.readLine();
			if(option.equals("1")) {
				return new MyAccountRequest();
			}
			else if(option.equals("2")) {
				System.out.println("Select account by ID:");
				String id = reader.readLine();
				return new ChangeAccountRequest(Integer.parseInt(id));
			}
			else if(option.equals("3")) {
				System.out.println("Amount:");
				float amount = (float) Double.parseDouble(reader.readLine());
				return new WithdrawRequest(amount);
			}
			else if(option.equals("4")) {
				System.out.println("Transaction ended");
				message = "bye";
				return new EndTransactionRequest();
			}
			else {
				action();
			}
		}
		
		return null;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static void main(String[] args) {
		System.out.println("1 - ATM mode");
		System.out.println("2 - Remote office mode");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String option = "";
		try {
			option = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int port = 2004;
		if(Integer.parseInt(option) == 1)
			port = 2004;
		else if(Integer.parseInt(option) == 2)
			port = 2005;
		
		System.out.println("connecting on port " + port);
		BankClient bankClient = new BankClient(port);
		bankClient.run();
	}
}
