<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client"%>
<%@ page import="com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%!Client client;%>
	<strong>Client:</strong>
	<%=client = (Client) request.getAttribute("client")%>
	<br />
	<strong>Active account: </strong>
	<%=((Client) request.getAttribute("client")).getActiveAccount().toString() %>
	<br />
	<strong>Balance:</strong>
	<%=request.getAttribute("balance")%>
	<br />
	<br />
	<%
		for (Account account : client.getAccountsList()) {
			out.println("\t" + account.toString());
			out.println("<br/>");
		}
	%>
	<a href='AtmInterface.html'> Continue 1 </a>

</body>
</html>