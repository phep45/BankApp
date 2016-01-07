package com.luxoft.cjp_krakow_2015.cjp.bankapp.database;

import java.sql.Connection;

public interface BaseDAO {

	Connection openConnection() throws DAOException;
	void closeConnection();
	
}
