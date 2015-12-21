package com.luxoft.cjp_krakow_2015.cjp.bankapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Gender;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.EmailException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.InvalidClientNameException;

public class BankServiceImpl implements BankService {

	@Override
	public void addClient(Bank bank, Client client) throws ClientExistsException {
		bank.addClient(client);
	}

	@Override
	public void removeClient(Bank bank, Client client) {
		bank.removeClient(client);
	}

	@Override
	public void addAccount(Client client, Account account) {
		client.addAccount(account);
	}

	@Override
	public void setActiveAccount(Client client, Account account) {
		client.setActiveAccount(account);
	}

	@Override
	public Client getClient(Bank bank, String clientName) {
		return bank.getClient(clientName);
	}

	@Override
	public Client createClient() {
		Pattern pattern;
		Matcher matcher;

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.println("Enter clients name: ");
			String name = reader.readLine();
			pattern = Pattern.compile("([A-Z]\\w+ [A-Z]\\w+)");
			matcher = pattern.matcher(name);
			if(!matcher.find()) {
				System.err.println("Name should consist of firstname and surname with first capital letters");
				createClient();
			}

			System.out.println("Gender: [M/F]");
			String gender = reader.readLine();
			pattern = Pattern.compile("([mMfF]{1})");
			matcher = pattern.matcher(gender);
			if(!matcher.find()) {
				System.err.println("Write only m/f");
				createClient();
			}

			System.out.println("Email:");
			String email = reader.readLine();
			pattern = Pattern.compile("(\\w+@\\w+\\.\\w{2,4})");
			matcher = pattern.matcher(email);
			if(!matcher.find()) {
				System.err.println("invalid email");
				createClient();
			}

			System.out.println("City: ");
			String city = reader.readLine();
			pattern = Pattern.compile("([A-Z]\\w+)");
			matcher = pattern.matcher(city);
			if(!matcher.find()) {
				System.err.println("City name should start with capital letter");
				createClient();
			}
			System.out.println("Overdraft: ");
			float overdraft = (float) Double.parseDouble(reader.readLine());


			if(gender.toLowerCase().equals("male") || gender.toLowerCase().equals("m"))
				return new Client(name, Gender.MALE, email, city, overdraft);
			else if(gender.toLowerCase().equals("female") || gender.toLowerCase().equals("f"))
				return new Client(name, Gender.FEMALE, email, city, overdraft);
			else
				System.err.println("Invalid gender");
		} catch(InvalidClientNameException e) {
			System.err.println(e.getMessage());
		} catch(EmailException e) {
			System.err.println(e.getMessage());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
