
<%@page import="bean.*"%>
<%@page import="bean.CorsoBean.*"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Comparator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">

<link rel="stylesheet" href="CSS/Corso.css">
<!-- Bootstrap-->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<!-- CSS Style & Font-->

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css?family=Permanent+Marker|Ubuntu"
	rel="stylesheet">

<!-- Script-->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
	integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
	integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
	crossorigin="anonymous"></script>


<title>YouLearn</title>
</head>

<body>

	<!-- 
		ATTRIBUTI DA GESTIRE (FORMA STRINGA TREU-FALSE), IN SESSIONE E ELIMINALI
		corsoCancellato
		verificato (per supervisore)
		iscrizioneEffettuata
	 -->


	<!--
	 
		QUI IL CORSO VIENE INIZIALIZZATO IN BASE ALLE INFORMAZIONI IN SESSIONE, NELL'OGGETTO ACCOUNT
	
	 -->

	<%@ include file="Navbar.jsp"%>


	<% 
		String idCorso = request.getParameter("idCorso");
		if(idCorso==null || !idCorso.matches("^[0-9]+")){
			response.sendRedirect(request.getContextPath()+"\\Welcome.jsp");
			return;
		}
		
		CorsoBean corso=(CorsoBean)request.getSession().getAttribute("corso");
		String ruolo=(String)  request.getSession().getAttribute("ruolo");
		String updated=(String) request.getSession().getAttribute("updated");
		if(updated==null || corso==null || ruolo==null || corso.getIdCorso()!=Integer.parseInt(idCorso)){
			response.sendRedirect(request.getContextPath()+"\\VisualCorsoServlet?idCorso="+idCorso);
			return;
		}
		else{
			request.getSession().removeAttribute("updated");
			//request.getSession().removeAttribute("corso");
			//request.getSession().removeAttribute("ruolo");
			System.out.println("id: "+corso.getIdCorso()+" descrizione: "+corso.getDescrizione());
		}
		//Ora abbiamo le iscrizioni del corso e ruolo che ci dice da dove proveniamo
%>


	<h1 id="titolo-pagina" class="display-3"><%=corso.getNome() %></h1>
	<div class="row first-row">
		<div id="" class="div-container col-lg-6" style="text-align: center;">
			<img id="img-corso"
				src="Resources\<%=corso.getIdCorso()%>\<%=corso.getCopertina()%>"
				alt="" width="250px" height="250px">
		</div>
		<div class="div-container col-lg-6"
			style="display: flex; align-items: center; justify-content: center">
			<p id="p-info-corso"><%=corso.getDescrizione() %></p>
		</div>
	</div>
	<div class="row">
		<%
			System.out.println(ruolo);
			if(!ruolo.equals("NonIscritto")){ %>
		<div id="" class="div-container col-12">

			<div class="table-wrapper w-75 mx-auto">
				<table class="fl-table">
					<thead>
						<tr>
							<th colspan="5"><i class="fas fa-info"></i> INFO CORSO</th>

						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Numero iscritti</td>
							<td><%=corso.getnIscritti() %></td>

						</tr>
						<tr>
							<td>Numero lezioni</td>
							<td><%=corso.getnLezioni() %></td>

						</tr>
						<tr>
							<td>Data scadena iscrizioni</td>
							<td><%=corso.getDataFine() %></td>

						</tr>
					<tbody>
				</table>
			</div>
		</div>
	</div>



	<div style="margin-top: 50px;" class="card w-50 mx-auto">
		<h5 class="card-h-corsi card-header">LEZIONI</h5>
		<% 
			if(corso.getLezioni().isEmpty()){
			System.out.println("Sono vuoto");%>
		<div class=" card-b-corsi card-body">Questo corso non contiene
			lezioni</div>
		<%
			
			}else{
				LinkedList<LezioneBean> lezioni=(LinkedList<LezioneBean>)corso.getLezioni();
				lezioni.sort(new Comparator<LezioneBean>() {
					public int compare(LezioneBean b1, LezioneBean b2) {
						if (b1.getNumeroLezione() > b2.getNumeroLezione())
							return 1;
						if (b1.getNumeroLezione() < b2.getNumeroLezione())
							return -1;
						return 0;
					}
				});
			for(LezioneBean e: corso.getLezioni()){
				%>

		<div class=" card-b-corsi card-body">
			<a href="http://localhost/YouLearn/Corso.jsp?idCorso="><img
				class="img-corsi-attesa rounded float-left" src="Images/Image.jpg"
				alt="FAIL" width="170" height="170"></a>
			<ul class="informazioni-corso rounded float-left">
				<li><a
					href="http://localhost:8080/YouLearn/Lezione.jsp?idLezione=<%=e.getIdLezione()%>"><%=e.getNome() %></a></li>
				<li>N°Visualizzazioni:<%=e.getVisualizzazioni() %></li>


			</ul>
		</div>
		<%}%>
	</div>


	<%}}
			else if (ruolo.equals("NonIscritto")){
			%>

	<div class="row">
		<div id="prezzo" id="" class="div-container col-6"
			style="text-align: center">
			<div
				style="display: flex; align-items: center; justify-content: center">
				<p id="p-info-acquisto">Acquista questo fantastico corso ed
					amplia le tue conoscenze grazie ai corsi forniti da YouLearn</p>
			</div>
			<button type="button" id="in"
				style="font-size: 2.5rem; margin-top: 20px;"
				class="btn btn-outline-success btn-lg acquisto">
				Prezzo:
				<%=corso.getPrezzo() %>
				&euro;
			</button>
		</div>

		<div id="lista" class="div-container col-6">

			<div class="table-wrapper">
				<table class="fl-table">
					<thead>
						<tr>
							<th colspan="5"><i class="fas fa-info"></i> INFO CORSO</th>

						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Numero iscritti</td>
							<td><%=corso.getnIscritti() %></td>

						</tr>
						<tr>
							<td>Numero lezioni</td>
							<td><%=corso.getnLezioni() %></td>

						</tr>
						<tr>
							<td>Data scadena iscrizioni</td>
							<td><%=corso.getDataFine() %></td>

						</tr>
					<tbody>
				</table>
			</div>



			<%} %>

		</div>
	</div>



	<%
	if(ruolo.equals("NonIscritto")){
%>

	<div id="message-box" class="">
		<form class="checkout d-none" method="post">
			<div class="checkout-header">
				<h1 class="checkout-title">
					Checkout <span class="checkout-price"><%=corso.getPrezzo() %>&euro;</span>
				</h1>
			</div>
			<p>
				<input type="text" class="checkout-input checkout-name"
					placeholder="Your name"
					value=<%=account.getCarta().getNomeIntestatario() %>> <input
					type="text" class="checkout-input checkout-exp" placeholder="MM"
					value=<%=account.getCarta().getMeseScadenza() %>> <input
					type="text" class="checkout-input checkout-exp" placeholder="YY"
					value=<%=account.getCarta().getAnnoScadenza() %>>
			</p>
			<p>
				<input type="text" class="checkout-input checkout-card"
					placeholder="4111 1111 1111 1111"
					value=<%=account.getCarta().getNumeroCarta() %>> <input
					type="text" class="checkout-input checkout-cvc" maxlength="3"
					name="cvc" placeholder="CVC" data-toggle="tooltip"
					data-placement="right"
					title="Inserisci il tuo CVC per completare il pagamento!">
			</p>
			<p>
				<input type=submit onclick="return controlloCVC(this.form)"
					formaction="http://localhost:8080/YouLearn/IscrizioneServlet?idCorso=<%=corso.getIdCorso()%>"
					value="Acquista" class="btn btn-success"> <input
					type="button" id="out" value="Annulla" class="btn btn-danger">
			</p>
		</form>
	</div>
	<%}%>

	<%@ include file="Footer.jsp"%>

	<script type="text/javascript" src="JS/Corso.js"></script>
	<script src="JS/alertify.js-0.3.11/lib/alertify.min.js"></script>

</body>

</html>