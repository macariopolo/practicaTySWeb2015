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
		Connection bd=Broker.get().getDBSelector();
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

	public static int getNumeroDeAnuncios() throws SQLException {
		int result=0;
		Connection bd=Broker.get().getDBSelector();
		try {
			String sql="select count(*) from Anuncios";
			PreparedStatement ps=bd.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			if (rs.next()) {
				result=rs.getInt(1);
			}
			return result;
		}
		catch (SQLException e) {
			throw e;
		}
	}

	public static void select(int idAnuncio, Anuncio anuncio) throws SQLException {
		String sql="Select Anuncios.id, Anuncios.fechaDeAlta, descripcion, Categorias.nombre, Usuarios.email " + 
			"from Anuncios inner join Categorias on Anuncios.idCategoria=Categorias.id " + 
			"inner join Usuarios on Anuncios.idAnunciante=Usuarios.id where Anuncios.id=?";
		Connection bd=Broker.get().getDBSelector();
		PreparedStatement ps=bd.prepareStatement(sql);
		ResultSet r=ps.executeQuery();
		//r.relative(idAnuncio);
		bd.close();
	}
	
	public static Anuncio getAnuncioIEsimo(int n) throws SQLException {
		String sql="Select Anuncios.id, Anuncios.fechaDeAlta, descripcion, Categorias.id, Categorias.nombre, Usuarios.id, Usuarios.email " + 
				"from Anuncios inner join Categorias on Anuncios.idCategoria=Categorias.id " + 
				"inner join Usuarios on Anuncios.idAnunciante=Usuarios.id";
		Connection bd=Broker.get().getDBSelector();
		PreparedStatement ps=bd.prepareStatement(sql);
		ResultSet r=ps.executeQuery();
		r.relative(n);
		Anuncio result=new Anuncio();
		result.setIdAnuncio(r.getInt(1));
		result.setDescripcion(r.getString(3));
		result.setIdCategoria(r.getInt(4));
		result.setNombreCategoria(r.getString(5));
		result.setEmailUsuario(r.getString(7));
		bd.close();
		return result;
	}

	public static int getNumeroDeFotos(Anuncio anuncio) throws SQLException {
		int result=0;
		Connection bd=Broker.get().getDBSelector();
		try {
			String sql="select count(*) from Fotos where idAnuncio=?";
			PreparedStatement ps=bd.prepareStatement(sql);
			ps.setInt(1, anuncio.getIdAnuncio());
			ResultSet rs=ps.executeQuery();
			if (rs.next()) {
				result=rs.getInt(1);
			}
			return result;
		}
		catch (SQLException e) {
			throw e;
		}
	}

	public static InputStream getContenidoDePrimeraFoto(Anuncio anuncio) throws SQLException {
		InputStream result=null;
		Connection bd=Broker.get().getDBSelector();
		try {
			String sql="select contenido from Fotos limit 1";
			PreparedStatement ps=bd.prepareStatement(sql);
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

	public static String getContentTypePrimeraFoto() throws SQLException {
		String result=null;
		Connection bd=Broker.get().getDBSelector();
		try {
			String sql="select contentType from Fotos limit 1";
			PreparedStatement ps=bd.prepareStatement(sql);
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
