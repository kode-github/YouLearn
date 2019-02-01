
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
	alert("Ciao");

    //var usrIsOK = formRegistration.username.value.match(usrValidator);
    var name = formSettingCorso.nome.value;
    console.log(name + " "+ name.length);
    var desc = formSettingCorso.descrizione.value;
    console.log(desc);
    var date = formSettingCorso.dataScadenza.value;
    console.log(date);
    var todayDate = new Date();
    var prezzo = formSettingCorso.prezzo.value;
    console.log(prezzo);
    
   


    if(name.length == 0 || name.length<5 || name.length >20){
    	
    	alertify.error("("+name + ") non e' valido, prova con un nome che contenga almeno 4 caratteri e che non sia piu' lungo di 20 caratteri.");
    	
    	return false;
    } else
    
    if(desc.length==0 || desc.length<10 || desc.length>400){
	alertify.error("Descrizione non valida, la lunghezza del descrizione deve essere compresa tra 10 e 400 caratteri.");
    	return false;
    } else 
    	if(isValidDate(date) == false){
    	
    	return false;
    	
    } else
    	if(prezzo == 0 || prezzo<5 || prezzo>500) {
    		alertify.error("Prezzo non valido, il prezzo deve essere compreso tra 5"+String.fromCharCode(8364)+" e 500"+String.fromCharCode(8364)+".");
        	return false;
        	
        } else
    	
    
        return true; //Grant access
}


function isValidDate(dateString)
{
	console.log("sono dentr")
    // First check for the pattern


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
    var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

    // Adjust for leap years
    if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
        monthLength[1] = 29;

    // Check the range of the day
    return day > 0 && day <= monthLength[month - 1];
};

