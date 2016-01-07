package com.luxoft.cjp_krakow_2015.cjp.bankapp.tests;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.concurrent.Callable;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.BankClient;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.ChangeAccountRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.EndTransactionRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.LoginRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.WithdrawRequest;

public class BankClientMock extends BankClient implements Callable<Long>{
	
	private final float amountToWithdraw = 1f;
	private final int accountID = 2;
	private final String username = "Ala MaKota";
	
	public BankClientMock() {
		super(2004);
	}
	
	/*
	@Override
	public void run() {
		try {
			requestSocket = new Socket(SERVER, 2004);
			System.out.println("Connected to localhost in port 2004");
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			//login to bank and choose account
			try {
				
				message = (String) in.readObject();
				sendRequest(new LoginRequest(username));
				message = (String) in.readObject();
				sendRequest(new ChangeAccountRequest(accountID));
				message = (String) in.readObject();
				sendRequest(new WithdrawRequest(amountToWithdraw));
				System.out.println(this + " completed");
				sendRequest(new EndTransactionRequest());
				message = (String) in.readObject();
			} catch (ClassNotFoundException e1) {
				
			}
			
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
	*/

	@Override
	public Long call() throws Exception {
		Calendar timer = Calendar.getInstance();
		
		
		try {
			requestSocket = new Socket(SERVER, 2004);
//			System.out.println("Connected to localhost in port 2004");
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			//login to bank and choose account
			try {
				
				message = (String) in.readObject();
				sendRequest(new LoginRequest(username));
				message = (String) in.readObject();
				sendRequest(new ChangeAccountRequest(accountID));
				message = (String) in.readObject();
				sendRequest(new WithdrawRequest(amountToWithdraw));
//				System.out.println(this + " completed");
				sendRequest(new EndTransactionRequest());
				message = (String) in.readObject();
			} catch (ClassNotFoundException e1) {
				
			}
			
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
		
		long out = timer.getTimeInMillis();
		timer = Calendar.getInstance();
		out = timer.getTimeInMillis() - out;
		
		return out;
	}
	
}
