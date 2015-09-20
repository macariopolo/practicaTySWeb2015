package edu.uclm.esi.common.server.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
public abstract class JSONAction extends ActionSupport {
	protected Exception exception=null;
	protected String ip;
	
	public JSONAction() {
		try {
			ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");  
			HttpServletRequest request = ServletActionContext.getRequest();
			this.ip=request.getRemoteAddr();
		} catch (Exception e) {
			this.exception=e;
		}
	}
	
	public abstract String execute();

	public abstract void setCommand(String cmd);

	public abstract String getResultado();
}
