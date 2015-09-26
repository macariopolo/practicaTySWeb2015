package edu.uclm.esi.common.server.domain;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Vector;

import javax.sql.rowset.serial.SerialException;

import edu.uclm.esi.common.server.persistence.DAOAnuncio;

public class Anuncio {
	private int idAnuncio;
	private User user;
	private String descripcion;
	private int idCategoria;
	
	private String emailUsuario;
	private String nombreCategoria;
	private int numeroDeFotos;
	
	public Anuncio() {
	}

	public Anuncio(User user, String descripcion, int idCategoria) {
		super();
		this.user = user;
		this.descripcion = descripcion;
		this.idCategoria = idCategoria;
	}
	
	public Anuncio(int idAnuncio) throws SQLException {
		this();
		DAOAnuncio.select(idAnuncio, this);
	}

	public static void insert(Anuncio a) throws SQLException {
		DAOAnuncio.insert(a);
	}

	public User getUser() {
		return user;
	}

	public int getIdAnuncio() {
		return idAnuncio;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public void setIdAnuncio(int idAnuncio) {
		this.idAnuncio=idAnuncio;
	}
	
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
	
	public String getEmailUsuario() {
		return emailUsuario;
	}
	
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	
	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public int addFoto(File theFile, String uploadContentType) throws SerialException, IOException, SQLException {
		return DAOAnuncio.addFoto(this, theFile, uploadContentType);
	}

	public Vector<Integer> getIdsDeFotos() throws SQLException {
		return DAOAnuncio.getIdsDeFotos(this);
	}
	
	public String getPrimeraFoto() throws SQLException, IOException {
		InputStream x=DAOAnuncio.getContenidoDePrimeraFoto(this);
		int length=x.available();
		byte[] bytesFichero=new byte[length];
		x.read(bytesFichero);
		String result=Base64.getEncoder().encodeToString(bytesFichero);
		return result;
	}
	
	public String getContentTypePrimeraFoto() throws SQLException {
		return DAOAnuncio.getContentTypePrimeraFoto();
	}

	public String getContenido(int idFoto) throws SQLException, IOException {
		InputStream x=DAOAnuncio.getContenido(this, idFoto);
		int length=x.available();
		byte[] bytesFichero=new byte[length];
		x.read(bytesFichero);
		String result=Base64.getEncoder().encodeToString(bytesFichero);
		return result;
	}

	public String getContentType(int idFoto) throws SQLException {
		return DAOAnuncio.getContentType(this, idFoto);
	}
	
	public static int getNumeroDeAnuncios() throws SQLException {
		return DAOAnuncio.getNumeroDeAnuncios();
	}
	
	public int getNumeroDeFotos() {
		try {
			this.numeroDeFotos=DAOAnuncio.getNumeroDeFotos(this);
		} catch (SQLException e) {
			this.numeroDeFotos=0;
		}
		return this.numeroDeFotos;
	}
	
	public String toJSONString() {
		String result="{\"idAnuncio\" : " + idAnuncio + ", \"descripcion\" : \"" + descripcion + "\", \"idCategoria\" : " + idCategoria + ", " +
				"\"emailUsuario\" : \"" + emailUsuario + "\", \"nombreCategoria\" : \"" + nombreCategoria + "\", \"numeroDeFotos\": " + getNumeroDeFotos() + "}";
		System.out.println(result);
		return result;
	}
}
