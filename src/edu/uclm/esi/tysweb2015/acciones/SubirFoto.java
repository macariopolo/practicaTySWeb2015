package edu.uclm.esi.tysweb2015.acciones;

import java.io.File;
import java.util.Enumeration;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edu.uclm.esi.common.server.domain.Anuncio;

public class SubirFoto extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private File upload;//The actual file
	private String uploadContentType; //The content type of the file
	private String tempFileName;
	private Exception exception;
	private int idFoto;

	public String execute() {
		try { 
			String tmpFolder=System.getProperty("java.io.tmpdir");
			int rnd = Math.abs(new Random().nextInt());
			this.tempFileName = tmpFolder + rnd;
			File theFile = new File(tempFileName);
			FileUtils.copyFile(upload, theFile);
			Anuncio anuncio=(Anuncio) ServletActionContext.getRequest().getSession().getAttribute("anuncio");
			this.idFoto=anuncio.addFoto(theFile, this.uploadContentType);
			return SUCCESS;
		} catch (Exception e) {
			this.exception=e;
			return ERROR;
		}
	}
	
	public void setFoto(File upload) {
		this.upload = upload;
	}

	public void setFotoContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getResultado() {
		JSONObject result=new JSONObject();
		try {
			if (this.exception!=null) {
				result.put("tipo", "error");
				result.put("mensaje", this.exception.toString());
			} else {
				result.put("tipo", "OK");
				result.put("mensaje", this.idFoto);
			}
			return result.toString();
		} catch (JSONException e) {
			return "{\"tipo\" : \"error\", \"mensaje\" : \"Error\"}";
		}	
	}
}
