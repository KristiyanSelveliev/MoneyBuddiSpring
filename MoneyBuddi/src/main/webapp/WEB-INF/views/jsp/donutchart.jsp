<%@page import="com.model.Account"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Charts</title>
</head>
<body>

<h1><%= LocalDate.now().getMonth()%><%= LocalDate.now().getYear() %></h1>

	<form action="showDonutChart" method="GET">
		   <select name="accountId" required>
				<%
				ArrayList<Account> accounts=(ArrayList<Account>)request.getAttribute("accounts");
				long accountId=0;
				for(Account a: accounts){
				%>
		    		<option value="<%= accountId=a.getId() %>"><%= a.getName() %></option>
				<%} 
				%>
			</select><br>
			From :<input type="date" name="from" required><br>
			To  :<input type="date" name="to" required><br>
			<input type="submit" value="Show">
	</form>

</body>
</html>