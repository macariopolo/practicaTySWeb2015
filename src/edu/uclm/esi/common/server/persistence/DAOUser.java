package edu.uclm.esi.common.server.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.uclm.esi.common.server.domain.User;


public class DAOUser {
	
	public static void registrar(User user, String pwd) throws SQLException {
		Connection bd=Broker.get().getDBInserter();
		try {
			String sql="{call insertarUsuario (?, ?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement cs=bd.prepareCall(sql);
			cs.setString(1, user.getEmail());
			cs.setString(2, pwd);
			cs.setString(3, user.getNombre());
			cs.setString(4, user.getApellido1());
			cs.setString(5, user.getApellido2());
			cs.setString(6, user.getTelefono());
			cs.setInt(7, user.getIdUbicacion());
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			String exito=cs.getString(8);
			if (exito!=null && !(exito.equals("OK")))
				throw new SQLException(exito);
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			bd.close();
		}
	}

	public static Connection identificar(String email, String pwd) throws SQLException {
		return Broker.get().getDB(email, pwd);
	}
	
	public static void registrarConGoogle(String email) throws SQLException {
		Connection bd=Broker.get().getDBInserter();
		try {
			String sql="{call insertarUsuarioGoogle (?, ?, ?, ?, ?)}";
			CallableStatement cs=bd.prepareCall(sql);
			cs.setString(1, email);
			cs.setString(2, Broker.get().GOOGLE_PWD);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			String exito=cs.getString(3);
			// q1 y q2 se ponen simplemente con fines ilustrativos, para que se vea el resultado de la transacción del procedimiento almacenado.
			String q1=cs.getString(4);		
			String q2=cs.getString(5);
			if (exito!=null && !(exito.equals("OK")))
				throw new SQLException(exito);
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			bd.close();
		}
	}

	public static Connection identificarConGoogle(String email) throws SQLException {
		try {
			registrarConGoogle(email);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return Broker.get().getGoogleDB(email);
	}

	public static void select(Connection bd, String email, User user) throws SQLException {
		String sql="Select id, fechaDeAlta, nombre, apellido1, apellido2, telefono, idUbicacion from Usuarios where email=?";
		PreparedStatement ps=bd.prepareStatement(sql);
		ps.setString(1, email);
		ResultSet r=ps.executeQuery();
		if (r.next()) {
			user.setId(r.getInt(1));
			user.setEmail(email);
			user.setFechaDeAlta(r.getDate(2));
			user.setNombre(r.getString(3));
			user.setApellido1(r.getString(4));
			user.setApellido2(r.getString(5));
			user.setTelefono(r.getString(6));
			user.setIdUbicacion(r.getInt(7));
		}
		ps.close();
	}
}
