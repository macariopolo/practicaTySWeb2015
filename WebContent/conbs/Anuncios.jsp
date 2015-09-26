<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="UTF-8"%>
<%@ page import="edu.uclm.esi.common.server.domain.Anuncio, edu.uclm.esi.common.server.persistence.DAOAnuncio, java.io.*, java.util.Random, java.sql.SQLException, java.util.Base64" %>

<%

int ANUNCIOS_DESEADOS=8;
int numeroTotalDeAnuncios=DAOAnuncio.getNumeroDeAnuncios();
Random rnd=new Random();
int cols=4;
int filas=ANUNCIOS_DESEADOS/cols;
int n;

for (int fila=0; fila<filas; fila++) {
	%>
		<div class="row">
	<%
	for (int col=0; col<cols; col++) {
		n=rnd.nextInt(numeroTotalDeAnuncios);
		Anuncio anuncio=DAOAnuncio.getAnuncioIEsimo(n);
		%>
			<div id="anuncio<%= anuncio.getIdAnuncio() %>" class="col-sm-4 col-md-4 col-lg-4">
				<% out.print(anuncio.toJSONString()); 
				%>
				<img src="data:<%= anuncio.getContentTypePrimeraFoto() %>;base64,<%=anuncio.getPrimeraFoto()%>" width="100" height="100"></img>
			</div>
		<%
		out.flush();
	}
	%>
	</div>
	<%
}
%>

