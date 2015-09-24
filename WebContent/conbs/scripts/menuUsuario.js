/**
 * 
 */

function menuUsuario() {
	$("#zonaPrincipal").load("MenuUsuario.html");
}

function guardarAnuncio() {
	var request=new XMLHttpRequest();
	request.open("post", "GuardarAnuncio.action");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			if (request.status==200) {
				var respuesta=JSON.parse(request.responseText);
				localStorage.setItem("idAnuncio", respuesta.mensaje);
				$("#formFoto").show();
			} else {
				alert("Error en guardarAnuncio: " + request.responseText);
			}
		} 
	}
	var pars="idCategoria=" + document.getElementById("categoria").value + "&descripcion=" + document.getElementById("descripcion").value;
	request.send(pars);
}

function cargarCategorias() {
	var selectCategorias=document.getElementById("categoria");
	var request = new XMLHttpRequest();	
	request.open("post", "../jsp/getCategorias.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			if (request.status==200) {
				var categorias=JSON.parse(request.responseText);
				for (var i=0; i<categorias.length; i++) {
					var categoria=categorias[i];
					var optionCategoria=document.createElement("option");
					optionCategoria.setAttribute("value", categoria.id);
					optionCategoria.innerHTML=categoria.nombre;
					selectCategorias.appendChild(optionCategoria);
				}
			} else {
				alert("Error en cargarCategorias: " + request.responseText);
			}
		}
	};
	request.send();
}

function subirFoto() {
	var request=new XMLHttpRequest();
	var progreso=document.getElementById("progreso");
	var f=document.getElementById("formFoto");
	var formData=new FormData(f);
	request.open("post", "SubirFoto.action");
	request.onreadystatechange = function() {
		if (request.readyState==3) {
			progreso.innerHTML="Respuesta recibida";
		} 
		if (request.readyState==4) {
			var respuesta=JSON.parse(request.responseText);
			respuesta=JSON.parse(respuesta.resultado);
			if (request.status==200) {
				progreso.innerHTML="Foto subida correctamente. Id=" + respuesta.mensaje;
				cargarZonaDeFotos();
			} else {
				progreso.innerHTML=respuesta.mensaje;
			}
		}
	};		
	request.send(formData);
}

function cargarZonaDeFotos() {
	var request=new XMLHttpRequest();
	var zonaDeFotos=document.getElementById("zonaDeFotos");
	zonaDeFotos.innerHTML="";
	request.open("post", "GetIdsDeFotos.action");
	request.onreadystatechange = function() {
		if (request.readyState==4) {
			if (request.status==200) {
				var resultado=JSON.parse(request.responseText);  // Llega: {"resultado": [1, 2, 3]}
				resultado=JSON.parse(resultado.resultado);
				var idsFotos=resultado.mensaje;
				if (idsFotos.length>0) {
					var tabla=crearTabla(idsFotos);
					zonaDeFotos.appendChild(tabla);
				}
			} else {
				alert("Error en cargarZonaDeFotos: " + request.responseText);
			}
		}
	};		
	request.send();
}

function crearTabla(idsFotos) {
	var tabla=document.createElement("table");
	var cont=0;
	var columnas=3;
	var filas=Math.round(idsFotos.length/columnas);
	for (var iFila=0; iFila<filas; iFila++) {
		var row=document.createElement("tr");
		for (var iCol=0; iCol<=columnas; iCol++) {
			var celda=document.createElement("td");
			if (cont<idsFotos.length) {
				cargarFoto(idsFotos[cont], celda);
				cont++;
			}
			row.appendChild(celda);
		}
		tabla.appendChild(row);
	}
	return tabla;
}

function cargarFoto(idFoto, celda) {
	var request=new XMLHttpRequest();
	request.open("get", "../jsp/getFoto.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.responseType="arraybuffer";
	request.onreadystatechange = function() {
		if (request.readyState==4) {
			if (request.status==200) {
				var nodoImg=document.createElement("img");
				var blob=new Blob([request.response], {type:"image/jpeg"});
				alert("blob: " + blob);
				var reader=new FileReader();
				reader.readAsBinaryString(blob);
				alert("blob: " + blob);
				var contenido=reader.result;
				alert("contenido: " + contenido);
				var losBytes=btoa(contenido);
				alert("losBytes: " + losBytes);
				nodoImg.setAttribute("src", "data:image/jpeg;base64," + losBytes);
				nodoImg.setAttribute("width", "50");
				nodoImg.setAttribute("height", "50");
				celda.appendChild(nodoImg);
			} else {
				alert("Error en cargarFoto: " + request.responseText);
			}
		}
	};
	var pars="idFoto=" + idFoto;
	request.send(pars);
}