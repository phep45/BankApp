package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.extended;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.Request;

public class RemoveClientRequest implements Request {

	private static final long serialVersionUID = -7819504343670778262L;

	private final String name;
	
	public RemoveClientRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public void printInfo() {
		System.out.println("Remove client");
	}

}
