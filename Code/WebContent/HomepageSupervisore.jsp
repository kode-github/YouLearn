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

	<%@ include file="Navbar.jsp"%>


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
					<div class="col-6">
						<a id="btn-utente" class="btn btn-primary btn btn-lg btn-block"
							data-toggle="collapse" href="#collapseModEmail" role="button"
							aria-expanded="false" aria-controls="collapseModEmail">
							Modifica E-mail</a>


						<div class="collapse" id="collapseModEmail">
							<form name="changeMailForm" id="changeMailForm"
								onsubmit="return validateMail(changeMailForm)">
								<div class="card card-body"
									style="margin-bottom: 10px; text-align: center;">
									<input type="email" name="newMail" class="form-control"
										id="inputEmailDimenticata" aria-describedby="emailHelp"
										placeholder="Inserisci la tua nuova e-mail">
								</div>
								<button type="submit" class="btn btn-info btn-lg btn-block"
									formaction="http://localhost:8080/YouLearn/CambiaMailServlet">Conferma</button>
							</form>
						</div>
					</div>
					<div class="col-6">

						<a id="btn-utente" class="btn btn-primary btn-lg btn-block"
							data-toggle="collapse" href="#collapseModPass" role="button"
							aria-expanded="false" aria-controls="collapseExample">
							Modifica Password</a>


						<div class="collapse" id="collapseModPass">
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


								</form>
							</div>
							<button type="submit" class="btn btn-info btn-lg btn-block"
								formaction="http://localhost:8080/YouLearn/CambiaPassServlet">Conferma</button>
						</div>


					</div>
				</div>
			</div>
		</div>


		<div class="card w-75 mx-auto">
			<h5 class="card-h-corsi card-header">CORSI DA SUPERVISIONARE</h5>
			<%
				LinkedList<CorsoBean> corsiDaSupervsionare = (LinkedList<CorsoBean>) account1.getCorsiDaSupervisionare();
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
					href="http://localhost/YouLearn/Corso.jsp?idCorso=<%=i.getIdCorso()%>"><img
					class="img-corsi-attesa rounded float-left"
					src=<%=i.getCopertina()%> alt="FAIL" width="170" height="170"></a>
				<ul class="informazioni-corso rounded float-left">
					<li>NOME CORSO: <a
						href="http://localhost/YouLearn/Corso.jsp?idCorso=<%=i.getIdCorso()%>"><%=i.getNome()%></a></li>
					<li>NUMERO ISCRITTI: <%=i.getnIscritti()%></li>
					<li>SCADENZA ISCRIZIONI: <%=i.getDataFine()%></li>

				</ul>

				<div class="float-right ">
					<button type="button" class="btn btn-success btn-lg ">Conferma</button>
					<button type="button" class="btn btn-outline-secondary btn-lg ">Vai
						al corso</button>
					<button type="button" class="btn btn-danger btn-lg">Rifiuta</button>

				</div>

				<%
					}
					}
				%>

			</div>

			<!-- FINE -->
		</div>


	</div>


</body>

</html>