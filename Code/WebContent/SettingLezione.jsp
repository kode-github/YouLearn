<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="CSS/SettingLezione.css">

<script type="text/javascript" src="JS/jquery-3.3.1.js"></script>
<script type="text/javascript" src="JS/jquery-ui.js"></script>




<title>YouLearn - Gestisci qui le tue lezioni</title>
</head>
<body>



	<%@ include file="Navbar.jsp"%>

	<div class="card w-50 mx-auto">
		<div class="card-header text-center" style="font-size: 30px;">
			<h3>GESTIONE LEZIONI</h3>
		</div>
		<div class="collection">

			<%for(int i = 0; i<10; i++){ %>
			<div class="card-body item" id=<%=i+10 %>>
				<div class="float-left num"><%=i%></div>
				<div class="float-left">
					<ul id="list-lezione">
						<li id="nome-lezione" class="li-lezione">Nome Lezione</li>
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
				<button id="b" class="btn btn-outline-primary">Modifica...</button>



			</div>
<%} %>
	


		</div>
		<button id="conferma" class="btn btn-success btn-block">CONFERMA MODIFICHE</button>

	</div>

	


	Output
	<br />
	<div id="items"></div>
	-->


	<script src="JS/SettingLezione.js"></script>


</body>

</html>