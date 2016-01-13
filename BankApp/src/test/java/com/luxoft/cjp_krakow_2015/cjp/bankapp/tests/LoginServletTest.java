package com.luxoft.cjp_krakow_2015.cjp.bankapp.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets.LoginServlet;

public class LoginServletTest extends Mockito {

	@Test
	public void testLoginAsUser() throws ServletException, IOException {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("name")).thenReturn("Ala MaKota");
		when(request.getParameter("email")).thenReturn("ala@ma.kota");
		when(request.getParameter("pass")).thenReturn("");
		
		LoginServlet servlet = new LoginServlet();
		servlet.doPost(request, response);
		
		verify(response).sendRedirect("/ATM.html");
	}

	@Test
	public void testLoginAsSuperUser() throws ServletException, IOException {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		
		when(request.getSession()).thenReturn(session);
		when(request.getParameter("name")).thenReturn("superuser");
		when(request.getParameter("email")).thenReturn("super@user.com");
		when(request.getParameter("pass")).thenReturn("super");
		
		LoginServlet servlet = new LoginServlet();
		servlet.doPost(request, response);
		
		verify(response).sendRedirect("/BankStats.html");
	}
}
