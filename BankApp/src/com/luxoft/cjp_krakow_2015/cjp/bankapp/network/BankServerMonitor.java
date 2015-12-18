package com.luxoft.cjp_krakow_2015.cjp.bankapp.network;

public class BankServerMonitor implements Runnable {

	@Override
	public void run() {
		while(true) {
			System.out.println("Number of connections: " + BankServerThreaded.getCounter());
			try {
				
				Thread.sleep(10000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
