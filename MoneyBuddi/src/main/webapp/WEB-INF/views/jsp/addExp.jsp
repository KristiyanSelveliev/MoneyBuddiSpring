<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<link rel="icon" type="image/png" href="assets/img/favicon.ico">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>Transactions</title>

	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />

    <script src="js/jquery.3.2.1.min.js" type="text/javascript"></script>
    <!-- Bootstrap core CSS     -->
    <link href="css/bootstrap.min.css" rel="stylesheet" />

    <!-- Animation library for notifications   -->
    <link href="css/animate.min.css" rel="stylesheet"/>

    <!--  Light Bootstrap Table core CSS    -->
    <link href="css/light-bootstrap-dashboard.css?v=1.4.0" rel="stylesheet"/>


    <!--  CSS for Demo Purpose, don't include it in your project     -->
    <link href="css/demo.css" rel="stylesheet" />


    <!--     Fonts and icons     -->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300' rel='stylesheet' type='text/css'>
    <link href="css/pe-icon-7-stroke.css" rel="stylesheet" />
    
    

</head>


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
                   
                    <a class="navbar-brand" href="#"> Overview</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-left">
                        
                        <li>
                           <form action="addIncome" method="get">
                                <button type="submit" class=" btn  btn-success " > <a1 style="font-size: 125%;">Add Income</a1></button>
                              </form>
                        </li>
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
						<li class="separator hidden-lg"></li>
                    </ul>
                </div>
            </div>
        </nav>


        <div class="content">
            <div class="container-fluid">
                <div class="row">
                   
                    <div class="col-md-4">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Add Expense</h4>
                                 <form action="addExpense" method="post">
                                 
                                   		 <div class="row">
			                                <a style="font-size: 140%;" class="col-md-10">Accounts </a><br>
			                                </div>
			                                   <select  class="col-md-11" name="accountId">
			                                
					                                <c:forEach var="account" items="${requestScope.accounts }">
					                                 <option value="${account.getId()}">${ account.getName() }-${account.getBalance()}-${account.getCurrency().getType().toString()} </option>
					                                
					                                </c:forEach>
			                                   </select > 
			                                      
			                                 <div class="row">
			                                 
			                                <a style="font-size: 140%;" class="col-md-10">Categories </a><br>
			                                </div>
			                                   <select  class="col-md-11" name="categoryId">
			                                
					                                <c:forEach var="category" items="${requestScope.categories }">
					                                 <option value="${category.getId()}" >${ category.getCategory() } </option>
					                                
					                                </c:forEach>
			                                   </select >
			                                   <div class="row">
			                                    <a style="font-size: 140%;" class="col-md-10" >Currencies </a>
			                                    </div>
			                                  
			                                   <select class="col-md-11" name="currencyId" >
			                                
					                                <c:forEach var="currency" items="${requestScope.currencies }">
					                                 <option  value="${currency.getId()}">${ currency.getType().toString()}  </option>
					                                
					                                </c:forEach>
			                                   </select >
			                                  
			                                   <div class="row">
			                                   		<a style="font-size: 140%;" class="col-md-9">Amount </a>
			                                   </div>
			                                   <input type="number"  name="amount" class="col-md-7" style="font-size: 115%;" value="100"> 
			                                    <div class="row" class="col-md-5">
			                                   		<a style="font-size: 125%;" class="col-md-9">Date </a>
			                                   </div>
			                                   <input type="date"  name="date"   class="col-md-7" style="font-size: 115%;" required> 
			                                   
			                                   
			                                 <div class="row">
			                                        <div class="col-md-8">
			                                  			<button  submit" rel="tooltip" title="Create" class="btn btn-danger focus"  >Add </button>
			                                  			</div>
			                                  </div>
                        					</form>
                        					
                        					 <c:if test="${requestScope.currency!=null && requestScope.budgetCurrency==null }">
                        					 
                                     		<script type="text/javascript">
										    	$(document).ready(function(){
										
										    		
										
										        	$.notify({
										            	icon: 'pe-7s-cash',
										            	message: "You just bought something! Well Done"
										
										            }
										        	,{
										        		
										                type: 'info',
										                timer: 2000
										            });
										
										    	});
											</script>
											<h4 >
								                 Your account lost ${requestScope.accountAmount}- ${requestScope.currency.getType().toString()}
								              </h4>
																			
								</c:if>
                               
                            </div>
                            
                        </div>
                    </div>
                    
                    
                    <div class="col-md-4">
                           <div class="card">
                            <div class="header">
                                <h4 class="title">Join a budget to your transaction</h4>
                                 <form action="addBudgetExpense" method="post">
                                 
                                   		 <div class="row">
			                                <a style="font-size: 140%;" class="col-md-10">Accounts </a><br>
			                                </div>
			                                   <select  class="col-md-11" name="accountId">
			                                
					                                <c:forEach var="account" items="${requestScope.accounts }">
					                                 <option value="${account.getId()}">${ account.getName() }-${account.getBalance()}-${account.getCurrency().getType().toString()} </option>
					                                
					                                </c:forEach>
			                                   </select > 
			                                      
			                                 <div class="row">
			                                 
			                                <a style="font-size: 140%;" class="col-md-10">Budgets</a><br>
			                                </div>
			                                   <select  class="col-md-11" name="budgetId">
			                                
					                                <c:forEach var="budget" items="${requestScope.budgets }">
					                                 <option value="${budget.getId()}">${ budget.getCategory().getCategory() }-${budget.getAmount()}-
					                                  ${budget.getCurrency().getType().toString() }- ${budget.getCategory().getType().toString()} </option>
					                                
					                                </c:forEach>
			                                   </select >
			                                   <div class="row">
			                                    <a style="font-size: 140%;" class="col-md-10" >Currencies </a>
			                                    </div>
			                                  
			                                   <select class="col-md-11" name="currencyId" >
			                                
					                                <c:forEach var="currency" items="${requestScope.currencies }">
					                                 <option  value="${currency.getId()}">${ currency.getType().toString()}  </option>
					                                
					                                </c:forEach>
			                                   </select >
			                                  
			                                   <div class="row">
			                                   		<a style="font-size: 140%;" class="col-md-9">Amount </a>
			                                   </div>
			                                   <input type="number"  name="amount" class="col-md-7" style="font-size: 115%;" value="100"> 
			                                    <div class="row" class="col-md-5">
			                                   		<a style="font-size: 125%;" class="col-md-9">Date </a>
			                                   </div>
			                                   <input type="date"  name="date"   class="col-md-7" style="font-size: 115%;" required> 
			                                   
			                                   
			                                 <div class="row">
			                                        <div class="col-md-8">
			                                  			<button  submit" rel="tooltip" title="Create" class="btn btn-danger focus"  >Add </button>
			                                  			</div>
			                                  </div>
                        					</form>
                        					
                        					 <c:if test="${requestScope.budgetCurrency!=null }">
                        					 
                                     		<script type="text/javascript">
										    	$(document).ready(function(){
										
										    		
										
										        	$.notify({
										            	icon: 'pe-7s-cash',
										            	message: "You just bought something! Well Done"
										
										            }
										        	,{
										        		
										                type: 'info',
										                timer: 2000
										            });
										
										    	});
											</script>
											<h4 >
								                 <p>Your account lost ${requestScope.accountAmount}- ${requestScope.currency.getType().toString()}</p>
								                 <p> You budget lost ${requestScope.budgetAmount}- ${requestScope.budgetCurrency.getType().toString()}</p>
								              </h4>
																			
								</c:if>
                               
                            </div>
                            
                        </div>
                    
                    
                    
                    
                    </div>
                    
                    
                    
                    
                    
                    
                    
                    
                </div>



                
        </div>

    </div>



</body>



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

	<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
	<script src="js/demo.js"></script>


</html>