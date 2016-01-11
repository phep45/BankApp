package com.luxoft.cjp_krakow_2015.cjp.bankapp.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckLoggedFilter implements Filter {

	Logger log = Logger.getLogger("clients." + this.getClass().getName());
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();
		
		String name = (String) session.getAttribute("name");
		String path = ((HttpServletRequest) request).getRequestURI();
		HttpServletResponse resp = (HttpServletResponse) response;
		
		if(path.startsWith("/AtmInterface") || path.equals("/welcome") || path.equals("/") || name != null) {
			chain.doFilter(request, resp);
		}
		else {
			((HttpServletResponse) response).sendRedirect("/AtmInterface.html");
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
