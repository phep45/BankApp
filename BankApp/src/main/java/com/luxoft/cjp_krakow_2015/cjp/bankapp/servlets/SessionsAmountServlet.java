package com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionsAmountServlet extends HttpServlet {

	private static final long serialVersionUID = 1456934123901798575L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ServletOutputStream out = resp.getOutputStream();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<body>");
		out.print("Sessions: ");
		out.println(SessionListener.clientsConnected);
		out.println("</body>");
		out.println("</html>");
	}
	
}
