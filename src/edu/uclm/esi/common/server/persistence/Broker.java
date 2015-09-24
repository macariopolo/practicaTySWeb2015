package edu.uclm.esi.common.server.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Broker {
	public final String GOOGLE_PWD = "JugadorGoogle35";
	private static Broker yo;
	private String url;
	
	private Broker() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.url="jdbc:mysql://alarcosj.esi.uclm.es:3306/tysweb2015?noAccessToProcedureBodies=true";
		} catch (ClassNotFoundException e) { 
			System.out.println(e.toString());
		}
	}
	
	public static Broker get() {
		if (yo==null)
			yo=new Broker();
		return yo;
	}
	
	public Connection getDBSelector() throws SQLException {
		return DriverManager.getConnection(url, "selectorTSW2015", "");
	}
	
	public Connection getDBInserter() throws SQLException {
		return DriverManager.getConnection(url, "inserterTyS2015", "inserterTyS2015");
	}
	
	public Connection getDB(String email, String password) throws SQLException {
		Connection db=getDBSelector();
		try {
			String SQL="Select id from Usuarios where email=?";
			PreparedStatement p=db.prepareStatement(SQL);
			p.setString(1, email);
			ResultSet r=p.executeQuery();
			Connection result=null;
			if (r.next()) {
				int id=r.getInt(1);
				String idUsuario="tysweb2015" + id;
				result=DriverManager.getConnection(url, idUsuario, password);
				r.close();
			} else {
				throw new SQLException("Login o password inválidos");
			}
			return result;
		} 
		catch (SQLException e) {
			throw e;
		}
		finally {
			db.close();
		}
	}

	public Connection getGoogleDB(String email) throws SQLException {
		Connection db=getDBSelector();
		try {
			String SQL="Select id from User where email=?";
			PreparedStatement p=db.prepareStatement(SQL);
			p.setString(1, email);
			ResultSet r=p.executeQuery();
			Connection result=null;
			if (r.next()) {
				int id=r.getInt(1);
				String idUsuario="jugadorGoogle" + id;
				result=DriverManager.getConnection(url, idUsuario, GOOGLE_PWD);
				r.close();
			} else {
				throw new SQLException("Login o password inválidos");
			}
			return result;
		} 
		catch (SQLException e) {
			throw e;
		}
		finally {
			db.close();
		}
	}
	
	
}
