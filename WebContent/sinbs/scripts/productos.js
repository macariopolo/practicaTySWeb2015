/**
 * 
 */

var ulCategorias=document.getElementById("ulCategorias");
var zonaPrincipal=document.getElementById("zonaCentro.principal");
var zonaRegistro=document.getElementById("cabecera.registro");

cargarArbol();

function cargarArbol() {
	var request = new XMLHttpRequest();	
	request.open("post", "../jsp/getCategorias.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			if (request.status==200) {
				var categorias=JSON.parse(request.responseText);
				for (var i=0; i<categorias.length; i++) {
					var categoria=categorias[i];
					var liCategoria=document.createElement("li");
					var link=document.createElement("a");
					link.setAttribute("id", "categoria" + categoria.id);
					link.setAttribute("href", "javascript:cargarSubcategorias(" + categoria.id + ")");
					link.innerHTML=categoria.nombre;
					liCategoria.setAttribute("class", "categoria");
					liCategoria.appendChild(link);
					ulCategorias.appendChild(liCategoria);
				}
			} else alert(request.status);
		}
	};
	request.send();
}

function cargarSubcategorias(idCategoria) {
	limpiarSubcategorias();
	var categoriaPadre=document.getElementById("categoria" + idCategoria);
	var request = new XMLHttpRequest();	
	request.open("post", "../jsp/getSubcategorias.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4 && request.status==200) {
			var categorias=JSON.parse(request.responseText);
			if (categorias.length==0) {
				cargarProductos(idCategoria);
			} else {				
				var ul=document.createElement("ul");
				categoriaPadre.parentNode.appendChild(ul);
				for (var i=0; i<categorias.length; i++) {
					var categoria=categorias[i];
					var liCategoria=document.createElement("li");
					var link=document.createElement("a");
					link.setAttribute("href", "javascript:cargarProductos(" + categoria.id + ")");
					link.innerHTML=categoria.nombre;
					liCategoria.setAttribute("class", "subcategoria");
					liCategoria.appendChild(link);
					ul.appendChild(liCategoria);
				}
			}
		}
	};
	var pars="categoria=" + idCategoria;
	request.send(pars);
}

function limpiarSubcategorias() {
	var llii=ulCategorias.getElementsByTagName("li");
	for (var i=0; i<llii.length; i++) {
		var li=llii[i];
		var uull=li.getElementsByTagName("ul");
		for (var j=0; j<uull.length; j++) {
			li.removeChild(uull[j]);
		}
	}
}

function cargarProductos(idCategoria) {
	zonaPrincipal.innerHTML="Está pendiente de implementar la <code>function cargarProductos(idCategoria)</code>: cargarProductos(" + idCategoria + ")";
} 
