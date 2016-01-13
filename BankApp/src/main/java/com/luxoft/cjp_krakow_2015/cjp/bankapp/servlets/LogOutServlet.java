package com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutServlet extends HttpServlet {

	private static final long serialVersionUID = 4740711555042389618L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		ServletOutputStream out = response.getOutputStream();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<body>");
		out.println("Logged out... <br/>");
		out.println("<a href='/welcome'>main page</a>");
		out.println("</body>");
		out.println("</html>");
	}
	
}
