<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script language= "javascript"> 
function chama(id){
	console.log(id);
	$('#parametroMenu').val(id);
}
</script>

<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sistema de Cadastro de Usuario</title>
<style type="text/css">
<!--

#menu {
	height: 162px;
	width: 229px;
	position: absolute;
	left: 426px;
	top: 157px;
	right: 0%;
}
-->
</style>
<style type="text/css">
<!--
#cabecalho {
	height: 318px;
	width: 655px;
	position: relative;
	left: 278px;
	top: 6px;
}
-->
</style>
</head>
<body>

<div  id="cabecalho">
	<form name="form" action="usuario" method="POST">
		<s:hidden id="parametroMenu"  name="parametroMenu" />
		<h1><s:actionmessage/></h1>
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
				<th>Editar</th>
				<th>Excluir</th>
			</tr>
			</thead>
			<c:choose>
				<c:when test="${!empty listaUsuario}">
					<c:forEach items="${listaUsuario}" varStatus="i" var="usuario">
						<tr onmouseover="chama('${usuario.id}')">
							<td>${usuario.id}</td>
							<td>${usuario.nome}</td>
							<td>${usuario.login}</td>
							<td><input type="submit" id="_editarUsuario" name="method:editarUsuario" value="Editar"></td>
							<td><input type="submit" id="_excluirUsuario" name="method:excluirUsuario" value="Excluir"></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise> 
					<tr>
						<td style="color: red" colspan="5">Nenhum Registro Encontrado</td>
					</tr>
				</c:otherwise>
			</c:choose>
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
			
				<td colspan="2">
					<s:submit  method="pesquisar" value="Filtrar"/>
				</td>	
		</table>
        <div id= "menu" >
          <table>
            <tr>
              <td> <s:submit  method="criarBanco" value="Criar tabelas no banco"/> </td>
            </tr>
          </table>
          <table>
            <tr>
              <td> <s:submit  method="listarUsuario" value="Listar todos os usuarios"/> </td>
            </tr>
          </table>
          <table>
            <tr>
              <td> <s:submit  method="criarUsuario" value="Criar usuario"/></td>
            </tr>
          </table>
      </div>
	</form>
</div>
</body>
</html>