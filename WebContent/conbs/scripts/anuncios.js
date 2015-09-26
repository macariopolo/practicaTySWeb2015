/**
 * 	<div class="row">
		<div class="col-sm-3 col-md-3 col-lg-3"></div>
	</div>
 */

function getAnunciosAleatorios(n) {
	var cols=4;
	var filas=n/cols;
	
	for (var fila=0; fila<filas; fila++) {
		var bsFila=document.createElement("div");
		bsFila.setAttribute("class", "row");
		$("#zonaDeFotos").append(bsFila);
		
		for (var col=0; col<cols; col++) {
			var request = new XMLHttpRequest();	
			request.open("get", "../jsp/getAnuncioAleatorio.jsp");
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.onreadystatechange=function() {
				if (request.readyState==4) {
					if (request.status==200) {
						var respuesta=JSON.parse(request.responseText);
						addAnuncio(bsFila, respuesta);
					} else alert("Error en getAnunciosAleatorios: " + request.status);
				}
			};
			request.send();
		}
	}
}

function addAnuncio(bsFila, anuncioEnJSON) {
	var bsCol=document.createElement("div");
	bsCol.setAttribute("class", "col-sm-3 col-md-3 col-lg-3");
	bsCol.innerHTML=anuncioEnJSON.descripcion;
	bsFila.appendChild(bsCol);
}
