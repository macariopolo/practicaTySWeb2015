<%@ page language="java" contentType="application/json" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.uclm.esi.common.server.persistence.*, java.sql.*, org.json.*" %>

<%
JSONArray jsa=new JSONArray();

Broker bd=Broker.get();
String sql="Select id, nombre from Categorias where isnull(idPadre) order by nombre";
PreparedStatement p=bd.getDBPrivilegiada().prepareStatement(sql);
ResultSet r=p.executeQuery();
while (r.next()) {
	JSONObject jso=new JSONObject();
	jso.put("id", r.getInt(1));
	jso.put("nombre", r.getString(2));
	jsa.put(jso);
}
%>
<%= jsa.toString() %>