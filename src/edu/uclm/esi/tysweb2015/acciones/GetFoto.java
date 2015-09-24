package edu.uclm.esi.tysweb2015.acciones;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import edu.uclm.esi.common.server.domain.Anuncio;

public class GetFoto extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Exception exception;
	private int idFoto;
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
	
	public void setIdFoto(int idFoto) {
		this.idFoto = idFoto;
	}
	
	public String getContentType() throws SQLException {
		return this.anuncio.getContentType(this.idFoto);
	}
	
	public InputStream getContenido() throws SQLException, IOException {
		InputStream x=this.anuncio.getContenido(idFoto);
		int length=x.available();
		byte[] b=new byte[length];
		x.read(b);
		String imageDataString=Base64.encodeBase64URLSafeString(b);
		return this.anuncio.getContenido(this.idFoto);
	}
	
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
		}
		catch (Exception e) {}
		return result.toString();
	}
}
