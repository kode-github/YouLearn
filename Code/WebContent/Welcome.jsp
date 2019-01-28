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
</head>
<title>YouLearn</title>
</head>
<body>

	<%
		Boolean passwordErrata=(Boolean)request.getSession().getAttribute("passwordErrata");
		if(passwordErrata!=null){
			request.getSession().removeAttribute("passwordErrata");
	%>
	<!-- QUI VA ERRORE PER PASSWORD ERRATA -->
	<div class="alert alert-warning" style="text-align: center;" role="alert">Password Errata! Riprova nuovamente.
		</div>
	<% } %>

	<%
		Boolean erroreLogin=(Boolean)request.getSession().getAttribute("erroreLogin");
		if(erroreLogin!=null){
			request.getSession().removeAttribute("erroreLogin");
	%>
		<div class="alert alert-warning" style="text-align: center;" role="alert">Errore login! L'E-mail non esiste, riprova!
		</div>
	<%
		}
	%>

	<%
		Boolean erroreConnessione=(Boolean)request.getSession().getAttribute("erroreConnessione");
		if(erroreConnessione!=null){
			request.getSession().removeAttribute("erroreConnessione");
	%>
	<!-- QUI VA ERRORE PER ERRORE CONNESSIONE (DIRE DI PROVARE PIU' TARDI) -->
	<div class="alert alert-warning" style="text-align: center;" role="alert"> Siamo spiacenti, abbiamo avuto dei problemi di connessione. Riprova più tardi!
		</div>
	<% } %>


	<div class="header">
		<div class="row">
			<div class="image col-lg-5">
				<img class="" src="Images/Logo1.png" alt="" srcset="" width="150px"
					height="150px" style="float: right;">
			</div>
			<div class="text col-lg-6">
				<p class="text-image" style="font-size: 40px; padding-left: 90px;">YouLearn</p>
				<p class="text-image" style="font-size: 35px;">Increse your
					knowledges</p>
			</div>
		</div>

	</div>


	<section class="hero-section set-bg">
		<div id="login-register">
			<div class="row">
				<div class="col-lg-6">
					<div class="card card-5">
						<div class="card-header text-center" style="font-size: 30px;">LOGIN</div>
						<div class="card-body">
							<form class="login" method="post" onSubmit="return validateLogin();">
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


				<div class="col-lg-6">
					<div class="card card-5">
						<div class="card-header text-center " style="font-size: 30px;">REGISTRAZIONE</div>
						<div class="card-body">

							 <form action="" method="post" class="register" name="formRegistration" id="formRegistration" onsubmit="return validateRegistration(formRegistration)">
                <div class="form-group">
                    <label for="name">Nome</label>
                    <input type="text" name="name" id="name" class="form-control" placeholder="name">
                </div>
                <div class="form-group">
                    <label for="surname">Cognome</label> <input name="surname" id="surname" type="text" class="form-control"
                        placeholder="Cognome">
                </div>
                <div class="form-group">
                    <label for="email">Indirizzo e-mail</label> <input name="email" id="email" type="email" class="form-control"
                        aria-describedby="emailHelp" placeholder="Inserisci la tua e-mail"> <small id="emailHelp" class="form-text text-muted">
                        Non
                        condivideremo mai la tua e-mail con nessun altro. </small>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" name="password" id="password" class="form-control" placeholder="Password">
                </div>
                <div class="form-group">
                    <label for="passwordConf">Conferma Password</label> <input type="password" class="form-control"
                        name="passwordConf" id="passwordConf" placeholder="Conferma Password">
                </div>

								<p>
									<a class="btn btn-primary" data-toggle="collapse"
										href="#pagamento" role="button" aria-expanded="false"
										aria-controls="collapseExample"> Dati Pagamento </a>
								</p>
								<div class="collapse" id="pagamento">

									<div class="form-group">
                    <label for="nCarta">Numero Carta</label> <input type="text" class="form-control" id="nCarta" name="nCarta"
                        placeholder="0000-0000-0000-0000" maxlength="12">
                </div>
                <div class="form-group">
                    <label for="intestatario">Nome Intestatario</label> <input type="text" name="intestatario" class="form-control"
                        id="intestatario" placeholder="Mario Rossi">
                </div>
                <div class="form-group">
                    <label for="tipoCarta">Tipo Carta</label>
                    <div class="dropdown">
                        <select class="form-control" name="tipoCarta">
                            <option value="paypal">PayPal</option>
                            <option value="visa">Visa</option>
                            <option value="mastercard">MasterCard</option>
                        </select>
                    </div>
                </div>
                <div class="form-group d-inline">
                    <label for="tipoCarta">Scadenza Carta</label>
                    <div class="dropdown  d-inline">


                        <select class="form-control1 d-inline" name="scadenzaMese">
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
                    </div> / <div class="dropdown  d-inline">
                        <select class="form-control1 d-inline" name="scadenzaAnno">
                            <option value="paypal">2019</option>
                            <option value="visa">2020</option>
                            <option value="mastercard">2021</option>
                        </select>
                    </div>

								</div> </div>
								<button type="submit" class="btn btn-primary btn-lg btn-block">Registrati</button>
							</form>
						</div>
					</div>
				</div>
			</div>

		</div>
	</section>

	<footer id="footer">
		<div class="icon-footer">
			<i class="icon-footer fab fa-twitter"></i> <i
				class="icon-footer fab fa-facebook-f"></i> <i
				class="icon-footer fab fa-instagram"></i> <i
				class="icon-footer fas fa-envelope"></i>
		</div>
		<p>© Copyright 2019 YouLearn</p>

	</footer>

<script type="text/javascript">


function validateRegistration(formRegistration) {
    
    //Define registration regExp validators
    var usrValidator = /^(\w+[_\.\-]*\w*){4,}$/;
    var pswValidator = /^[a-zA-Z 0-9 \@\._\!\?\-]{8,}$/;
    var nameValidator = /^[a-zA-Z]+([\s\-]?[A-Za-z]+)*$/;
    var surnameValidator = /^[A-Za-z]+([\s\'\-]?[A-Za-z]+)*$/;
    var mailValidator = /^\w+([\._\-]?\w+)*@\w+([\.\-]?\w+)*(\.\w+)+$/;
    var phoneValidator = /^[0-9]{10}$/;

    //Save all matches in a variable

    //var usrIsOK = formRegistration.username.value.match(usrValidator);
    var pswIsOK = formRegistration.password.value.match(pswValidator);
    var nameIsOK = formRegistration.name.value.match(nameValidator);
    var surnameIsOK = formRegistration.surname.value.match(surnameValidator);
    var mailIsOK = formRegistration.email.value.match(mailValidator);
    //var phoneIsOK = formRegistration.phone.value.match(phoneValidator);


    if (!pswIsOK) { //Check password
        alert("La password deve contenere almeno 5 caratteri tra lettere, numeri e simboli");
        document.getElementById("password").focus(); //Set focus
        return false; //Negate access
    } else

    if (!nameIsOK) { //Check name
        alert(
            "Il cognome non può terminare con uno spazio oppure un apostrofo \ne non può contenere numeri o simboli"
        );
        document.getElementById("name").focus(); //Set focus
        return false; //Negate access
    } else

    if (!surnameIsOK) { //Check surname
        alert(
            "Il cognome non può terminare con uno spazio oppure un apostrofo\ne non può contenere numeri o simboli"
        );
        document.getElementById("surname").focus(); //Set focus
        return false; //Negate access
    } else

    if (!mailIsOK) { //Check email
        alert("Inserisci email valida")
        document.getElementById("email").focus(); //Set focus
        return false; //Negate access
    } else
        return true; //Grant access
}


function validateLogin(){
  //Define login regExp validators
  var usrValidator = /^(\w+[_\.\-]*\w*){4,}$/;
  var pswValidator = /^[a-zA-Z 0-9 \@\._\!\?\-]{8,}$/;

  //Save all matches in a variable
  var usrIsOK = document.formLogin.user.value.match(usrValidator);
  var pswIsOK = document.formLogin.psw.value.match(pswValidator);


  if (!usrIsOK) { //Check username
    alert('Lo username deve contenere lettere, numeri o i caratteri "_", "." "-"  e deve essere lungo almeno 8');
    document.getElementById("user").focus(); //Set focus
    return false; //Negate access
  } else

  if (!pswIsOK) { //Check password
    alert("La password deve contenere almeno 8 caratteri tra lettere, numeri e simboli");
    document.getElementById("psw").focus(); //Set focus
    return false; //Negate access
  } else
  return true;
}

function validateUsr(changeUsrForm){
    var usrValidator = /^(\w+[_\.\-]*\w*){4,}$/;
    var newUsr= changeUsrForm.newUser.value;

    if(!newUsr.match(usrValidator)) {
      alert("Username non valido");
      return false;
    } else return true;
}


function validatePsw(changePswForm){
    var pswValidator = /^[a-zA-Z 0-9 \@\._\!\?\-]{8,}$/;
    var oldPsw= changePswForm.oldPass.value;
    var newPsw= changePswForm.newPass.value;



    if(!oldPsw.match(pswValidator)){
      alert("Vecchia password errata");
      return false;
    } else if(!newPsw.match(pswValidator))
    {
      alert("Nuova password non valida");
      return false;
    } else return true;
}

function validateMail(changeMailForm){
    var mailValidator = /^\w+([\._\-]?\w+)*@\w+([\.\-]?\w+)*(\.\w+)+$/;
    var newMail= changeMailForm.newMail.value;

    if(!newMail.match(mailValidator)){
      alert("Email non valida");
      return false;
    } else return true;
}






</script>

</body>
</html>