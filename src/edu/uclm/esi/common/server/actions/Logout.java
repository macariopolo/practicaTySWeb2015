package edu.uclm.esi.common.server.actions;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;


@SuppressWarnings("serial")
public class Logout extends JSONAction {
	private int id;
	
	@Override
	public String execute() {
		try {
			Manager manager=Manager.get();
			User user=null;
			if (this.id!=0)
				user=manager.findUserById(id);
			if (user!=null) {
				user.getDB().close();
				manager.closeSession(user);
			} else throw new Exception("El usuario no tenía sesión abierta");
			ServletActionContext.getRequest().getSession().removeAttribute("user");
			return SUCCESS;
		} catch (Exception e) {
			ActionContext.getContext().getSession().put("exception", e);
			this.exception=e;
			return ERROR;
		}
	}

	public String getResultado() {
		JSONObject result=new JSONObject();
		try {
			if (this.exception!=null) {
				result.put("tipo", "error");
				result.put("mensaje", this.exception.toString());
			} else {
				result.put("tipo", "OK");
			}
			return result.toString();
		} catch (JSONException e) {
			return "{\"tipo\" : \"error\", \"mensaje\" : \"Error\"}";
		}
	}

	@Override
	public void setCommand(String cmd) {
		JSONObject jso;
		try {
			jso = new JSONObject(cmd);
			this.id=jso.getInt("id");
		} catch (JSONException e) {
			this.exception=e;
		}
	}
}
