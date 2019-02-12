<%@page import="java.util.Comparator"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="bean.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="CSS/SettingLezione.css">
<link rel="stylesheet" href="CSS/NewFile.css">
<script type="text/javascript" src="JS/jquery-3.3.1.js"></script>
<script type="text/javascript" src="JS/jquery-ui.js"></script>







<title>YouLearn - Gestisci qui le tue lezioni</title>
</head>
<body>

	<%@ include file="Navbar.jsp"%>

	<!-- BLOCCARE LA POSSIBILITA' DI MODIFICARE L'ORDINE DURANTE E DOPO INSERIMENTO -->

	<%
		int idCorso = 0;
		CorsoBean corso = null;
		if (request.getParameter("idCorso") != null) {
			idCorso = Integer.parseInt(request.getParameter("idCorso"));
		}

		//Se ho inserito delle lezioni o modificato l'ordine,ricarico
		String updated = (String) request.getSession().getAttribute("updated");
		if (updated != null) {
			response.sendRedirect(request.getContextPath() + "\\GetLezioniServlet?idCorso=" + idCorso);
			return;
		}
		corso = account.getCorsoTenuto(idCorso);
	%>

	<div class="card w-75 mx-auto">
		<div class="card-header text-left" style="font-size: 30px;">
			<h3>
				GESTIONE LEZIONI <a href="#insLezione"><button onclick=""
						id="insert" class="btn btn-outline-success float-right">Inserisci
						lezioni</button></a>
			</h3>
		</div>


		<%
			LinkedList<LezioneBean> lezioni = (LinkedList<LezioneBean>) corso.getLezioni();
			lezioni.sort(new Comparator<LezioneBean>() {
				public int compare(LezioneBean b1, LezioneBean b2) {
					if (b1.getNumeroLezione() > b2.getNumeroLezione())
						return 1;
					if (b1.getNumeroLezione() < b2.getNumeroLezione())
						return -1;
					return 0;
				}
			});
		%>

		<div class="collection">


			<%
				if (lezioni.isEmpty()) {
			%>

			<div class="card-body item">
				<div class="float-left num"></div>
				<div class="float-left">
					<p>Non ci sono lezioni in questo corso, aggiungile!</p>
				</div>
			</div>


			<%
				} else {

					for (LezioneBean l : lezioni) {
			%>


			<div class="card-body item" id=<%=l.getIdLezione()%>>
				<form method="POST" enctype='multipart/form-data'>
					<div class="float-left num"><%=l.getNumeroLezione()%></div>
					<input class="d-none" name="idL" value="<%=l.getIdLezione()%>">
					<div class="float-left" style="margin-bottom: 5px;">
						<div id="uploads">
							<ul id="list-lezione">
								<li id="nome" class="nome-lezione li-lezione"><input
									type="text" name="nome" readonly="readonly" autocomplete="off"
									value="<%=l.getNome()%>"></li>
								<li id="file-lezione" class="li-lezione"><input type="file" 
									class="d-none" name="fileMod" value="<%=l.getFilePath()%>"><%=l.getFilePath()%> 
									<button type="button" name="modLezione"
										style="font-size: 0.9rem;"
										onclick="modificaLezione(this.form)"
										class="d-none btn btn-outline-secondary but"></button></li>
								<li style="list-style: none">

									<button name="btnM" type="button" onclick="modifica(this.form)"
										class="btn btn-primary modifica">Modifica</button>
									<button name="btnC" type="button" 										
										class="btn btn-success conferma d-none UPL">Conferma
										modifiche</button>

									<button name="btnA" type="button" onclick="annulla(this.form)"
										class="btn btn-warning d-none">Annulla</button>
									<button type="submit" class="btn btn-danger mx-auto"
										formaction="http://localhost:8080/YouLearn/CancLezioneServelt?idLezione=<%=l.getIdLezione()%>&idCorso=<%=l.getCorso().getIdCorso()%>">Cancella</button>
								</li>
							</ul>
						</div>

					</div>
					<div class="float-right	 Commands">
						<button id="b-UD" name="up" type="button"
							class=" btn btn-outline-secondary b-up b" value='up'>
							<i class="fas fa-arrow-up"></i>
						</button>
						<button name="down" style="margin-top: 5px;" id="b-UD"
							type="button" class="btn btn-outline-secondary b-up b"
							value='down'>
							<i class="fas fa-arrow-down"></i>
						</button>
					</div>



				</form>
			</div>

			<%
				}
				}
			%>



		</div>
		<button onclick="UploadResult()" id="conferma"
			class="btn btn-success btn-block d-none">CONFERMA MODIFICHE</button>
	</div>


	<div class="card w-50 mx-auto" id="insLezione">
		<div class="card-header" style="font-size: 30px">INSERIMENTO
			LEZIONI</div>
		<div class="card-body body-ins-lezione">
			<div id="uploads">
				<ul style="list-style: none;">
					<li class="d-inline"><label>Nome Lezione:</label> <input
						type="text" id="nome-lezione"></li>
					<li class="d-inline"><input type="file" max="1" name="file">
					</li>

				</ul>
				<button id="upload" class="btn btn-success btn-lg btn-block">UPLOAD</button>
			</div>
		</div>
	</div>


	<script src="JS/SettingLezione.js"></script>
	<script src="JS/simpleUpload.min.js"></script>
	<script src="JS/scriptUpload.js"></script>


</body>

</html>