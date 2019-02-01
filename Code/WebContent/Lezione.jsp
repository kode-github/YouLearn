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
<link rel="stylesheet" href="CSS/Lezione.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css?family=Permanent+Marker|Ubuntu"
	rel="stylesheet">


<script src="build/mediaelement-and-player.min.js"></script>
<link rel="stylesheet" href="build/mediaelementplayer.css" />
<script src="build/custom.js"></script>

<title>YouLearn</title>
</head>
<body>

	<div class="videocontent">
		<video id="my_video_1" style="width: 100%; height: 100%;"
			controls="controls" width="100%" height="100%" preload="auto"
			poster="Images/black.jpg">
			<source src="https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/mp4/BigBuckBunny.mp4" type='video/mp4'>
		</video>
	</div>

	<div class="card w-75 mx-auto text-center">
		<div class="card-header text-center">NOME LEZIONE</div>
		<div class="card-body">
			<h5 class="card-title"><i class="fas fa-user fa-2x"></i> Nome docente</h5>
		</div>
		<div class="card-body">
			<h5 class="card-title"><i class="far fa-laugh-beam fa-2x"></i> Visualizzazioni</h5>
		</div>
	</div>

	<div style="margin-bottom:20px;"class="card w-75 mx-auto text-center">
		<div class="card-header text-center">COMMENTI</div>
		<div class="card-body">
			<textarea id="txtarea" name="commento"
				placeholder="Inserisci qui il tuo commento!" class="text-center"></textarea>
		</div>
		<div>
			<a href="#" class="btn float-right">AGGIUNGI COMMENTO</a>
		</div>
		
		<div class="card-body">
					<div id="com-doc" class=" card-body rounded border border-success">Commenti Docente</div>
			
		</div>
		
		<div class="card-body">
					<div class="com-ut card-body rounded border border-primary">Commenti Utenti</div>
			
		</div>

	</div>







	<%@ include file="Footer.jsp"%>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
		crossorigin="anonymous"></script>

</body>
</html>