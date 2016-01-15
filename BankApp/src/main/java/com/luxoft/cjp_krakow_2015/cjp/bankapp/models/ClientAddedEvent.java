package com.luxoft.cjp_krakow_2015.cjp.bankapp.models;

import org.springframework.context.ApplicationEvent;

public class ClientAddedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 8910873453029421034L;

	public ClientAddedEvent(Client client) {
		super(client);
	}
	
}
