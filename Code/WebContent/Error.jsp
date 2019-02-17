<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="CSS/Error.css">
<script type="text/javascript" src="JS/jquery-3.3.1.js"></script>
<script type="text/javascript" src="JS/jquery-ui.js"></script>
<script type="text/javascript" src="JS/Error.js">
<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
</script>
<title>Something went wrong</title>
</head>
<body>

	<!-- da sistemare, che quando chiama error potrebbe non essere in sessione -->
	<%@include file="Navbar.jsp"%>
<h1 id="h1">Whoops!</h1>
<p id="p"> Something went wrong </p>
</body>
</html>