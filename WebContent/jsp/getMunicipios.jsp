<%@ page language="java" contentType="application/json" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.uclm.esi.common.server.persistence.*, java.sql.*, org.json.*" %>

<%
String provincia=request.getParameter("provincia");
JSONArray jsa=new JSONArray();

if (provincia!=null && provincia.trim().length()>0) {
	Broker bd=Broker.get();
	String sql="Select id, nombre from Ubicaciones where tipo='Municipio' and idPadre=? order by nombre";
	PreparedStatement p=bd.getDBSelector().prepareStatement(sql);
	p.setInt(1, Integer.parseInt(provincia));
	ResultSet r=p.executeQuery();
	while (r.next()) {
		JSONObject jso=new JSONObject();
		jso.put("id", r.getInt(1));
		jso.put("nombre", r.getString(2));
		jsa.put(jso);
	}
}
%>
<%= jsa.toString() %>