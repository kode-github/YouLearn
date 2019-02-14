var idCorso="";

$(document).ready(function(){

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


	/*
	 * Just because the success callback is called doesn't mean your
	 * application logic was successful, so check application success.
	 *
	 * Data as returned by the server on...
	 * success:  {"success":true,"format":"..."}
	 * error:  {"success":false,"error":{"code":1,"message":"..."}}
	 */

	//Questo metodo funziona con il pulsante dentro il card body
	
//	$( '#upload' ).click(function() {
//		console.log("sono dentro");
//
//
//		var div=$(this).parent();
//		var ul =div.children("ul");
//		console.log(ul);
//		var liText = ul.children("li").eq(0);
//		console.log(liText);
//		var liFile = ul.children("li").eq(1);
//		console.log(liFile);
//		var text = liText.children("input[type=text]");
//		var f = liFile.children("input[type=file]");
//		console.log($(text).val());
//		console.log(f.val());
//
//
//
//
//		var idCorso= getUrlParameter('idCorso');
//		console.log(idCorso);
//
//		f.simpleUpload("http://localhost:8080/YouLearn/InsLezioneServlet?name="+text.val()+"&idCorso="+ idCorso, {
//
//			allowedExts: ["jpg", "jpeg", "jpe", "jif", "jfif", "jfi", "png", "gif", "exe","mp4"],
//			allowedTypes: ["video/mp4" ,"image/pjpeg", "image/jpeg", "image/png", "image/x-png", "image/gif", "image/x-gif", "application/x-dosexe"],
//			maxFileSize: 50000000, //50 MB in bytes
//
//			start: function(file){
//				//Modificare nome file con file.name=qualcosa
//
//				this.block = $('<div class="block"></div>');
//				this.progressBar = $('<div class="progressBar"></div>');
//				this.cancelButton = $('<div class="cancelButton">x</div>');
//
//				/*
//				 * Since "this" differs depending on the function in which it is called,
//				 * we need to assign "this" to a local variable to be able to access
//				 * this.upload.cancel() inside another function call.
//				 */
//
//				var that = this;
//
//				this.cancelButton.click(function(){
//					that.upload.cancel();
//					//now, the cancel callback will be called
//				});
//
//				this.block.append(this.progressBar).append(this.cancelButton);
//				$('#uploads').append(this.block);
//
//			},
//
//			progress: function(progress){
//				//received progress
//				this.progressBar.width(progress + "%");
//			},
//
//			success: function(data){
//				//upload successful
//
//				this.progressBar.remove();
//				this.cancelButton.remove();
//
//				if (data.success) {
//					//now fill the block with the format of the uploaded file
//
//					var format = data.format;
//					var formatDiv = $('<div class="format"></div>').text(format);
//					this.block.append(formatDiv);
//				} else {
//					//our application returned an error
//					var error = data.error.message;
//					var errorDiv = $('<div class="error"></div>').text(error);				this.block.append(errorDiv);
//				}
//
//			},
//			error: function(error){
//				//upload failed
//				this.progressBar.remove();
//				this.cancelButton.remove();
//				var error = error.message;
//				var errorDiv = $('<div class="error"></div>').text(error);
//				this.block.append(errorDiv);
//			},
//
//			cancel: function(){
//				//upload cancelled
//				this.block.fadeOut(400, function(){
//					$(this).remove();
//				});
//			}
//		});
//	});
	
	$( '.upload' ).click(function() {
		console.log("sono dentro");
		

		var div=$(this).parent();
		var div1 = div.parent();	
		var liText = div1.children("li").eq(0);
		var liFile = div1.children("li").eq(1);
		var text = liText.children("input[type=text]");
		var f = liFile.children("input[type=file]");
	

		
		if(checkNome(text.val()) == false){
			
			return false;
			
		}
		
		if(!document.getElementById("input1").value){
			
			alertify.error("Riempi il campo file con la lezione che vuoi caricare!");
			return false;
		}


		$(btnFine).removeAttr("disabled");

		var idCorso= getUrlParameter('idCorso');
		console.log(idCorso);

		f.simpleUpload("http://localhost:8080/YouLearn/InsLezioneServlet?name="+text.val()+"&idCorso="+ idCorso, {

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
				$('.uploadsA').append(this.block);

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
					var errorDiv = $('<div class="error"></div>').text(error);	
					this.block.append(errorDiv);
				}

			},
			error: function(error){
				//upload failed
				this.progressBar.remove();
				this.cancelButton.remove();
				var error = error.message;
				var errorDiv = $('<div class="error"></div>').text("Upload riuscito!");
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
	
	$( '.upload2' ).click(function() {
		console.log("sono dentro");
		

		var div=$(this).parent();
		//console.log(div)
		var div1 = div.parent();
		//var ul =div1.children("ul");
		//console.log(div1);
		
		var liText = div1.children("li").eq(0);
		//console.log(liText);
		var liFile = div1.children("li").eq(1);
		//console.log(liFile);
		var text = liText.children("input[type=text]");
		var f = liFile.children("input[type=file]");
		//console.log($(text).val());
		//console.log(f.val());

		
		if(checkNome(text.val()) == false){
			
			return false;
			
		}
		
		if(!document.getElementById("input2").value){
			
			alertify.error("Riempi il campo file con la lezione che vuoi caricare!");
			return false;
		}


		$(btnFine).removeAttr("disabled");

		var idCorso= getUrlParameter('idCorso');
		console.log(idCorso);

		f.simpleUpload("http://localhost:8080/YouLearn/InsLezioneServlet?name="+text.val()+"&idCorso="+ idCorso, {

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
				$('.uploadsB').append(this.block);

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
					var errorDiv = $('<div class="error"></div>').text(error);	
					this.block.append(errorDiv);
				}

			},
			error: function(error){
				//upload failed
				this.progressBar.remove();
				this.cancelButton.remove();
				var error = error.message;
				var errorDiv = $('<div class="error"></div>').text("Upload riuscito!");
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

	
	$( '.upload3' ).click(function() {
		console.log("sono dentro");
		

		var div=$(this).parent();
		//console.log(div)
		var div1 = div.parent();
		//var ul =div1.children("ul");
		//console.log(div1);
		
		var liText = div1.children("li").eq(0);
		//console.log(liText);
		var liFile = div1.children("li").eq(1);
		//console.log(liFile);
		var text = liText.children("input[type=text]");
		var f = liFile.children("input[type=file]");
		//console.log($(text).val());
		//console.log(f.val());

		
		if(checkNome(text.val()) == false){
			
			return false;
			
		}
		
		if(!document.getElementById("input3").value){
			
			alertify.error("Riempi il campo file con la lezione che vuoi caricare!");
			return false;
		}

		$(btnFine).removeAttr("disabled");


		var idCorso= getUrlParameter('idCorso');
		console.log(idCorso);

		f.simpleUpload("http://localhost:8080/YouLearn/InsLezioneServlet?name="+text.val()+"&idCorso="+ idCorso, {

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
				$('.uploadsC').append(this.block);

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
					var errorDiv = $('<div class="error"></div>').text(error);	
					this.block.append(errorDiv);
				}

			},
			error: function(error){
				//upload failed
				this.progressBar.remove();
				this.cancelButton.remove();
				var error = error.message;
				var errorDiv = $('<div class="error"></div>').text("Upload riuscito!");
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




});


function checkNome(nome){
	var nameValidator = /^[a-zA-Z\s\!\-\d]{5,50}$/;

	if(!nome.match(nameValidator)){
		alertify.error("Il nome della lezione deve essere lungo almeno 5 caratteri e non può contenere carateri speciali diversi da ?,- e numeri");
		return false;

	}

//	else if(!document.getElementById("input1").value){


	else {
		
		return true;}

}

$(".input-nome-lezione1").on("input", function() {
	if(this.value.length == 0){

		$(".modifica").removeAttr("disabled");
		$(".b").removeAttr("disabled");


	} else {


		$(".modifica").attr("disabled", "disabled");
		$(".b").attr("disabled", "disabled");
	}


});

$(".input-nome-lezione2").on("input", function() {
	if(this.value.length == 0){

		$(".modifica").removeAttr("disabled");
		$(".b").removeAttr("disabled");


	} else {


		$(".modifica").attr("disabled", "disabled");
		$(".b").attr("disabled", "disabled");
	}


});


$(".input-nome-lezione3").on("input", function() {
	if(this.value.length == 0){

		$(".modifica").removeAttr("disabled");
		$(".b").removeAttr("disabled");


	} else {


		$(".modifica").attr("disabled", "disabled");
		$(".b").attr("disabled", "disabled");
	}


});



