package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands;

public class LoginCmd implements NetCommand {

	private static final long serialVersionUID = -4763014847861430529L;

	private String login;
	
	public LoginCmd(String login) {
		this.login = login;
	}
	
	public String getLogin() {
		return new String(login);
	}
	
	@Override
	public void printInfo() {
		// TODO Auto-generated method stub

	}

}
