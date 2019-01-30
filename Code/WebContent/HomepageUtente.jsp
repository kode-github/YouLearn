<%@page import="bean.CorsoBean.Stato"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
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

	  <%@ include file="Navbar.jsp" %>
	

	<!-- IL NOME DEL CAMPO PER LA NUOVA MAIL HA name="newMail" -->

	<%
		Boolean emailModificata=(Boolean)request.getSession().getAttribute("emailModificata");
		if(emailModificata!=null){
			request.getSession().removeAttribute("emailModificata");
	%>
	<div id="alert" class="alert alert-success" style="text-align: center;"
		role="alert">E-mail modificata con successo!</div>
		
	
	<% } %>
	
	<%
		Boolean emailGiaEsistente=(Boolean)request.getSession().getAttribute("emailGiaEsistente");
		if(emailGiaEsistente!=null){
			request.getSession().removeAttribute("emailGiaEsistente");
	%>
	<div class="alert alert-warning" style="text-align: center;"
		role="alert">Ci dispiace! L'e-mail � gi� in uso. Prova con
		un'altra!</div>
	<% } %>


	<%
		Boolean passwordModificata=(Boolean)request.getSession().getAttribute("passwordModificata");
		if(passwordModificata!=null){
			request.getSession().removeAttribute("passwordModificata");
	%>
	<div class="alert alert-success" style="text-align: center;"
		role="alert">Password modificata con successo!</div>
	<% } %>

	<%
		Boolean passwordNonModificata=(Boolean)request.getSession().getAttribute("passwordNonModificata");
		if(passwordNonModificata!=null){
			request.getSession().removeAttribute("passwordNonModificata");
	%>

	<div class="alert alert-warning" style="text-align: center;"
		role="alert">C'� stato un problema! Le password non coincidono,
		riprova.</div>
	<% } %>

	<!-- FORM PASSWORD -->
	<!-- <form method="post">
		<input name="newPass" type="text"> <input type="submit"
			formaction="http://localhost/YouLearn/CambiaPassServlet">
	</form> -->

	<%
		Boolean cartaModificata=(Boolean)request.getSession().getAttribute("cartaModificata");
		if(cartaModificata!=null){
			request.getSession().removeAttribute("cartaModificata");
	%>
	<!-- QUI VA MESSAGGIO CARTA MODIFICATA -->
	<% } %>

	<%
		Boolean cartaNonModificata=(Boolean)request.getSession().getAttribute("cartaNonModificata");
		if(cartaNonModificata!=null){
			request.getSession().removeAttribute("cartaNonModificata");
	%>
	<!-- QUI VA MESSAGGIO CARTA NON MODIFICATA -->
	<% } %>

	<!-- INSERIRE FORM CARTA
		OGNI LABEL AVRA' NOME UGUALE AL CAMPO NEL DATABASE, cioe name="nomeTabella"
	 -->


	<div class="container-fluid">
	
		 <div class="row ">
            <img style="float: left; margin: 25px 0px 0px 25px;" class="profile-img " src="Images/Image.jpg" alt="FAIL" width="220"
                height="220">
            <div class="utente-tab  mx-auto w-75">
            <%AccountBean account1 = (AccountBean)request.getSession().getAttribute("account"); 
            %>
                <div class="row">
                    <div class="col-4">
                        <div class="card mb-3 mx-auto">
                            <div class="card-utente card-header">Nome:</div>
                            <div class="card-b-utente card-body ">
                                <h5 class="card-title"><%=account1.getNome() %></h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="card mb-3 mx-auto">
                            <div class="card-utente card-header">Cognome:</div>
                            <div class="card-b-utente card-body ">
                                <h5 class="card-title"><%=account1.getCognome() %></h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="card mb-3 mx-auto">
                            <div class="card-utente card-header">E-mail:</div>
                            <div class="card-b-utente card-body ">
                                <h5 class="card-title"><%=account1.getMail() %></h5>
                            </div>
                        </div>
                    </div>
                </div>




				<div class="row">
					<div class="col-4">
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
					<div class="col-4">

						<a id="btn-utente" class="btn btn-primary btn-lg btn-block"
							data-toggle="collapse" href="#collapseModPass" role="button"
							aria-expanded="false" aria-controls="collapseExample">
							Modifica Password</a>


						<div class="collapse" id="collapseModPass">
							<div class="card card-body"
								style="margin-bottom: 10px; text-align: center;">
								<form method="post" name="changePswForm" id="changePswForm"
									onsubmit="return validatePsw(changePswForm)">
									<div class="form-group"><input type="password" name="newPass" id="newPass"
										class="form-control"
										placeholder="Inserisci la tua nuova password"> </div>
									<div class="form-group"><input
										type="password" name="oldPass" id="oldPass"
										class="form-control" placeholder="Conferma tua nuova password"></div>
									
									
								</form>
							</div>
							<button type="submit" class="btn btn-info btn-lg btn-block"
										formaction="http://localhost:8080/YouLearn/CambiaPassServlet">Conferma</button>
						</div>


					</div>
					<div class="col-4">

						<a id="btn-utente" class="btn btn-primary btn-lg btn-block"
							data-toggle="collapse" href="#collapseModCarta" role="button"
							aria-expanded="false" aria-controls="collapseExample">
							Modifica Carta</a>

						
							<div class="collapse" id="collapseModCarta">
							<form method="post" name="modCarta"
							onsubmit="return cardnumberTest()">
								<div class="card card-body"
									style="margin-bottom: 10px; text-align: center;">
									<div class="form-group">
									<input type="text" name="cardnumber" class="form-control"
										placeholder="Inserisci il numero della nuova carta"> 
										</div>
										<div class="form-group">
										<input	type="text" class="form-control" name="nomeIntestatario"	placeholder="Inserisci il nome dell'intestatario">
										</div>
										<div class="form-group">
										
										<div class="dropdown  d-inline">
										<label for="tipoCarta">Tipo Carta</label>	
										<select name="cardname" class="form-control"
											>
											<option value="Visa">Visa</option>
											<option value="MasterCard">MasterCard</option>
											<option value="Maestro">Maestro</option>
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
												<%for(int i=2019; i<=2040;i++){ %>
												<option value=<%=i %>><%=i %></option>
												<%} %>
											</select>
										</div>

									</div>

								</div>
								<button type="submit" class="btn btn-info btn-lg btn-block"
									formaction="#">Conferma</button>
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
				<img src="Images/Img-Slider1.jpg" class="w-100 img-slider" alt="...">
			</div>
			<div class="carousel-item">
				<img src="Images/Img-Slider2.jpg" class="w-100 img-slider" alt="...">
			</div>
		</div>
	</div>

	<div class="row">
		<div class="three-infromazioni col-lg-4">ROBA DA SCRIVERE</div>
		<div class="three-infromazioni col-lg-4">ROBA DA SCRIVERE</div>
		<div class="three-infromazioni col-lg-4">ROBA DA SCRIVERE</div>
	</div>

	<!-- INIZIO CORSI SEGUITI -->


	<div class="corsi-seguiti card w-75 mx-auto">
		<h5 class="card-h-corsi card-header">CORSI SEGUITI</h5>
		<%
                         	AccountBean a=(AccountBean)request.getSession().getAttribute("account");
                         	LinkedList<IscrizioneBean> corsi=(LinkedList<IscrizioneBean>) a.getIscrizioni();
                    		if(corsi.isEmpty()){
                         %>
		<div class="card-b-corsi card-body">Non ci sono corsi
			seguiti....Corri ad iscriverti!</div>
		<%}
                    		else{
                    			for(IscrizioneBean i: corsi){
                    		%>
                    		
                    		<div class="card-body">
                        <img class="img-corsi-attesa rounded float-left" src="Images/Image.jpg" alt="FAIL" srcset="">
                        <ul class="informazioni-corso rounded float-left">
                            <li>NOME CORSO:</li>
                            <li>NUMERO ISCRITTI:</li>
                            <li>SCADENZA ISCRIZIONI:</li>

                        </ul>
                        <div class="float-lg-right ">
                            <button type="button" class="btn btn-primary btn-lg d-lg-block w-100">Vai al corso</button>
                            <button type="button" class="btn btn-success btn-lg d-lg-block w-100">Conferma</button>
                            <button type="button" class="btn btn-danger btn-lg d-lg-block w-100">Rifiuta</button>
                        </div>


                    </div>
		<div class="card-b-corsi card-body">
			<a
				href="http://localhost:8080/YouLearn/Corso.jsp?idCorso=<%=i.getCorso().getIdCorso()%>">
				<%=i.getCorso().getNome()%></a>
		</div>
		<%} 
                            }%>
	</div>

	<!-- INIZIO CORSI TENUTI -->

	<div class="card w-75 mx-auto">
		<h5 class="card-h-corsi card-header">CORSI TENUTI   
		<a href="http://localhost:8080/YouLearn/SettingCorso.jsp">+</a></h5>
		<%
                         	LinkedList<CorsoBean> corsiTenuti=(LinkedList<CorsoBean>) a.getCorsiTenuti();
                    		if(corsiTenuti.isEmpty()){
                         %>
		<div class="card-b-corsi card-body">Non ci sono corsi
			Tenuti....M'occ a mammt</div>
		<%}
                    		else{
                    			for(CorsoBean i: corsiTenuti){
                    		%>
                    		<div class="card-body">
                    		<form method="post">
                        <a href="http://localhost:8080/YouLearn/Corso.jsp?idCorso=<%=i.getIdCorso()%>"><img class="img-corsi-attesa rounded float-left" src=<%=i.getCopertina() %> alt="FAIL" width="170" height="170" ></a>
                        <ul class="informazioni-corso rounded float-left">
                            <li>NOME CORSO: <a href="http://localhost:8080/YouLearn/Corso.jsp?idCorso=<%=i.getIdCorso()%>"><%=i.getNome()%></a></li>
                            <li>NUMERO ISCRITTI: <%=i.getnIscritti() %></li>
                            <li>SCADENZA ISCRIZIONI: <%=i.getDataFine() %></li>
                            <li>STATO: <%=i.getStato() %></li>

                        </ul>
                       	<%if(i.getStato().equals(Stato.Completamento)){
                       	%>	
                        <div class="float-lg-right ">
                       		<button type="submit" formaction="http://localhost:8080/YouLearn/ConfermaCorsoServlet?idCorso=<%=i.getIdCorso() %>" 
                       		class="btn btn-success btn-lg ">Conferma</button>
                            <button type="submit"
                             formaction="http://localhost:8080/YouLearn/SettingCorso.jsp?idCorso=<%=i.getIdCorso() %>"
                              formtarget="_blank" class="btn btn-outline-secondary btn-lg ">Modifica</button>
                            <button type="submit" class="btn btn-outline-secondary btn-lg  ">Gestisci lezioni</button>
                            <button type="submit"
                       		 formaction="http://localhost:8080/YouLearn/CancCorsoServlet?idCorso=<%=i.getIdCorso() %>" 
                       		 class="btn btn-danger btn-lg  ">Elimina Corso</button>
                        </div>
                    		
		<%}
                            }}%>
			</form>
	</div>

	<!-- FINE -->
	</div>
	</div>

<script type="text/javascript">



function validateUsr(changeUsrForm){
    var usrValidator = /^(\w+[_\.\-]*\w*){4,}$/;
    var newUsr= changeUsrForm.newUser.value;

    if(!newUsr.match(usrValidator)) {
      alert("Username non valido");
      return false;
    } else return true;
}




function validatePsw(changePswForm){
	console.log("Password ok")
    var pswValidator = /^[a-zA-Z 0-9 \@\._\!\?\-]{8,}$/;
    var newPsw= changePswForm.newPass.value;
    var oldPsw= changePswForm.oldPass.value;
   

	if(!newPsw.match(oldPsw)){
		alert("Le password non coincidono");
	      return false;
		}
	

    if(!newPsw.match(pswValidator)){
      alert("La password deve contenere almeno 8 caratteri tra lettere, numeri e simboli");
      return false;
    } 
     else return true;
}




function validateMail(changeMailForm){
    var mailValidator = /^\w+([\._\-]?\w+)*@\w+([\.\-]?\w+)*(\.\w+)+$/;
    var newMail= changeMailForm.newMail.value;

    if(!newMail.match(mailValidator)){
      alert("Email non valida");
      return false;
    } else return true;
}

function cardnumberTest()
{
	
	var name = document.modCarta.nomeIntestatario;
	var x = document.modCarta.cardname;
	console.log(x.value + "dovrebbe esserci la carta");
	var inputtxt = document.modCarta.cardnumber;
	console.log(inputtxt.value + "dovrebbe esserci la carta");

	
	

	if (x.value === "Visa") {
			var cardno = /^(?:4[0-9]{12}(?:[0-9]{3})?)$/;

			if (inputtxt.value.match(cardno)) {
				allLetter(name.value)
				return true;
			} else {
				alert("Not a valid Visa credit card number!");
				return false;
			}
		} else if (x.value === "MasterCard") {

			var cardno = /^(?:5[1-5][0-9]{14})$/;
			if (inputtxt.value.match(cardno)) {
				if(allLetter(name) == false) return false;
				alert('Abbiamo vinto')
				return true;
			} else {
				alert("Not a valid Mastercard number!");
				return false;
			}

		} else if (x.value === "AmericanExpress") {

			var cardno = /^(?:3[47][0-9]{13})$/;
			if (inputtxt.value.match(cardno)) {
				return true;
			} else {
				alert("Not a valid Amercican Express credit card number!");
				return false;
			}
		} else {

			alert("Numero di carta non valido!");

		}
	}

	function allLetter(name) {
		var letters = /^[A-Za-z]+$/;
		if (name.value.match(letters)) {
			return true;
		} else {
			alert('Username must have alphabet characters only');
			
			return false;
		}
	}
</script>
</body>

</html>