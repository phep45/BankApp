package com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.AccountDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.AccountDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.BankNotFoundException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAO;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.ClientDAOImpl;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.DAOException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;

public class FindClientsServlet extends HttpServlet {

	private static final long serialVersionUID = 2594637568219545073L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name = request.getParameter("name");

		BankDAO bankDAO = new BankDAOImpl();
		ClientDAO clientDAO = new ClientDAOImpl();
		AccountDAO accountDAO = new AccountDAOImpl();

		List<Client> clients = null;
		Client client = null;
		Bank bank = null;
		try {
			bank = bankDAO.getBankByName("My Bank");
			clients = clientDAO.getAllClients(bank);
			client = clientDAO.findClientByName(bank, name);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (BankNotFoundException e) {
			e.printStackTrace();
		}

		try {
			client = clientDAO.findClientByName(bank, name);
			for(Account account : accountDAO.getClientAccounts(client.getID()))
				client.addAccount(account);

		} catch(DAOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

		int id = client.getAccountsList().get(0).getID();


		request.setAttribute("name", name);
		client.setActiveAccount(client.searchAccount(id));

		request.setAttribute("clientsList", clients);
		request.setAttribute("balance", client.getActiveAccount().getBalance());
		request.setAttribute("client", client);
		
		//TODO: redirect to addclient.jsp | client.jsp
		request.getRequestDispatcher("/addclient.jsp").forward(request, response);
		response.sendRedirect("/addclient.jsp");
	}

}
