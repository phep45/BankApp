package com.luxoft.cjp_krakow_2015.cjp.bankapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAOImpl implements BaseDAO {

	protected Connection conn;
	
	@Override
	public Connection openConnection() throws DAOException {
		try{
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:~/bankapp", "sa", "");
			return conn;
		} catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void closeConnection() {
		try {
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
