
// current chat destination
var chatTo = 'all';

// Chat room that holds every conversation
var chatRoom = {
	'all' : []
};

var socket = undefined;

var contador = 0;
var contadorEntrada = 0;

$(document).ready(function() {

	//alert(localStorage.key(0));
	//alert(localStorage.getItem("elems"));
	
	if(localStorage.getItem($("#usuario_nick").val()) != null)
	{		
		$("#message-board").append(localStorage.getItem($("#usuario_nick").val()));
		/*
		 	alert(localStorage.getItem("contador"));
			alert(localStorage.getItem("contadorEntrada"));	
		 */
		if(localStorage.getItem("contador") != null)
		{
/*
			alert(localStorage.getItem("contador"));
			alert(localStorage.getItem("contadorEntrada"));
*/
			contador = localStorage.getItem("contador");
		
			if(localStorage.getItem("contadorEntrada") != null){

				alert(localStorage.getItem("contador"));
				alert(localStorage.getItem("contadorEntrada"));

				
				contadorEntrada = localStorage.getItem("contadorEntrada");
				
			}
		}
		
		
	}

						socket = new WebSocket("ws://localhost:8080/" +"JatrickWebServer" + "/chat/" + $("#room").val());

						socket.onopen = socketOnOpen;
						socket.onmessage = socketOnMessage;
						socket.onclose = socketOnClose;
						

	 
		 $("#connect").click(function(){
			 
			
			socket = new WebSocket("ws://localhost:8080/" +"JatrickWebServer" + "/chat/" + $("#room").val());
			
			socket.onopen = socketOnOpen;
			socket.onmessage = socketOnMessage;
			socket.onclose = socketOnClose;
			
			
			
			
			if(localStorage.getItem($("#usuario_nick").val()) != null)
			{		
				$("#message-board").append(localStorage.getItem($("#usuario_nick").val()));
				//alert(localStorage.getItem($("#usuario_nick").val()));
				
				contador = localStorage.getItem("contador");
				contadorEntrada = localStorage.getItem("contadorEntrada");
			/*	
				alert(localStorage.getItem("contador"));
				alert(localStorage.getItem("contadorEntrada"));
*/
				
			}
			
			$("#disconnect").prop('disabled', false);
			$("#connect").prop('disabled', true);
		 });
		
						
		 $("#send").click(function(){
			 
			 if($("#message").val().length != 0)
			{
				 				 
				 	sendMessage();
				 	
			}else
 			{
 					//alert("Ingrese un mensaje");
 			}
		 });
	 
		 
		 $("#all").click(function(){
			 
			 chatToFn('all');
	 
		 });
	 
		 
		 $("#disconnect").click(function(){
			 
			disconnect();

			
		 });
		 
		 $("#logoutLink").click(function(){
			 localStorage.removeItem($("#usuario_nick").val());
				//localStorage.clear()
				//alert("HOLAAA")
				//alert(localStorage.getItem($("#usuario_nick").val()));
				
			 });
	
	 
});

function disconnect() {
	// close the connection and the reset the socket object
	
	socket.close();
	socket = undefined;
		  
    $("#disconnect").prop('disabled', true);
	$("#connect").prop('disabled', false);
		
}

function socketOnOpen(e) {
	/*
	var element = document.getElementById("message");
	var attrValue = element.getAttribute('disabled');
	element.disabled = false;
	$("#username").prop('disabled', true);
	*/
	$("#connect").prop('disabled', true);
	$("#disconnect").prop('disabled', false);
	$("#send").prop('disabled', false);
	
	$("#message").attr( "disabled",false );
	//$("#message").prop('disabled', false);
	
}

function socketOnMessage(e) {
	var eventName = e.data.substr(0, e.data.indexOf("|"));
	var data = e.data.substr(e.data.indexOf("|") + 1);
	
	
	var fn;
	if (eventName == 'newUser')
		fn = newUser;
	else if (eventName == 'removeUser')
		fn = removeUser;
	else if (eventName == 'message')
		fn = getMessageFrom;
	

	fn.apply(null, data.split('|'));
	
	/// Guardo en sesion
	
	var elems =	$( "#message-board" ).html();
	
	$("#chatToEl").html('All');
	
	localStorage.setItem($("#usuario_nick").val(), elems);
	/*
	 ACA GUARDO EN SESION
	 */
	
}

function socketOnClose(e) {
	
	$("#connect").prop('disabled', false);
	$("#username").prop('disabled', false);
	$("#disconnect").prop('disabled', true);
	$("#send").prop('disabled', true);
	$("#message").prop('disabled', true);
	
		//$("#username").val('');
	var elems =	$( "#message-board" ).html();
		
		$("#message-board").html('');
		
		$("#chatToEl").html('All');
		
		localStorage.setItem($("#usuario_nick").val(), elems);
		//alert(localStorage.key(0));
		//alert(localStorage.key(0));
		
	//	var elem2 =localStorage.getItem("elems");
		
		
		
	//	$("#message-board").append(elem2);
	
	$("#userList").html('');
	
	
	
	contador = 0;
	contadorEntrada = 0;
}

function newUser() {
	// if there is no users, return from the function
	if (arguments.length == 1 && arguments[0] == "")
		return;
	var usernameList = arguments;

	// Loop through all online users and create a list from it
	var documentFragment = document.createDocumentFragment();
	for (var i = 0; i < usernameList.length; i++) {
		var username = usernameList[i];
		var liEl = document.createElement("li");
		liEl.id = username;
		liEl.textContent = username;
		liEl.onclick = chatToFn(username);
		if (username !=	$("#username").val())
			liEl.classList.add('hoverable');
		documentFragment.appendChild(liEl);
	}
	;
	$("#userList").append(documentFragment);
}

function getMessage(sender, message, to) {
	to = to || sender;

	if (chatTo == to) {
		var newChatEl = createNewChat(sender, message);
		$("#message-board").append(newChatEl);
		var d = new Date();
	
		
		
		$("#divMensaje"+"Yo"+contador).append('<label class="fecha">Enviado: '+ d.getDate().toString()+"/"+(d.getMonth()+1).toString()+"/"+d.getFullYear().toString()+"  "+d.getHours().toString() +":"+d.getMinutes().toString()+"</label>");
		$("#divMensaje"+"Yo"+contador).append("<br />");
		
		/*
		$("#limit").css({
		    height:$("#div").height()
		});
		*/
		
	} else {
		
		var toEl = $("#userList").$('#' + to);
		addCountMessage(toEl);
	}

	if (chatRoom[to])
		chatRoom[to].push(newChatEl);
	else
		chatRoom[to] = [ newChatEl ];
}

function getMessageFrom(sender, message, to,count) {
	to = to || sender;

	if (chatTo == to) {
		
		contadorEntrada = contadorEntrada + 1;		
		
		// Guardo en sesion contadorEntrada
		localStorage.setItem("contadorEntrada", contadorEntrada);
	
	//	alert(localStorage.getItem("contadorEntrada"));

		
		
		var newChatEl = createNewChatFrom(sender, message,contadorEntrada);
		
		$("#message-board").append(newChatEl);
		
		var d = new Date();
			
		
		$("#divMensaje"+sender+contadorEntrada).append('<label class="fecha">Recibido: '+ d.getDate().toString()+"/"+(d.getMonth()+1).toString()+"/"+d.getFullYear().toString()+"  "+d.getHours().toString() +":"+d.getMinutes().toString()+"</label>");
		$("#divMensaje"+sender+contadorEntrada).append("<br />");
		
		
		
	} else {
		
		var toEl = $("#userList").$('#' + to);
		addCountMessage(toEl);
	}

	if (chatRoom[to])
		chatRoom[to].push(newChatEl);
	else
		chatRoom[to] = [ newChatEl ];
}

function removeUser(removedUsername) {
	
	var usernameList = $("#userList").children();
	
	for (var i = 0; i < usernameList.length; i++) {
		var username = usernameList[i].textContent;
		if (username == removedUsername) {			
			$("#userList #" + usernameList[i]).remove();			
		}
	}
}

function createNewChat(sender, message) {
	var newChatDivEl = document.createElement('div');
	var senderEl = document.createElement('span');
	var messageEl = document.createElement('span');
	var d = new Date();

	
	
	//alert(d.getDate()+"/"+(d.getMonth()+1)+"/"+d.getFullYear()+"  "+d.getHours() +":"+d.getMinutes());//+":"+d.getSeconds());
	
	if (sender == 	$("#username").val()){
		sender = 'Yo';
		contador = contador + 1;
		
		// Guardar en sesion
		localStorage.setItem("contador", contador);
	//	alert(localStorage.getItem("contador"));
		
	}
	
	newChatDivEl.setAttribute("id", "divMensaje"+sender+contador.toString());
	newChatDivEl.setAttribute("class", "mensajeChat");


	
	senderEl.textContent = sender;
	messageEl.textContent = message;

	
	newChatDivEl.appendChild(senderEl);
	newChatDivEl.appendChild(messageEl);
	

	
	return newChatDivEl;
}

function createNewChatFrom(sender, message,count) {
	var newChatDivEl = document.createElement('div');
	var senderEl = document.createElement('span');
	var messageEl = document.createElement('span');
	
	newChatDivEl.setAttribute("id", "divMensaje"+sender+contadorEntrada.toString());
	newChatDivEl.setAttribute("class", "mensajeChat");
	
	senderEl.textContent = sender;
	messageEl.textContent = message;

	
//	$("#divMensaje"+sender+count).append(d.getDate()+"/"+(d.getMonth()+1)+"/"+d.getFullYear()+"  "+d.getHours() +":"+d.getMinutes());
	//messageEl.append(d.getDate()+"/"+(d.getMonth()+1)+"/"+d.getFullYear()+"  "+d.getHours() +":"+d.getMinutes());
	
	newChatDivEl.appendChild(senderEl);
	newChatDivEl.appendChild(messageEl);
	return newChatDivEl;
}

function addCountMessage(toEl) {

	var countEl = toEl.$(".count");
	
	if (countEl) {
		var count = countEl.textContent;
		count = +count;
		countEl.textContent = count + 1;
	} else {
		var countEl = document.createElement('span');
		countEl.classList.add('count');
		countEl.textContent = '1';
		toEl.appendChild(countEl);
	}
}


function sendMessage() {
	
	var message = $("#message").val();
	if (message == '')
		return;
	// la funcion send envia mensajes al servidor
	
	
	socket.send(chatTo + '|' + message);
	$("#message").val('');

	var sender = $("#username").val();
	getMessage(sender, message, chatTo);
	
	$("#message-board").scrollTop($("#message-board").height());
	
	/// Guardo en sesion
	var elems =	$( "#message-board" ).html();
	
	$("#chatToEl").html('All');
	
	localStorage.setItem($("#usuario_nick").val(), elems);
	
	
}

function chatToFn(username) {
	return function(e) {
		if (username == $("#username").val())
			return;
		
		
		/*
		
		var countEl = $("#message-board").$('#' + username + '>.count');
	//	var countEl = usernameListEl.querySelector('#' + username + '>.count');
		if (countEl) {
			countEl.remove();
		}
		*/
		
		
	$("#chatTo").val(username);
		chatTo = username;
		$("#message-board").html('');
		var conversationList = chatRoom[chatTo];
		if (!conversationList)
			return;
		var df = document.createDocumentFragment();
		conversationList.forEach(function(conversation) {
			df.appendChild(conversation);
		});
		
		$("#message-board").append(df);
	}
}

