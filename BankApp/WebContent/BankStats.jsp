<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Bank Statistics</h3>

	<c:forEach var="client" items="${clientsList}">
  		 	Name: <c:out value="${client.name}" />


		<br/>
	</c:forEach>

	<div id="buttons">
		<table style="border: 0px;">
			<tr>
				<td style="padding: 0px;">
					<div id="next">
						<button type="button" onclick="location.href='SearchClient.jsp';">Search
							client</button>
					</div>
				</td>
				<td>
					<div id="next">
						<button type="button" onclick="location.href='AddClient.html';">Add
							client</button>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>