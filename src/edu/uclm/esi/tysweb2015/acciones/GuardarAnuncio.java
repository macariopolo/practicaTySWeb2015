package edu.uclm.esi.tysweb2015.acciones;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import edu.uclm.esi.common.server.domain.Anuncio;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;

public class GuardarAnuncio extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String descripcion;
	private int idCategoria;
	private Exception exception;
	private Anuncio anuncio;

	public String execute() {
		try { 
			User user=(User) ServletActionContext.getRequest().getSession().getAttribute("user");
			this.anuncio=Manager.get().insertarAnuncio(user, descripcion, idCategoria);
			ServletActionContext.getRequest().getSession().setAttribute("anuncio", this.anuncio);
			return SUCCESS;
		} catch (Exception e) {
			this.exception=e;
			return ERROR;
		}
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion=descripcion;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getResultado() {
		JSONObject result=new JSONObject();
		try {
			if (this.exception!=null) {
				result.put("tipo", "error");
				result.put("mensaje", this.exception.toString());
			} else {
				result.put("tipo", "OK");
				result.put("mensaje", anuncio.getIdAnuncio());
			}
			return result.toString();
		} catch (JSONException e) {
			return "{\"tipo\" : \"error\", \"mensaje\" : \"Error\"}";
		}	
	}
}
