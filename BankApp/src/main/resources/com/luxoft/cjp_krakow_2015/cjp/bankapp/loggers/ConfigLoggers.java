package com.luxoft.cjp_krakow_2015.cjp.bankapp.loggers;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ConfigLoggers {
	
	public ConfigLoggers() {
		Logger logger = Logger.getLogger("exceptions");
		logger.addHandler(new FileHandler("exceptions.log"));
		logger = Logger.getLogger("clients");
		logger.addHandler(new FileHandler("clients.log"));
		logger = Logger.getLogger("db");
		logger.addHandler(new FileHandler("db.log"));
	}
	
}
