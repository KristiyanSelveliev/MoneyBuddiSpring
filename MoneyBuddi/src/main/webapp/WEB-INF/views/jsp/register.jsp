<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>MoneyBuddi</title>
 <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="css/bootstrap.min.css">
  
  <!-- Theme style -->
  <link rel="stylesheet" href="css/MoneyBuddi.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="css/blue.css">
  

  
 
  
</head>
<body class="hold-transition register-page">
<div class="register-box">
  <div class="register-logo">
    <a ><b>Money</b>Buddi</a>
  </div>

  <div class="register-box-body">
   
    <form action="register" method="post">
      <div class="form-group has-feedback" >
        <input type="text" pattern="^[0-9a-zA-Z]{5,45}" title="Username must be at least 5 symbols" class="form-control" name="username" placeholder="Username" required>
        
      </div>
      <div class="form-group has-feedback">
        <input type="email" pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$" title="local-part@domain" class="form-control" name="email" placeholder="Email" required>
      
      </div>
      <div class="form-group has-feedback">
        <input type="password" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$" title="Strong password contains at least one digit, upper and lower case letter and special symbol." class="form-control" name="password1" placeholder="Password" required>
         
       
      </div>
      <div class="form-group has-feedback">
        <input type="password" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$" title="Strong password contains at least one digit, upper and lower case letter and special symbol." class="form-control" name="password2" placeholder="Retype password" required>
       
      </div>
       
      <div class="form-group has-feedback">
        <input type="number" pattern="(1[4-9]|[2-9][0-9]|100)" title="Age must be number between 14 and 100." class="form-control" name="age"placeholder="Age" required>
       
      </div>
      
      <div class="row">
       
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">Register</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

    
    <a href="login.html" class="text-center">I already have an account</a>
  </div>
  <!-- /.form-box -->
</div>
<!-- /.register-box -->


</body>
</html>