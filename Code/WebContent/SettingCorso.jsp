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

 <div class="card card-5 w-50 mx-auto">
        <div class="card-header text-center " style="font-size: 30px;">CREAZIONE CORSO</div>
        <div class="card-body">

            <form class="register">
                <div class="form-group">
                    <label for="InputName">TITOLO DEL CORSO</label>
                    <input type="text" name="" class="form-control" id="InputName"
                        placeholder="Titolo">
                </div>
                <div class="form-group">
                    <label for="InputCognome">DESCRIZIONE DEL CORSO</label>
                    <textarea name="" id="" cols="30" rows="10" class="form-control" id="InputDescrizione"> </textarea>
                </div>
                <div class="form-group">
                    <label for="InputCategoria">CATEGORIA</label>
                    <div class="dropdown">
                        <select class="form-control">
                            <option value="Informatica">Informatica</option>
                            <option value="Elettronica">Visa</option>
                            <option value="Musica">Musica</option>
                            <option value="Fotografia">Fotografia</option>
                            <option value="Danza">Danza</option>
                        </select>
                    </div>
                  </div>
                    <div class="form-group">
                        <label for="InputName">SCADENZA ISCRIZIONE CORSO</label>
                        <input type="text" name="dataDiScadeza" class="form-control" id="InputGiorno" placeholder="GG/MM/AAAA">

                    </div>
                    <div class="form-group">
                        <label for="InputPrezzo">PREZZO</label>
                        <input type="text" name="prezzo" class="form-control" id="InputPrezzo" placeholder="" >

                    </div>
                <div class="form-group">
                    <label for="InputImmCop">IMMAGINE DI COPERTINA</label>
                    <input type="file" class="form-control" id="InputImmCop" placeholder="" name="CARICA FILE">
                </div>
                <div class="row">
                    <div class="col-lg-6 col-sm-6"><button type="submit" class="btn btn-success btn-lg btn-block">CONFERMA</button></div>
                    <div class="col-lg-6 col-sm-6"><button type="submit" class="btn btn-danger btn-lg btn-block">ANNULLA</button></div>
                </div>
               
                
            </form>
        </div>
        </div>
    

</body>
</html>