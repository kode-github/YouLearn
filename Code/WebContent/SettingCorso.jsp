<%@page import="bean.CorsoBean.Categoria"%>
<%@page import="org.hamcrest.core.Is,bean.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Bootstrap-->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<!-- CSS Style & Font-->
<link rel="stylesheet" href="CSS/Corso.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css?family=Permanent+Marker|Ubuntu"
	rel="stylesheet">
<!-- include the core styles -->
<link rel="stylesheet" href="JS/alertify.js-0.3.11/themes/alertify.core.css" />
<!-- include a theme, can be included into the core instead of 2 separate files -->
<link rel="stylesheet" href="JS/alertify.js-0.3.11/themes/alertify.default.css" />


<title>YouLearn</title>
</head>
<body>

	<%@ include file="Navbar.jsp"%>

	<%
	int idCorso=0;
	CorsoBean corso=null;
	if(request.getParameter("idCorso")!=null){
		idCorso=Integer.parseInt(request.getParameter("idCorso"));
		corso=account.getCorsoTenuto(idCorso);
	}
		
%>

	<div style="margin:20px 0px;" class="card card-5 w-50 mx-auto">
		<div class="card-header text-center " style="font-size: 30px;">
			<h3>
				<% if(corso==null){ %>
				CREAZIONE CORSO
				<% }else {%>MODIFICA CORSO
				<%} %>
			</h3>
		</div>


		<div class="card-body">

			<form name="formSettingCorso" class="register" method="post"
				enctype="multipart/form-data"
				onsubmit="return validateCorso(formSettingCorso)">
				<div class="form-group">
					<label for="InputName">TITOLO DEL CORSO</label> <input
						required="required" input="text" name="nome" class="form-control"
						id="InputName" placeholder="Titolo" <%if(corso!=null) {%>
						value=<%=corso.getNome() %> <%} %>>
				</div>
				<div class="form-group">
					<label for="InputCognome">DESCRIZIONE DEL CORSO</label>
					<textarea required="required" name="descrizione" id="" cols="30"
						rows="10" class="form-control" id="InputDescrizione">
						<%if(corso!=null){ %> <%=corso.getDescrizione() %> <%} %>
					</textarea>
				</div>
				<div class="form-group">
					<label for="InputCategoria">CATEGORIA</label>
					<div class="dropdown">
						<select class="form-control" name="categoria">
							<option value="Informatica"
								<%if(corso!=null && corso.getCategoria().equals(Categoria.Informatica)){ %>
								selected <%} %>>Informatica</option>
							<option value="Elettronica"
								<%if(corso!=null && corso.getCategoria().equals(Categoria.Elettronica)){ %>
								selected <%} %>>Visa</option>
							<option value="Musica"
								<%if(corso!=null && corso.getCategoria().equals(Categoria.Musica)){ %>
								selected <%} %>>Musica</option>
							<option value="Fotografia"
								<%if(corso!=null && corso.getCategoria().equals(Categoria.Fotografia)){ %>
								selected <%} %>>Fotografia</option>
							<option value="Danza"
								<%if(corso!=null && corso.getCategoria().equals(Categoria.Danza)){ %>
								selected <%} %>>Danza</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="InputName">SCADENZA ISCRIZIONE CORSO</label> <input
						required="required" type="date" name="dataScadenza"
						class="form-control" placeholder="AAAA/MM/GG"
						<%if(corso!=null){ %> value="<%=corso.getDataFine().toString() %>"
						<%} %>>

				</div>
				<div class="form-group">
					<label for="InputPrezzo">PREZZO</label> <input required="required"
						type="text" name="prezzo" class="form-control" id="InputPrezzo"
						placeholder="Prezzo" <%if(corso!=null){ %>
						value="<%=corso.getPrezzo()%>" <%} %>>

				</div>
				<div class="form-group">
					<label for="InputImmCop">IMMAGINE DI COPERTINA</label> <input
						type="file" accept=".jpg,.png" class="form-control"
						id="InputImmCop" placeholder="" name="CARICA FILE">
				</div>
				<div class="row">
					<div class="col-lg-6 col-sm-6">
						<button type="submit" <%if(corso!=null) 
                    {%>
							formaction="http://localhost:8080/YouLearn/ModCorsoServlet?idCorso=<%=idCorso %>"
							<%}else{ %>
							formaction="http://localhost:8080/YouLearn/CreaCorsoServlet"
							<%} %> class="btn btn-success btn-lg btn-block">CONFERMA</button>
					</div>
					<div class="col-lg-6 col-sm-6">
						<button type="submit"
							formaction="http://localhost:8080/YouLearn/HomepageUtente.jsp"
							class="btn btn-danger btn-lg btn-block">ANNULLA</button>
					</div>
				</div>


			</form>
		</div>
	</div>

		  <%@ include file="Footer.jsp" %>


	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
		crossorigin="anonymous"></script>
	<script src="JS/ValidationSettingCorso.js"></script>
		<script src="JS/alertify.js-0.3.11/lib/alertify.min.js"></script>
	
</body>
</html>