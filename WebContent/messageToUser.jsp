<%@page import="util.GlobalConstants"%>
<html>
<head>
<title>Message</title>
 <!-- Bootstrap core CSS-->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin.css" rel="stylesheet">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

	<link rel="stylesheet" type="text/css" href="css/component.css" />
	
</head>
<body class="bg-dark">
	<div style="width: 700px; margin: auto;">
		<div class="jumbotron txtCenter">
			<p>
			<% 
             String message = (String)request.getAttribute(GlobalConstants.MESSAGE);		
			%>
			<%=message %>
			</p>
		</div>
	</div>
</body>
</html>