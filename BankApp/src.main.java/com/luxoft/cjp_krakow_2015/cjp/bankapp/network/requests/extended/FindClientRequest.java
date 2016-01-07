package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.extended;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.Request;

public class FindClientRequest implements Request {

	private static final long serialVersionUID = 6812191825438510890L;

	private final String name;
	
	public FindClientRequest(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public void printInfo() {
		System.out.println("Find client");
	}

}
