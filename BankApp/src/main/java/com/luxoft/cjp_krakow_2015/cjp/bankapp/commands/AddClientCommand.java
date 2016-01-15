package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.DAOException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Gender;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.EmailException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.InvalidClientNameException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankCommander;

public class AddClientCommand implements Command {

	private ClientDAO clientDAO;
	
	@Override
	public void execute() throws IOException {

		Pattern pattern;
		Matcher matcher;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter clients name: ");
		String name = reader.readLine();
		pattern = Pattern.compile("([A-Z]\\w+ [A-Z]\\w+)");
		matcher = pattern.matcher(name);
		if(!matcher.find()) {
			System.err.println("Name should consist of firstname and surname with first capital letters");
			execute();
		}
			
		System.out.println("Gender: [M/F]");
		String gender = reader.readLine();
		pattern = Pattern.compile("([mMfF]{1})");
		matcher = pattern.matcher(gender);
		if(!matcher.find()) {
			System.err.println("Write only m/f");
			execute();
		}
		
		System.out.println("Email:");
		String email = reader.readLine();
		pattern = Pattern.compile("(\\w+@\\w+\\.\\w{2,4})");
		matcher = pattern.matcher(email);
		if(!matcher.find()) {
			System.err.println("invalid email");
			execute();
		}
		
		System.out.println("City: ");
		String city = reader.readLine();
		pattern = Pattern.compile("([A-Z]\\w+)");
		matcher = pattern.matcher(city);
		if(!matcher.find()) {
			System.err.println("City name should start with capital letter");
			execute();
		}
		System.out.println("Overdraft: ");
		float overdraft = (float) Double.parseDouble(reader.readLine());
		try {
			if(gender.toLowerCase().equals("male") || gender.toLowerCase().equals("m"))
				BankCommander.bank.addClient(new Client(name, Gender.MALE, email, city, overdraft));
			else if(gender.toLowerCase().equals("female") || gender.toLowerCase().equals("f"))
				BankCommander.bank.addClient(new Client(name, Gender.FEMALE, email, city, overdraft));
			else
				System.err.println("Invalid gender");
			
			clientDAO = new ClientDAOImpl();
			clientDAO.save(new Client(Client.getNextId(), name, gender, email, overdraft, BankCommander.bank.getId()));
			
		} catch(InvalidClientNameException e) {
			System.err.println(e.getMessage());
		} catch(EmailException e) {
			System.err.println(e.getMessage());
		}catch (ClientExistsException e) {
			System.err.println(e.getMessage());
		} catch (DAOException e) {
			System.err.println(e.getMessage());
		}

	}

	@Override
	public void printCommandInfo() {
		System.out.println("Add client");
	}

	public ClientDAO getClientDAO() {
		return clientDAO;
	}

	public void setClientDAO(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

}
