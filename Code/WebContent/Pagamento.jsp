<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<link rel="stylesheet" href="CSS/Pagamento.css">


<title>Youlearn - Pagamento</title>
</head>
<body>
	<%@ include file="Navbar.jsp"%>
	<%
		AccountBean a = (AccountBean) request.getSession().getAttribute("account");
		System.out.print(a.getCarta().getNumeroCarta());
	%>
	<div id="alert" class="d-none" style="text-align: center;" role="alert"></div>


	<div class="container">
		<div class="col1">
			<div class="card">
				<div class="front">
					<div class="type">
						<img class="bankid" />
					</div>
					<span class="chip"></span> <span class="card_number">&#x25CF;&#x25CF;&#x25CF;&#x25CF;
						&#x25CF;&#x25CF;&#x25CF;&#x25CF; &#x25CF;&#x25CF;&#x25CF;&#x25CF;
						&#x25CF;&#x25CF;&#x25CF;&#x25CF; </span>
					<div class="date">
						<span class="date_value">MM / YYYY</span>
					</div>
					<span class="fullname">FULL NAME</span>
				</div>
				<div class="back">
					<div class="magnetic"></div>
					<div class="bar"></div>
					<span class="seccode">&#x25CF;&#x25CF;&#x25CF;</span> <span
						class="chip"></span><span class="disclaimer">This card is
						property of Random Bank of Random corporation. <br> If found
						please return to Random Bank of Random corporation - 80047 Napoli,
						San Giuseppe Vesuviano, 119
					</span>
				</div>
			</div>
		</div>
		<div class="col2">
			<label>Numero di Carta</label><input value=<%=a.getCarta().getNumeroCarta() %> name="cardnumber" class="number" type="text"
				ng-model="ncard" maxlength="19"
				onclick='return event.charCode >= 48 && event.charCode <= 57' />
			<label>Nome Intestatario</label> <input class="inputname" type="text"
				placeholder="" /> <label>Data di scadenza</label> <input class="expire"
				type="text" placeholder="MM / YYYY" /> <label>Codice di sicurezza</label> <input class="ccv" type="text" placeholder="CVC"
				maxlength="3"
				onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
			<button class="buy" onclick="return cardnumberTest()">
				 Paga  &euro;

			</button>
		</div>
	</div>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script src="JS/CreditCard.js"></script>
</body>
</html>