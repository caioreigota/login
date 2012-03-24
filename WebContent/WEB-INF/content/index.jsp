<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"/>

<script type="text/javascript">
function chamaMetodo(id){
	console.log(id);
	$('#parametroMenu').val(id);
	$('#metodo').val("editarUsuario");
	form.submit();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div style="text-align: center;" id="cabecalho">
	<form name="form" action="usuario" method="POST">
		<s:hidden id="parametroMenu"  name="parametroMenu" />
		<table border="1" style="width: 50%">
		<caption>
		<h1>
			Sistema de Cadastro de Usuario
		</h1>
		</caption>
			<thead>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Login</th>
				
			</tr>
			</thead>
			
			<c:forEach items="${listaUsuario}" varStatus="i" var="usuario">
			<tr onclick="chamaMetodo('${usuario.id}')">
				<td>${usuario.id}</td>
				<td>${usuario.nome}</td>
				<td>${usuario.login}</td>
				
			</tr>
			</c:forEach>
			<tr>
			</tr>
		</table>
		<table style="width: 50%" border="1">
			<caption>
				Pesquisa
			</caption>
			<tr>
				<td>Nome</td>
				<td>Login</td>
			</tr>
			<tr>
				<td><input type="text" name="usuario.nome"/></td>
				<td><input type="text" name="usuario.login"/></td>
			</tr>
			<tr>
				<td colspan="2"><s:submit  method="pesquisar" value="Criar tabelas no banco"/></td>
			</tr>
		</table>
		<table border="1">
			<tr >
				<td>
					<s:submit  method="criarBanco" value="Criar tabelas no banco"/>
				</td>
				<td>
					<s:submit  method="listarUsuario" value="Listar usuarios"/>
				</td>
				<td>
					<s:submit  method="criarUsuario" value="Criar Usuario"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>