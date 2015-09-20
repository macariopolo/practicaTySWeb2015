package edu.uclm.esi.common.server.actions;

import java.util.Random;

import javax.mail.MessagingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class RecoverPwd extends ActionSupport {
	private String email;
	private Exception exception;
	
	public String execute() {
		try {
			EMailSenderService server=new EMailSenderService();
			long codigo=new Random().nextLong();
			server.enviarPorGmail(this.email, codigo);
			// TODO: guardar c??digo en el servidor.
			return SUCCESS;
		} catch (MessagingException e) {
			this.exception=e;
			return ERROR;
		}
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResultado() {
		JSONObject result=new JSONObject();
		try {
			if (this.exception!=null) {
				result.put("tipo", "error");
				result.put("mensaje", this.exception.toString());
			} else {
				result.put("tipo", "RecoverPwd");
				result.put("mensaje", "OK");
			}
			return result.toString();
		} catch (JSONException e) {
			return "{\"tipo\" : \"error\", \"mensaje\" : \"Error\"}";
		}
	}

}
