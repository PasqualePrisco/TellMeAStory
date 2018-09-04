<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	Collection<?> albums = (Collection<?>) session.getAttribute("albums");
	Account account = (Account) session.getAttribute("account");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=ISO-8859-1"
	import="java.util.*,java.sql.*,entity.*"%>
<head>

<%@ include file="meta.jsp" %> 

<title>Emozioni</title>

<%@ include file="header.jsp" %> 

</head>

<body id="page-top">

<%
		if (account == null) {
			response.sendRedirect("login.jsp");
			return;
		}

	%>
	
	<script> 
$(document).ready(function(){
	$(".deleteButton").click(function(){
		var id= $(this).parent("figure").parent("li").val();
		$(this).parent("figure").parent("li").remove();
		$.ajax({
			type : "POST",
			url : "addAlbum",
			data : {
				action : "deleteA",
				idA : id,
			},
			success : function(){
			}
		});
	});	
});	
</script>

	<%@ include file="navbar.jsp" %>

	<div id="wrapper">

		<!-- Sidebar -->
		<%@ include file="sidebar.jsp" %>
		
		<div id="content-wrapper">

			<div class="container-fluid">

				<!-- Breadcrumbs-->
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="emozioni.jsp">Emozioni</a></li>
					<li class="breadcrumb-item active">Overview</li>
				</ol>


				<!-- Gallery -->
				<div class="card mb-3 demo-3">
				<div class="card-header" id="head"> Emozioni  <span class="floatR myMargin" title="Elimina un Album"><a  id="buttonShake" data-toggle="modal" href="#" onclick="setAction('add')" ><i class="fas fa-minus-square my-fa"></i></a></span><span title="Aggiungi un album" class="floatR"><a  data-toggle="modal" href="#popup1" onclick="setAction('add')" ><i class="fas fa-plus-square my-fa"></i></a></span></div>
				<ul class="grid txtCenter">
					<%
						if (albums != null && albums.size() != 0) {
							Iterator<?> it = albums.iterator();
							while (it.hasNext()) {
								Album bean = (Album) it.next();
								if(bean.getTipo()==1){
					%>
					<li value=<%= bean.getId()%>>
						<figure class="effect-zoe">
						<span class="deleteButton hidden">&times;</span>
							<img src=<%=bean.getPath()%> class="hov" alt=<%=bean.getNome()%>>
							<figcaption>
								<h3><%=bean.getNome()%></h3> 
								<a class="linkButton" href="addAlbum?action=getAlbum&param=<%=bean.getId()%>">Gestisci</a>
								<a class="linkButton modifyButton"  data-toggle="modal" data-target="#popup1"  onclick="setAction('modifyAlbum'), setTarget(<%=bean.getId()%>)">Modifica</a>
							</figcaption>
						</figure>
					</li>
					<%
								}
						}
						}
					%>
				</ul>
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
				<div class="modal-body"><div
				class="d-flex justify-content-center align-items-center container ">
				<form class="txtCenter" method="post" action="addAlbum"
					enctype="multipart/form-data">
					<div class="form-group">
						<div id="image-preview">
							<label for="image-upload" id="image-label">Choose File</label> <input
								type="file" name="file" id="image-upload" pattern="^\.|\.jpg$|\.gif$|.png$" />
						</div>
					</div>
					<div class="form-group">
					<input type="hidden" id="target" name="idAlbum" value="">
						<input type="hidden" id="act" name="action" value="modifyAlbum"> <input
							class="form-control" name="nome" type="text" maxlength="20"
							required placeholder="Nome">
							<input type="hidden" name="type" value="1">
					</div>
					<button type="submit" class="btn btn-primary">Salva</button>
				</form>
				</div></div>
			</div>
		</div>
	</div>



	<!-- Bootstrap core JavaScript-->
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="js/sb-admin.min.js"></script>
	
	<script>
	$(document).ready(function(){
	$("#buttonShake").click(function(){
		if($(".deleteButton").hasClass("hidden")){
			$(".deleteButton").removeClass("hidden");
		}
		else{
			$(".deleteButton").addClass("hidden");
		}
		});
	});
	</script>


	<script type="text/javascript">
	$(document).ready(function() {
  		$.uploadPreview({
    		input_field: "#image-upload",
   			 preview_box: "#image-preview",
   			 label_field: "#image-label"
  		});
			});
	</script>
	
	
	<script>
	function setTarget(idAlbum){
		document.getElementById("target").value = idAlbum;
	}
	</script>

<script>
	function setAction(action){
		document.getElementById("act").value = action;
	}
	</script>

<script src="js/toucheffects.js"></script>
</body>

</html>