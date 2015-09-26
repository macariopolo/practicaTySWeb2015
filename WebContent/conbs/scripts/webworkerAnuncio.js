/**
 * 
 */


getAnuncioAleatorio();

function getAnuncioAleatorio() {
	var request = new XMLHttpRequest();	
	request.open("get", "../../jsp/getAnuncioAleatorio.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			if (request.status==200) {
				var respuesta=JSON.parse(request.responseText);
			    postMessage(respuesta);
			} else alert("Error en getAnuncioAleatorio: " + request.status);
		}
	};
	request.send();
}
