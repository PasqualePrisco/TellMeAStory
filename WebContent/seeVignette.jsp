<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	Album album = (Album) request.getSession().getAttribute("album");
	Account account = (Account) session.getAttribute("account");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=ISO-8859-1"
	import="java.util.*,java.sql.*,entity.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Vignette</title>

    <!-- Bootstrap core JavaScript-->
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


    <!-- Page level plugin JavaScript-->
    <script src="vendor/chart.js/Chart.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin.min.js"></script>

 	<!-- Bootstrap core CSS-->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Page level plugin CSS-->
    <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin.css" rel="stylesheet">

<link rel="shortcut icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css" href="css/set1.css" />
	<link rel="stylesheet" type="text/css" href="css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/component.css" />
<link rel="stylesheet" type="text/css" href="css/popup.css" />
<link rel="stylesheet" type="text/css" href="css/upload.css" />
<link rel="stylesheet" type="text/css" href="css/animate.css" />
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

<script src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
 <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="http://code.jquery.com/ui/1.8.21/jquery-ui.min.js"></script>
<script src="js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="js/uploadPreview.min.js"></script>


</head>

<body id="page-top">

	<%
		if (account == null) {
			response.sendRedirect("login.jsp");
			return;
		}
	%>

 <script>
        $(function() {
          $( "#sortable" ).sortable();
          $( "#sortable" ).disableSelection();
        });
        </script>


	<script> 
	function myFunction(){
		var array = new Array();
		  $( ".num").each(function() {
		        var numero = $( this ).val();
		        array.push(numero);
		   });
	   var json = JSON.stringify(array);
		$.ajax({
			type : "POST",
			dataType: "json",
			url : "addAlbum",
			data : {
				action : "changeOrder",
				prova : json,
			},
			success : function(){
			}
		});
	};	
</script>

<script> 
$(document).ready(function(){
	$(".deleteButton").click(function(){
		var id= $(this).parent("figure").parent("li").val();
		$(this).parent("figure").parent("li").remove();
		$.ajax({
			type : "POST",
			url : "addAlbum",
			data : {
				action : "deleteV",
				idV : id,
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
					<%if(album.getTipo()==0) {%>
					<li class="breadcrumb-item"><a href="index.jsp">Storie</a></li>
					<%}
						if(album.getTipo()==1){%>
							<li class="breadcrumb-item"><a href="emozioni.jsp">Emozioni</a></li>
					<%}
							if(album.getTipo()==2){%>
									<li class="breadcrumb-item"><a href="sequenze.jsp">Sequenze</a></li>
					<%}%>
					<li class="breadcrumb-item active">Vignette</li>
				</ol>


				<!-- Gallery -->
				<div class="card mb-3 demo-3">
					<div class="card-header"> Metti in ordine le vignette <span class="floatR myMargin" title="Elimina una Vignetta"><a  id="buttonShake" data-toggle="modal" href="#" onclick="setAction('add')" ><i class="fas fa-minus-square my-fa"></i></a></span><span title="Aggiungi una Vignetta" class="floatR"><a  data-toggle="modal" href="#popup1" onclick="setAction('add')" ><i class="fas fa-plus-square my-fa"></i></a></span></div>
						<%try{
							if (album != null) {%>
						<input type='hidden' class="size" value=<%=album.getVignette().size()%> />
						<ul class="grid ui-sortable txtCenter"  id="sortable">
							<%
							Collection<?> vignette= album.getVignette();
							Iterator<?> it = vignette.iterator();
							while (it.hasNext()) {
								Vignetta v = (Vignetta) it.next();
					%>
							<li  class="ui-state-default vLi" value=<%= v.getId()%> >
							<%
							String position;
							if(album.getTipo() != 2){
							if(v.getNumero()==vignette.size()){position="Corretta";}
							else if(v.getNumero()==vignette.size()-1){position="Sbagliata";}
							else{position=v.getNumero()+"";}
							}
							else{
							position=v.getNumero()+"";
							}
								 %>
								<figure class="effect-zoe vFigure">
								<span class="deleteButton hidden">&times;</span>
								<button  style="border:0;" class="settingButton audioButton" data-toggle="modal" data-target="#audio" value=<%=v.getAudio()%> onclick="audioAdd(this)"><input type="hidden" value=<%= v.getId()%>><i class="fas fa-music"></i></button>
									<img src=<%=v.getPath()%> alt="img04">
									<p class="txtCenter"><%=position%></p>
								</figure> <input type='hidden' class="num" value=<%=v.getNumero() %> />
							</li>
							<%
							}
						}
							}catch(NullPointerException e){

					}%>
					</ul>
						<div class="card-footer small text-muted txtCenter"><button class="btn btn-primary" type="button" onclick="myFunction()">Salva ordine</button></div>
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
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">Select "Logout" below if you are ready
					to end your current session.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="log?action=logout">Logout</a>
				</div>
			</div>
		</div>
	</div>
	
		<div class="modal fade" id="popup1" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Inserisci i dati</h5>
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
								type="file" name="file" id="image-upload" />
						</div>
					</div>
					<input type="hidden" id="target" name="idAlbum" value="">
						<input type="hidden"  name="action" value="addV"><button type="submit" class="btn btn-primary">Salva</button>
				</form>
				</div></div>
			</div>
		</div>
	</div>
	
	
		<!-- Audio-->
		<div class="modal fade" id="audio" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Registra l'audio</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span id="closeAudio" aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body" id="audio-body"><div
				class="d-flex justify-content-center align-items-center container ">
						<div id="controls">
  	 						<button class="btn btn-primary" type=button id="recordButton" title="Registra un nuovo audio per questa vignetta"><i class="fas fa-microphone"></i></button>
  	 						<button class="btn btn-primary" id="stopButton" type=button id="stopButton" title="Ferma la registrazione" disabled><i class="fas fa-stop"></i></button>
  	 						<button onclick="player.play()" id="listenButton" class="btn btn-primary" type=button title="Ascolta l'audio della vignetta" disabled><i class="fas fa-play"></i></button>
  	 						<button id="saveButton" class="btn btn-primary" title="Salva il nuovo audio" disabled>Salva</button>
  	 						<button id="deleteButton" class="btn btn-danger" title="Elimina audio" disabled>Elimina</button>
    					</div>
					<div id="recordingsList">
					</div>
				</div></div>
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
    		input_field: "#image-upload",
   			 preview_box: "#image-preview",
   			 label_field: "#image-label"
  		});
			});
	</script>


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
	
	<script>
	 $('#sortable').sortable({
	        stop: function(e, ui) {
	            console.log($.map($(this).find('li'), function(el) {
	            	var size= $(".size").val();
	                var index= $(el).index();
	                <% if(album.getTipo() != 2){%>
	                if((size-1)==index){
	                	$(el).find("p").text("Corretta");	
	                }
	                else if((size-2)==index){
	                	$(el).find("p").text("Sbagliata");	
	                }
	                else{
	               	$(el).find("p").text(index+1);
	                }
	                <%} else{%>
	            	$(el).find("p").text(index+1);
	            	<%}%>
	            }));
	        }
	    });

	$("#sortable").disableSelection();
	</script>


	<script src="https://cdn.rawgit.com/mattdiamond/Recorderjs/08e7abd9/dist/recorder.js"></script>
  	<script src="js/app.js"></script>
  	
</body>

</html>

