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
                    <a class="navbar-brand" href="#">Budgets</a>
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
                    <div class="col-md-12">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Your budgets</h4>
                            </div>
                            <div class="content all-icons">
                                <div class="row">
                                 <div class="font-icon-list col-lg-2 col-md-3 col-sm-4 col-xs-6 col-xs-6">
                                 	<div class="container-fluid">
			                             <form action="budgetCreate" method="post">    
			                                 <div class="row">
			                                <a style="font-size: 140%;" class="col-md-10">Categories </a><br>
			                                </div>
			                                   <select  class="col-md-8" name="categoryId">
			                                  
					                                <c:forEach var="category" items="${requestScope.categories }">
					                                 <option value="${category.getId()}">${ category.getCategory() }-${category.getType().toString()} </option>
					                                
					                                </c:forEach>
			                                   </select >
			                                   <div class="row">
			                                    <a style="font-size: 140%;" class="col-md-10" >Currencies </a>
			                                    </div>
			                                  
			                                   <select class="col-md-8" name="currencyId" >
			                                
					                                <c:forEach var="currency" items="${requestScope.currencies }">
					                                 <option  value="${currency.getId()}">${ currency.getType().toString()}  </option>
					                                
					                                </c:forEach>
			                                   </select >
			                                  
			                                   <div class="row">
			                                   		<a style="font-size: 140%;" class="col-md-10">Amount </a>
			                                   </div>
			                                   <input type="number"  name="amount" class="form-control"  style="font-size: 130%;" value="100" required> 
			                                    <div class="row">
			                                   		<a style="font-size: 125%;" class="col-md-10">Begin </a>
			                                   </div>
			                                   <input type="date"  name="begin" class="form-control"  style="font-size: 130%;" required> 
			                                   <div class="row">
			                                  		 <a style="font-size: 125%;" class="col-md-10">End </a>
			                                   </div>
			                                   	<input type="date"  name="end" class="form-control"  style="font-size: 130%;" required> 
			                                   
			                                  <button type="submit" rel="tooltip" title="Create" class="btn btn-success focus"  >Create </button>
                        					</form>
                        			</div>
                        		</div>
                        		
                                 <div class="content table-responsive">
                              <div class="scroll1">
                                <table id="table2" class="table table-responsive table-hover " >
                                 <colgroup span="2"></colgroup>
                                    <tr>
                                        
                                    	<th>Category</th>
                                    	<th>Type</th>
                                    	<th>Amount</th>
                                    	<th>Currency</th>
                                    	<th>DeadLine</th>
                                    	<th  colspan="2" scope="colgroup" ></th>
                                    	
                                    	
                                    </tr>
                                    <tbody  >
                                      <c:forEach var="budget" items="${requestScope.budgets }">
                                        <tr>
                                      <form  action="budgetUpdate" method="post">
                                       <input type="hidden" name="id" value="${budget.getId()}">
                                        
                                           
                                        	<td>${budget.getCategory().getCategory().toString()}</td>
                                        	
                                        	<c:choose>
                                        	<c:when test="${budget.getCategory().getType().toString()=='INCOME'}">
                                        	<td style="color:green">${budget.getCategory().getType().toString()}</td>
                                        	</c:when>
                                        	<c:otherwise>
                                        	<td style="color:red">${budget.getCategory().getType().toString()}</td>
                                        	</c:otherwise>
                                        	</c:choose>
                                        	
                                        	
                                        	<td>+<a style="color:green"><input type="value" style="max-width:70px" name="amount" value="${Math.round( budget.getAmount()* 100) / 100}"></a></td>
                                        	<td>${budget.getCurrency().getType().toString()}</td>
                                        	<c:choose>
                                        	     <c:when test="${budget.getEndDate().compareTo(requestScope.now)>3}">
                                        	     <td style="color:green"><input type="value" name="duration" style="max-width:25px" value="${budget.getEndDate().compareTo(requestScope.now)}">&nbspdays left</td>
                                        	     </c:when>
                                        	     <c:when test="${budget.getEndDate().compareTo(requestScope.now)==1}">
                                        	     <td style="color:red"><input type="value" name="duration" style="max-width:25px" value="${budget.getEndDate().compareTo(requestScope.now)}">&nbspday left</td>
                                        	     </c:when>
                                        	     <c:otherwise>
                                        	     <td style="color:red"><input type="value "name="duration" style="max-width:25px" value="${budget.getEndDate().compareTo(requestScope.now)}">&nbspdays left</td>
                                        	     </c:otherwise>
                                        	</c:choose>
                                        	
                                        	<td style="padding-right:0px;"><button type="submit" class="btn btn-info btn-fill ">Update</button></td>
                                          
                                         </form>
                                         <form action="budgetDelete" method="post">
                                         <input type="hidden" name="id" value="${budget.getId() }"> 
                                           <td style="padding-right:5px; padding-left:0px"><button type="submit" class="btn btn-danger btn-fill">Delete</button></td>
                                        	</form>
                                          </tr>
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
                
            </div>
        </footer>
        </div>

    </div>
</div>
<style>

        
 .scroll1 {
      width=100%;
      max-width:1200px;
      height:500px;
      overflow-y:scroll;
      
}


</style>


</body>

<script>

function deleteBudget(id){
	
	console.log(id);
	var request=new XMLHttpRequest();
	request.open("Post","budgetDelete?id="+id);
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
    <script type="javascript" src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>

    <!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
	<script src="js/light-bootstrap-dashboard.js?v=1.4.0"></script>

	<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
	<script src="js/demo.js"></script>


</html>