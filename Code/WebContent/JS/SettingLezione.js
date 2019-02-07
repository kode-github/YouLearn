
function modify(){
	var nome = $("#nome").text();
	if($("#b").text()=="Modifica..."){
	$("#b").removeClass("btn-outline-primary");
	$("#b").addClass("btn-danger");
	$("#b").html("Annulla");
	console.log(nome);
	$(".item").css('height','8rem!important');
	$(".nome-lezione").append($('<li><input id="input" class="in" type="text" placeholder="Inserisci il nuovo nome"></li>'));
	
	} else if($("#b").text()=="Annulla"){
		$("#b").removeClass("btn-danger");
		$("#b").addClass("btn-outline-primary");
		$("#b").html("Modifica...");
		var nome1=$("#input").val();
		$(".in").remove();
		$("#nome").html(nome1);
	}


}
	
function moveUp(item) {
	var prev = item.prev();
	if (prev.length == 0) return;
	prev.css('z-index', 999).css('position', 'relative').animate({
		top: item.height()
	}, 250);
	item.css('z-index', 1000).css('position', 'relative').animate({
		top: '-' + prev.height()
	}, 300, function () {
		prev.css('z-index', '').css('top', '').css('position', '');
		item.css('z-index', '').css('top', '').css('position', '');
		item.insertBefore(prev);

		sendOrderToServer();
	});
}

function moveDown(item) {
	var next = item.next();
	if (next.length == 0) return;
	next.css('z-index', 999).css('position', 'relative').animate({
		top: '-' + item.height()
	}, 250);
	item.css('z-index', 1000).css('position', 'relative').animate({
		top: next.height()
	}, 300, function () {
		next.css('z-index', '').css('top', '').css('position', '');
		item.css('z-index', '').css('top', '').css('position', '');
		item.insertAfter(next);

		sendOrderToServer();
	});
}



$(".collection").sortable({
	items: ".item"
});


var str="";
var firstStr="";
var lastStr="";
var orderList = jQuery.grep($(".collection").sortable('toArray'), function (n, i) {
	firstStr= firstStr + n+"-"+(i+1) + ","; 

	return (n !== "" && n != null);
});


function sendOrderToServer() {
	lastStr="";
	var items = $(".collection").sortable('toArray');
	var itemList = jQuery.grep(items, function (n, i) {

		lastStr= lastStr+ n+"-"+(i+1) + ","; 

		return (n !== "" && n != null);
	});

	console.log("vecchio: "+firstStr+" nuovo:"+ lastStr);
	if(firstStr == lastStr) {
		$("#conferma").fadeOut().removeClass("d-block");
		$("#conferma").addClass("d-none");


	}else {
		$("#conferma").removeClass("d-none").fadeIn();
		$("#conferma").addClass("d-block");

	}	
}


$('.b-up').click(function () {

	var btn = $(this);
	var val = btn.val();
	if (val == 'up') moveUp(btn.parents('.item'));
	else moveDown(btn.parents('.item'));


});


	
	var getUrlParameter = function getUrlParameter(sParam) {
	    var sPageURL = window.location.search.substring(1),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
	        }
	    }
	};	


	$( document ).ajaxComplete(function() {
		  $( ".log" ).text( "Triggered ajaxComplete handler." );
		  window.location.reload();
		});
	


function UploadResult()
{
	console.log(lastStr);
	var coppie = lastStr.substring(0, lastStr.length-1);
	var idCorso= getUrlParameter("idCorso");
	console.log("idcorso: "+ idCorso)
	$.ajax({
		type: "POST",
		url: "http://localhost:8080/YouLearn/ModificaOrdineServlet",
		data:{ "coppie": coppie,
				"idCorso": idCorso	}
	});
}

