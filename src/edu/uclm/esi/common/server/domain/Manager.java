package edu.uclm.esi.common.server.domain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;

public class Manager {
	private static Manager yo;
	
	private Hashtable<String, User> usersByEmail;
	private Hashtable<Integer, User> usersById;
	
	private Manager() {
		this.usersByEmail=new Hashtable<String, User>();
		this.usersById=new Hashtable<Integer, User>();
	}
	
	public static Manager get() {
		if (yo==null)
			yo=new Manager();
		return yo;
	}
	
	public void add(User user, String ip) throws IOException {
		if (usersByEmail.get(user.getEmail())!=null) { 
			usersByEmail.remove(user.getEmail());
			usersById.remove(user.getId());
		}
		user.setIp(ip);
		usersByEmail.put(user.getEmail(), user);
		usersById.put(user.getId(), user);
	}
	
	public User findUserByEmail(String email) {
		return this.usersByEmail.get(email);
	}

	public User closeSession(User user) throws SQLException {
		user=usersById.remove(user.getId());
		if (user!=null) {
			user.getDB().close();
		}
		return usersByEmail.remove(user.getEmail());
	}

	public User findUserById(int id) {
		return this.usersById.get(id);
	}

	public Anuncio insertarAnuncio(User user, String descripcion, int idCategoria) throws SQLException {
		Anuncio a=new Anuncio(user, descripcion, idCategoria);
		Anuncio.insert(a);
		return a;
	}
}
