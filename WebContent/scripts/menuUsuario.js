/**
 * 
 */

var zonaPrincipal=window.parent.document.getElementById("zonaCentro.principal");

function menuUsuario() {
	zonaPrincipal.src="MenuUsuario.html";
}

function cargarCategorias() {
	var selectCategorias=document.getElementById("categoria");
	var request = new XMLHttpRequest();	
	request.open("post", "getCategorias.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4 && request.status==200) {
			var categorias=JSON.parse(request.responseText);
			for (var i=0; i<categorias.length; i++) {
				var categoria=categorias[i];
				var optionCategoria=document.createElement("option");
				optionCategoria.setAttribute("id", categoria.id);
				optionCategoria.innerHTML=categoria.nombre;
				selectCategorias.appendChild(optionCategoria);
			}
		}
	};
	request.send();
}

function subirFoto() {
	var request=new XMLHttpRequest();
	var progreso=document.getElementById("progreso");
	var f=document.getElementById("datosAnuncio");
	var formData=new FormData(f);
	request.open("post", "SubirFoto.action");
	request.onreadystatechange = function() {
		if (request.readyState==3) {
			progreso.innerHTML="Respuesta recibida";
		} else if (request.readyState==4) {
			var respuesta=JSON.parse(request.responseText);
			respuesta=JSON.parse(respuesta.resultado);
			if (request.status==200) {
				progreso.innerHTML=respuesta.mensaje;
			} else {
				progreso.innerHTML=respuesta.mensaje;
			}
		}
	};		
	request.send(formData);
}