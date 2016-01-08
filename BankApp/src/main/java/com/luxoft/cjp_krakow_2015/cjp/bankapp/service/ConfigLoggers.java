package com.luxoft.cjp_krakow_2015.cjp.bankapp.service;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ConfigLoggers {
	
	public ConfigLoggers() throws SecurityException, IOException {
		Logger logger = Logger.getLogger("exceptions");
		logger.addHandler(new FileHandler("exceptions.xml"));
		logger = Logger.getLogger("clients");
		logger.addHandler(new FileHandler("clients.xml"));
		logger = Logger.getLogger("db");
		logger.addHandler(new FileHandler("db.xml"));
	}
	
}

