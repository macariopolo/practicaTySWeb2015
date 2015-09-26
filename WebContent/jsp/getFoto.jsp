<%@ page language="java" contentType="text/plain" pageEncoding="UTF-8"%>
<%@ page import="edu.uclm.esi.common.server.domain.Anuncio, java.sql.SQLException" %>

<%
String sIdFoto=request.getParameter("idFoto");
int idFoto=Integer.parseInt(sIdFoto);
Anuncio anuncio=(Anuncio) request.getSession().getAttribute("anuncio");

try {
	String contenido=anuncio.getContenido(idFoto);
	response.getWriter().print(imageDataString);
}
catch (SQLException e) {
	response.getWriter().print("Error");
}
		
%>