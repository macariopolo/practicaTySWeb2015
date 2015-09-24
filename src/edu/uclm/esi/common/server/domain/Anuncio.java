package edu.uclm.esi.common.server.domain;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Vector;

import javax.sql.rowset.serial.SerialException;

import org.json.JSONArray;

import edu.uclm.esi.common.server.persistence.DAOAnuncio;

public class Anuncio {
	private int idAnuncio;
	private User user;
	private String descripcion;
	private int idCategoria;

	public Anuncio(User user, String descripcion, int idCategoria) {
		super();
		this.user = user;
		this.descripcion = descripcion;
		this.idCategoria = idCategoria;
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

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdAnuncio(int idAnuncio) {
		this.idAnuncio=idAnuncio;
	}

	public int addFoto(File theFile, String uploadContentType) throws SerialException, IOException, SQLException {
		return DAOAnuncio.addFoto(this, theFile, uploadContentType);
	}

	public Vector<Integer> getIdsDeFotos() throws SQLException {
		return DAOAnuncio.getIdsDeFotos(this);
	}

	public InputStream getContenido(int idFoto) throws SQLException {
		return DAOAnuncio.getContenido(this, idFoto);
	}

	public String getContentType(int idFoto) throws SQLException {
		return DAOAnuncio.getContentType(this, idFoto);
	}
	
	
}
