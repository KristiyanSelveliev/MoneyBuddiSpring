
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="icon" type="image/png" href="assets/img/favicon.ico">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>Transactions</title>

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
    
   <!--   Core JS Files   -->
    <script src="js/jquery.3.2.1.min.js" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
   
    
    <!-- User behaviour table -->
      <script src = "https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js">
      </script>
     <script src = "https://code.highcharts.com/highcharts.js"></script> 
     <script src = "https://code.highcharts.com/modules/data.js"></script>
     
     
     
     
     <script type="text/javascript" src="js/Chart.js"></script>

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
                   
                    <a class="navbar-brand" > Overview</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-left">
                        
                        <li>
                           <form action="addExpense" method="get">
                                <button type="submit" class=" btn  btn-danger " > <a1 style="font-size: 125%;">Add Expense</a1></button>
                              </form>
                        </li>
                    </ul>
                    
                    <ul class="nav navbar-nav navbar-left">
                        
                        <li>
                           <form action="addIncome" method="get">
                                <button type="submit" class=" btn  btn-success "> <a2 style="font-size: 125%;">Add Income</a2></button>
                              </form>
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
                                <h4 class="title">Transaction statistics</h4>
                                <p class="category">Last Campaign Performance</p>
                            </div>
                            <div class="content">
                            
                               
                                  <div>
                                  
                                  
                                  
                                  <canvas id="mycanvas" width="400" height="300" ></canvas>
													
													
															<script>
															var canvas = document.getElementById('mycanvas');
															var ctx = canvas.getContext('2d');
															ctx.height = 350;
															ctx.width=350;
															
															
													
													
													 
													var myChart = new Chart(ctx, {
													    type: 'doughnut',
													    data: {
													        labels: ["Income","Expense"],
													        
													        datasets: [{
													            label: '# of Votes',
													            data: [${requestScope.numIncomes }, ${requestScope.numExpenses } ],
													            backgroundColor: [
													            	'rgba(32,175,221,0.8)',
													            	'rgba(55,56,102,0.8)',
													                
													            ],
													            borderColor: [
													            	'rgba(250,250,250,0.8)',
													                'rgba(250,250,250,0.8)',
													                
													            ],
													            borderWidth: 3
													        }]
													    },
													    options: {
													    	maintainAspectRatio: false,
													        scales: {
													          
													        }       
													    }
													});
													</script>
													
											</div>		                     
                               

                                <div class="footer">
                                    <h3> <b>Expense transactions count</b><a style="color:#2C4A60; font-size:125%"> ${requestScope.numExpenses } </a></h5>
                                    <h3> <b>Income transactions count&nbsp&nbsp</b><a style="color:#1EBAE4; font-size:125%"> ${requestScope.numIncomes } </a></h5>
                                   
                               
                                </div>
                            </div>
                        </div>
                        <div class="col-md-13">
                        <div class="card ">
                            <div class="header">
                                <h4 class="title">Transaction editor</h4>
                                <p class="category">Choose date</p>
                                <input id="Edit"  type="date" ></input>
                                
                                 <button class="btn" onclick="show()">go</button>
                                
                            </div>
                            <div class="content">
                                <div id="chartActivity" class="ct-chart">
                                
                                
                                
                                </div>
                                 
								                                
                            </div>
                        </div>
                    </div>
                            
                    </div>
                         <form action="transactions" method="POST">
                         <input type="date" name=beginDate>
                          <input type="date" name=endDate>
                          <button type="submit">Show </button>
                         </form>

                    <div class="col-md-7">
                              
                      
			                                <div id = "container" style = " max-width:900px  width: 100%; height: 300px; margin: 0 auto"></div>
											      <script language = "JavaScript">
											         $(document).ready(function() {
											            var data = {
											               table: 'datatable'
											            };
											            var chart = {
											               type: 'column'
											            };
											            var title = {
											               text: 'User Behavior'   
											            };      
											            var yAxis = {
											               allowDecimals: false,
											               title: {
											                  text: 'Units'
											               }
											            };
											            var tooltip = {
											               formatter: function () {
											                  return '<b>' + this.series.name + '</b><br/>' +
											                     this.point.y + ' ' + this.point.name;
											               }
											            };
											            var credits = {
											               enabled: false
											            };  
											            var json = {};   
											            json.chart = chart; 
											            json.title = title; 
											            json.data = data;
											            json.yAxis = yAxis;
											            json.credits = credits;  
											            json.tooltip = tooltip;  
											            $('#container').highcharts(json);
											         });
											      </script>
											      <div class="scroll1">
											      <table id = "datatable"  class=" table table-hover scroll ">
											         <thead>
											            <tr>
											                <td>Date</td>
											               <td>Incomes</td>
											               <td>Expenses</td>
											               
											            </tr>
											         </thead>
											         <tbody>
											         <c:forEach  var="date" items="${requestScope.statistics.keySet() }">
											               <tr>
											                  <td>${date}</td>
											                  <td>${requestScope.statistics.get(date).getIncomeAmount() }</td>
											                  <td>${requestScope.statistics.get(date).getExpenseAmount() }</td>
											         
											         
											         </c:forEach>
											           
											         </tbody>
											      </table>
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
<style>

.scroll1 {
      width:100%;
      height:400px;
      overflow-x:scroll;
      
}


			
		
</style>
 
</body>
      <script>
        function show(){
        	var body = document.getElementById("chartActivity");
        	var pole=document.getElementById("Edit");
        	var date=pole.value;
        	var request=new XMLHttpRequest();
        	request.open("GET","showTransactions?date=" +date);
        	request.onreadystatechange = function() {
        		if(this.readyState == 4 && this.status == 200){
        			var result = this.responseText;
        			result = JSON.parse(result);
        			body.innerHTML ="";
        			var select='<select id="mySelect" style="max-width:300px; width:75%" >';
        			for(var i=0;i<result.length;i++){
        				var option='<option value='+result[i]['id']+'>';
        				option+=result[i]['category']+" "+result[i]['amount']+" "+result[i]['account']+" ";
        				option+=result[i]['type'];
        				option+='</option>';
        				
        				select+=option;
        					
        			}
        			select+='</select>';
        			select+='<button onclick="edit()" class="btn ">Edit</button>';
        			body.innerHTML+=select;
        				
        		}
        	}
        	request.send();
        	   	
        }
        
        function edit(){
        	var body = document.getElementById("chartActivity");
        	var select = document.getElementById("mySelect");
        	
        	var transactionId=select.options[select.selectedIndex].value;
        
        	console.log(transactionId);
        	var request=new XMLHttpRequest();
        	request.open("GET","editTransaction?transactionId=" +transactionId);
        	request.onreadystatechange = function() {
        		if(this.readyState == 4 && this.status == 200){
        			var result = this.responseText;
        			result = JSON.parse(result);
        	        var form='<form action="updateTransaction" method="post"><div class="col-md-10"><div class="form-group">';
        	        form+='<label  style="color:black"><b>'+result["category"] +"-"+result["type"] +'</b></label></div></div>';
        	        form+='<div class="col-md-10"><div class="form-group"> <label style="color:black"><b> Account ('+result["account"]+')</b></label></div></div>';
        		    form+='<div class="col-md-10"><div class="form-group"><label style="color:black"><b>Amount<b></label>';
                    form+='<input  name="amount" class="form-control" value='+ result["amount"] +'> </div></div>';
        		    form+='<button type="submit" class="btn btn-fill" style=" background-color:rgba(55,56,102,0.8)">Update</button> </form>';
        			        			body.innerHTML+=form;
        				
        		}
        	}
        	request.send();
        	   	
        }
        
     
      
      </script>

	<!--  Charts Plugin -->
	<script src="js/chartist.min.js"></script>

    <!--  Notifications Plugin    -->
    <script src="js/bootstrap-notify.js"></script>

    
 
	<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
	<script src="js/light-bootstrap-dashboard.js?v=1.4.0"></script>
    
	

</html>
