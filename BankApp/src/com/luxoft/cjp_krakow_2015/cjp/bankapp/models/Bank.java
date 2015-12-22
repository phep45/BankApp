package com.luxoft.cjp_krakow_2015.cjp.bankapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.ClientExistsException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.EmailException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.InvalidClientNameException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.listeners.ClientRegistrationListener;

public class Bank implements Report, Serializable{
	
	private static final long serialVersionUID = -2449631634399254092L;
	
	private String name;
	private int id;
	
	private Set<Client> clientsSet;
	private List<ClientRegistrationListener> eventListeners;
	private Map<String, Set<Client>> cities;
	private Map<String, Client> clientsMap;
	
	public Bank() {
		clientsSet = new TreeSet<Client>();
		cities = new TreeMap<String, Set<Client>>();
		clientsMap = new HashMap<String, Client>();
		
		eventListeners = new ArrayList<ClientRegistrationListener>();
		
		registerEvent(new PrintClientListener());
		registerEvent(new EmailNotificationListener());
		registerEvent(new DebugListener());
	}
	
	public Bank(String name) {
		this();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	class PrintClientListener implements ClientRegistrationListener, Serializable {
		private static final long serialVersionUID = -64836434897157030L;

		@Override
		public void onClientAdded(Client client) {
			System.out.println(client.toString());
			clientsMap.put(client.getName(), client);
		}
	}
	
	class EmailNotificationListener implements ClientRegistrationListener, Serializable {
		private static final long serialVersionUID = 8135160452909267352L;

		@Override
		public void onClientAdded(Client client) {
			System.out.println("Notification email for client " + client.getName() + " to be sent");
		}
	}
	
	class DebugListener implements ClientRegistrationListener, Serializable {
		private static final long serialVersionUID = -8062218719108457162L;

		@Override
		public void onClientAdded(Client client) {
			System.out.println(client.getName() + " " + new GregorianCalendar().getTime());
		}
	}
	
	public Set<Client> getClients() {
		return Collections.unmodifiableSet(clientsSet);
	}
	
	public Client getClient(String name) {
		for(Client client : clientsSet)
			if(client.getName().equals(name))
				return client;
		return null;
	}
	
	public Client getClient(int id) {
		for(Client client : clientsSet)
			if(client.getID() == id)
				return client;
		return null;
	}
	
	private void registerEvent(ClientRegistrationListener listner) {
		eventListeners.add(listner);
	}
	
	public void addClient(Client client) throws ClientExistsException {
		if(getClient(client.getName()) != null)
			throw new ClientExistsException("this client already exists");
		clientsSet.add(client);
		System.out.println(client.getCity());
		if(!cities.containsKey(client.getCity())) {
			cities.put(client.getCity(), new TreeSet<Client>());
			cities.get(client.getCity()).add(client);
		}
		else {
			cities.get(client.getCity()).add(client);
		}
		for(ClientRegistrationListener li : eventListeners) {
			li.onClientAdded(client);
		}
		
	}
	
	public void removeClient(Client client) {
		clientsSet.remove(client);
	}
	
	public Map<String, Set<Client>> getCities() {
		return Collections.unmodifiableMap(cities);
	}
	
	public void parseFeed(Map<String, String> feed) {
		if(!checkFeedConsistency(feed)){
			System.err.println("Feed inconsistent");
			System.err.println("Feed format should be: ");
			System.err.println("accounttype=c|s;balance=X;overdraft=X;name=X X;email=X@X.X;gender=m|f;");
			return;
		}
		String name = feed.get("name");
		try {
			Client client;
			if(feed.get("gender").equals("m"))
				client = new Client(name, Gender.MALE, feed.get("email"), feed.get("city"));
			else if(feed.get("gender").equals("f"))
				client = new Client(name, Gender.FEMALE, feed.get("email"), feed.get("city"));
			else {
				return;
			}
			if(getClient(name) == null) {
				addClient(client);
			}
			else {
				client = getClient(name);
			}
			client.parseFeed(feed);
		} catch (InvalidClientNameException e) {
			e.printStackTrace();
		} catch (ClientExistsException e) {
			e.printStackTrace();
		} catch (EmailException e) {
			e.printStackTrace();
		}
		
	}

	private boolean checkFeedConsistency(Map<String, String> feed) {
		if(feed.containsKey("name") && 
				feed.containsKey("accounttype") &&
				feed.containsKey("balance") &&
				feed.containsKey("overdraft") &&
				feed.containsKey("email") &&
				feed.containsKey("gender") &&
				feed.containsKey("city"))
			return true;
		
		return false;
	}

	@Override
	public void printReport() {
		if(clientsSet.isEmpty()) {
			System.out.println("No clients in bank");
			return;
		}
		for(Client client : clientsSet) {
			System.out.println(client);
			for(Account account : client.getAccountsList()) {
				System.out.print("\t");
				account.printReport();
			}
			System.out.println();
		}
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
