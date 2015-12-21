package com.luxoft.cjp_krakow_2015.cjp.bankapp.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.EndTransactionRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.Request;

public class ServerThread extends BankServer implements Runnable {

	private final Socket clientSocket;
	
	public ServerThread(Socket clientSocket) {
		super(new Bank());
		this.clientSocket = clientSocket;
	}
	
	public ServerThread(Socket clientSocket, Bank bank, int port) {
		this(clientSocket);
		this.activeBank = bank;
	}
	
	@Override
	public void run() {
//		BankServerThreaded.incrementCounter();
		
		activeBank.printReport();
		
		try {
			System.out.println("Connection recived from " + clientSocket.getInetAddress().getHostName());
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			sendMessage("Connection successful");
			
			do {
				try {
					
					request = (Request) in.readObject();
					request.printInfo();
					sendMessage(handleRequest(request));
					
				} catch (ClassNotFoundException e) {
					System.err.println("Data recived in unknown format");
				}
			} while(!(request instanceof EndTransactionRequest));

		} catch (IOException e) {
//			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
//				providerSocket.close();
			} catch(IOException e) {
//				e.printStackTrace();
			} finally {
				BankServerThreaded.decrementCounter();
			}
		}
	}

}
