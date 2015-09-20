<%@ page language="java" contentType="application/json" pageEncoding="ISO-8859-1"%>
<%@ page import="edu.uclm.esi.common.server.persistence.*, java.sql.*, org.json.*" %>

<%
String ca=request.getParameter("ca");
JSONArray jsa=new JSONArray();
if (ca!=null && ca.trim().length()>0) {
	Broker bd=Broker.get();
	String sql="Select id, nombre from Ubicaciones where tipo='Provincia' and idPadre=? order by nombre";
	PreparedStatement p=bd.getDBPrivilegiada().prepareStatement(sql);
	p.setInt(1, Integer.parseInt(ca));
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