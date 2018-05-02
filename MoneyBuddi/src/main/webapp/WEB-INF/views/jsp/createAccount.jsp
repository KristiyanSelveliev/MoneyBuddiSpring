<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
    
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="icon" type="image/png" href="assets/img/favicon.ico">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>Accounts</title>

	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />


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
                    <a class="navbar-brand" href="#">Accounts</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-left">
                      
                        <li>
                           <a href="">
                                <i class="fa fa-search"></i>
								<p class="hidden-lg hidden-md">Search</p>
                            </a>
                        </li>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <li>
                           <a href="">
                               <p>Account</p>
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
                    <div class="col-md-12">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Your accounts</h4>
                            </div>
                            <div class="content all-icons">
                                <div class="row">
                                 <div class="font-icon-list col-lg-2 col-md-3 col-sm-4 col-xs-6 col-xs-6">
                                 	<div class="container-fluid">
			                             <form action="createAccount" method="post"> 
			                             		<div class="row">
			                                   		<a style="font-size: 140%;" class="col-md-10">Name </a>
			                                   </div>
			                                   <input type="text"  name="name" class="form-control"  style="font-size: 130%;" placeholder="Account name"> 
			                                       
			                                   <div class="row">
			                                    <a style="font-size: 140%;" class="col-md-10" >Currencies </a>
			                                    </div>
			                                  
			                                   <select class="col-md-8" name="currencyId" >
			                                
					                                <c:forEach var="currency" items="${requestScope.currencies }">
					                                 <option  value="${currency.getId()}">${ currency.getType().toString()}  </option>
					                                
					                                </c:forEach>
			                                   </select >
			                                  
			                                   <div class="row">
			                                   		<a style="font-size: 140%;" class="col-md-10">Balance </a>
			                                   </div>
			                                   <input type="number"  name="balance" class="form-control" min="0" step=".01" style="font-size: 130%;" placeholder="Balance"> 
			                                   
			                                  <button type="submit" rel="tooltip" title="Create" class="btn btn-success focus"  >Create Account</button>
                        					</form>
                        			</div>
                        		</div>
                               <c:forEach var="account" items="${requestScope.accounts }">
                                <div class="font-icon-list col-lg-2 col-md-3 col-sm-4 col-xs-6 col-xs-6">
                                
                                   <div class="font-icon-detail">
                                       <form action="updateAccount" method="post">
                                       <input type="hidden" name="id" value="${account.id }">
                                       <a class="text-muted" style="font-size: 130%;">${account.name}</a>
                                       <c:choose >
                                         <c:when test="${account.balance>0}"> 
                                         <input type="number" name="balance" class="form-control"  style="font-size: 130%; color:green "value="${account.balance}">
                                          </c:when>
                                         <c:otherwise > 
                                         <input type="number" name="balance" min="0" step="any" class="form-control"  style="font-size: 130%; color:red "value="${account.balance}">
                                          </c:otherwise>
                                       </c:choose> 
                                     
                                      <a style="font-size: 130%;">${account.getCurrency().getType().toString()} </a>
                                      <button type="submit" rel="tooltip" title="Update" class="btn btn-info btn-fill pull-right">Update </button>
                                      </form>
                                    </div>
                                     <form action=deleteAccount method="post">
                                     <input type="hidden" name="id" value="${account.id}">
                                    <button type="submit" rel="tooltip" title="Remove" class="btn btn-danger pull-top">Remove </button>
                                     </form>
                                </div>
                                   
                                </c:forEach>
                                 </div>
                               
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <footer class="footer">
            <div class="container-fluid">
                <nav class="pull-left">
                    <ul>
                        <li>
                            <a href="profile">
                                Home
                            </a>
                        </li>
                        <li>
                            <a href="profile">
                                Privacy Policy
                            </a>
                        </li>
                        <li>
                            <a href="https://github.com/KristiyanSelveliev/MoneyBuddiSpring">
                               GitHub
                            </a>
                        </li>
                    </ul>
                </nav>
                <p class="copyright pull-right">
                    &copy; <script>document.write(new Date().getFullYear())</script> <a href="http://www.creative-tim.com">Creative Tim</a>, made with love for a better web
                </p>
            </div>
        </footer>


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
    <script type="javascript" src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>

    <!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
	<script src="js/light-bootstrap-dashboard.js?v=1.4.0"></script>

	<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
	<script src="js/demo.js"></script>


</html>