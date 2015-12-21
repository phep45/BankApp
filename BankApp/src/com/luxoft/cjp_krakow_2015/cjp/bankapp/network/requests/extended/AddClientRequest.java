package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.extended;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.Request;

public class AddClientRequest implements Request {

	private static final long serialVersionUID = 4742373058979678139L;
	
	private final Client client;
	
	public AddClientRequest(Client client) {
		this.client = client;
	}
	
	public Client getClient() {
		return client;
	}

	@Override
	public void printInfo() {
		System.out.println("Add client");
	}

}
