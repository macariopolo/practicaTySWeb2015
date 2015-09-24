package edu.uclm.esi.common.server.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.json.JSONArray;

import edu.uclm.esi.common.server.domain.Anuncio;
import edu.uclm.esi.common.server.domain.User;

public class DAOAnuncio {

	public static void insert(Anuncio a) throws SQLException {
		User user=a.getUser();
		Connection bd=user.getDB();
		try {
			String sql="{call insertarAnuncio (?, ?, ?, ?)}";
			CallableStatement cs=bd.prepareCall(sql);
			cs.setInt(1, user.getId());
			cs.setString(2, a.getDescripcion());
			cs.setInt(3, a.getIdCategoria());
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.executeUpdate();
			a.setIdAnuncio(cs.getInt(4));
		}
		catch (SQLException e) {
			throw e;
		}
	}

	public static int addFoto(Anuncio anuncio, File theFile, String uploadContentType) throws IOException, SerialException, SQLException {
		FileInputStream fi=new FileInputStream(theFile);
		byte[] b=new byte[fi.available()];
		fi.read(b);
		Blob contenido=new SerialBlob(b);
		fi.close();
		Connection bd=anuncio.getUser().getDB();
		try {
			String sql="{call insertarFoto (?, ?, ?, ?)}";
			CallableStatement cs=bd.prepareCall(sql);
			cs.setInt(1, anuncio.getIdAnuncio());
			cs.setBlob(2,contenido);
			cs.setString(3,  uploadContentType);
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.executeUpdate();
			return cs.getInt(4);
		}
		catch (SQLException e) {
			throw e;
		}
	}

	public static Vector<Integer> getIdsDeFotos(Anuncio anuncio) throws SQLException {
		Vector<Integer> result=new Vector<Integer>();
		Connection bd=anuncio.getUser().getDB();
		try {
			String sql="select idFoto from Fotos where idAnuncio=?";
			PreparedStatement ps=bd.prepareStatement(sql);
			ps.setInt(1, anuncio.getIdAnuncio());
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				result.add(rs.getInt(1));
			}
			return result;
		}
		catch (SQLException e) {
			throw e;
		}
	}

	public static InputStream getContenido(Anuncio anuncio, int idFoto) throws SQLException {
		InputStream result=null;
		Connection bd=anuncio.getUser().getDB();
		try {
			String sql="select contenido from Fotos where idFoto=?";
			PreparedStatement ps=bd.prepareStatement(sql);
			ps.setInt(1, idFoto);
			ResultSet rs=ps.executeQuery();
			if (rs.next()) {
				result=rs.getBlob(1).getBinaryStream();
			}
			return result;
		}
		catch (SQLException e) {
			throw e;
		}
	}

	public static String getContentType(Anuncio anuncio, int idFoto) throws SQLException {
		String result=null;
		Connection bd=anuncio.getUser().getDB();
		try {
			String sql="select contentType from Fotos where idFoto=?";
			PreparedStatement ps=bd.prepareStatement(sql);
			ps.setInt(1, idFoto);
			ResultSet rs=ps.executeQuery();
			if (rs.next()) {
				result=rs.getString(1);
			}
			return result;
		}
		catch (SQLException e) {
			throw e;
		}
	}

}
