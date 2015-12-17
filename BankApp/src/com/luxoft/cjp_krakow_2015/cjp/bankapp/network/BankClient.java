package com.luxoft.cjp_krakow_2015.cjp.bankapp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands.LoginCmd;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands.MyAccountsCmd;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands.NetCommand;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands.WithdrawCmd;

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
					sendCommnd(action());
//					sendMessage("");
//					message = "bye";
//					sendMessage("bye");
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

	private void sendCommnd(NetCommand command) {
		try {
			out.writeObject(command);
			out.flush();
			System.out.print("client>> ");
			command.printInfo();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private NetCommand action() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		if(user == null) {
			System.out.println("Username: ");
			try {
				user = reader.readLine();
				return new LoginCmd("user");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("1) My Accounts");
			System.out.println("2) Change active accout");
			System.out.println("3) Withdraw");
			String option = reader.readLine();
			if(option.equals("1")) {
				return new MyAccountsCmd();
			}
			else if(option.equals("2")) {
				
			}
			else if(option.equals("3")) {
				System.out.println("Amount:");
				float amount = (float) Double.parseDouble(reader.readLine());
				return new WithdrawCmd(amount);
			}
		}
		
		return null;
	}

	private void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client>> " + msg);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Username: ");
		try {
			String msg = reader.readLine();

			BankClient client = new BankClient();
			client.setMessage(msg);
			client.run();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}