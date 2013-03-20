<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib prefix="tag" uri="/tags/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
<c:choose>
	<c:when test="${empty usuario.id}">
		<s:text name="usuario.criacao"></s:text>
	</c:when>
	<c:otherwise>
		<s:text name="usuario.edicao"></s:text>
	</c:otherwise>
</c:choose>
</title>
<style type="text/css">
<!--
#formulario {
	position: absolute;
	left: 491px;
	top: 29px;
}
#botao {
	position: absolute;
	width: 110px;
	left: 365,000px;
	top: 50,000px;
}
-->
</style>
</head>
<body>
<div id="formulario">
<c:choose>
<c:when test="${empty usuario.id}">
	<s:text name="usuario.criacao"/>
</c:when>
<c:otherwise>
	<s:text name="usuario.edicao"/>
</c:otherwise>
</c:choose>
<form action="usuario" method="POST">
<s:hidden name="usuario.id" />
	<table >
		
				<s:textfield  label="Nome" name="usuario.nome"/>
				<br>
				<s:fielderror fieldName="usuario.nome"/>
			
				<s:textfield label="Login" name="usuario.login"/>
				<br>
				<s:fielderror fieldName="usuario.login"/>
			
				<s:password label="Senha" name="usuario.senha"/>
			
				<c:choose>
					<c:when test="${empty usuario.id}">
						<s:submit  method="salvar" value="Criar Usuario"/>
					</c:when>
					<c:otherwise>
						<s:submit  method="editar" value="Editar Usuario"/>
					</c:otherwise>
				</c:choose>
			
			
	</table>
	<div id="botao">
		<s:submit  method="listarUsuario" value="Voltar"/>
	</div>
</form>
</div>



</body>
</html>