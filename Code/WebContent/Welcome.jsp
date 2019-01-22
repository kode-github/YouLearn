<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <!-- Bootstrap-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
        crossorigin="anonymous">
    <!-- CSS Style & Font-->
    <link rel="stylesheet" href="CSS/Welcome.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
        crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">

    <!-- Script-->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
        crossorigin="anonymous"></script>
</head>
<title>YouLearn</title>
</head>
<body>

    <div class="header">
        <div class="row">
            <div class="image col-lg-5"> <img class="" src="Images/Logo1.png" alt="" srcset="" width="150px" height="150px"
                    style="float: right ; "></div>
            <div class="text col-lg-6">
                <p class="text-image" style="font-size: 40px; padding-left: 90px;">YouLearn</p>
                <p class="text-image" style="font-size: 35px;">Increse your knowledges</p>
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
                            <form class="login">
                                <div class="form-group">
                                    <label for="exampleInputEmail">E-mail</label>
                                    <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                                        placeholder="Enter e-mail">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword">Password</label>
                                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                                </div>


                                <button type="submit" class="btn btn-primary btn-lg btn-block">Accedi</button>


                                <p style="margin-top:5px;">
                                    <a data-toggle="collapse" href="#collapseExample" aria-expanded="false"
                                        aria-controls="collapseExample">
                                        Hai dimenticato la password? Clicca qui! </a>

                                </p>
                                <div class="collapse" id="collapseExample">
                                    <div class="card card-body" style="margin-bottom:10px; text-align: center;">
                                        <i class="fas fa-user-lock fa-5x"></i>
                                        <label style="margin-top:5px;" for="inputEmailDimenticata">Problemi di accesso?</label>
                                        <label for="inputEmailDimenticata">Inserisci la tua e-mail e ti invieremo un
                                            link per accedere di nuovo al tuo account.</label>
                                        <input type="email" class="form-control" id="inputEmailDimenticata"
                                            aria-describedby="emailHelp" placeholder="Inserisci la tua e-mail">
                                    </div>
                                    <button type="submit" class="btn btn-info btn-lg btn-block">Invia il link di
                                        accesso</button>
                                </div>
                            </form>

                        </div>

                    </div>
                </div>


                <div class="col-lg-6">
                    <div class="card card-5">
                        <div class="card-header text-center " style="font-size: 30px;">REGISTRAZIONE</div>
                        <div class="card-body">

                            <form class="register">
                                <div class="form-group">
                                    <label for="InputName">Nome</label>
                                    <input type="text" class="form-control" id="InputName" aria-describedby="emailHelp"
                                        placeholder="Nome">
                                </div>
                                <div class="form-group">
                                    <label for="InputCognome">Cognome</label>
                                    <input type="email" class="form-control" id="InputCognome" aria-describedby="emailHelp"
                                        placeholder="Cognome">
                                </div>
                                <div class="form-group">
                                    <label for="InputEmailReg">Indirizzo e-mail</label>
                                    <input type="email" class="form-control" id="InputEmailReg" aria-describedby="emailHelp"
                                        placeholder="Inserisci la tua e-mail">
                                    <small id="emailHelp" class="form-text text-muted">
                                        Non condivideremo mai la tua e-mail con nessun altro.
                                    </small>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPasswordReg">Password</label>
                                    <input type="password" class="form-control" id="exampleInputPasswordReg"
                                        placeholder="Password">
                                </div>
                                <div class="form-group">
                                    <label for="InputConfermaPassword">Conferma Password</label>
                                    <input type="password" class="form-control" id="InputConfermaPassword" placeholder="Conferma Password">
                                </div>


                                <p>
                                    <a class="btn btn-primary" data-toggle="collapse" href="#pagamento" role="button"
                                        aria-expanded="false" aria-controls="collapseExample">
                                        Dati Pagamento
                                    </a>
                                </p>
                                <div class="collapse" id="pagamento">

                                    <div class="form-group">
                                        <label for="InputNumeroCarta">Numero Carta</label>
                                        <input type="text" class="form-control" id="InputNumeroCarta" placeholder="0000-0000-0000-0000"
                                            maxlength="12">
                                    </div>
                                    <div class="form-group">
                                        <label for="InputNomeIntestatario">Nome Intestatario</label>
                                        <input type="text" class="form-control" id="InputNomeIntestatario" placeholder="Mario Rossi">
                                    </div>
                                    <div class="form-group">
                                        <label for="InputTipoCarta">Tipo Carta</label>
                                        <div class="dropdown">
                                            <select class="form-control">
                                                <option value="paypal">PayPal</option>
                                                <option value="visa">Visa</option>
                                                <option value="mastercard">MasterCard</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="InputScadenzaCarta">Scadenza Carta</label>
                                        <input type="text" class="form-control" id="InputScadenzaCarta" placeholder="3 cifre"
                                            maxlength="3">
                                    </div>

                                </div>
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
            <i class="icon-footer fab fa-twitter"></i>
            <i class="icon-footer fab fa-facebook-f"></i>
            <i class="icon-footer fab fa-instagram"></i>
            <i class="icon-footer fas fa-envelope"></i>
        </div>
        <p>© Copyright 2019 YouLearn</p>

    </footer>


</body>
</html>