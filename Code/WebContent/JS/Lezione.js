function validateCommento(form){

	var desc = form.testoCommento.value;

	console.log(desc.length);

	if(desc.length==0){
		alertify.error("Riempi il campo commento per poterne inserire uno!");
		$("#txtArea").focus();
		return false;
	}
	return true;

}



