package com.luxoft.cjp_krakow_2015.cjp.bankapp.network;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BankServerMonitor implements Runnable {

	private boolean isRunning = true;
	
	static Logger log = Logger.getLogger(BankServerMonitor.class.getName());
	
	@Override
	public void run() {
		while(isRunning) {
			System.out.println("Number of connections: " + BankServerThreaded.getCounter());
			log.log(Level.INFO, "Number of connections: " + BankServerThreaded.getCounter());
			try {
				
				Thread.sleep(10);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void terminate() {
		isRunning = false;
	}
	
}
