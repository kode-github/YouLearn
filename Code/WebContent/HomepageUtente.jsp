<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="bean.*,java.util.LinkedList" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">


    <!-- Bootstrap-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
        crossorigin="anonymous">
    

    <!-- Script-->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
        crossorigin="anonymous"></script>
        <!-- CSS Style & Font-->
    <link rel="stylesheet" href="CSS/HomepageUtente.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
        crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Permanent+Marker|Ubuntu" rel="stylesheet">

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
	<!-- QUI VA MESSAGGIO EMAIL MODIFICATA -->
	<% } %>
	
	<%
		Boolean emailGiaEsistente=(Boolean)request.getSession().getAttribute("emailGiaEsistente");
		if(emailGiaEsistente!=null){
			request.getSession().removeAttribute("emailGiaEsistente");
	%>
	<!-- QUI VA MESSAGGIO EMAIL GIA ESISTENTE -->
	<% } %>
	
	<!-- FORM MAIL -->
	<form method="post">
		<input name="newMail" type="text">
		<input type="submit" formaction="http://localhost/YouLearn/CambiaMailServlet">	
	</form>
		
	<%
		Boolean passwordModificata=(Boolean)request.getSession().getAttribute("passwordModificata");
		if(passwordModificata!=null){
			request.getSession().removeAttribute("passwordModificata");
	%>
	<!-- QUI VA MESSAGGIO PASSWORD MODIFICATA -->
	<% } %>
	
	<%
		Boolean passwordNonModificata=(Boolean)request.getSession().getAttribute("passwordNonModificata");
		if(passwordNonModificata!=null){
			request.getSession().removeAttribute("passwordNonModificata");
	%>
	<!-- QUI VA MESSAGGIO PASSWORD MODIFICATA -->
	<% } %>
	
		<!-- FORM PASSWORD -->
	<form method="post">
		<input name="newPass" type="text">
		<input type="submit" formaction="http://localhost/YouLearn/CambiaPassServlet">	
	</form>
	
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
        <div class="row " >
            <div class="utente-tab col-lg-3 position-fixed">
                <img class="profile-img " src="Images/Image.jpg" alt="FAIL" srcset="" width="160" height="160">
                <div class="card  mb-3 mx-auto">
                    <div class="card-utente card-header">Nome:</div>
                    <div class="card-b-utente card-body ">
                        <h5 class="card-title">Pasquale</h5>
                    </div>
                </div>
                <div class="card mb-3 mx-auto">
                    <div class="card-utente card-header">Cognome:</div>
                    <div class="card-b-utente card-body">
                        <h5 class="card-title">Ambrosio</h5>
                    </div>
                </div>
                <div class="card mb-3 mx-auto">
                    <div class="card-utente card-header">Indirizzo E-mail:</div>
                    <div class="card-b-utente card-body">
                        <h5 class="card-title" style="font-size:1.1rem">pasquale.ambrosio112@gmail.com</h5>
                    </div>
                </div>



                <button type="button" id="btn-utente" class="btn btn-light btn-lg btn-block">Modifica E-mail</button>
                <button type="button" id="btn-utente" class="btn btn-light btn-lg btn-block">Modifica Password</button>
                <button type="button" id="btn-utente" class="btn btn-light btn-lg btn-block">Modifica Carta</button>


            </div>
            <div class="col-lg-9 div-scroll" ><!-- style=" position: fixed!important; right:0;
            overflow: auto!important;
            width: 100%;
            height:700px;" -->

                <div id="carouselSlider" class="carousel slide" data-interval="5000" data-ride="carousel">
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
                    <div class="three-infromazioni col-lg-4">ROBA DA SCRIVERE </div>
                    <div class="three-infromazioni col-lg-4">ROBA DA SCRIVERE </div>
                    <div class="three-infromazioni col-lg-4">ROBA DA SCRIVERE </div>
                </div>

			<!-- INIZIO CORSI SEGUITI -->
			
                <div class="row">
                    <div class="col-lg-12">
                        <div class="corsi-seguiti card">
                            <h5 class="card-h-corsi card-header">CORSI SEGUITI</h5>
                         <%
                         	AccountBean a=(AccountBean)request.getSession().getAttribute("account");
                         	LinkedList<IscrizioneBean> corsi=(LinkedList<IscrizioneBean>) a.getIscrizioni();
                    		if(corsi.isEmpty()){
                         %>
                         <div class="card-b-corsi card-body">
                                Non ci sono corsi seguiti....Corri ad iscriverti!
                            </div>
                         <%}
                    		else{
                    			for(IscrizioneBean i: corsi){
                    		%>
                            	<div class="card-b-corsi card-body">
                                	<a href="http://localhost/YouLearn/Corso.jsp?idCorso=<%=i.getCorso().getIdCorso()%>"><%=i.getCorso().getNome()%></a>
                            	</div>
                            <%} 
                            }%>
                        </div>
                    </div>

                </div>
			<!-- INIZIO CORSI TENUTI -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <h5 class="card-h-corsi card-header">CORSI TENUTI</h5>
                            <%
                         	LinkedList<CorsoBean> corsiTenuti=(LinkedList<CorsoBean>) a.getCorsiTenuti();
                    		if(corsiTenuti.isEmpty()){
                         %>
                         <div class="card-b-corsi card-body">
                                Non ci sono corsi Tenuti....M'occ a mammt
                            </div>
                         <%}
                    		else{
                    			for(CorsoBean i: corsiTenuti){
                    		%>
                            	<div class="card-b-corsi card-body">
                                	<a href="http://localhost/YouLearn/Corso.jsp?idCorso=<%=i.getIdCorso()%>"><%=i.getNome()%></a>
                            	</div>
                            <%} 
                            }%>
                           
                        </div>
                    </div>

                </div>
			<!-- FINE -->
            </div>
            
        </div>
  
   	</div>
  
    
    
    

</body>

</html>