package com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServletContextListenerImpl implements ServletContextListener {

	ServletContext servletContext;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		servletContext = (ServletContext) new ClassPathXmlApplicationContext("servlet-context.xml");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
