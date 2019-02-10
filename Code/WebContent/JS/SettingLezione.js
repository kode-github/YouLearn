
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
		$("#conferma").fadeOut();
		$("#conferma").removeClass("d-block");
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

var nomeLezione;

function modifica(form){


	//inizializzo le variabili
	var nome = form.nome;
	var path = $("#file-lezione").text();
	//var path2 =$("#file-lezione").text("CIAO");
	$("#file-lezione").text("");
	$("#file-lezione").append("<input type='text' name='nomeL'>");

	console.log(path)
	//console.log(path2)
	var file = form.nomeL;
	var btn = form.btnM;
	var btnA = form.btnA;
	btnC = form.btnC;
	nomeLezione = $(nome).val();
	
	//Diasbilito gli altri bottoni per la modifica
	disableButton($(btn).text());
	
	//Rendo l'input text editabile
	$(btn).removeAttr("disabled");
	$(nome).attr("readonly", false);
    $(nome).focus();
    
    $(file).attr("type","file");
    $(file).removeClass("d-none");



	//Rendo visibili a schermo gli altri tasti COnferma, Annulla
	$(btn).addClass("d-none");
	$(btnA).removeClass("d-none");
	$(btnC).removeClass("d-none");
}


function disableButton(x){

	if(x == "Conferma modifiche"){


	} else if($(".modifica").is(":disabled")){

		$(".modifica").removeAttr("disabled");

	}else

		$(".modifica").attr("disabled", "disabled");

}

function annulla(form){

	var nome = form.nome;
	var btn = form.btnM;
	var btnA = form.btnA;
	
	console.log($(nome).val());

	$(nome).val(nomeLezione+"");
	$(nome).attr("readonly", true);

	disableButton();
	$(btn).removeClass("d-none");
	$(btnC).addClass("d-none");
	$(btnA).addClass("d-none");

}

function applyUpdate(form){

	var nome = form.nome;
	//var path = $("#file-lezione").html();
	var idCorso= getUrlParameter("idCorso");
	
	var id = form.idL;


	console.log("idcorso: "+ idCorso);
	console.log("idlezione" + $(id).val());
	console.log("idlezione" + id);


	$.ajax({
		type: "POST",
		url: "http://localhost:8080/YouLearn/ModificaLezioneServlet",
		data:{ "nomeLezione": nome,
				"idLezione": idLezione,
				"idCorso": idCorso	}
	});


}


$( '#uploads1' ).click(function() {
	console.log("sono dentro");
	
	
	
	var li =$(this).parent();
	var ul = li.parent();
	var liText = ul.children("li").eq(0);
	console.log(liText);
	var liFile = ul.children("li").eq(1);
	console.log(liFile);
	var text = liText.children("input[type=text]");
	var f = liFile.children("input[type=file]");
	console.log($(text).val());
	console.log(f.val());
	
	
	var idCorso= getUrlParameter('idCorso');
	console.log(idCorso);
	
	f.simpleUpload("http://localhost:8080/YouLearn/ModificaLezioneServlet?name="+text.val()+"&idCorso="+ idCorso, {

		allowedExts: ["jpg", "jpeg", "jpe", "jif", "jfif", "jfi", "png", "gif", "exe","mp4"],
		allowedTypes: ["video/mp4" ,"image/pjpeg", "image/jpeg", "image/png", "image/x-png", "image/gif", "image/x-gif", "application/x-dosexe"],
		maxFileSize: 50000000, //50 MB in bytes

		start: function(file){
			//Modificare nome file con file.name=qualcosa

			this.block = $('<div class="block"></div>');
			this.progressBar = $('<div class="progressBar"></div>');
			this.cancelButton = $('<div class="cancelButton">x</div>');

			/*
			 * Since "this" differs depending on the function in which it is called,
			 * we need to assign "this" to a local variable to be able to access
			 * this.upload.cancel() inside another function call.
			 */

			var that = this;

			this.cancelButton.click(function(){
				that.upload.cancel();
				//now, the cancel callback will be called
			});

			this.block.append(this.progressBar).append(this.cancelButton);
			$('#uploads').append(this.block);

		},

		progress: function(progress){
			//received progress
			this.progressBar.width(progress + "%");
		},

		success: function(data){
			//upload successful

			this.progressBar.remove();
			this.cancelButton.remove();

		if (data.success) {
				//now fill the block with the format of the uploaded file

				var format = data.format;
				var formatDiv = $('<div class="format"></div>').text(format);
				this.block.append(formatDiv);
			} else {
				//our application returned an error
				var error = data.error.message;
				var errorDiv = $('<div class="error"></div>').text(error);				this.block.append(errorDiv);
			}

		},

		error: function(error){
			//upload failed
			this.progressBar.remove();
			this.cancelButton.remove();
			var error = error.message;
			var errorDiv = $('<div class="error"></div>').text(error);
			this.block.append(errorDiv);
		},

		cancel: function(){
			//upload cancelled
			this.block.fadeOut(400, function(){
				$(this).remove();
			});
		}

	});
});
