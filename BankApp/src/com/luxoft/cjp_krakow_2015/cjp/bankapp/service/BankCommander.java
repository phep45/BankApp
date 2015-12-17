package com.luxoft.cjp_krakow_2015.cjp.bankapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.AddClientCommand;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.ChangeActiveAccount;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.Command;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.CreateAccount;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.DepositCommand;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.FindClientCommand;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.GetAccountsCommand;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.LoadBankStateCommand;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.LoadClient;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.LoadFromFileCommand;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.SaveBankStateCommand;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.SaveClientCommand;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.TransferCommand;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.commands.WithdrawCommand;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;

public class BankCommander {
	public static Bank bank = new Bank();
	public static Client currentClient;
	
	private static boolean isRunning = true;
	static Map<Integer, Command> commandsMap;
	
	static Command[] commands = {
			new FindClientCommand(),
			new GetAccountsCommand(),
			new WithdrawCommand(),
			new DepositCommand(),
			new TransferCommand(),
			new AddClientCommand(),
			new CreateAccount(),
			new ChangeActiveAccount(),
			new LoadFromFileCommand(),
			new SaveClientCommand(),
			new LoadClient(),
			new SaveBankStateCommand(),
			new LoadBankStateCommand(),
			new Command() {
				@Override
				public void printCommandInfo() {
					System.out.println("Exit");
				}
				
				@Override
				public void execute() {
					isRunning = false;
				}
			}
			
	};
	
	static {
		commandsMap = new TreeMap<Integer, Command>();
		int idx = 0;
		for(Command cmd : commands) {
			commandsMap.put(idx, cmd);
			idx++;
		}
	}
	
	public void registerCommand(Integer name, Command command) {
		commandsMap.put(name, command);
	}
	
	public void removeCommand(String name) {
		commandsMap.remove(name);
	}
	
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		do {
			System.out.println("----------------------------");
			if(currentClient != null) {	
				System.out.println(currentClient);
				if(currentClient.getActiveAccount() != null)
					System.out.println(currentClient.getActiveAccount());
			} else {
				System.out.println("No client selected");
			}
			System.out.println("----------------------------");
			
			for(Integer cmdIdx : commandsMap.keySet()) {
				System.out.print(cmdIdx+") ");
				commandsMap.get(cmdIdx).printCommandInfo();
			}
			
			try {
				String commandString = reader.readLine();
				if(Integer.parseInt(commandString) >= 0 || Integer.parseInt(commandString) < commandsMap.size()) {
					commandsMap.get(Integer.parseInt(commandString)).execute();
				}
				else {
					throw new ArrayIndexOutOfBoundsException();
				}
            } catch(IOException e) {
            	System.err.println(e.getMessage());
            } catch(NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            	System.err.println("Use numbers 0 - " + (commands.length - 1));
            } 
            
		} while(isRunning);
	}
	
}
