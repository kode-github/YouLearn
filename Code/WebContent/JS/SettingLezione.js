$('#b').click(function () {
	var nome = $("#nome-lezione").text();
	console.log(nome);
    $("#nome-lezione").html("");
    $("#nome-lezione").append($('<input>', {
        type: 'text',
        val: nome
    }))
	
	
});

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

function sendOrderToServer() {
	var array=[];
	str="";
    var items = $(".collection").sortable('toArray');
    var itemList = jQuery.grep(items, function (n, i) {
    	array.push([n,i]);
    	
    	str= str + n+"-"+i + ","; 

        return (n !== "" && n != null);
    });
    console.log(str);

    $("#items").html(array);
}


$(".collection").sortable({
    items: ".item"
});

$('.b-up').click(function () {
	
    var btn = $(this);
    var val = btn.val();
    if (val == 'up') moveUp(btn.parents('.item'));
    else moveDown(btn.parents('.item'));
});
var str="";
var array =[];
var orderList = jQuery.grep($(".collection").sortable('toArray'), function (n, i) {
	array.push([n,i]);
	str= str + n+"-"+i + ","; 

	return (n !== "" && n != null);
});


$("#conferma").click(function(){
	
	/*$.ajax({
	    type: 'GET',
	    url: 'localhost:8080/YouLearn/',
	    data: {"coppia": str}
	});*/
	$.get("localhost:8080/YouLearn",{"coppia" :str},function(response) {
		alert(response);
	});
	
});


