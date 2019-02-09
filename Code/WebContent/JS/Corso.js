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