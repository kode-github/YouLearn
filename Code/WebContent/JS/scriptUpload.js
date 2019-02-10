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
	
$( '#upload' ).click(function() {
		console.log("sono dentro");
		
		
		var div=$(this).parent();
		var ul =div.children("ul");
		console.log(ul);
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



});