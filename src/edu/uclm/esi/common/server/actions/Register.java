package edu.uclm.esi.common.server.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.server.domain.User;

@SuppressWarnings("serial")
public class Register extends JSONAction {
	private User user;
	private String pwd1, pwd2;
	
	public String execute() {
		try {
			User.insert(this.user, pwd1);
			return SUCCESS;
		} catch (Exception e) {
			ActionContext.getContext().getSession().put("exception", e);
			this.exception=e;
			return ERROR;
		} 
	}
	
	@Override
	public void setCommand(String cmd) {
		try {
			JSONObject jso=new JSONObject(cmd);
			this.user=new User();
			this.user.setEmail(jso.get("email").toString());
			this.user.setNombre(jso.get("nombre").toString());
			this.user.setApellido1(jso.get("apellido1").toString());
			this.user.setApellido2(jso.get("apellido2").toString());
			this.user.setTelefono(jso.get("telefono").toString());
			this.user.setIdUbicacion(jso.getInt("idUbicacion"));
			this.pwd1=jso.getString("pwd1").toString();
			this.pwd2=jso.getString("pwd2").toString();
			validar();
		} catch (Exception e) {
			this.exception=e;
		}	
	}

	@Override
	public String getResultado() {
		JSONObject result=new JSONObject();
		try {
			if (this.exception!=null) {
				result.put("tipo", "error");
				result.put("mensaje", this.exception.toString());
			} else {
				result.put("tipo", "OK");
				result.put("mensaje", "OK");
			}
			return result.toString();
		} catch (JSONException e) {
			return "{\"tipo\" : \"error\", \"mensaje\" : \"Error\"}";
		}
	}
	
	private void validar() throws Exception {
		if (this.pwd1==null || this.pwd2==null || this.pwd1.length()==0 || this.pwd2.length()==0) {
			throw new Exception("Las passwords deben coincidir");
		}
		if (!this.pwd1.equals(this.pwd2)) {
			throw new Exception("Las passwords deben coincidir");
		}
		if (!validateEmail(this.user.getEmail()))
			throw new Exception("La dirección de correo es inválida");
	}
	
	 private boolean validateEmail(String email) {
	    	String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	        // Compiles the given regular expression into a pattern.
	        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
	 
	        // Match the given input against this pattern
	        Matcher matcher = pattern.matcher(email);
	        return matcher.matches();
	 
	    }
	 
}
