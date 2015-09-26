<%@ page language="java" contentType="application/json; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ page import="edu.uclm.esi.common.server.domain.Anuncio, edu.uclm.esi.common.server.persistence.DAOAnuncio, java.io.*, java.util.Random, java.sql.SQLException, java.util.Base64" %>

<%
int numeroDeAnuncios=DAOAnuncio.getNumeroDeAnuncios();
Random rnd=new Random();
int n=rnd.nextInt(numeroDeAnuncios);

try {
	Anuncio anuncio=DAOAnuncio.getAnuncioIEsimo(n);
	out.println(anuncio.toJSONString());
}
catch (SQLException e) {
	response.getWriter().print("Error");
}
		
%>