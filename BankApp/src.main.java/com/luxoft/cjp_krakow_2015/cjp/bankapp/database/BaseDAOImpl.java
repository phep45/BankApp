package com.luxoft.cjp_krakow_2015.cjp.bankapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseDAOImpl implements BaseDAO {

	protected Connection conn;
	
	static Logger log = Logger.getLogger(BaseDAOImpl.class.getName());
	
	@Override
	public Connection openConnection() throws DAOException {
		try{
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:~/bankapp", "sa", "");
			return conn;
		} catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void closeConnection() {
		try {
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
