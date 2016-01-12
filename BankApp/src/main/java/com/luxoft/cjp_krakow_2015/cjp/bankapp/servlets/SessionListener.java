package com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		final ServletContext context = httpSessionEvent.getSession().getServletContext();
		synchronized(SessionListener.class) {
			Integer clientsConnected = (Integer) context.getAttribute("clientsConnected");
			if(clientsConnected == null) {
				clientsConnected = 1;
			}
			else {
				clientsConnected++;
			}
			context.setAttribute("clientsConnected", clientsConnected);
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		final ServletContext context = httpSessionEvent.getSession().getServletContext();
		synchronized (SessionListener.class) {
			Integer clientsConnected = (Integer) context.getAttribute("clientsConnected");
			if(clientsConnected == null) {
				return;
			}
			else {
				clientsConnected--;
			}
			context.setAttribute("clientsConnected", clientsConnected);
		}
	}

}
