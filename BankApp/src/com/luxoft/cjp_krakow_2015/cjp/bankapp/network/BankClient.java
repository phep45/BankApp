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

public class BankClient {

	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message;
	private static final String SERVER = "localhost";
	private String user = null;
	
	public void run() {
		try {
			requestSocket = new Socket(SERVER, 2004);
			System.out.println("Connected to localhost in port 2004");
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			do {
				
				try {
					message = (String) in.readObject();
					System.out.println("server>> " + message);
					
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

	private void sendRequest(Request request) {
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
		BankClient bankClient = new BankClient();
		bankClient.run();
	}
}
