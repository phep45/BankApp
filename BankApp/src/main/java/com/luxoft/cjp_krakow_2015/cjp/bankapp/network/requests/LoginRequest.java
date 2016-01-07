package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests;

public class LoginRequest implements Request {

	private static final long serialVersionUID = -4763014847861430529L;

	private String login;
	
	public LoginRequest(String login) {
		this.login = login;
	}
	
	public String getLogin() {
		return new String(login);
	}
	
	@Override
	public void printInfo() {
		System.out.println("Login");
	}

}
