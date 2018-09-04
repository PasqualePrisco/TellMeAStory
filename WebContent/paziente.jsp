<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	Collection<?> albums = (Collection<?>) session.getAttribute("albums");
	Account account = (Account) session.getAttribute("account");
	Collection<?> pazienti = (Collection<?>) session.getAttribute("pazienti");
	Paziente p = (Paziente) request.getAttribute("paziente");
	Collection<?> associatedAlbum = (Collection<?>) request.getAttribute("associatedAlbum");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=ISO-8859-1"
	import="java.util.*,java.sql.*,entity.*"%>

<head>
<%@ include file="meta.jsp" %>

<title>Paziente</title>

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
					<li class="breadcrumb-item active">Paziente</li>
				</ol>

				<!-- Nome-Cognome Paziente -->
				<h3 style="margin-bottom: 20px;" class="title"><%=p.getNome() + " " + p.getCognome()%></h3>

				<!-- DataTables Example -->
				<div class="card mb-3">
					<div class="card-header" id="head">
						Album associati <span class="floatR"><a data-toggle="modal"
							href="#popup1" onclick="setAction('add')">Associa Album</a></span>
					</div>
					<div class="card-body">
					<h6 style="margin-bottom: 20px; margin-top: 20px;">Storie</h6>
					<ul class="list-group list-group-flush">
							<%
										if (associatedAlbum != null && associatedAlbum.size() != 0) {
											Iterator<?> it = associatedAlbum.iterator();
											while (it.hasNext()) {
												Album b = (Album) it.next();
									%>
									
						    <% if (b.getTipo() == 0) {	%>
							<li class="list-group-item d-flex justify-content-between align-items-center">
							<%=b.getNome() %> <span class="badge badge-danger badge-pill"><a href="pazienteC?action=deleteA&id=<%= b.getId()%>&idPaziente=<%=p.getId()%>">
								<i style="color: #fff;" class="fas fa-trash-alt"></i></a></span>
							</li>
							<%}
						    }
						    }%>
						</ul>
						<h6 style="margin-bottom: 20px; margin-top: 20px;">Emozioni</h6>
						<ul class="list-group list-group-flush">
							<%
										if (associatedAlbum != null && associatedAlbum.size() != 0) {
											Iterator<?> it = associatedAlbum.iterator();
											while (it.hasNext()) {
												Album b = (Album) it.next();
									%>
									
						    <% if (b.getTipo() == 1) {	%>
							<li class="list-group-item d-flex justify-content-between align-items-center"><%=b.getNome() %>
							 <span class="badge badge-danger badge-pill"><a href="pazienteC?action=deleteA&id=<%= b.getId()%>&idPaziente=<%=p.getId()%>"><i style="color: #fff;" class="fas fa-trash-alt"></i></a></span>
							</li>
								<%}
						    }
						    }%>
						</ul>
						<h6 style="margin-bottom: 20px; margin-top: 20px;">Sequenze</h6>
						<ul class="list-group list-group-flush">
							<%
										if (associatedAlbum != null && associatedAlbum.size() != 0) {
											Iterator<?> it = associatedAlbum.iterator();
											while (it.hasNext()) {
												Album b = (Album) it.next();
									%>
									
						    <% if (b.getTipo() == 2) {	%>
							<li class="list-group-item d-flex justify-content-between align-items-center"><%=b.getNome() %> <span class="badge badge-danger badge-pill"><a href="pazienteC?action=deleteA&id=<%= b.getId()%>&idPaziente=<%=p.getId()%>"><i style="color: #fff;" class="fas fa-trash-alt"></i></a></span>
							</li>
								<%}
						    }
						    }%>
						</ul>
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
					<h5 class="modal-title" id="exampleModalLabel">Sicuro di voler
						uscire ?</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">Seleziona "Logout" se intendi
					terminare la sessione.</div>
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
					<h5 class="modal-title" id="exampleModalLabel">Scegli album</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="justify-content-center align-items-center container ">
						<form class="txtCenter" method="post" action="pazienteC">
							<div class="input-group mb-4">
								<select class="custom-select" id="inputGroupSelect02" name="id" required>
									<option selected>Clicca per scegliere..</option>
									<%
										if (albums != null && albums.size() != 0) {
											Iterator<?> it = albums.iterator();
											int i=0,j=0,z=0;
											while (it.hasNext()) {
												Album bean = (Album) it.next();
												if (bean.getTipo() == 0) {
									%>
									<%if(i==0){%><option disabled>STORIE</option><%} i=1; %>
									<option value="<%=bean.getId()%>"><%=bean.getNome()%></option>
									<%
										}
										if (bean.getTipo() == 1) {%>
										<%if(j==0){%><option disabled>EMOZIONI</option><%} j=1; %>
										<option value="<%=bean.getId()%>"><%=bean.getNome()%></option>
										<%} 
										if (bean.getTipo() == 2) {%>
										<%if(z==0){%><option disabled>SEQUENZE</option><%} z=1; %>
										<option value="<%=bean.getId()%>"><%=bean.getNome()%></option>		
										<% 
										}
											}
										}
									%>
								</select>
								<input type="hidden" name="action" value="doAssociate">
								<input type="hidden" name="idP" value="<%=p.getId()%>">
							</div>
							<button type="submit" class="btn btn-primary">Associa</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>



	<!-- Bootstrap core JavaScript-->
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="js/sb-admin.min.js"></script>


	<script type="text/javascript">
		$(document).ready(function() {
			$.uploadPreview({
				input_field : "#image-upload",
				preview_box : "#image-preview",
				label_field : "#image-label"
			});
		});
	</script>




	<script src="js/toucheffects.js"></script>
</body>

</html>