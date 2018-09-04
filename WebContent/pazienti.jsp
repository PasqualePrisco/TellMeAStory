<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	Collection<?> albums = (Collection<?>) session.getAttribute("albums");
	Account account = (Account) session.getAttribute("account");
	Collection<?> pazienti = (Collection<?>) session.getAttribute("pazienti");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=ISO-8859-1"
	import="java.util.*,java.sql.*,entity.*"%>

<head>
<%@ include file="meta.jsp" %>

<title>Pazienti</title>

<%@ include file="header.jsp" %>


</head>

<body id="page-top">

	<%
		if (account == null) {
			response.sendRedirect("login.jsp");
			return;
		}

	%>


	<%@ include file="navbar.jsp" %>

	<div id="wrapper">

		<!-- Sidebar -->
		<%@ include file="sidebar.jsp" %>

		<div id="content-wrapper">

			<div class="container-fluid">

				<!-- Breadcrumbs-->
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="pazienti.jsp">Pazienti</a></li>
					<li class="breadcrumb-item active">Overview</li>
				</ol>


          <!-- DataTables Example -->
          <div class="card mb-3">
            <div class="card-header" id="head"> Pazienti <span class="floatR"><a  data-toggle="modal" href="#popup1" onclick="setAction('add')" >Nuovo Paziente</a></span></div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Nome</th>
                      <th>Cognome</th>
                      <th>Email</th>
                      <th>Password</th>
                      <th style="border-right:  none;"></th>
                      <th style="border-left:  none;"></th>
                    </tr>
                  </thead>
                  <tbody>
                  <%
						if (pazienti != null && pazienti.size() != 0) {
							Iterator<?> it = pazienti.iterator();
							while (it.hasNext()) {
								Paziente p = (Paziente) it.next();
					%>
                    <tr>
                      <td><%=p.getNome() %></td>
                      <td><%=p.getCognome() %></td>
                      <td><%=p.getEmail() %></td>
                      <td><%=p.getPassword() %></td>
                      <td class="txtCenter" style="width: 82px;border-right:  none; padding-right: .5rem; padding-left: .5rem;"><a class="tableButton" href="pazienteC?action=getPaziente&id=<%=p.getId()%>">Visualizza</a></td>
                      <td class="txtCenter" style="width: 82px;border-left:  none;padding-right: .5rem;padding-left: .5rem;"><a class="tableButton" style="background:#ed4e6e;color:  none;"  href="pazienteC?action=deletePaziente&id=<%=p.getId()%>">Rimuovi</a></td>
                    </tr>
                    <% }
							}%>
                  </tbody>
                </table>
              </div>
				</div>
			</div>


			<!-- /.container-fluid -->

			<!-- Sticky Footer -->
			<footer class="sticky-footer">
				<div class="container my-auto">
					<div class="copyright text-center my-auto">
						<span>Copyright © Your Website 2018</span>
					</div>
				</div>
			</footer>

		</div>
		<!-- /.content-wrapper -->

	</div>
	<!-- /#wrapper -->


	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>

	<!-- Logout Modal-->
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Sicuro di voler uscire ?</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">Seleziona "Logout" se intendi terminare la sessione.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="log?action=logout">Logout</a>
				</div>
			</div>
		</div>
	</div>


	<!-- Modify&Insert Modal-->
	<div class="modal fade" id="popup1" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Inserisci Dati</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body"><div class="d-flex justify-content-center align-items-center container ">
				<form class="txtCenter" method="post" action="pazienteC">
					<div class="form-group">
						<input type="hidden" name="action" value="addPaziente"> <input
							class="form-control" name="nome" type="text" maxlength="100"
							required placeholder="Nome">
					</div>
						<div class="form-group">
						<input type="hidden" name="action" value="addPaziente"> <input
							class="form-control" name="cognome" type="text" maxlength="100"
							required placeholder="Cognome">
					</div>
						<div class="form-group">
						<input type="hidden" name="action" value="addPaziente"> <input
							class="form-control" name="email" type="text" maxlength="100"
							required placeholder="Email">
					</div>
						<div class="form-group">
						<input type="hidden" name="action" value="addPaziente"> <input
							class="form-control" name="password" type="text" maxlength="100"
							required placeholder="Password">
					</div>
					<button type="submit" class="btn btn-primary">Salva</button>
				</form>
				</div>
				</div></div>
			</div>
		</div>



	<!-- Bootstrap core JavaScript-->
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="js/sb-admin.min.js"></script>


	<script type="text/javascript">
	$(document).ready(function() {
  		$.uploadPreview({
    		input_field: "#image-upload",
   			 preview_box: "#image-preview",
   			 label_field: "#image-label"
  		});
			});
	</script>
	
	


<script src="js/toucheffects.js"></script>
</body>

</html>