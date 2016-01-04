package com.luxoft.cjp_krakow_2015.cjp.bankapp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;

public class BankFeedService {
	
	private static Bank activeBank;

	public static Bank getActiveBank() {
		return activeBank;
	}

	public static void setActiveBank(Bank activeBank) {
		BankFeedService.activeBank = activeBank;
	}

	public static void loadFeed(String folder) throws IOException {
		if(activeBank == null)
			return;
		int lineNmbr = 1;
		File file = new File(folder);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		while(reader.ready()){
			String[] data = reader.readLine().split(";");
			Map<String, String> dataMap = new HashMap<String, String>();
			for(String s : data) {
				String[] tmp = s.split("=");
				if(tmp.length == 2) {
					dataMap.put(tmp[0], tmp[1]);
				}
				else {
					System.err.println("Data inconsistent for file " + file.getName() + " on line: " + lineNmbr);
					break;
				}
			}
			activeBank.parseFeed(dataMap);
			lineNmbr++;
				
		}
		reader.close();
	}
	
}
