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
    <link rel="stylesheet" href="../CSS/Risultati.css">
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

    <title>YouLearn - RIcerca Corsi</title>
</head>
<body>

	<%@ include file="Navbar.jsp" %>

        <div class="card mx-auto w-50">
                <h5 class="card-header">LISTA CORSI</h5>
                <div class="card-body">
                    <img class="rounded float-left" src="../Image.jpg" alt="FAIL" srcset="" width="150" height="150">
                    <ul class="informazioni-corso rounded float-left">
                        <li>NOME CORSO:</li>
                        <li>NUMERO ISCRITTI:</li>
                        <li>SCADENZA ISCRIZIONI:</li>


                    </ul>
                    
                </div>
                <div class="card-body">
                        <img class="rounded float-left" src="../Image.jpg" alt="FAIL" srcset="" width="150" height="150">
                        <ul class="informazioni-corso rounded float-left">
                            <li>NOME CORSO:</li>
                            <li>NUMERO ISCRITTI:</li>
                            <li>SCADENZA ISCRIZIONI:</li>
    
    
                        </ul>
                    
                </div>
                <div class="card-body">
                        <img class="rounded float-left" src="../Image.jpg" alt="FAIL" srcset="" width="150" height="150">
                        <ul class="informazioni-corso rounded float-left">
                            <li>NOME CORSO:</li>
                            <li>NUMERO ISCRITTI:</li>
                            <li>SCADENZA ISCRIZIONI:</li>
    
    
                        </ul>
                    
                </div>
            </div>
    
</body>
</html>