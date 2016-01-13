<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client"%>
<%@ page import="com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Account"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add client</title>
</head>
<body>

	<%!Client client;%>
	<strong>Client:</strong>
	<%=client = (Client) request.getAttribute("client")%>

	<div id="top">
		<h1>Add client</h1>
	</div>
	<div id="mid">
		<form id="form" action="/addclient" method="POST" target="_self">
			<table>
				<tr>
					<td colspan=2 style="font-weight: bold;">BankApp Co. ATM
						terminal:</td>
				</tr>
				<tr>
					<td>Full name:</td>
					<td><input type="text" name="name" id="name" value="<%=client.getName() %>" /></td>
				</tr>
				<tr>
					<td>City:</td>
					<td><input type="text" name="city" id="city" /></td>
				</tr>
				<tr>
					<td><input type="radio" name="sex" ${client.gender=="MALE"?"checked":""} />Male</td>
					<td><input type="radio" name="sex" ${client.gender=="FEMALE"?"checked":""} />Female</td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><input type="text" name="email" id="email" value="<%=client.getEmail().toString() %>"/></td>
				</tr>
				<tr>
					<td>Saving account:</td>
					<td><input type="checkbox" name="account" id="account" />Open</td>
				</tr>
				<tr>
					<td>Initial balance:</td>
					<td><input type="text" name="balance" id="balance" value="<%=client.getBalance() %>" /></td>
				</tr>
				<tr>
					<td>
						<div id="addBtn">
							<input type="submit" onclick="return addClient();"
								value="Add client">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>


	<a href='AtmInterface.html'> Continue 1 </a>

</body>
</html>