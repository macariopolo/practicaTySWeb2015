package edu.uclm.esi.tysweb2015.acciones;

import java.util.Vector;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import edu.uclm.esi.common.server.domain.Anuncio;

public class GetIdsDeFotos extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Exception exception;
	private Anuncio anuncio;

	public String execute() {
		try { 
			this.anuncio = (Anuncio) ServletActionContext.getRequest().getSession().getAttribute("anuncio");
			return SUCCESS;
		} catch (Exception e) {
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
				Vector<Integer> nnff = this.anuncio.getIdsDeFotos();
				JSONArray jsa=new JSONArray(nnff);
				result.put("mensaje", jsa);
			}
		}
		catch (Exception e) {}
		return result.toString();
	}
}
