<%@page import="bean.*,bean.CorsoBean.Stato"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="bean.*,java.util.LinkedList"%>
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
<link rel="stylesheet" href="CSS/HomepageUtente.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous">

<!-- include the style -->
<link rel="stylesheet" href="JS/alertifyjs/css/alertify.min.css" />
<!-- include a theme -->
<link rel="stylesheet" href="JS/alertifyjs/css/themes/default.min.css" />
<link
	href="https://fonts.googleapis.com/css?family=Permanent+Marker|Ubuntu"
	rel="stylesheet">


<!-- Script-->

<title>YouLearn</title>
</head>

<body>

	<%@ include file="Navbar.jsp"%>
	<%
		String seguiti = (String) request.getSession().getAttribute("seguiti");
		String tenuti = (String) request.getSession().getAttribute("tenuti");

		if (tenuti == null || seguiti == null) {
			response.sendRedirect(request.getContextPath() + "//VisualizzaProfiloServlet");
			return;
		}
		request.getSession().removeAttribute("seguiti");
		request.getSession().removeAttribute("tenuti");
	%>

	<%
		Boolean emailModificata = (Boolean) request.getSession().getAttribute("emailModificata");
		if (emailModificata != null) {
			request.getSession().removeAttribute("emailModificata");
	%>
	<div id="alert1" class="alert alert-success d-none"
		style="text-align: center;" role="alert">E-mail modificata con
		successo!</div>


	<%
		}
	%>

	<%
		Boolean emailGiaEsistente = (Boolean) request.getSession().getAttribute("emailGiaEsistente");
		if (emailGiaEsistente != null) {
			request.getSession().removeAttribute("emailGiaEsistente");
			System.out.println("KTM");
	%>
	<div id="alert1" class="alert alert-warning "
		style="text-align: center;" role="alert">Ci dispiace! L'e-mail è
		già in uso. Prova con un'altra!</div>
	<%
		}
	%>


	<%
		Boolean passwordModificata = (Boolean) request.getSession().getAttribute("passwordModificata");
		if (passwordModificata != null) {
			request.getSession().removeAttribute("passwordModificata");
	%>
	<div id="alert1" class="alert alert-success d-none"
		style="text-align: center;" role="alert">Password modificata con
		successo!</div>
	<%
		}
	%>

	<%
		Boolean passwordNonModificata = (Boolean) request.getSession().getAttribute("passwordNonModificata");
		if (passwordNonModificata != null) {
			request.getSession().removeAttribute("passwordNonModificata");
	%>

	<div id="alert1" class="alert alert-warning d-none"
		style="text-align: center;" role="alert">C'è stato un problema!
		Le password non coincidono, riprova.</div>
	<%
		}
	%>


	<%
		Boolean cartaModificata = (Boolean) request.getSession().getAttribute("cartaModificata");
		if (cartaModificata != null) {
			request.getSession().removeAttribute("cartaModificata");
	%>
	<div id="alert1" class="alert alert-success d-none"
		style="text-align: center;" role="alert">Complimenti! La tua
		carta è stata modificata con successo!</div>
	<%
		}
	%>

	<%
		Boolean cartaNonModificata = (Boolean) request.getSession().getAttribute("cartaNonModificata");
		if (cartaNonModificata != null) {
			request.getSession().removeAttribute("cartaNonModificata");
	%>
	<div id="alert1" class="alert alert-warning d-none"
		style="text-align: center;" role="alert">C'è stato un problema!
		La carta inserita è già in uso, riprova.</div>
	<%
		}
	%>
	
	
	<%
		String creato = (String) request.getSession().getAttribute("creato");
		if (creato != null) {
			request.getSession().removeAttribute("creato");
	%>
	<div id="alert1" class="alert alert-success d-none"
		style="text-align: center;" role="alert">Corso creato! Puoi trovarlo nella sezione 'corsi seguiti'"</div>
	<%
		}
	%>

	<div id="alert" class="" style="text-align: center;" role="alert"></div>

	<div class="container-fluid">

		<div class="row ">
			<img style="float: left; margin: 25px 0px 0px 25px;"
				class="profile-img " src="Images/Image.jpg" alt="FAIL" width="220"
				height="220">
			<div class="utente-tab  mx-auto w-75">
				<%
					AccountBean account1 = (AccountBean) request.getSession().getAttribute("account");
				%>
				<div class="row">
					<div class="col-4">
						<div class="card mb-3 mx-auto">
							<div class="card-utente card-header">Nome:</div>
							<div class="card-b-utente card-body ">
								<h5 class="card-title"><%=account1.getNome()%></h5>
							</div>
						</div>
					</div>
					<div class="col-4">
						<div class="card mb-3 mx-auto">
							<div class="card-utente card-header">Cognome:</div>
							<div class="card-b-utente card-body ">
								<h5 class="card-title"><%=account1.getCognome()%></h5>
							</div>
						</div>
					</div>
					<div class="col-4">
						<div class="card mb-3 mx-auto">
							<div class="card-utente card-header">E-mail:</div>
							<div class="card-b-utente card-body ">
								<h5 class="card-title"><%=account1.getMail()%></h5>
							</div>
						</div>
					</div>
				</div>




				<div class="row">
					<div class="col-4">

						<a id="btn-utente" class="email btn btn-primary btn-lg btn-block">
							Modifica E-mail </a>

						<div class="emailToogle">
							<form name="changeMailForm" id="changeMailForm"
								onsubmit="return validateMail(changeMailForm)">
								<div class="card card-body"
									style="margin-bottom: 10px; text-align: center;">
									<div class="form-group">
										<input type="email" name="newMail" class="form-control"
											placeholder="Inserisci la tua nuova e-mail" required="required">
									</div>
									<button type="submit"
										onclick="return confirm('Sei sicuro di voler continuare?\nLa tua email verrà moodificata!')"
										class="btn btn-info btn-lg btn-block"
										formaction="http://localhost:8080/YouLearn/CambiaMailServlet">Conferma</button>
								</div>

							</form>
						</div>
					</div>
					<div class="col-4">

						<a id="btn-utente"
							class="password btn btn-primary btn-lg btn-block"
							data-toggle="collapse" href="#collapseModPass" role="button"
							aria-expanded="false" aria-controls="collapseModPass">
							Modifica Password</a>


						<div class="pswToogle">
							<div class="card card-body"
								style="margin-bottom: 10px; text-align: center;">
								<form method="post" name="changePswForm" id="changePswForm"
									onsubmit="return validatePsw(changePswForm)">
									<div class="form-group">
										<input type="password" name="oldPass" id="oldPass"
											class="form-control"
											placeholder="Inserisci la tua nuova password">
									</div>
									<div class="form-group">
										<input type="password" name="newPass" id="newPass"
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
					<div class="col-4">

						<a id="btn-utente" class="carta btn btn-primary btn-lg btn-block"
							data-toggle="collapse" href="#collapseModCarta" role="button"
							aria-expanded="false" aria-controls="collapseModCarta">
							Modifica Carta</a>


						<div class="cartaToogle" id="collapseModCarta">
							<form method="post" name="modCarta"
								onsubmit="return cardnumberTest()">
								<div class="card card-body"
									style="margin-bottom: 10px; text-align: center;">
									<div class="form-group">
										<input type="text" name="cardnumber" class="form-control"
											placeholder="Inserisci il numero della nuova carta">
									</div>
									<div class="form-group">
										<input type="text" class="form-control"
											name="nomeIntestatario"
											placeholder="Inserisci il nome dell'intestatario">
									</div>
									<div class="form-group">

										<div class="dropdown  d-inline">
											<label for="tipoCarta">Tipo Carta</label> <select
												name="cardname" class="form-control">
												<option value="Visa">Visa</option>
												<option value="MasterCard">MasterCard</option>
												<option value="AmericanExpress">AmericanExpress</option>
											</select>
										</div>
									</div>


									<div class="form-group">
										<label for="scadenzaCarta">Scadenza Carta</label>
										<div class="dropdown  d-inline">
											<select class="form-control1 d-inline" name="scadenzaMese">
												<option value="">1</option>
												<option value="">2</option>
												<option value="">3</option>
												<option value="">4</option>
												<option value="">7</option>
												<option value="">8</option>
												<option value="">9</option>
												<option value="">10</option>
												<option value="">11</option>
												<option value="">12</option>
											</select>
										</div>
										/
										<div class="dropdown  d-inline">
											<select class="form-control1 d-inline" name="scadenzaAnno">
												<%
													for (int i = 2019; i <= 2040; i++) {
												%>
												<option value=<%=i%>><%=i%></option>
												<%
													}
												%>
											</select>
										</div>

									</div>

								</div>
								<button type="submit"
									onclick="return confirm('Sei sicuro di voler continuare?\nLa tua carta verrà moodificata!')"
									class="btn btn-info btn-lg btn-block" formaction="http://localhost:8080/YouLearn/CambiaCartaServlet">Conferma</button>
							</form>
						</div>


					</div>
				</div>
				<!-- <button type="button" id="btn-utente" class="btn btn-light btn-lg btn-block">Modifica E-mail</button>-->


				<!-- Form modifica password -->

				<!-- Form modifica carta -->



			</div>
		</div>

		<div id="carouselSlider" class="carousel slide" data-interval="5000"
			data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#carouselSlider" data-slide-to="0" class="active"></li>
				<li data-target="#carouselSlider" data-slide-to="1"></li>
			</ol>
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img src="Images/Img-Slider1.jpg" class="w-100 img-slider"
						alt="...">
				</div>
				<div class="carousel-item">
					<img src="Images/Img-Slider2.jpg" class="w-100 img-slider"
						alt="...">
				</div>
			</div>
		</div>

		<div class="row">
			<div class="three-infromazioni col-lg-4">Crea un corso e condividi le tue conoscenze!</div>
			<div class="three-infromazioni col-lg-4">Iscriviti ad un corso e apprendi</div>
			<div class="three-infromazioni col-lg-4">Fai entrambe le precedenti! Sei felice?</div>
		</div>

		<!-- INIZIO CORSI SEGUITI -->


		<div class="corsi-seguiti card w-75 mx-auto">
			<h5 class="card-h-corsi card-header">CORSI SEGUITI</h5>
			<%
				AccountBean a = (AccountBean) request.getSession().getAttribute("account");
				LinkedList<IscrizioneBean> corsi = (LinkedList<IscrizioneBean>) a.getIscrizioni();
				if (corsi.isEmpty()) {
			%>
			<div class="card-b-corsi card-body">Non ci sono corsi
				seguiti....Corri ad iscriverti!</div>
			<%
				} else {
					for (IscrizioneBean i : corsi) {
			%>

			<div class="card-b-corsi card-body">
				<a
					href="http://localhost:8080/YouLearn/Corso.jsp?from=iscrizioni&idCorso=<%=i.getCorso().getIdCorso()%>"><img
					class="img-corsi-attesa rounded float-left"
					src="Resources\<%=i.getCorso().getIdCorso()%>\<%=i.getCorso().getCopertina()%>"
					alt="FAIL" width="170" height="170"></a>
				<ul class="informazioni-corso rounded float-left">
					<li>NOME CORSO: <a
						href="http://localhost:8080/YouLearn/Corso.jsp?from=iscrizioni&idCorso=<%=i.getCorso().getIdCorso()%>"><%=i.getCorso().getNome()%></a></li>
					<li>NUMERO ISCRITTI: <%=i.getCorso().getnIscritti()%></li>
					<li>STATO: <%=i.getCorso().getStato()%></li>

				</ul>


				<a
					href="http://localhost:8080/YouLearn/Corso.jsp?from=iscrizioni&idCorso=<%=i.getCorso().getIdCorso()%>"><button
						style="margin-top: 48px;"
						class="float-right btn btn-success btn-lg ">Vai al Corso</button></a>

			</div>
			<%
				}
				}
			%>

		</div>
		<!-- INIZIO CORSI TENUTI -->

		<div style="margin-bottom: 20px;" class="card w-75 mx-auto">
			<div class="card-header">
				<h5 class="card-h-corsi">CORSI TENUTI</h5>
				<a class="float-right position-absolute"
					style="text-decoration: none !important; color: green; right: 0px; right: 9px; top: 8px; font-size: 20px; font-weight: bold;"
					href="http://localhost:8080/YouLearn/SettingCorso.jsp"><button
						style="font-family: 'Ubuntu', sans-serif; border-color: #f8f9fa !important; font-size: 22px !important;"
						type="button" class="btn btn-outline-success">
						<i class="fas fa-plus-circle"></i> Crea un corso!
					</button></a>
			</div>

			<%
				LinkedList<CorsoBean> corsiTenuti = (LinkedList<CorsoBean>) a.getCorsiTenuti();
				if (corsiTenuti.isEmpty()) {
			%>
			<div class="card-b-corsi card-body">Non ci sono corsi
				Tenuti...</div>
			<%
				} else {
					for (CorsoBean i : corsiTenuti) {
			%>
			<div class="card-b-corsi card-body">
				<form method="post">
					<a
						href="http://localhost:8080/YouLearn/Corso.jsp?from=tenuti&idCorso=<%=i.getIdCorso()%>"><img
						class="img-corsi-attesa rounded float-left"
						src="Resources\<%=i.getIdCorso()%>\<%=i.getCopertina()%>"
						alt="FAIL" width="170" height="170"></a>
					<ul class="informazioni-corso rounded float-left">
						<li>NOME CORSO: <a
							href="http://localhost:8080/YouLearn/Corso.jsp?from=tenuti&idCorso=<%=i.getIdCorso()%>"><%=i.getNome()%></a></li>
						<li>NUMERO ISCRITTI: <%=i.getnIscritti()%></li>
						<li>SCADENZA ISCRIZIONI: <%=i.getDataFine()%></li>
						<li>STATO: <%=i.getStato()%></li>

					</ul>

					<%
						if (i.getStato().equals(Stato.Completamento)) {
					%>
					<div class="float-lg-right ">
						<button type="submit"
							onclick="return confirm('Sei sicuro di voler continuare?\nIl corso verrà assegnato ad un Supervisore!')"
							formaction="http://localhost:8080/YouLearn/ConfermaCorsoServlet?idCorso=<%=i.getIdCorso()%>"
							class="btn btn-success btn-lg conferma">Conferma</button>
						<button type="submit"
							formaction="http://localhost:8080/YouLearn/SettingCorso.jsp?idCorso=<%=i.getIdCorso()%>"
							class="btn btn-outline-secondary btn-lg ">Modifica</button>
						<button type="submit" class="btn btn-outline-secondary btn-lg "
							formaction="http://localhost:8080/YouLearn/SettingLezione.jsp?idCorso=<%=i.getIdCorso()%>">Gestisci
							lezioni</button>
						<button type="submit"
							onclick="return confirm('Sei sicuro di voler continuare?\nIl corso verrà cancellato e perderai tutti i dati!')"
							formaction="http://localhost:8080/YouLearn/CancCorsoServlet?idCorso=<%=i.getIdCorso()%>"
							class="btn btn-danger btn-lg elimina">Elimina Corso</button>
					</div>

					<%
						}
					%>
				</form>
			</div>
			<%
				}
				}
			%>



		</div>

	</div>
	<!-- FINE -->

	<%@ include file="Footer.jsp"%>
	<script src="JS/ValidationHome.js"></script>
	<script src="JS/alertifyjs/alertify.min.js"></script>

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

</body>

</html>
