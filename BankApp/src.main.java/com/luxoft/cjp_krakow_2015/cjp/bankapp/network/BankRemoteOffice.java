package com.luxoft.cjp_krakow_2015.cjp.bankapp.network;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.EndTransactionRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.LoginRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.Request;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.extended.AddClientRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.extended.FindClientRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.extended.GetStatisticsRequest;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.extended.RemoveClientRequest;

public class BankRemoteOffice extends BankServer {

	public BankRemoteOffice(Bank bank, int port) {
		super(bank,port);
	}
	
	@Override
	public String handleRequest(Request request) {
		
		if(request instanceof AddClientRequest) {
			try {
				activeBank.addClient( ((AddClientRequest)request).getClient() );
			} catch (ClientExistsException e) {
				return "This client already exist";
			}
		}
		else if(request instanceof LoginRequest) {
			if(((LoginRequest)request).getLogin().equals("superuser")) {
				return "Logged in as super user";
			}
			else {
				return "Username incorrect";
			}
		}
		else if(request instanceof FindClientRequest) {
			return activeBank.getClient(((FindClientRequest)request).getName()).toString();
		}
		else if(request instanceof RemoveClientRequest) {
			Client clientToRemove = activeBank.getClient( ((RemoveClientRequest)request).getName() );
			activeBank.removeClient(clientToRemove);
			return "Client removed";
		}
		else if(request instanceof GetStatisticsRequest) {
			return new BankInfo(activeBank).getStatistics();
		}
		else if(request instanceof EndTransactionRequest) {
			return "bye";
		}
		else {
			return "invalid request";
		}
		
		return "invalid request";
	}
	
}
