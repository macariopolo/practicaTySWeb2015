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

public class SubirFoto extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private File upload;//The actual file
	private String uploadContentType; //The content type of the file
	private String uploadFileName; //The uploaded file name
	private String fileCaption;//The caption of the file entered by user
	private String tempFileName;
	private Exception exception;

	public String execute() {
		try { 
			HttpServletRequest request = ServletActionContext.getRequest();
			Enumeration<String> parNames=request.getParameterNames();
			String parName, parValue;
			while (parNames.hasMoreElements()) {
				parName=parNames.nextElement();
				parValue=request.getParameter(parName);
				System.out.println("<li>" + parName + " : " + parValue + "</li>");
			}
			String tmpFolder=System.getProperty("java.io.tmpdir");
			int rnd = Math.abs(new Random().nextInt());
			this.tempFileName = tmpFolder + rnd;
			File theFile = new File(tempFileName);
			FileUtils.copyFile(upload, theFile);
			return SUCCESS;
		} catch (Exception e) {
			this.exception=e;
			return ERROR;
		}
	}

	public void setFileCaption(String fileCaption) {
		this.fileCaption = fileCaption;
	}

	public void setFoto(File upload) {
		this.upload = upload;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
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
			return result.toString();
		} catch (JSONException e) {
			return "{\"tipo\" : \"error\", \"mensaje\" : \"Error\"}";
		}	
	}
}
