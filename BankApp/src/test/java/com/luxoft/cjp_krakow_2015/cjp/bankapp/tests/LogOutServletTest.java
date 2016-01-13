package com.luxoft.cjp_krakow_2015.cjp.bankapp.tests;


import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets.LogOutServlet;

public class LogOutServletTest extends Mockito {

	@Test
	public void test() throws IOException {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		session.setAttribute("name", "Ala MaKota");
		
		ServletOutputStream stream = new ServletOutputStream() {
			@Override
			public void write(int b) throws IOException {
				
			}
		};
		
		when(request.getSession()).thenReturn(session);
		when(response.getOutputStream()).thenReturn(stream);
		
		new LogOutServlet().doGet(request, response);
		
		verify(session).invalidate();
	}

}
