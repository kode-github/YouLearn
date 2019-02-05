if($("#alert1").css('display') == 'none'){
    	alert("NIENTE");
    	$("#alert1").removeClass("d-none");
    	   setTimeout(function(){
    	       
    	          $("#alert1").fadeOut(2000);
    	        
    	       
    	       
    	   }, 3000);
    };



$(document).ready(function(){

    $(".emailToogle").hide();
    $(".pswToogle").hide();
    $(".cartaToogle").hide();

    $(".email").click(function(){
        $(".emailToogle").slideToggle();
    });
    $(".password").click(function(){
        $(".pswToogle").slideToggle();
    });
    $(".carta").click(function(){
        $(".cartaToogle").slideToggle();
    });

    

})






function toogleEmail(){
	
	
	
		$(".emailToogle").slideToogle("slow");
	
		
	
}

function showAlert(string){
	var div = $("#alert").addClass("alert alert-warning");
	div.removeClass("d-none").hide();
	div.fadeIn();
	div.html(string);
	 setTimeout(function(){
	       
          $("#alert").fadeOut(2000);
       
   }, 3000);
	
	
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
	console.log("Password ok")
    var pswValidator = /^[a-zA-Z 0-9 \@\._\!\?\-]{8,}$/;
    var newPsw= changePswForm.newPass.value;
    var oldPsw= changePswForm.oldPass.value;


	if(!newPsw.match(oldPsw)){
		showAlert('Le password non corrispondono');
		console.log("Password no")

	      return false;
		}


    if(!newPsw.match(pswValidator)){
		showAlert('La password deve contenere almeno 8 caratteri tra lettere, numeri e simboli');
		console.log("Password no")

      return false;
    }
     else return true;
}




function validateMail(changeMailForm){
    var mailValidator = /^\w+([\._\-]?\w+)*@\w+([\.\-]?\w+)*(\.\w+)+$/;
    var newMail= changeMailForm.newMail.value;

    if(!newMail.match(mailValidator)){
		showAlert('Email non valida');
      return false;
    } else return true;
}

function cardnumberTest()
{

	var name = document.modCarta.nomeIntestatario;
	var x = document.modCarta.cardname;
	console.log(x.value + "dovrebbe esserci la carta");
	var inputtxt = document.modCarta.cardnumber;
	console.log(inputtxt.value + "dovrebbe esserci la carta");




	if (x.value === "Visa") {
			var cardno = /^(?:4[0-9]{12}(?:[0-9]{3})?)$/;

			if (inputtxt.value.match(cardno)) {
				allLetter(name.value)
				return true;
			} else {
				showAlert('Numero di carta di credito Visa, non valido!');

				return false;
			}
		} else if (x.value === "MasterCard") {

			var cardno = /^(?:5[1-5][0-9]{14})$/;
			if (inputtxt.value.match(cardno)) {
				if(allLetter(name) == false) return false;
				return true;
			} else {
				showAlert('Numero di carta di credito MasterCard, non valido!');

				return false;
			}

		} else if (x.value === "AmericanExpress") {

			var cardno = /^(?:3[47][0-9]{13})$/;
			if (inputtxt.value.match(cardno)) {
				return true;
			} else {
				showAlert('Numero di carta di credito AmericanExpress, non valido!');

				return false;
			}
		} else {

			showAlert('Numero di carta non valido!');


		}
	}

	function allLetter(name) {
		var letters = /^[A-Za-z]+$/;
		if (name.value.match(letters)) {
			return true;
		} else {
			alert('Username must have alphabet characters only');

			return false;
		}
	}