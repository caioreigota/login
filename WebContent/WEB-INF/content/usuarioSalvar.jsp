<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib prefix="s" uri="/struts-tags" %>
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
</head>
<body>

<c:choose>
<c:when test="${empty usuario.id}">
	<s:text name="usuario.criacao"></s:text>
</c:when>
<c:otherwise>
	<s:text name="usuario.edicao"></s:text>
</c:otherwise>
</c:choose>
<form action="usuario" method="POST">
	<table >
		<tr>
			<td>
				Nome:
			</td>
			<td>
				<input type="text" name="usuario.nome"/>
			</td>
		</tr>
		<tr>
			<td>
				Login:
			</td>
			<td>
				<input type="text" name="usuario.login"/>
			</td>
		</tr>
		<tr>
			<td>
				Senha:
			</td>
			<td>
				<input type="password" name="usuario.senha"/>
			</td>
		</tr>
		<tr>
			<td>
				
			</td>
			<td>
				<c:choose>
					<c:when test="${empty usuario.id}">
						<s:submit  method="salvar" value="usuario.criar"/>
					</c:when>
					<c:otherwise>
						<s:submit  method="editar" value="usuario.editar"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
			
	</table>
</form>

</body>
</html>