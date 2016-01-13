<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="top">
		<h1>Search client</h1>
	</div>
	<div id="mid">
		<form id="form" action="/SearchClient" method="POST" target="_self">
			<table>
				<tr>
					<td colspan=2 style="font-weight: bold;">BankApp Co. ATM
						terminal:</td>
				</tr>
				<tr>
					<td>Name:</td>
					<td><input type="text" name="name" id="name" /></td>
					<td id="nameError" class="error"></td>
				</tr>
				<tr>
					<td>
						<div id="searchBtn">
							<input type="submit" onclick="return searchClient();"
								value="Search">
						</div>
					</td>
			</table>
		</form>
	</div>
	<div id="list">
		<c:forEach var="client" items="${clientsList}">
  		 	Name: <c:out value="${client.name}" />
   			City: <c:out value="${client.city}" />
   			Balance: <c:out value="${client.ballance}" />
			<br>
		</c:forEach>
	</div>
</body>
</html>