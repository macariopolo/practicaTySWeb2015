<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="UTF-8"%>
<%@ page import="edu.uclm.esi.common.server.domain.Anuncio, edu.uclm.esi.common.server.persistence.*, java.sql.*, java.io.*, java.util.Random, java.sql.SQLException, java.util.*" %>

<%

int ANUNCIOS_DESEADOS=8;
int numeroTotalDeAnuncios=DAOAnuncio.getNumeroDeAnuncios();
Random rnd=new Random();
int cols=4;
int filas=ANUNCIOS_DESEADOS/cols;
int n;

Connection bd=Broker.get().getDBSelector();
String sql="Select id from Anuncios";
PreparedStatement ps=bd.prepareStatement(sql);
ResultSet rs=ps.executeQuery();
Vector<Integer> ids=new Vector<Integer>();
while (rs.next()) {
	ids.add(rs.getInt(1));	
}
ps.close();

Anuncio anuncio;
for (int i=0; i<ids.size(); i++) {
	int id=ids.get(i);
	%>
		<div class="row">
	<%
		anuncio=new Anuncio(id);
		%>
			<div id="anuncio<%= anuncio.getIdAnuncio() %>" class="col-sm-4 col-md-4 col-lg-4">
				<% out.print(anuncio.toJSONString()); 
				%>
				<img src="data:<%= anuncio.getContentTypePrimeraFoto() %>;base64,<%=anuncio.getPrimeraFoto()%>" width="100" height="100"></img>
			</div>
		<%
		out.flush();
	%>
	</div>
	<%
}
%>

