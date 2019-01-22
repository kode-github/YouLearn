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
    <link rel="stylesheet" href="CSS/HomepageSupervisore.css">
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

  <%@ include file="Navbar.jsp" %>


    <div class="container-fluid">
        <div class="row ">
            <div class="utente-tab col-lg-3 position-fixed">
                <img class="profile-img " src="Images/Image.jpg" alt="FAIL" srcset="" width="250" height="250">
                <div class="card w-100  mb-3 mx-auto">
                    <div class="card-utente card-header">Nome:</div>
                    <div class="card-b-utente card-body ">
                        <h5 class="card-title">Pasquale</h5>
                    </div>
                </div>
                <div class="card w-100 mb-3 mx-auto">
                    <div class="card-utente card-header">Cognome:</div>
                    <div class="card-b-utente card-body">
                        <h5 class="card-title">Ambrosio</h5>
                    </div>
                </div>
                <div class="card w-100 mb-3 mx-auto">
                    <div class="card-utente card-header">Indirizzo E-mail:</div>
                    <div class="card-b-utente card-body">
                        <h5 class="card-title">pasquale.ambrosio112@gmail.com</h5>
                    </div>
                </div>



                <button type="button" class="btn-utente btn btn-light btn-lg btn-block">Modifica E-mail</button>
                <button type="button" class="btn-utente btn btn-light btn-lg btn-block">Modifica Password</button>


            </div>
            <div class="corsi-tab col-lg-9">

                
                <div class="card mx-auto w-100">
                    <h5 class="card-lista-attesa card-header">LISTA CORSI</h5>
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
                    <div class="card-body">
                        <img class="img-corsi-attesa rounded float-left" src="Images/Image.jpg" alt="FAIL" srcset="" width="150" height="150">
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
                    <div class="card-body">
                        <img class="img-corsi-attesa rounded float-left" src="Images/Image.jpg" alt="FAIL" srcset="" width="150" height="150">
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
               
                </div>

            </div>
        </div>
    </div>

</body>

</html>