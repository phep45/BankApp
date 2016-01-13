package com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets;

import java.io.IOException;

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
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.EmailException;

public class SaveClientServlet extends HttpServlet {

	private static final long serialVersionUID = 414059828290779306L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		Client client = null;

		BankDAO bankDAO = new BankDAOImpl();
		ClientDAO clientDAO = new ClientDAOImpl();
		try {
			client = clientDAO.findClientByName(bankDAO.getBankByName("My Bank"), name);
			client.setName(name);
			client.setEmail(email);
			clientDAO.save(client);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (BankNotFoundException e) {
			e.printStackTrace();
		} catch (EmailException e) {
			e.printStackTrace();
		}

		request.setAttribute("client", client);
		request.setAttribute("name", name);
		//TODO: redirect to addclient.jsp | client.jsp
//		request.getRequestDispatcher("/client.jsp").forward(request, response);
//		response.sendRedirect("/client.jsp");
		ServletOutputStream out = response.getOutputStream();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<body>");
		out.println("Changes saved <br/>");
		out.println("<a href='/welcome'>main page</a>");
		out.println("</body>");
		out.println("</html>");
	}

}
