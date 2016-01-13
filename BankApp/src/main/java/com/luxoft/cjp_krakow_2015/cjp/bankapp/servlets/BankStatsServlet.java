package com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankNotFoundException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.DAOException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;

public class BankStatsServlet extends HttpServlet {

	private static final long serialVersionUID = 6756507965498733269L;

	private Logger log = Logger.getLogger("clients." + this.getClass().getName());
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BankDAO bankDAO = new BankDAOImpl();
		ClientDAO clientDAO = new ClientDAOImpl();

		List<Client> clients = null;
		Bank bank = null;
		try {
			bank = bankDAO.getBankByName("My Bank");
			clients = clientDAO.getAllClients(bank);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (BankNotFoundException e) {
			e.printStackTrace();
		}

		request.setAttribute("clientsList", clients);
		request.getRequestDispatcher("/BankStats.jsp").forward(request, response);
		log.info("BankStats servlet sends");
		ServletOutputStream out = response.getOutputStream();
		out.println("bank stats");
	}
	
}
