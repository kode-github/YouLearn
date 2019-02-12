	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<link rel="stylesheet" href="CSS/Welcome.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css?family=Ubuntu"
	rel="stylesheet">

<!-- Script-->
<script src="JS/alertify.js-0.3.11/lib/alertify.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- include the core styles -->
<link rel="stylesheet"
	href="JS/alertify.js-0.3.11/themes/alertify.core.css" />
<!-- include a theme, can be included into the core instead of 2 separate files -->
<link rel="stylesheet"
	href="JS/alertify.js-0.3.11/themes/alertify.default.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
	integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
	integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
	crossorigin="anonymous"></script>
</head>
<title>YouLearn - increse your knowledge</title>
</head>
<body>

	<%
		Boolean passwordErrata=(Boolean)request.getSession().getAttribute("passwordErrata");
		if(passwordErrata!=null){
			
			request.getSession().removeAttribute("passwordErrata");
	%>

	<!-- QUI VA ERRORE PER PASSWORD ERRATA -->
	<div id="alert" class="alert alert-warning d-none"
		style="text-align: center;" role="alert">Password Errata!
		Riprova nuovamente.</div>
	<% } %>

	<%
		Boolean erroreLogin=(Boolean)request.getSession().getAttribute("erroreLogin");
		if(erroreLogin!=null){
			request.getSession().removeAttribute("erroreLogin");
	%>
	<div id="alert" class="alert alert-warning d-none"
		style="text-align: center;" role="alert">Errore login! L'E-mail
		non esiste, riprova!</div>
	<%
		}
	%>

	<%
		Boolean erroreConnessione=(Boolean)request.getSession().getAttribute("erroreConnessione");
		if(erroreConnessione!=null){
			request.getSession().removeAttribute("erroreConnessione");
	%>
	<!-- QUI VA ERRORE PER ERRORE CONNESSIONE (DIRE DI PROVARE PIU' TARDI) -->
	<div id="alert" class="alert alert-warning d-none"
		style="text-align: center;" role="alert">Siamo spiacenti,
		abbiamo avuto dei problemi di connessione. Riprova più tardi!</div>
	<% } %>

	<div id="h" class="header">
		<div class="row">
			<div class="image col-lg-5 col-md-5 col-sm-6 col-xs-6">
				<img id="img" class="" src="Images/Logo1.png" alt="">
			</div>
			<div class="text col-lg-6 col-md-6 col-sm-6 col-xs-6">
				<p id="title" class="text-image">YouLearn</p>
				<p id="sub-title" class="text-image">Increse your knowledge</p>
			</div>
		</div>

	</div>


	<section class="hero-section set-bg">
		<div id="login-register">
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
					<div class="card card-5">
						<div class="card-header text-center" style="font-size: 30px;">LOGIN</div>
						<div class="card-body">
							<form name="formLogin" class="login" method="post" onsubmit="return validateLogin(formLogin)">
								<div class="form-group">
									<label for="exampleInputEmail">E-mail</label> <input
										name="email" type="email" class="form-control"
										id="exampleInputEmail1" aria-describedby="emailHelp"
										placeholder="Enter e-mail">
								</div>
								<div class="form-group">
									<label for="exampleInputPassword">Password</label> <input
										name="password" type="password" class="form-control"
										id="exampleInputPassword1" placeholder="Password">
								</div>

								<!-- Pulsante di login -->
								<button type="submit"
									formaction="http://localhost:8080/YouLearn/LoginServlet"
									class="btn btn-primary btn-lg btn-block">Accedi</button>


								<p style="margin-top: 5px;">
									<a data-toggle="collapse" href="#collapseExample"
										aria-expanded="false" aria-controls="collapseExample"> Hai
										dimenticato la password? Clicca qui! </a>

								</p>
								<div class="collapse" id="collapseExample">
									<div class="card card-body"
										style="margin-bottom: 10px; text-align: center;">
										<i class="fas fa-user-lock fa-5x"></i> <label
											style="margin-top: 5px;" for="inputEmailDimenticata">Problemi
											di accesso?</label> <label for="inputEmailDimenticata">Inserisci
											la tua e-mail e ti invieremo un link per accedere di nuovo al
											tuo account.</label> <input type="email" class="form-control"
											id="inputEmailDimenticata" aria-describedby="emailHelp"
											placeholder="Inserisci la tua e-mail">
									</div>
									<button type="submit" class="btn btn-info btn-lg btn-block">Invia
										il link di accesso</button>
								</div>
							</form>

						</div>

					</div>
				</div>


				<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
					<div class="form-reg card card-5">
						<div class="card-header text-center " style="font-size: 30px;">REGISTRAZIONE</div>
						<div class="card-body">

							<form action="" method="get" class="register"
								name="formRegistration" id="formRegistration">
								<!-- onsubmit="return validateRegistration(formRegistration)" -->
								<div class="form-group">
									<label for="name">Nome</label> <input type="text" name="name"
										id="name" class="form-control" placeholder="Mario" required="required">
								</div>
								<div class="form-group">
									<label for="surname">Cognome</label> <input name="surname"
										id="surname" type="text" class="form-control"
										placeholder="Rossi" required="required">
								</div>
								<div class="form-group">
									<label for="email">Indirizzo e-mail</label> <input name="email"
										id="email" type="email" class="form-control"
										aria-describedby="emailHelp"
										placeholder="Inserisci la tua e-mail" required="required"> <small
										id="emailHelp" class="form-text text-muted"> Non
										condivideremo mai la tua e-mail con nessun altro. </small>
								</div>
								<div class="form-group">
									<label for="password">Password</label> <input type="password"
										name="password" id="password" class="form-control"
										placeholder="Password" required="required">
								</div>
								<div class="form-group">
									<label for="passwordConf">Conferma Password</label> <input
										type="password" class="form-control" name="passwordConf"
										id="passwordConf" placeholder="Conferma Password" required="required">
								</div>

								<p>
									<a class="btn btn-primary" data-toggle="collapse"
										href="#pagamento" role="button" aria-expanded="false"
										aria-controls="collapseExample"> Dati Pagamento </a>
								</p>
								<div class="collapse" id="pagamento">
									<div class="form-group">
										<label for="tipoCarta">Tipo Carta</label>
										<div class="dropdown">
											<select class="form-control" name="tipoCarta" id="tipoCarta">
												<option value="Visa">Visa</option>
												<option value="MasterCard">MasterCard</option>
												<option value="AmericanExpress">AmericanExpress</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="nCarta">Numero Carta</label> <input type="text"
											class="form-control" id="nCarta" name="nCarta" maxlength="16"
											placeholder="0000-0000-0000-0000" required="required">
									</div>
									<div class="form-group">
										<label for="intestatario">Nome Intestatario</label> <input
											type="text" name="nIntestatario" class="form-control"
											id="nIntestatario" placeholder="Mario Rossi" required="required">
									</div>

									<div class="form-group d-inline">
										<label for="tipoCarta">Scadenza Carta</label>
										<div class="dropdown  d-inline">


											<select class="form-control1 d-inline" name="meseScadenza">
												<option value="paypal">1</option>
												<option value="paypal">2</option>
												<option value="paypal">3</option>
												<option value="paypal">4</option>
												<option value="paypal">7</option>
												<option value="paypal">8</option>
												<option value="paypal">9</option>
												<option value="paypal">10</option>
												<option value="paypal">11</option>
												<option value="paypal">12</option>
											</select>
										</div>
										/
										<div class="dropdown  d-inline">
											<select class="form-control1 d-inline" name="annoScadenza">
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
									formaction="http://localhost:8080/YouLearn/RegistrazioneServlet"
									class="btn btn-primary btn-lg btn-block">Registrati</button>
							</form>
						</div>
					</div>
				</div>
			</div>

		</div>
	</section>

	<%@ include file="Footer.jsp"%>


	<script src="JS/ValidationWelcome.js"></script>
</body>
</html>