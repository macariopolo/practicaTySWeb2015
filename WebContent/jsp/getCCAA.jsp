<%@ page language="java" contentType="application/json" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.uclm.esi.common.server.persistence.*, java.sql.*, org.json.*" %>

<%
Broker bd=Broker.get();
String sql="Select id, nombre from Ubicaciones where tipo='Comunidad autónoma' order by nombre";
PreparedStatement p=bd.getDBSelector().prepareStatement(sql);
JSONArray jsa=new JSONArray();
ResultSet r=p.executeQuery();
while (r.next()) {
	JSONObject jso=new JSONObject();
	jso.put("id", r.getInt(1));
	jso.put("nombre", r.getString(2));
	jsa.put(jso);
}
%>
<%= jsa.toString() %>