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


			<%
				LinkedList<LezioneBean> lezioni = (LinkedList<LezioneBean>) corso.getLezioni();
				lezioni.sort(new Comparator<LezioneBean>(){
					public int compare(LezioneBean b1,LezioneBean b2){
						if(b1.getNumeroLezione()>b2.getNumeroLezione())
							return 1;
						if(b1.getNumeroLezione()<b2.getNumeroLezione())
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
				<form method="post"> 
					<div class="float-left num"><%=l.getNumeroLezione()%></div>
					<div class="float-left">
						<ul id="list-lezione">
							<li id="nome" class="nome-lezione li-lezione"><%=l.getNome()%></li>
							<li id="file-lezione" class="li-lezione"><%=l.getFilePath()%></li>
						</ul>
						 <button type="submit"
							formaction="http://localhost:8080/YouLearn/CancLezioneServelt?idLezione=<%=l.getIdLezione()%>&idCorso=<%=l.getCorso().getIdCorso()%>">Cancella</button>
					</div>
					<div class="float-right	 Commands">
						<button id="b-UD" type="button" class=" btn btn-outline-secondary b-up"
							value='up'>
							<i class="fas fa-arrow-up"></i>
						</button>
						<button style="margin-top: 5px;" id="b-UD" type="button"
							class="btn btn-outline-secondary b-up" value='down'>
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


	<div class="card">
		<div class="card-header">INSERIMENTO LEZIONI</div>
		<div class="card-body body-ins-lezione">
			<div id="uploads">
				<input type="text" id="nome-lezione"> <input type="file"
					max="1" name="file">
				<button id="upload">UPLOAD</button>
			</div>
		</div>
	</div>



	<script src="JS/simpleUpload.min.js"></script>
	<script src="JS/scriptUpload.js"></script>
	<script src="JS/SettingLezione.js"></script>

</body>

</html>