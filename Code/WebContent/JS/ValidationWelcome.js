if($("#alert").css('display') == 'none'){

	$("#alert").removeClass("d-none").hide();

	$("#h").append($("#alert"));

	$("#alert").fadeIn();
	setTimeout(function(){

		$("#alert").fadeOut(2000);



	}, 3000);
}

function validateLogin(formLogin){

	var psw = formLogin.password;
	var pswValidator = /^[a-zA-Z 0-9 \@\._\!\?\-]{8,}$/;

	if(!psw.value.match(pswValidator)){

		alertify.error("La password deve contenere almeno 8 caratteri tra lettere, numeri e simboli");
		return false;

	} else return true;


}

function validateRegistration(formRegistration) {

	//Define registration regExp validators
	var usrValidator = /^(\w+[_\.\-]*\w*){4,}$/;
	var pswValidator = /^[a-zA-Z 0-9 \@\._\!\?\-]{8,45}$/;
	var nameValidator = /^[a-zA-Z]+([\s\-]?[A-Za-z]+){3,25}/;
	var surnameValidator = /^[A-Za-z]+([\s\'\-]?[A-Za-z]+){3,25}/;
	var mailValidator = /^\w+([\._\-]?\w+)*@\w+([\.\-]?\w+)*(\.\w+)+$/;
	var phoneValidator = /^[0-9]{10}$/;

	//Save all matches in a variable

	var nameIsOK = formRegistration.name.value.match(nameValidator);
	var surnameIsOK = formRegistration.surname.value.match(surnameValidator);
	var pswIsOK = formRegistration.password.value.match(pswValidator);
	var psw = formRegistration.password;
	var psw2 = formRegistration.passwordConf;
	var mailIsOK = formRegistration.email.value.match(mailValidator);
	var tipoCarta = formRegistration.tipoCarta.value;
	var nCarta = formRegistration.nCarta.value;
	var nIntestatario = formRegistration.nIntestatario;
	var meseScadenza = formRegistration.meseScadenza;
	var annoScadenza = formRegistration.annoScadenza;


	if (!nameIsOK) { //Check name
		alertify.error(
				"Il nome deve contenere almeno 4 lettere, non può terminare con uno spazio oppure un apostrofo e non può contenere numeri o simboli"
		);
		document.getElementById("name").focus(); //Set focus
		return false; //Negate access
	} else

		if (!surnameIsOK) { //Check surname
			alertify.error(
					"Il cognome deve contenere almeno 4 lettere, non può terminare con uno spazio oppure un apostrofo e non può contenere numeri o simboli"
			);
			document.getElementById("surname").focus(); //Set focus
			return false; //Negate access
		} else

			if (!pswIsOK) { //Check password
				alertify.error("La password deve contenere almeno 8 caratteri tra lettere, numeri e simboli");
				document.getElementById("password").focus(); //Set focus
				return false; //Negate access
			} else 
				if(!validatePsw(psw, psw2)){
					$(psw2).focus(); //Set focus

				} else

					if (!mailIsOK) { //Check email
						alertify.error("Inserisci una email valida")
						document.getElementById("email").focus(); //Set focus
						return false; //Negate access
					} else if(!cardnumberTest(tipoCarta, nCarta, nIntestatario)){

						return false;

					} else{
						return true; //Grant access
					}}

function validatePsw(psw1, psw2){
	console.log("Password ok")

	console.log(psw1.value + " pass1, pass2" + psw2.value );
	console.log(psw1.value.match(psw2.value));
	var pswValidator = /^[a-zA-Z 0-9 \@\._\!\?\-]{8,}$/;

	if(psw2.value==""){
		alertify.error("Riempi il campo di conferma passowrd");
		return false;
	}else

		if(psw1.value != psw2.value){
			alertify.error('Le password non corrispondono');
			console.log("Password no")

			return false;
		}

		else return true;
}

function cardnumberTest(tipoCarta, nCarta, nIntestatario)
{

	var name1 = nIntestatario;
	var nCarta1 = nCarta;
	var tipoCarta1 = tipoCarta;
	console.log(nCarta1);


	if(nCarta1.length < 16){
		
		alertify.error("La lunghezza del numero carta deve essere di 16 numeri");
		document.getElementById('nIntestatario').focus();

		return false;
		
	}else
		
	if (tipoCarta1 === "Visa") {
		var cardno = /^(?:4[0-9]{12}(?:[0-9]{3})?)$/;

		if (nCarta1.match(cardno)) {
			if(allLetter(name1.value) == false){
				alertify.error("Il nome dell'intestatario non può contenere numeri");
				document.getElementById('nIntestatario').focus();

				return false;
			}			return true;
		} else {
			alertify.error('Numero di carta di credito Visa, non valido!');
			document.getElementById('nCarta').focus();


			return false;
		}
	} else if (tipoCarta1 === "MasterCard") {

		var cardno = /^(?:5[1-5][0-9]{14})$/;
		if (nCarta1.match(cardno)) {
			if(allLetter(name1.value) == false){
				alertify.error("Il nome dell'intestatario non può contenere numeri");
				document.getElementById('nIntestatario').focus();

				return false;
			}			return true;
		} else {
			alertify.error('Numero di carta di credito MasterCard, non valido!');
			document.getElementById('nCarta').focus();


			return false;
		}

	} else if (tipoCarta1 === "AmericanExpress") {

		var cardno = /^(?:3[47][0-9]{13})$/;
		if (nCarta1.match(cardno)) {
			if(allLetter(name1.value) == false){
				alertify.error("Il nome dell'intestatario non può contenere numeri");
				document.getElementById('nIntestatario').focus();

				return false;
			}
			return true;
		} else {
			alertify.error('Numero di carta di credito AmericanExpress, non valido!');
			document.getElementById('nCarta').focus();


			return false;
		}
	} else {

		alertify.error('Numero di carta non valido!');
		document.getElementById('nCarta').focus();


	}
}

function allLetter(name) {
	var letters = /^[a-zA-Z]+$/;
	console.log(name);

	if(name === ""){
		alertify.error("Riempi il campo nome intestatario");
		document.getElementById('nIntestatario').focus();

		return false;
	}else

		if(name.length<8 || name.length>50){

			alertify.error("Il nome del intestatario deve essere compreso tra 8 e 50");
			document.getElementById('nIntestatario').focus();
			return false;
		}else 
			if (name.match(letters)) {
				return true;

			} else {
				return false;
			}
}



