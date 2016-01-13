package com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets;

import java.io.IOException;

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

public class BalanceServlet extends HttpServlet {

	private static final long serialVersionUID = -7348140197293965154L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name = (String) request.getSession().getAttribute("name");
		
		ClientDAO clientDAO = new ClientDAOImpl();
		AccountDAO accountDAO = new AccountDAOImpl();
		BankDAO bankDAO = new BankDAOImpl();
		Bank bank = new Bank();
		try {
			bank = bankDAO.getBankByName("My Bank");
		} catch (DAOException | BankNotFoundException e1) {
			e1.printStackTrace();
		}

		Client client = null;

		try {
			client = clientDAO.findClientByName(bank, name);
			for(Account account : accountDAO.getClientAccounts(client.getID()))
				client.addAccount(account);
			
		} catch(DAOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		int id = client.getAccountsList().get(0).getID();
		client.setActiveAccount(client.searchAccount(id));
		
		request.setAttribute("balance", client.getActiveAccount().getBalance());
		request.setAttribute("client", client);
		request.getRequestDispatcher("/client.jsp").forward(request, response);
//		ServletOutputStream out = response.getOutputStream();
//		out.println("<!DOCTYPE html>");
//		out.println("<html>");
//		out.println("<body>");
//		out.println(client.getName());
//		out.print("Balance: ");
//		out.println(client.getActiveAccount().getBalance() + "<br/>");
//		out.println("Accounts:<br/>");
//		for(Account account : client.getAccountsList()) {
//			out.println("\t" + account.toString());
//			out.println("<br/>");
//		}
//		out.println("====================<br/>");
//		out.println("<a href='ATM.html'> Continue </a>");
//		out.println("</body>");
//		out.println("</html>");
		
	}

}
