<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="cabecalho" id="cabecalho">
Sistema de Cadastro de Usuario
</div>
<h1>
ola mundo
</h1>
<a href="olamundo">Redirecionar<a>
<table>
	<tr>
		<td>Id</td>
		<td>Nome</td>
		<td>Login</td>
	</tr>
	<c:forEach items="${listaUsuario}" var="usuario">
	<tr>
		<td>${usuario.id}</td>
		<td>${usuario.nome}</td>
		<td>${usuario.login}</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>