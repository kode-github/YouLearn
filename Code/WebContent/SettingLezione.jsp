<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="bean.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="CSS/SettingLezione.css">

<script type="text/javascript" src="JS/jquery-3.3.1.js"></script>
<script type="text/javascript" src="JS/jquery-ui.js"></script>
   <link href="https://transloadit.edgly.net/releases/uppy/v0.29.1/dist/uppy.min.css" rel="stylesheet">





<title>YouLearn - Gestisci qui le tue lezioni</title>
</head>
<body>

	<%@ include file="Navbar.jsp"%>
	
	<!-- BLOCCARE LA POSSIBILITA' DI MODIFICARE L'ORDINE DURANTE E DOPO INSERIMENTO -->

	<%
	int idCorso=0;
	CorsoBean corso=null;
	if(request.getParameter("idCorso")!=null){
		idCorso=Integer.parseInt(request.getParameter("idCorso"));
		corso=account.getCorsoTenuto(idCorso);
	}
	
	//Se ho inserito delle lezioni o modificato l'ordine,ricarico
	String updated=(String)request.getSession().getAttribute("updated");
	if(updated!=null){
		response.sendRedirect(request.getContextPath()+"\\GetLezioniServlet");
		return;
	}

		
%>

	<div class="card w-50 mx-auto">
		<div class="card-header text-left" style="font-size: 30px;">
			<h3>
				GESTIONE LEZIONI
				<button onclick="" id="insert"
					class="btn btn-outline-success float-right">Inserisci
					lezioni</button>
				<button style="margin: 0px 10px;" onclick="modify()" id="b"
					class="btn btn-outline-primary float-right">Modifica...</button>
			</h3>
		</div>
		
		
		<div class="collection">
		
			
			<%
						for (int i = 0; i < 10; i++) {
					%>
			<div class="card-body item" id=<%=i + 10%>>
				<div class="float-left num"><%=i + 1%></div>
				<div class="float-left">
					<ul id="list-lezione">
						<li id="nome" class="nome-lezione li-lezione">Nome Lezione</li>
						<li id="file-lezione" class="li-lezione">Nome file</li>
					</ul>
				</div>
				<div class="float-right	 Commands">
					<button id="b-UD" class=" btn btn-outline-secondary b-up"
						value='up'>
						<i class="fas fa-arrow-up"></i>
					</button>
					<button style="margin-top: 5px;" id="b-UD"
						class="btn btn-outline-secondary b-up" value='down'>
						<i class="fas fa-arrow-down"></i>
					</button>
				</div>



			</div>
			<%
						}
					%>



		</div>
		<button onclick="UploadResult()" id="conferma"
			class="btn btn-success btn-block d-none">CONFERMA MODIFICHE</button>
	</div>
	



	<script src="JS/SettingLezione.js"></script>
	<script src="https://transloadit.edgly.net/releases/uppy/v0.29.1/dist/uppy.min.js"></script>
    <!-- <script>
      var uppy = Uppy.Core()
        .use(Uppy.Dashboard, {
          inline: true,
          target: '#drag-drop-area'
        })
        .use(Uppy.Tus, {endpoint: 'https://master.tus.io/files/'})

      uppy.on('complete', function result() {
        console.log('Upload complete! We’ve uploaded these files:', result.successful)
      })
    </script> -->


</body>

</html>