var iFrameRegistro=window.parent.document.getElementById("iFrameRegistro");

if (iFrameRegistro==undefined || iFrameRegistro==null) {
	iFrameRegistro=window.parent.document.getElementById("iFrameRegistro");
}

/***
 * COMPROBACIONES AL CARGAR LA PÁGINA PRINCIPAL
 */
function comprobarRecarga() {
	if (sessionStorage.getItem("idUsuario")!=null && sessionStorage.getItem("email")!=null) {
		cargarInformacionDeCuenta();
	} else {
		sessionStorage.removeItem("idUsuario");
		sessionStorage.removeItem("email");
	}
}

/**
 * REGISTRO
 */

function registrarse() {
	iFrameRegistro.src="FormRegistro.html";
	iFrameRegistro.setAttribute("style", "display:block");
}

function Usuario() {
	this.email=document.getElementById("email").value;
	this.nombre=document.getElementById("nombre").value;
	this.apellido1=document.getElementById("apellido1").value;
	this.apellido2=document.getElementById("apellido2").value;
	this.telefono=document.getElementById("telefono").value;
	this.pwd1=document.getElementById("pwd1").value;
	this.pwd2=document.getElementById("pwd2").value;
	this.idUbicacion=document.getElementById("formMunicipios").contentDocument.getElementById("municipios").value;
}

function crearCuenta() {
	var usuario=new Usuario();
	var usuarioEnJSON=JSON.stringify(usuario);
	
	var request = new XMLHttpRequest();	
	request.open("post", "Register.action");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4 && request.status==200) {
			var respuesta=JSON.parse(request.responseText);
			respuesta=JSON.parse(respuesta.resultado);
			if (respuesta.tipo=="error") {
				alert("Ocurrió un error en tu registro: " + respuesta.mensaje);
			} else {
				alert("Bienvenid@, " + usuario.nombre + " " + usuario.apellido1 + " " + usuario.apellido2);
			}
		}
	};
	var pars="command=" + usuarioEnJSON;
	request.send(pars);
}


/***
 * LOGIN
 */

function entrar() {
	iFrameRegistro.src="FormLogin.html";
	iFrameRegistro.setAttribute("style", "display:block");
}

function Credenciales() {
	this.email=document.getElementById("email").value;
	this.pwd=document.getElementById("pwd").value;
}

function actualizar() {
	alert("Hola");
}

function login() {
	var credenciales=new Credenciales();
	var credencialesEnJSON=JSON.stringify(credenciales);
	
	var request = new XMLHttpRequest();	
	request.open("post", "Login.action");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4 && request.status==200) {
			var respuesta=JSON.parse(request.responseText);
			respuesta=JSON.parse(respuesta.resultado);
			if (respuesta.tipo=="error") {
				alert("Ocurrió un error en tu registro: " + respuesta.mensaje);
			} else {
				sessionStorage.setItem("idUsuario", respuesta.idUser);
				sessionStorage.setItem("email", credenciales.email);
				window.parent.document.getElementById("iFrameRegistro").src="FormLogueado.html";
			}
		}
	};
	var pars="command=" + credencialesEnJSON;
	request.send(pars);
}

function cargarInformacionDeCuenta() {
	iFrameRegistro.src="FormLogueado.html";
	iFrameRegistro.setAttribute("style", "display:block");
}

/***
 * LOGOUT
 */
function logout() {
	var comando= {
			id : sessionStorage.getItem("idUsuario")
	};
	
	var request = new XMLHttpRequest();	
	request.open("post", "Logout.action");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4 && request.status==200) {
			var respuesta=JSON.parse(request.responseText);
			respuesta=JSON.parse(respuesta.resultado);
			if (respuesta.tipo=="error") {
				alert("Ocurrió un error al desconectar: " + respuesta.mensaje);
			} 
			sessionStorage.clear();
			window.parent.document.getElementById("iFrameRegistro").setAttribute("style", "display:none");
		}
	};
	var pars="command=" + JSON.stringify(comando);
	request.send(pars);
}

/***
 * TRAER DATOS PERSONALES DEL USUARIO
*/
function getDatos() {
	var comando= {
			id : sessionStorage.getItem("idUsuario")
	};
	
	var request = new XMLHttpRequest();	
	request.open("post", "GetDatosUsuario.action");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4 && request.status==200) {
			var respuesta=JSON.parse(request.responseText);
			respuesta=JSON.parse(respuesta.resultado);
			if (respuesta.tipo=="error") {
				alert("Ocurrió un error al recuperar los datos: " + respuesta.mensaje);
			} else {
				var email=document.getElementById("email")
				var nombre=document.getElementById("nombre");
				var apellido1=document.getElementById("apellido1");
				var apellido2=document.getElementById("apellido2");
				var fechaDeAlta=document.getElementById("fechaDeAlta");
				var telefono=document.getElementById("telefono");
				var idUbicacion=document.getElementById("idUbicacion");
				if (email!=null) email.value=respuesta.email;
				if (nombre!=null) nombre.value=respuesta.nombre;
				if (apellido1!=null) apellido1.value=respuesta.apellido1;
				if (apellido2!=null) apellido2.value=respuesta.apellido2;
				if (fechaDeAlta!=null) fechaDeAlta.value=respuesta.fechaDeAlta;
				if (telefono!=null) telefono.value=respuesta.telefono;
				if (idUbicacion!=null) idUbicacion.value=respuesta.idUbicacion;
			}
		}
	};
	var pars="command=" + JSON.stringify(comando);
	request.send(pars);
}