package com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	static Integer clientsConnected;
	
	Logger log = Logger.getLogger("clients." + this.getClass().getName());
	
	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		final ServletContext context = httpSessionEvent.getSession().getServletContext();
		synchronized(SessionListener.class) {
			clientsConnected = (Integer) context.getAttribute("clientsConnected");
			if(clientsConnected == null) {
				clientsConnected = 1;
			}
			else {
				clientsConnected++;
				log.log(Level.ALL, "client connected");
			}
			context.setAttribute("clientsConnected", clientsConnected);
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		final ServletContext context = httpSessionEvent.getSession().getServletContext();
		synchronized (SessionListener.class) {
			clientsConnected = (Integer) context.getAttribute("clientsConnected");
			if(clientsConnected == null) {
				clientsConnected = 1;
			}
			else {
				clientsConnected--;
				log.log(Level.ALL, "client disconnected");
			}
			context.setAttribute("clientsConnected", clientsConnected);
		}
	}

}
