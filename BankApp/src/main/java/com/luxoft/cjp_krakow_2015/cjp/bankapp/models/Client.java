package com.luxoft.cjp_krakow_2015.cjp.bankapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.NoDB;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.BankException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.EmailException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.InvalidClientNameException;

public class Client implements Comparable<Client>, Serializable{

	private static final long serialVersionUID = 28313960506463671L;
	
	private String name;
	private Gender gender;
	private Email email;
	private String city;
	@NoDB private List<Account> accounts = new ArrayList<Account>();
	private Account activeAccount;
	private float initialOverdraft;
	@NoDB private int bankId;
	
	private static int idGenerator = 0;
	private int id;
	
	private Client() {
//		accounts = new ArrayList<Account>();
		id = idGenerator++;
	}
	
	public Client(String name) throws InvalidClientNameException {
		this();
		Pattern pattern = Pattern.compile("(^\\w+ \\w+$)");
		Matcher matcher = pattern.matcher(name);
		
		if(matcher.find()) {
		    this.name = name;
		} else {
			throw new InvalidClientNameException("Name must consist of firstname and surname");
		}
	}
	
	public Client(String name, Gender gender) throws InvalidClientNameException {
		this(name);
		this.gender = gender;
	}
	
	public Client(String name, Gender gender, String email) throws InvalidClientNameException, EmailException {
		this(name, gender);
		this.email = new Email(email);
	}
	
	public Client(String name, Gender gender, String email, String city) throws InvalidClientNameException, EmailException {
		this(name, gender, email);
		this.email = new Email(email);
		this.city = city;
	}
	
	public Client(String name, Gender gender, String email, String city, float initialOverdraft) throws InvalidClientNameException, EmailException {
		this(name, gender, email, city);
		this.initialOverdraft = initialOverdraft;
	}
	
	/**
	 * 
	 * Constructor for databases purposes.
	 * 
	 * @param id
	 * @param name
	 * @param gender
	 * @param email
	 * @param initialOverdraft
	 * @param bankId
	 * @throws EmailException
	 */
	public Client(int id, String name, String gender, String email, float initialOverdraft, int bankId) throws EmailException {
		this.id = id;
		this.name = name;
		this.email = new Email(email);
		this.initialOverdraft = initialOverdraft;
		this.bankId = bankId;
		this.city = "";
		if(gender.toLowerCase().equals("m")) {
			this.gender = Gender.MALE;
		}
		else if(gender.toLowerCase().equals("f")) {
			this.gender = Gender.FEMALE;
		}
	}
	
	public void setActiveAccount(Account activeAccount) {
		this.activeAccount = activeAccount;
	}
	
	public float getBalance() {
		return activeAccount.getBalance();
	}
	
	public List<Account> getAccountsList() {
		return Collections.unmodifiableList(accounts);
	}
	
	public void deposit(float amount) {
		if(activeAccount != null) activeAccount.deposit(amount);
	}
	
	public void withdraw(float amount) throws BankException {
		if(activeAccount != null) activeAccount.withdraw(amount);
	}
	
	public Account createAccount(String accountType) {
		Account account;
		if(accountType.toLowerCase().equals("saving")) {
			account = new SavingAccount();
		}
		else if(accountType.toLowerCase().equals("checking")) {
			account = new CheckingAccount(initialOverdraft);
		}
		else {
			System.err.println("unknown account type: " + accountType);
			return null;
		}
		accounts.add(account);
		return account;
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public float getInitialOverdraft() {
		return initialOverdraft;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public int getID() {
		return id;
	}
	
	public String getClientSalutation() {
		return gender.getSalutation() + " " + name;
	}
	
	public Account getActiveAccount() {
		return activeAccount;
	}
	
	public Account searchAccount(int accountID) {
		for(Account account : accounts) {
			if(accountID == account.getID()){
				return account;
			}
		}
		return null;
	}
	
	public String getCity() {
		return city;
	}
	
	public static int getNextId() {
		return idGenerator++;
	}

	@Override
	public String toString() {
		return new StringBuilder("Name: ")
				.append(getClientSalutation())
				.append(", ID:")
				.append(id)
				.append(", Email: ")
				.append(email)
				.append(", City: ")
				.append(city)
				.append(", initial overdraft: ")
				.append(initialOverdraft)
				.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + id;
		result = prime * result + Float.floatToIntBits(initialOverdraft);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gender != other.gender)
			return false;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(initialOverdraft) != Float.floatToIntBits(other.initialOverdraft))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Client other) {
		return name.compareTo(other.name);
	}

	public void parseFeed(Map<String, String> feed) {
		String accountType = feed.get("accounttype");
		Account account;
		if(accountType.equals("c")) {
			account = new CheckingAccount();
		} 
		else if(accountType.equals("s")) {
			account = new SavingAccount();
		}
		else {
			return;
		}
		accounts.add(account);
		account.parseFeed(feed);
		
	}
	
	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
	
	public int getBankId() {
		return bankId;
	}
	
	public Email getEmail() {
		return email;
	}

	public void setEmail(String email) throws EmailException {
		this.email = new Email(email);
	}
	
}
