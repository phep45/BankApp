package com.luxoft.cjp_krakow_2015.cjp.bankapp.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;

public class BankServerThreaded implements Runnable {
	
	private int port;
	private ServerSocket server;
	
	private static AtomicInteger connectionsCounter = new AtomicInteger();
	
	private Bank bank;
	
	private int poolSize = 128;
	private ExecutorService pool = Executors.newFixedThreadPool(poolSize);
	
	private boolean isRunning = true;
	
	public BankServerThreaded(Bank bank, int port) throws IOException {
		this.port = port;
		this.bank = bank;
		server = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		pool.execute(new BankServerMonitor());
		while(isRunning) {
			Socket clientSocket;
			try {
				clientSocket = server.accept();
				pool.execute(new ServerThread(clientSocket, bank, port));
				
				//only for tests
				if(connectionsCounter.get() == 0){
					System.err.println("end of work");
					isRunning = false;
				}
			} catch (IOException e) {
			}
		}
	}
	
	static int getCounter() {
		return connectionsCounter.get();
	}
	
	static void incrementCounter(){
		connectionsCounter.getAndIncrement();
	}
	
	static void decrementCounter() {
		connectionsCounter.getAndDecrement();
	}
}
