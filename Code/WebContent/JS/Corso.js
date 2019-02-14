$("#in").click(function(){
	
	if($(".checkout").css('display') == 'none'){

		$("#message-box").css('display','block');
		$("#message-box").addClass("d-none");
		$(".checkout").css('display','block');
		$(".checkout").addClass("d-none");

		
	} 
	
	$("#message-box").removeClass("d-none");

	$(".checkout").removeClass("d-none");
	$("#message-box").fadeIn();

	
	
	
	
	
});


$("#out").click(function(){
	
	
	$("#message-box").fadeOut();
	$(".checkout").fadeOut();
	
	
	
	
	
	
	
});

function controlloCVC(form){
	var cvcValidator= /^[0-9]+$/;

	var cvc = form.cvc.value;
	console.log(cvc)
	
	if(cvc.length==0 || !cvc.match(cvcValidator) || cvc.length < 3 ){
		
		alertify.error("Il CVC non può contere lettere e non può essere più piccolo di 3! ");
		return false;
		
	} else 
		return true;
	
	
}