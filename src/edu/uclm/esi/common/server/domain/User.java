package edu.uclm.esi.common.server.domain;

import java.sql.Connection;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.server.persistence.DAOUser;

public class User {
	private int id;
	private String email, nombre, apellido1, apellido2, telefono;
	private java.sql.Date fechaDeAlta;
	private int idUbicacion;
	private Connection db;
	private String ip;

	public User() {
	}
	
	public User(Connection bd, String email) throws SQLException {
		this();
		this.db=bd;
		DAOUser.select(bd, email, this);
	}

	public static Connection identify(String email, String pwd) throws SQLException {
		return DAOUser.identificar(email, pwd);
	}

	public static Connection identifyWithGoogle(String email) throws SQLException {
		return DAOUser.identificarConGoogle(email);
	}

	public static void insert(User user, String pwd) throws SQLException {
		DAOUser.registrar(user, pwd);
	}

	public String getEmail() {
		return email;
	}

	public java.sql.Date getFechaDeAlta() {
		return fechaDeAlta;
	}

	public void setFechaDeAlta(java.sql.Date fechaDeAlta) {
		this.fechaDeAlta = fechaDeAlta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Connection getDB() {
		return db;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject jso=new JSONObject();
		jso.put("tipo", "OK");
		jso.put("id", this.id);
		jso.put("email", this.email);
		jso.put("fechaDeAlta", fechaDeAlta);
		jso.put("nombre", nombre);
		jso.put("apellido1", apellido1);
		jso.put("apellido2", apellido2);
		jso.put("telefono", telefono);
		jso.put("idUbicacion", idUbicacion);
		return jso;
	}

	public void setIp(String ip) {
		this.ip=ip;
	}

	public String getIp() {
		return this.ip;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	
	public String getApellido1() {
		return apellido1;
	}
	
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	
	public String getApellido2() {
		return apellido2;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setIdUbicacion(int idUbicacion) {
		this.idUbicacion = idUbicacion;
	}
	
	public int getIdUbicacion() {
		return idUbicacion;
	}
}
