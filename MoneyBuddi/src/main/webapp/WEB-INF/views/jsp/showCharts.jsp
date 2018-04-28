<%@page import="com.model.Account"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.model.Category"%>
<%@page import="java.util.Map"%>
<%@page import="com.model.Transaction"%>
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

	<div>
		<div>
		<h2>Income details | </h2>
		<table style="width:50%">
	  <tr>
    	 <th>Category</th>
   		 <th>Amount</th>
   		 <th>%</th>
       </tr>
	  <% 
	  Map<String,Double> incomeFromTo=(Map<String,Double>)request.getAttribute("incomeFromTo");
	  double totalAmountIncome=0;
	  for(Map.Entry<String,Double> e : incomeFromTo.entrySet()){
	 	totalAmountIncome+=e.getValue();
			%>
			<tr>
	   			 <td><%= e.getKey() %></td>
	    		 <td><%= e.getValue() %></td>
	    		 <% 
	    		 double percentsIncome=(e.getValue())*100/(double)request.getAttribute("totalIncomeAmount");
	    		 DecimalFormat format_2Places = new DecimalFormat("0.00");
	    		 percentsIncome = Double.valueOf(format_2Places.format(percentsIncome));
	    		 %>
				 <td><%= percentsIncome%></td>
	  		</tr>
			<%} %>
	  
	</table>

	  <h3>Total : <%= totalAmountIncome %></h3>
	  <h3>Number of transactions : <%= request.getAttribute("transactionsCountIncome") %></h3>
		</div>
	</div>
	
	<div>
		<div>
		<h2>Expense details | </h2>
		
		<table style="width:50%">
	  <tr>
    	 <th>Category</th>
   		 <th>Amount</th>
   		 <th>%</th>
       </tr>
	  <% 
	  Map<String,Double> expenseFromTo=(Map<String,Double>)request.getAttribute("expenseFromTo");
	  double totalAmountExpense=0;
	  for(Map.Entry<String,Double> e : expenseFromTo.entrySet()){
		  totalAmountExpense+=e.getValue();
			%>
			<tr>
	   			 <td><%= e.getKey() %></td>
	    		 <td><%= e.getValue() %></td>
	    		 <% 
	    		 double percentsExpense=(e.getValue())*100/(double)request.getAttribute("totalExpenseAmount");
	    		 DecimalFormat format_2Places = new DecimalFormat("0.00");
	    		 percentsExpense = Double.valueOf(format_2Places.format(percentsExpense));
	    		 %>
				 <td><%= percentsExpense%></td>
	  		</tr>
			<%} %>
	  
	</table>

	  <h3>Total : <%= totalAmountExpense %></h3>
	  <h3>Number of transactions : <%= request.getAttribute("transactionsCountExpense") %></h3>
		
		</div>
	</div>

</body>
</html>