<%@page import="java.util.Collection"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="bean.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Bootstrap-->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<!-- CSS Style & Font-->
<link rel="stylesheet" href="CSS/Lezione.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css?family=Permanent+Marker|Ubuntu"
	rel="stylesheet">


<script src="build/mediaelement-and-player.min.js"></script>
<link rel="stylesheet" href="build/mediaelementplayer.css" />
<script src="build/custom.js"></script>

<title>YouLearn</title>
</head>
<body>

	<!-- PER QUESTI PARAMETRI HANNO VALORE TRUE O FALSE (COME STRINGHE)
		commentoInserito
		cancCommento
		SONO IN SESSIONE, DOPO AVERLI RECUPERATI RIMUOVILI DALLA SESSIONE
		VEDI ESEMPI ALTRE PAGINE, TIPO CORSO
	 -->

	<%@include file="Navbar.jsp"%>

	<%
		String idLezione = request.getParameter("idLezione");
		if (idLezione == null) {
			//O una pagina di errore
			response.sendRedirect(request.getContextPath() + "/Welcome.jsp");
			return;
		}

		CorsoBean corso = (CorsoBean) request.getSession().getAttribute("corso");
		if (corso == null) {
			//O pagina di errore?
			response.sendRedirect(request.getContextPath() + "/Welcome.jsp");
			return;
		}
		LezioneBean lezione = (LezioneBean) request.getSession().getAttribute("lezione");
		if (lezione == null) {
			response.sendRedirect(request.getContextPath() + "/VisualLezioneServlet?idLezione=" + idLezione);
			return;
		} else {
			request.getSession().removeAttribute("lezione");
		}
		//Ora abbiamo il corso da cui proviene la lezione e la lezione
	%>
	<div class="videocontent">
		<video id="my_video_1" style="width: 100%%; height: 100%%;"
			controls="controls" width="100%" height="100%" preload="auto"
			poster="Images/black.jpg">
			<source
				src="Resources\<%=corso.getIdCorso()%>\Lezioni\<%=lezione.getFilePath()%>"
				type='video/mp4'>
		</video>
	</div>

	<div class="card w-75 mx-auto text-center">
		<div class="card-header text-center"><%=lezione.getNome().toUpperCase()%></div>
		<div class="card-body">
			<h5 class="card-title">
				<i class="fas fa-user fa-2x"></i><%=lezione.getCorso().getDocente().getNome() + " " + lezione.getCorso().getDocente().getCognome()%></h5>
		</div>
		<div class="card-body">
			<h5 class="card-title">
				<i class="far fa-laugh-beam fa-2x"></i>
				<%=lezione.getVisualizzazioni()%></h5>
		</div>
	</div>

	<div style="margin-bottom: 20px;" class="card w-75 mx-auto text-center">
		<div class="card-header text-center">COMMENTI</div>
		<form name="formCommento" action="YouLearn/InsCommentoServlet"
			method="post" onsubmit="return validateCommento(formCommento)">
			<div class="card-body">

				<textarea id="txtarea" name="testoCommento" id="txtArea"
					placeholder="Inserisci qui il tuo commento!" class="text-center"></textarea>
			</div>
			<div>
				<button type="submit" id="btnTextArea" style="margin: 0px 5px 10px;"
					formaction="http://localhost:8080/YouLearn/InsCommentoServlet?idLezione=<%=lezione.getIdLezione()%>"
					class="btn float-right btn-success">AGGIUNGI COMMENTO</button>
			</div>
		</form>
		<%
			Collection<CommentoBean> lista = (Collection<CommentoBean>) lezione.getCommenti();
		%>



		<%
			if (lista.isEmpty()) {
		%>
		<div style="margin: 10px 20px 0px;" class="card-header rounded border">COMMENTI
			DEL DOCENTE:</div>
		<div class="card-body">
			<div id="com-doc" class=" card-body rounded border border-success">
				Non ci sono commenti da parte del Docente <b><%=lezione.getCorso().getDocente().getNome()%>
					<%=lezione.getCorso().getDocente().getCognome()%></b>
			</div>
		</div>
		<div style="margin: 0px 20px;" class="card-header rounded border">COMMENTI
			DEGLI UTENTI:</div>

		<div class="card-body">
			<div id="com-doc" class=" card-body rounded border border-primary">
				Non ci sono commenti da parte degli Utenti, <b><%=account.getNome()%></b>
				scrivi tu il primo commento!
			</div>
		</div>
		<%
			} else {
		%>
		<div style="margin: 10px 20px 0px;" class="card-header rounded border">COMMENTI
			DEL DOCENTE:</div>
		<div class="card-body">


			<%
				int x = 0;
					for (CommentoBean c : lista) {

						if (c.getAccountCreatore().getMail().equals(lezione.getCorso().getDocente().getMail())) {
			%>


			<!--  <div class="legend1"c.getAccountCreatore().getNome()e() %></div>-->
			<!--  <fieldset><%=c.getTesto()%></fieldset> -->
			<section style="margin-bottom: 10px;">
				<fieldset>
					<legend class="mr-auto"
						style="text-align: left; margin-left: 15px;">
						<div class="text-center">
							<b> Docente <%=c.getAccountCreatore().getNome()%></b>
						</div>
					</legend>
					<label> <%=c.getTesto()%>
					<%
						if (c.getLezione().getCorso().getDocente().getMail().equals(account.getMail())) {
					%>
					</label> <label style="float: right; margin-right: 15px;"><a
						onclick="confirm('Sei sicuro di voler cancellare il commento?')"
						href="http://localhost:8080/YouLearn/CancCommentoServlet?idCommento=<%=c.getIdCommento()%>&idLezione=<%=c.getLezione().getIdLezione()%>">
							<i style="color: red;" class="fas fa-times"></i>
					</a></label>
					<%} %>
				</fieldset>
			</section>



			<!-- <div id="com-doc" class=" card-body rounded border border-success"><%=c.getTesto()%></div> -->
			<%
				x = 1;

						}
					}
					if (x == 0) {
			%>

			<div class="card-body">
				<div id="com-doc" class=" card-body rounded border border-success ">
					Non ci sono commenti da parte del Docente <b><%=lezione.getCorso().getDocente().getNome()%>
						<%=lezione.getCorso().getDocente().getCognome()%></b>
				</div>
			</div>

			<%
				}
			%>

		</div>

		<div style="margin: 0px 20px;" class="card-header rounded border">COMMENTI
			DEGLI UTENTI:</div>

		<div class="card-body">
			<%
				int y = 0;
					for (CommentoBean c : lista) {

						if (!c.getAccountCreatore().getMail().equals(lezione.getCorso().getDocente().getMail())) {
			%>

			<section style="margin-bottom: 10px;">
				<fieldset>
					<legend class="mr-auto"
						style="text-align: left; margin-left: 15px;">
						<div class="text-center">
							<b><%=c.getAccountCreatore().getNome()%></b>
						</div>
					</legend>
					<label> <%=c.getTesto()%>
					</label>
					<%
						if (c.getLezione().getCorso().getDocente().getMail().equals(account.getMail()) || c.getAccountCreatore().getMail().equals(account.getMail())) {
					%>
					<label style="float: right; margin-right: 15px;"><a onclick="confirm('Sei sicuro di voler cancellare il commento?')"
						href="http://localhost:8080/YouLearn/CancCommentoServlet?idCommento=<%=c.getIdCommento()%>&idLezione=<%=c.getLezione().getIdLezione()%>">
						<i style="color: red;" class="fas fa-times"></i></a></label>
					<%
						}
					%>
				</fieldset>
			</section>

			<!-- <div id="com-doc" class=" card-body rounded border border-success"><%=c.getTesto()%></div> -->

			<%
				y = 1;
						}
					}
					if (y == 0) {
			%>

			<div id="com-doc" class=" card-body rounded border border-primary">
				Non ci sono commenti da parte degli Utenti, <b><%=account.getNome()%></b>
				scrivi tu il primo commento!
			</div>

			<%
				}

				}
			%>

		</div>

	</div>







	<%@ include file="Footer.jsp"%>

	<script src="JS/Lezione.js"></script>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
		crossorigin="anonymous"></script>
	<script src="JS/alertify.js-0.3.11/lib/alertify.min.js"></script>

</body>
</html>