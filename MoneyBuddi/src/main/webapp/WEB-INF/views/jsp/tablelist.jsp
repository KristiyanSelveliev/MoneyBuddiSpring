<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="icon" type="image/png" href="assets/img/favicon.ico">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>Tables</title>

	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />


    <!-- Bootstrap core CSS     -->
    <link href="css/bootstrap.min.css" rel="stylesheet" />

    <!-- Animation library for notifications   -->
    <link href="css/animate.min.css" rel="stylesheet"/>

    <!--  Light Bootstrap Table core CSS    -->
    <link href="css/light-bootstrap-dashboard.css?v=1.4.0" rel="stylesheet"/>


    <!--     Fonts and icons     -->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300' rel='stylesheet' type='text/css'>
    <link href="css/pe-icon-7-stroke.css" rel="stylesheet" />
</head>
<body>

<div class="wrapper">
    <div class="sidebar" data-color="blue" data-image="img/sidebar-5.jpg">
    	<div class="sidebar-wrapper">
           <div  class="logo">
                <div  class="register-logo">
                <h3> <b>Money</b>Buddi<h3>
                </div>
          
            </div>
             <ul class="nav">
                <li>
                    <a href="transactions">
                        <i class="pe-7s-graph"></i>
                        <p>Transactions</p>
                    </a>
                </li>
                <li >
                    <a href="profile">
                        <i class="pe-7s-user"></i>
                        <p>My Profile</p>
                    </a>
                </li>
                <li>
                    <a href="tables">
                        <i class="pe-7s-note2"></i>
                        <p>Table List</p>
                    </a>
                </li>
                <li>
                    <a href="accounts">
                        <i class="pe-7s-wallet"></i>
                        <p>Accounts</p>
                    </a>
                </li>
                <li>
                    <a href="budgets">
                        <i class="pe-7s-cash"></i>
                        <p>Budgets</p>
                    </a>
                </li>
                <li>
                   <a href="categories">
                       <i class="pe-7s-folder"></i>
                        <p>Categories</p>
                    </a>
                </li>
            </ul>
    	</div>
    </div>

    <div class="main-panel">
		<nav class="navbar navbar-default navbar-fixed">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example-2">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Table List</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-left">
                     
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <li>
                           <a href="profile">
                               <p>My Profile</p>
                            </a>
                        </li>
                        <li class="dropdown">
                              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <p>
										Actions
										<b class="caret"></b>
									</p>

                              </a>
                              <ul class="dropdown-menu">
                                <li><a href="transactions">Transactions</a></li>
                                <li><a href="tables">Table List</a></li>
                                <li><a href="accounts">Accounts</a></li>
                                <li><a href="budgets">Budgets</a></li>
                                <li><a href="categories">Categories</a></li>
                              </ul>
                        </li>
                        <li>
                             <form action="logout" method="get">
                                <button type="submit" class=" btn  btn-warning " > Log out</button>
                              </form>
                        </li>
						<li class="separator hidden-lg hidden-md"></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-6">
                       <div class="card"> 
                            <div class="header">
                                <h4 class="title" style="color:red">Filter for Expenses</h4>
                                
                                
                                          
                                             <p >
                                             <a style="color:purple"> <b> Begin-></b></a>
                                             <a style="color:purple"> <b> End</b></a>
                                                   <div id="total1">
                                                   </div>
			                                   
                                             </p>
			                                   <input id="begin1" type="date"  name="begin"   style="font-size: 130%;"> 
			                           
			                                   <input  id="end1" type="date"  name="end"   style="font-size: 130%;" > 
			                                   
                                         <button onclick="fillTable1()" class=" btn  btn-danger ">Show</button>
                               
                               
                                
                            </div>
                             <div class="content table-responsive">  
                               <div class="scroll1">
                                <table id="table1" class=" table table-responsive table-hover " >
                                    <tr >
                                        <th>ID</th>
                                    	<th>Category</th>
                                    	<th>Amount</th>
                                    	<th>Account</th>
                                    	<th>Date</th>
                                    </tr>
                                    <tbody id="body1" >
                                      <c:forEach var="expense" items="${requestScope.expenses }">
                                          <tr >
                                            <td>${expense.getId() }</td>
                                        	<td>${expense.getCategory().getCategory() }</td>
                                        	<td>-<a style="color:red">${expense.getAmount()}</a></td>
                                        	<td>${expense.getAccount().getName()}-${expense.getCurrency().getType().toString() }</td>
                                        	<td>${expense.getDate()}</td>
                                         <tr>	
                                      </c:forEach>
                                    
                                    </tbody>
                                </table>
                               </div>
                            </div> 
                          </div> 
                    </div>


                     <div class="col-md-6">
                        <div class="card">
                            <div class="header">
                                <h4 class="title" style="color:green">Filter for Incomes</h4>
                                
                                
                               
                                            <p>
                                             <a style="color:purple"> <b> Begin-></b></a>
                                             <a style="color:purple"> <b> End</b></a>
                                             <div id="total2">
                                                   </div>
                                             </p>
			                                   <input id="begin2" type="date"  name="begin"   style="font-size: 130%;"> 
			                              
			                                
			                                  
			                                   <input id="end2"  type="date"  name="end"   style="font-size: 130%;" > 
			                                   <button onclick="fillTable2()" class=" btn  btn-success ">Show</button>
                                
                              
                                </div>
                           
                            <div class="content table-responsive">
                              <div class="scroll1">
                                <table id="table2" class="table table-responsive table-hover" >
                                    <tr>
                                        <th>ID</th>
                                    	<th>Category</th>
                                    	<th>Amount</th>
                                    	<th>Account</th>
                                    	<th>Date</th>
                                    </tr>
                                    <tbody id="body2" >
                                      <c:forEach var="expense" items="${requestScope.expenses }">
                                          <tr>
                                            <td>${expense.getId() }</td>
                                        	<td>${expense.getCategory().getCategory() }</td>
                                        	<td>+<a style="color:green">${expense.getAmount()}</a></td>
                                        	<td>${expense.getAccount().getName()}-${expense.getCurrency().getType().toString() }</td>
                                        	<td>${expense.getDate()}</td>
                                         <tr>	
                                      </c:forEach>
                                    
                                    </tbody>
                                </table>
                                 
                                </div>
                                
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="card">
                            <div class="header">
                                <h4 class="title" style="color:red">Filter for  Expenses by account</h4>
                                
                                     
                            
                            <div class="row">
                                        <div class="col-md-4">
                                        <label><a style="color:purple"> <b>Account</b></a></label>
                                            <div class="form-group">
                                             
                                                <select id="ddlViewBy1"  class="col-md-11" name="accountId">
			                                       
					                                <c:forEach var="account" items="${requestScope.accounts }">
					                                 <option value="${account.getId()}">${ account.getName() }-${account.getBalance()}-${account.getCurrency().getType().toString()} </option>
					                                
					                               </c:forEach>
			                                  
			                                  </select>
                                            </div>
                                        </div>
                                        <div >
                                            <div >
                                                <label><a style="color:purple"> <b>Begin</b></a></label>
                                                  <input id="begin3" type="date"  name="begin"   style="font-size: 130%;"> 
                                            </div>
                                        </div>
                                        <div >
                                            <div >
                                              <label>  <a style="color:purple"> <b>End&nbsp&nbsp&nbsp&nbsp</b></a></label>
                                                 <input id="end3"   type="date"  name="end"   style="font-size: 130%;" > 
                                            </div>
                                        </div>
                                    </div>
                                      <button onclick="fillTable3()" class=" btn  btn-danger ">Show </button>
                                   
                                
                                 <div id="total3">
                                    </div>
                                
                            </div>
                            <div class="content table-responsive">
                              <div class="scroll1">
                                <table id="table3" class="table table-responsive table-hover" >
                                    <tr>
                                        <th>ID</th>
                                    	<th>Category</th>
                                    	<th>Amount</th>
                                    	<th>Account</th>
                                    	<th>Date</th>
                                    </tr>
                                    <tbody id="body3"  >
                                      <c:forEach var="expense" items="${requestScope.expenses }">
                                          <tr>
                                            <td>${expense.getId() }</td>
                                        	<td>${expense.getCategory().getCategory() }</td>
                                        	<td>-<a style="color:red">${expense.getAmount()}</a></td>
                                        	<td>${expense.getAccount().getName()}-${expense.getCurrency().getType().toString() }</td>
                                        	<td>${expense.getDate()}</td>
                                         <tr>	
                                      </c:forEach>
                                    
                                    </tbody>
                                </table>
                               </div>
                            </div>
                        </div>
                    </div>
                    
                    
                     <div class="col-md-6">
                        <div class="card">
                            <div class="header">
                                <h4 class="title" style="color:green">Filter for  Incomes by account</h4>
                                
                            
                                     <div class="row">
                                        <div class="col-md-4">
                                        <label><a style="color:purple"> <b>Account</b></a></label>
                                            <div class="form-group" >
                                          
                                                <select id="ddlViewBy" class="col-md-11" name="accountId">
			                                       
					                                <c:forEach var="account" items="${requestScope.accounts }">
					                                 <option value="${account.getId()}">${ account.getName() }-${account.getBalance()}-${account.getCurrency().getType().toString()} </option>
					                                
					                               </c:forEach>
			                                     
			                                  </select>
			                                 
                                            </div>
                                        </div>
                                        <div >
                                            <div  >
                                                <label><a style="color:purple"> <b>Begin</b></a></label>
                                                  <input id="begin4" type="date"  name="begin"   style="font-size: 130%;"> 
                                            </div>
                                        </div>
                                        <div >
                                            <div  >
                                              <label>  <a style="color:purple"> <b>End&nbsp&nbsp&nbsp&nbsp</b></a></label>
                                                 <input id="end4"  type="date"  name="end"   style="font-size: 130%;" > 
                                            </div>
                                        </div>
                                      </div>
                                     <button onclick="fillTable4()"  class=" btn  btn-success">Show</button>
                                    <div id="total4">
                                        </div>
                                   </div>
                            <div class="content table-responsive">
                                <div class="scroll1">
                                <table id="table4" class="table table-hover " >
                                    <tr>
                                        <th>ID</th>
                                    	<th>Category</th>
                                    	<th>Amount</th>
                                    	<th>Account</th>
                                    	<th>Date</th>
                                    </tr>
                                    <tbody id="body4" >
                                      <c:forEach var="expense" items="${requestScope.expenses }">
                                          <tr >
                                            <td>${expense.getId() }</td>
                                        	<td>${expense.getCategory().getCategory() }</td>
                                        	<td>+<a style="color:green">${expense.getAmount()}</a></td>
                                        	<td>${expense.getAccount().getName()}-${expense.getCurrency().getType().toString() }</td>
                                        	<td>${expense.getDate()}</td>
                                         <tr>	
                                      </c:forEach>
                                    
                                    </tbody>
                                </table>
                               </div>
                            </div>
                        </div>
                    </div>
                    
                    
            <div class="col-md-6">
                        <div class="card">
                            <div class="header">
                                <h4 class="title" style="color:red">Filter for  Expenses by category</h4>
                                
                            
                                     <div class="row">
                                        <div class="col-md-4">
                                        <label><a style="color:purple"> <b>Category</b></a></label>
                                            <div class="form-group" >
                                          
                                                <select id="ddlViewBy6" class="col-md-11" name="categoryId">
			                                       
					                                <c:forEach var="category" items="${requestScope.expenseCategories }">
					                                 <option value="${category.getId()}" >${ category.getCategory() } </option>
					                                
					                               </c:forEach>
			                                     
			                                  </select>
			                                 
                                            </div>
                                        </div>
                                        <div >
                                            <div  >
                                                <label><a style="color:purple"> <b>Begin</b></a></label>
                                                  <input id="begin6" type="date"  name="begin"   style="font-size: 130%;"> 
                                            </div>
                                        </div>
                                        <div >
                                            <div  >
                                              <label>  <a style="color:purple"> <b>End&nbsp&nbsp&nbsp&nbsp</b></a></label>
                                                 <input id="end6"  type="date"  name="end"   style="font-size: 130%;" > 
                                            </div>
                                        </div>
                                      </div>
                                     <button onclick="fillTable6()"  class=" btn  btn-success">Show</button>
                                    <div id="total6">
                                        </div>
                                   </div>
                            <div class="content table-responsive">
                                <div class="scroll1">
                                <table id="table6" class="table table-hover " >
                                    <tr>
                                        <th>ID</th>
                                    	<th>Category</th>
                                    	<th>Amount</th>
                                    	<th>Account</th>
                                    	<th>Date</th>
                                    </tr>
                                    <tbody id="body6" >
                                      <c:forEach var="expense" items="${requestScope.expensesByCategory }">
                                          <tr >
                                            <td>${expense.getId() }</td>
                                        	<td>${expense.getCategory().getCategory() }</td>
                                        	<td>+<a style="color:red">${expense.getAmount()}</a></td>
                                        	<td>${expense.getAccount().getName()}-${expense.getCurrency().getType().toString() }</td>
                                        	<td>${expense.getDate()}</td>
                                         <tr>	
                                      </c:forEach>
                                    
                                    </tbody>
                                </table>
                               </div>
                            </div>
                        </div>
                    </div>  
                    
			
			<div class="col-md-6">
                        <div class="card">
                            <div class="header">
                                <h4 class="title" style="color:green">Filter for  Incomes by category</h4>
                                
                            
                                     <div class="row">
                                        <div class="col-md-4">
                                        <label><a style="color:purple"> <b>Category</b></a></label>
                                            <div class="form-group" >
                                          
                                                <select id="ddlViewBy5" class="col-md-11" name="categoryId">
			                                       
					                                <c:forEach var="category" items="${requestScope.incomeCategories }">
					                                 <option value="${category.getId()}" >${ category.getCategory() } </option>
					                                
					                               </c:forEach>
			                                     
			                                  </select>
			                                 
                                            </div>
                                        </div>
                                        <div >
                                            <div  >
                                                <label><a style="color:purple"> <b>Begin</b></a></label>
                                                  <input id="begin5" type="date"  name="begin"   style="font-size: 130%;"> 
                                            </div>
                                        </div>
                                        <div >
                                            <div  >
                                              <label>  <a style="color:purple"> <b>End&nbsp&nbsp&nbsp&nbsp</b></a></label>
                                                 <input id="end5"  type="date"  name="end"   style="font-size: 130%;" > 
                                            </div>
                                        </div>
                                      </div>
                                     <button onclick="fillTable5()"  class=" btn  btn-success">Show</button>
                                    <div id="total5">
                                        </div>
                                   </div>
                            <div class="content table-responsive">
                                <div class="scroll1">
                                <table id="table5" class="table table-hover " >
                                    <tr>
                                        <th>ID</th>
                                    	<th>Category</th>
                                    	<th>Amount</th>
                                    	<th>Account</th>
                                    	<th>Date</th>
                                    </tr>
                                    <tbody id="body5" >
                                      <c:forEach var="income" items="${requestScope.incomesByCategory }">
                                          <tr >
                                            <td>${income.getId() }</td>
                                        	<td>${income.getCategory().getCategory() }</td>
                                        	<td>+<a style="color:green">${income.getAmount()}</a></td>
                                        	<td>${income.getAccount().getName()}-${income.getCurrency().getType().toString() }</td>
                                        	<td>${income.getDate()}</td>
                                         <tr>	
                                      </c:forEach>
                                    
                                    </tbody>
                                </table>
                               </div>
                            </div>
                        </div>
                    </div>
                                
                                
                            </div>
                        </div>
                    </div>
			

                </div>
            </div>
        </div>

        
    </div>
</div>


<style>

.flexy {
            display:block;
            max-height: 350px;
            overflow: auto;
        }
        
 .scroll1 {
      width=95%;
      max-width:750px;
      height:350px;
      overflow-x:scroll;
      
}


</style>


</body>

<script >
function fillTable1(){
	
	var pole=document.getElementById("begin1");
	var begin=pole.value;
	
     pole=document.getElementById("end1");
	var end=pole.value;
	
	var request=new XMLHttpRequest();
	request.open("GET","userExpense?begin=" +begin+"&end="+end);
	request.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 200){
			var result = this.responseText;
			result = JSON.parse(result);
			var total=0;
			var totalBody=document.getElementById("total1");
			var body = document.getElementById("body1");
			
			body.innerHTML = "";
			for(var i=0;i<result.length;i++){
				var tr="<tr>";
				tr+='<td>'+result[i]['id']+'</td>';
				
				tr+='<td>'+result[i]['category']+'</td>';
				
				tr+='<td> -<a style="color:red">'+result[i]['amount']+'</a></td>';

				tr+='<td>'+result[i]['account']+'</td>';
				
				tr+='<td>'+result[i]['date']+'</td>';
				tr+="</tr>";
				
				total+=result[i]['amount_converted'];
				body.innerHTML+=tr;
				
			}
			totalBody.innerHTML="";
			totalBody.innerHTML+='<h3  float:right ;margin-right:50px;font-size: 130%;">  Total:&nbsp-<a style="color:red">'+total+'</a>  EURO</h3>';
			
			
			
		}
	}
	request.send();
	}
	
function fillTable2(){
	
	var pole=document.getElementById("begin2");
	var begin=pole.value;
	
    pole=document.getElementById("end2");
	var end=pole.value;
	
	var request=new XMLHttpRequest();
	request.open("GET","userIncome?begin=" +begin+"&end="+end);
	request.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 200){
			var result = this.responseText;
			result = JSON.parse(result);
			var total=0;
			var totalBody=document.getElementById("total2");
			var body = document.getElementById("body2");
			
			body.innerHTML = "";
			for(var i=0;i<result.length;i++){
				var tr="<tr>";
				tr+='<td>'+result[i]['id']+'</td>';
				
				tr+='<td>'+result[i]['category']+'</td>';
				
				tr+='<td> +<a style="color:green">'+result[i]['amount']+'</a></td>';

				tr+='<td>'+result[i]['account']+'</td>';
				
				tr+='<td>'+result[i]['date']+'</td>';
				
				tr+="</tr>";
			   
				total+=result[i]['amount_converted'];
				body.innerHTML+=tr;
				
			}
			totalBody.innerHTML="";
			totalBody.innerHTML+='<h3  float:right ;margin-right:50px;font-size: 130%;">  Total:&nbsp+<a style="color:green">'+total+'</a>  EURO</h3>';
			
			
			
		}
	}
	request.send();
	}
	
	
	
function fillTable3(){
	
	var e = document.getElementById("ddlViewBy1");
	var accountId = e.options[e.selectedIndex].value;
	
	
	var pole=document.getElementById("begin3");
	var begin=pole.value;
	
     pole=document.getElementById("end3");
	var end=pole.value;
	
	var request=new XMLHttpRequest();
	request.open("GET","accountExpense?begin=" +begin+"&end="+end+"&id="+accountId);
	request.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 200){
			var result = this.responseText;
			result = JSON.parse(result);
			var total=0;
			var totalBody=document.getElementById("total3");
			var body = document.getElementById("body3");
			console.log(accountId);
			body.innerHTML ="";
			for(var i=0;i<result.length;i++){
				var tr="<tr>";
				tr+='<td>'+result[i]['id']+'</td>';
				
				tr+='<td>'+result[i]['category']+'</td>';
				
				tr+='<td>- <a style="color:red">'+result[i]['amount']+'</a></td>';

				tr+='<td>'+result[i]['account']+'</td>';
				
				tr+='<td>'+result[i]['date']+'</td>';
				
				tr+="</tr>";
				
				total+=result[i]['amount_converted'];
				body.innerHTML+=tr;
			
			}
			totalBody.innerHTML="";
			totalBody.innerHTML+='<h3  float:right ;margin-right:50px;font-size: 130%;">  Total:&nbsp-<a style="color:red">'+total+'</a>  EURO</h3>';
			
			
			
		}
	}
	request.send();
	}


function fillTable4(){
	
	var e = document.getElementById("ddlViewBy");
	var accountId = e.options[e.selectedIndex].value;
	console.log(accountId);
	
	var pole=document.getElementById("begin4");
	var begin=pole.value;
	
     pole=document.getElementById("end4");
	var end=pole.value;
	
	var request=new XMLHttpRequest();
	request.open("GET","accountIncome?begin=" +begin+"&end="+end+"&id="+accountId);
	request.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 200){
			var result = this.responseText;
			result = JSON.parse(result);
			var total=0;
			var totalBody=document.getElementById("total4");
			var body = document.getElementById("body4");
			body.innerHTML ="";
			for(var i=0;i<result.length;i++){
				var tr="<tr>";
				tr+='<td>'+result[i]['id']+'</td>';
				
				tr+='<td>'+result[i]['category']+'</td>';
				
				tr+='<td> +<a style="color:green">'+result[i]['amount']+'</a></td>';

				tr+='<td>'+result[i]['account']+'</td>';
				
				tr+='<td>'+result[i]['date']+'</td>';
				
				tr+="</tr>";
				total+=result[i]['amount_converted'];
				body.innerHTML+=tr;
				
			}
			totalBody.innerHTML="";
			totalBody.innerHTML+='<h3  float:right ;margin-right:50px;font-size: 130%;">  Total:&nbsp+<a style="color:green">'+total+'</a>  EURO</h3>';
			
			
			
			
		}
	}
	request.send();
	}

function fillTable5(){
	
	var e = document.getElementById("ddlViewBy5");
	var categoryId = e.options[e.selectedIndex].value;
	console.log(categoryId);
	
	var pole=document.getElementById("begin5");
	var begin=pole.value;
	
     pole=document.getElementById("end5");
	var end=pole.value;
	
	var request=new XMLHttpRequest();
	request.open("GET","categoryIncome?begin=" +begin+"&end="+end+"&id="+categoryId);
	request.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 200){
			var result = this.responseText;
			result = JSON.parse(result);
			var total=0;
			var totalBody=document.getElementById("total5");
			var body = document.getElementById("body5");
			body.innerHTML ="";
			for(var i=0;i<result.length;i++){
				var tr="<tr>";
				tr+='<td>'+result[i]['id']+'</td>';
				
				tr+='<td>'+result[i]['category']+'</td>';
				
				tr+='<td> +<a style="color:green">'+result[i]['amount']+'</a></td>';

				
				tr+='<td>'+result[i]['date']+'</td>';
				
				tr+="</tr>";
				total+=result[i]['amount_converted'];
				body.innerHTML+=tr;
				
			}
			totalBody.innerHTML="";
			totalBody.innerHTML+='<h3  float:right ;margin-right:50px;font-size: 130%;">  Total:&nbsp+<a style="color:green">'+total+'</a>  EURO</h3>';
			
			
			
			
		}
	}
	request.send();
	}


function fillTable6(){
	
	var e = document.getElementById("ddlViewBy6");
	var categoryId = e.options[e.selectedIndex].value;
	console.log(categoryId);
	
	var pole=document.getElementById("begin6");
	var begin=pole.value;
	
     pole=document.getElementById("end6");
	var end=pole.value;
	
	var request=new XMLHttpRequest();
	request.open("GET","categoryExpense?begin=" +begin+"&end="+end+"&id="+categoryId);
	request.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 200){
			var result = this.responseText;
			result = JSON.parse(result);
			var total=0;
			var totalBody=document.getElementById("total6");
			var body = document.getElementById("body6");
			body.innerHTML ="";
			for(var i=0;i<result.length;i++){
				var tr="<tr>";
				tr+='<td>'+result[i]['id']+'</td>';
				
				tr+='<td>'+result[i]['category']+'</td>';
				
				tr+='<td> +<a style="color:green">'+result[i]['amount']+'</a></td>';

				
				tr+='<td>'+result[i]['date']+'</td>';
				
				tr+="</tr>";
				total+=result[i]['amount_converted'];
				body.innerHTML+=tr;
				
			}
			totalBody.innerHTML="";
			totalBody.innerHTML+='<h3  float:right ;margin-right:50px;font-size: 130%;">  Total:&nbsp+<a style="color:green">'+total+'</a>  EURO</h3>';
			
			
			
			
		}
	}
	request.send();
	}




</script>







    <!--   Core JS Files   -->
    <script src="js/jquery.3.2.1.min.js" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>

	<!--  Charts Plugin -->
	<script src="js/chartist.min.js"></script>

    <!--  Notifications Plugin    -->
    <script src="js/bootstrap-notify.js"></script>

    <!--  Google Maps Plugin    -->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>

    <!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
	<script src="js/light-bootstrap-dashboard.js?v=1.4.0"></script>

	


</html>
