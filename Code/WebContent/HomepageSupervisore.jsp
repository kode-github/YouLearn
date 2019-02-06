<%@page import="java.util.LinkedList"%>
<%@page import="bean.CorsoBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">

<!-- Bootstrap-->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<!-- CSS Style & Font-->
<link rel="stylesheet" href="CSS/HomepageSupervisore.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css?family=Permanent+Marker|Ubuntu"
	rel="stylesheet">

<!-- Script-->

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

	<%@ include file="Navbar.jsp"%>


	<!-- MESSAGGIO DI CORSO CONFERMATO SU ATTRIBUTO IN SESSIONE "VERIFICATO" CON VALORE TRUE O FALSE (CONFERMATO O RIFIUTATO) -->

	<%
		String sup = (String) request.getSession().getAttribute("sup");

		if (sup == null) {
			response.sendRedirect(request.getContextPath() + "//VisualizzaProfiloServlet");
			return;
		} else
			request.getSession().removeAttribute("sup");
	%>
	<div id="alert" class="" style="text-align: center;" role="alert"></div>

	<div class="container-fluid">
		<div class="row ">
			<img style="float: left; margin: 25px 0px 0px 25px;"
				class="profile-img " src="Images/Image.jpg" alt="FAIL" width="220"
				height="220">
			<div class="utente-tab  mx-auto w-75">
				<div class="row">
					<div class="col-4">
						<div class="card mb-3 mx-auto">
							<div class="card-utente card-header">Nome:</div>
							<div class="card-b-utente card-body ">
								<h5 class="card-title"><%=account.getNome()%></h5>
							</div>
						</div>
					</div>
					<div class="col-4">
						<div class="card mb-3 mx-auto">
							<div class="card-utente card-header">Cognome:</div>
							<div class="card-b-utente card-body ">
								<h5 class="card-title"><%=account.getCognome()%></h5>
							</div>
						</div>
					</div>
					<div class="col-4">
						<div class="card mb-3 mx-auto">
							<div class="card-utente card-header">E-mail:</div>
							<div class="card-b-utente card-body ">
								<h5 class="card-title"><%=account.getMail()%></h5>
							</div>
						</div>
					</div>
				</div>




				<div class="row">
					<div class="col-6">
						<a id="btn-utente" class="email btn btn-primary btn-lg btn-block">
							Modifica E-mail </a>

						<div class="emailToogle">
							<form name="changeMailForm" id="changeMailForm"
								onsubmit="return validateMail(changeMailForm)">
								<div class="card card-body"
									style="margin-bottom: 10px; text-align: center;">
									<div class="form-group">
										<input type="email" name="newMail" class="form-control"
											placeholder="Inserisci la tua nuova e-mail">
									</div>
									<button type="submit"
										onclick="return confirm('Sei sicuro di voler continuare?\nLa tua email verrà moodificata!')"
										class="btn btn-info btn-lg btn-block"
										formaction="http://localhost:8080/YouLearn/CambiaMailServlet">Conferma</button>
								</div>

							</form>
						</div>
					</div>
					<div class="col-6">

						<a id="btn-utente"
							class="password btn btn-primary btn-lg btn-block"
							data-toggle="collapse" href="#collapseModPass" role="button"
							aria-expanded="false" aria-controls="collapseModPass">
							Modifica Password</a>


						<div class="pswToogle" id="collapseModPass">
							<div class="card card-body"
								style="margin-bottom: 10px; text-align: center;">
								<form method="post" name="changePswForm" id="changePswForm"
									onsubmit="return validatePsw(changePswForm)">
									<div class="form-group">
										<input type="password" name="newPass" id="newPass"
											class="form-control"
											placeholder="Inserisci la tua nuova password">
									</div>
									<div class="form-group">
										<input type="password" name="oldPass" id="oldPass"
											class="form-control"
											placeholder="Conferma tua nuova password">
									</div>

									<button type="submit"
										onclick="return confirm('Sei sicuro di voler continuare?\nLa tua password verrà moodificata!')"
										class="btn btn-info btn-lg btn-block"
										formaction="http://localhost:8080/YouLearn/CambiaPassServlet">Conferma</button>
								</form>
							</div>

						</div>


					</div>
				</div>
			</div>
		</div>


		<div style="margin-bottom: 20px;" class="card w-75 mx-auto">
			<h5 class="card-h-corsi card-header">CORSI DA SUPERVISIONARE</h5>
			<%
				LinkedList<CorsoBean> corsiDaSupervsionare = (LinkedList<CorsoBean>) account.getCorsiDaSupervisionare();
				if (corsiDaSupervsionare.isEmpty()) {
			%>
			<div class="card-b-corsi card-body">Non ci sono corsi da
				supervisionare fate proprio schifo...</div>
			<%
				} else {
					for (CorsoBean i : corsiDaSupervsionare) {
			%>
			<div class="card-body">

				<a
					href="http://localhost:8080/YouLearn/Corso.jsp?idCorso=<%=i.getIdCorso()%>"><img
					class="img-corsi-attesa rounded float-left"
					src="Resources\<%=i.getIdCorso()%>\<%=i.getCopertina()%>"
					alt="FAIL" width="170" height="170"></a>
				<ul class="informazioni-corso rounded float-left">
					<li>NOME CORSO: <a
						href="http://localhost:8080/YouLearn/Corso.jsp?idCorso=<%=i.getIdCorso()%>"><%=i.getNome()%></a></li>
					<li>NUMERO ISCRITTI: <%=i.getnIscritti()%></li>
					<li>SCADENZA ISCRIZIONI: <%=i.getDataFine()%></li>

				</ul>

				<div class="float-right ">
					<form method="post">
						<button type="submit"
							onclick="return confirm('Sei sicuro di voler continuare?\nIl corso verrà reso attivo e pubblicato sulla piattaforma!')"
							formaction="http://localhost:8080/YouLearn/VerificaCorsoServlet?verifica=true&idCorso=<%=i.getIdCorso()%>"
							class="btn btn-success btn-lg ">Conferma</button>
						<button type="submit" class="btn btn-outline-secondary btn-lg ">Vai
							al corso</button>
						<button type="submit"
							onclick="return confirm('Sei sicuro di voler continuare?\nIl corso verrà reso nuovamente in Completamento e verrà inviata una notifica al docente creatore.!')"
							formaction="http://localhost:8080/YouLearn/VerificaCorsoServlet?verifica=false&idCorso=<%=i.getIdCorso()%>"
							class="btn btn-danger btn-lg">Rifiuta</button>
					</form>
				</div>
			</div>

			<%
				}
				}
			%>

		</div>

		<!-- FINE -->
	</div>
	<%@ include file="Footer.jsp"%>

	<script src="JS/ValidationHome.js"></script>

</body>

</html>