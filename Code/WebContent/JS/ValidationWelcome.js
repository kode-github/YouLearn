if($("#alert").css('display') == 'none'){
	
	$("#alert").removeClass("d-none").hide();

	$("#h").append($("#alert"));
	
	   $("#alert").fadeIn();
	   setTimeout(function(){
	       
	          $("#alert").fadeOut(2000);
	        
	       
	       
	   }, 3000);
}

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