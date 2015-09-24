<%@ page language="java" pageEncoding="UTF-8"%>

<%
int idFoto=Integer.parseInt(request.getParameter("idFoto"));
Anuncio anuncio=(Anuncio) request.getSession().getAttribute("anuncio");
PrintWriter out = response.getWriter();
try {
	InputStream x=anuncio.getContenido(idFoto);
	int length=x.available();
	byte[] b=new byte[length];
	x.read(b);
	String imageDataString=Base64.encodeBase64URLSafeString(b);
	response.addHeader("Content-Type", anuncio.getContentType(idFoto));
	out.print(imageDataString);
	System.put.println(imageDataString);
}
catch (SQLException e) {
	out.print("Error");
}
		
%>