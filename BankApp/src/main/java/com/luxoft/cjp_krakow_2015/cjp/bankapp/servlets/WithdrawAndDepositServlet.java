package com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets;

import java.io.IOException;

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
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.BankException;

public class WithdrawAndDepositServlet extends HttpServlet {

	private static final long serialVersionUID = -6830636034626713267L;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		final Integer accountID = Integer.parseInt((String) request.getParameter("account"));
		final Integer amount = Integer.parseInt((String) request.getParameter("cash"));
		final String op = request.getParameter("operation");

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

		try {
			client.setActiveAccount(client.searchAccount(accountID));
			if("deposit".equals(op)) 
				client.deposit(amount);
			else if("withdraw".equals(op))
				client.withdraw(amount);
		} catch (BankException e) {
			e.printStackTrace();
		}
		
		try {
			accountDAO.save(client.getActiveAccount());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("/balance");
	}

}
