package com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
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

import java.util.List;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {

	Logger log = Logger.getLogger("clients." + this.getClass().getName());
	
	private static final long serialVersionUID = 5308814134579210996L;
	
	// protected -> public
	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final String name = request.getParameter("name");
		final String email = request.getParameter("email");
		final String pass = request.getParameter("pass");
		
		if(name == null) {
			log.fine("Client not found");
			throw new ServletException("No client specified");
		}
		request.getSession().setAttribute("name", name);
		log.info("Client " + name + " logged into ATM");
		if(isSuperuser(name, email, pass)) {
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
			response.sendRedirect("/BankStats.jsp");
			log.info("Client redirected to /BankStats.jsp");
		}
		else 
			response.sendRedirect("/ATM.html");
		log.info("Client redirected to /ATM.html");
	}

	private boolean isSuperuser(String name, String email, String pass) {
		if(!"superuser".equals(name)) return false;
		if(!"super@user.com".equals(email)) return false;
		if(!"super".equals(pass)) return false;
		
		return true;
	}
}
