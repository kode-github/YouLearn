
function allLetter(name) {
	var letters = /^[A-Za-z]+$/;
	if (name.value.match(letters)) {
		return true;
	} else {
		alert('Username must have alphabet characters only');

		return false;
	}
}

function validateCorso(formSettingCorso) {


	//Save all matches in a variable

	var nameValidator = /^[a-zA-Z\s\!\-\d]{5,50}$/;
	var prezzoValidator= /^[0-9]+$/;
	//var usrIsOK = formRegistration.username.value.match(usrValidator);
	var name = formSettingCorso.nome.value;
	console.log(name + " "+ name.length);
	var desc = formSettingCorso.descrizione.value;
	//desc = desc.replace(/\s/g, '');

	console.log(desc);
	var date = formSettingCorso.dataScadenza.value;
	console.log(date);
	var todayDate = new Date();
	var prezzo = formSettingCorso.prezzo.value;
	console.log(prezzo);



	if(!(name.match(nameValidator)) || name.length == 0 || name.length<5 || name.length >50){
		alertify.error("("+name + ") non e' valido, prova con un nome che contenga almeno 5 caratteri, che non sia piu' lungo di 30 caratteri e che non contenga caratteri speciali diversi da !,- o numeri.");
		$("#InputName").focus();
		return false;
	} else

		if(desc.length==0 || desc.length<10 || desc.length>1048){
			alertify.error("Descrizione non valida, la lunghezza del descrizione deve essere compresa tra 10 e 400 caratteri.");
			$("#txtarea").focus();
			return false;
		} else 
			if(date.length == 0 || isValidDate(date) == false){
				alertify.error("Riempi la data con valori corretti.");
				$("#data").focus();
				return false;

			} else
				if(!(prezzo.match(prezzoValidator)) || prezzo<=0) {
					alertify.error("Prezzo non valido, il prezzo non deve contere lettere e deve essere superiore o uguale a 1 "+String.fromCharCode(8364)+".");
					$("#InputPrezzo").focus();
					return false;

				} if(!$("#InputImmCop").val()){
					alertify.error("Il campo Immagine di Copertina non può essere vuoto.");
					$("#InputImmCop").focus();
					return false;
					
				}else


					return true; //Grant access
}


function isValidDate(dateString)
{
	console.log("sono dentr")
	// First check for the pattern
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth() + 1; //January is 0!
	var yyyy = today.getFullYear();

	console.log(dd);


	// Parse the date parts to integers
	var parts = dateString.split("-");
	var day = parseInt(parts[2], 10);
	console.log(day);
	var month = parseInt(parts[1], 10);
	console.log(month);
	var year = parseInt(parts[0], 10);
	console.log(year);

	

	// Check the ranges of month and year
	if(year < 2019 || year > 2030 || month == 0 || month > 12){
		alertify.error("L'anno di scadenza deve essere compreso tra il 2019 e il 2030");
		return false;
	}

	if(month < mm){
		
		alertify.error("Il mese deve essere successiva a quello odierna");
		return false;
		
	}

	var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

	// Adjust for leap years
	if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
		monthLength[1] = 29;

	// Check the range of the day
	return day > 0 && day <= monthLength[month - 1];
};

