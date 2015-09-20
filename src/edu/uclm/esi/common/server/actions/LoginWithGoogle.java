package edu.uclm.esi.common.server.actions;

import java.sql.Connection;

import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;

@SuppressWarnings("serial")
public class LoginWithGoogle extends JSONAction {
	private String email;
	private User user;
	private Exception exception;
	private String userType;
	
	public String execute() {
		try {
			Connection bd=User.identifyWithGoogle(email);
			this.user=new User(bd, email);
			Manager manager=Manager.get();
			manager.add(user, ip);
			return SUCCESS;
		} catch (Exception e) {
			this.exception=e;
			ActionContext.getContext().getSession().put("exception", e);
			return ERROR;
		}
	}

	@Override
	public void setCommand(String cmd){
		JSONObject jso;
		try {
			jso = new JSONObject(cmd);
			this.email=jso.get("email").toString();
			this.userType=jso.get("userType").toString();
		} catch (JSONException e) {
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
				result.put("tipo", "LoginOK");
				result.put("idUser", user.getId());
			}
			return result.toString();
		} catch (JSONException e) {
			return "{\"tipo\" : \"error\", \"mensaje\" : \"Error\"}";
		}
	}

}
