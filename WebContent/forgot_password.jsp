<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<% Account account = (Account) session.getAttribute("account"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html; charset=ISO-8859-1"
	import="java.util.*,java.sql.*,entity.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password</title>

   <!-- Bootstrap core CSS-->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin.css" rel="stylesheet">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">


</head>
<body class="bg-dark">

    <div class="container">
      <div class="card card-login mx-auto mt-5">
        <div class="card-header">Inserisci email</div>
        <div class="card-body">
          <form id="formControl" class="login-form" method="post" action="fp">
            <div class="form-group">
              <div class="form-label-group">
                <input type="email" name="inputEmail" id="inputEmail" class="form-control" placeholder="Email address"  autofocus="autofocus"
                 required>
                <label for="inputEmail">Email address</label>
              </div>
            </div>
            <button id="btnControl" class="btn btn-primary btn-block">Recupera password</button>
          </form>
          <div class="text-center">
            <a class="d-block small mt-3" href="register.jsp">Registra un Account</a>
           <!-- <a class="d-block small" href="forgot-password.html">Non ricordi la Password?</a> -->
          </div>
        </div>
      </div>
    </div>
    
     <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
     
   
</body>
</html>