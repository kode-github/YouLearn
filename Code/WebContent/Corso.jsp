<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<!-- 
	QUI IL CORSO VIENE INIZIALIZZATO IN BASE ALLE INFORMAZIONI IN SESSIONE, NELL'OGGETTO ACCOUNT
	
 -->
	
	<% 	
		String idCorso=request.getParameter("idCorso");
		if(idCorso==null)
			//Manda su Homepage
			
		//recupera il corso da Account, prima su CorsiTenuti e poi su Iscrizioni
		//Se il corso è in CorsiTenuti, account è un docente e quindi visualizza interamente
		//Se il corso è in Iscrizioni, account è uno studente del corso
		//Altrimenti, non mostrare le lezioni e mostra pulsante di Acquisto
	%>

</body>
</html>