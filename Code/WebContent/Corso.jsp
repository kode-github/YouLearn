<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">


    <!-- Bootstrap-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
        crossorigin="anonymous">
    <!-- CSS Style & Font-->
    <link rel="stylesheet" href="CSS/Corso.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
        crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Permanent+Marker|Ubuntu" rel="stylesheet">

    <!-- Script-->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
        crossorigin="anonymous"></script>


    <title>YouLearn</title>
</head>

<body>

	<!--
	 
		QUI IL CORSO VIENE INIZIALIZZATO IN BASE ALLE INFORMAZIONI IN SESSIONE, NELL'OGGETTO ACCOUNT
	
	 -->
	
	

<% 	
		//String idCorso = request.getParameter("idCorso");
		//if(idCorso==null){}
			//Manda su Homepage
		//recupera il corso da Account, prima su CorsiTenuti e poi su Iscrizioni
		//Se il corso è in CorsiTenuti, account è un docente e quindi visualizza interamente
		//Se il corso è in Iscrizioni, account è uno studente del corso
		//Altrimenti, non mostrare le lezioni e mostra pulsante di Acquisto
	%>
	
	<h1 id="titolo-pagina" class="display-3">NOME CORSO</h1>
	<div class="container-fluid">
		<div class="row">
			<div id="" class="div-container col-lg-6" style="text-align: center;">
				<img id="img-corso" src="Images/Image.jpg" alt="" srcset=""
					width="250px" height="250px">
			</div>
			<div id="" class="div-container col-lg-6"
				style="display: flex; align-items: center; justify-content: center">
				<p id="p-info-corso">Una descrizione del corso</p>
			</div>
		</div>
		<div class="row">
			<div id="prezzo" id="" class="div-container col-lg-6"
				style="text-align: center">
				<div
					style="display: flex; align-items: center; justify-content: center">
					<p id="p-info-acquisto">Acquista questo fantastico corso ed
						amplia le tue conoscenze grazie ai corso forniti da YouLearn</p>
				</div>
				<button type="button" id="btn-acquisto"
					class="btn btn-outline-success btn-lg">Prezzo: 00.00£</button>
			</div>
			<div id="" class="div-container col-lg-6">
				<table class="table table-striped w-50 mx-auto">
					<thead>
						<tr>
							<th colspan="2" scope="col"><i class="fas fa-info fa-3x"></i>
								INFO DEL CORSO</th>

						</tr>
					</thead>
					<tbody>
						<tr>
							<td scope="row">Numero iscritti</td>
							<td>Mark</td>



						</tr>
						<tr>
							<th scope="row">Numero Lezioni</th>
							<td>Jacob</td>

						</tr>
						<tr>
							<th scope="row">Data scadenza iscrizioni</th>
							<td>Larry</td>

						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="card w-50 mx-auto">
		<h5 class="card-h-corsi card-header">LEZIONI</h5>
	
                         	
		<!-- <div class="card-b-corsi card-body">Non ci sono corsi
			Tenuti....M'occ a mammt</div>  -->
	
                    	<div class="mx-auto card-b-corsi card-body">
                        <a href="http://localhost/YouLearn/Corso.jsp?idCorso="><img class="img-corsi-attesa rounded float-left" src="Images/Image.jpg" alt="FAIL" width="170" height="170" ></a>
                        <ul class="informazioni-corso rounded float-left">
                            <li>NOME LEZIONE <a href="#"></a></li>
                            <li>VIEWS: </li>
                         

                        </ul>
                    	</div>	
		

	</div>

	<!-- FINE -->
	</div>
		

</body>

</html>