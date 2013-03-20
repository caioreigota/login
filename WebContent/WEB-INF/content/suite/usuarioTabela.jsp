<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script src="/login/js/jquery-1.9.0.js"></script>

<script language= "javascript"> 
function chama(id){
	console.log(id);
	$('#parametroMenu').val(id);
}
</script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sistema de Cadastro de Usuario</title>
<style type="text/css">
<!--

#menu {
	position: absolute;
	left: 800px;
	top: 200px;
	right: 0%;
}

<!-- Estilo do Fomulário -->


<!-- Estilo do Fomulário -->

<!-- Estilo da Tabela -->

/* 
	Blue Dream
	Written by Teylor Feliz  http://www.admixweb.com
*/


table { background:#D3E4E5;
 width:80%;
 color:#fff;
 font:normal 12px verdana, arial, helvetica, sans-serif;
}
caption { border:1px solid #5C443A;
 color:#5C443A;
 font-weight:bold;
 text-align:center;
}
td, th { color:#363636;
 padding:.4em;
}
tr { border:1px dotted gray;
}
thead th, tfoot th { background:#104E8B;
 color:#FFFFFF;
 text-align:left;
 text-transform:uppercase;
}
tbody td a { color:#363636;
 text-decoration:none;
}
tbody td a:visited { color:gray;
 text-decoration:line-through;
}
tbody td a:hover { text-decoration:underline;
}
tbody th a { color:#363636;
 font-weight:normal;
 text-decoration:none;
}
tbody th a:hover { color:#363636;
}
tbody td+td+td+td a { background-image:url('bullet_blue.png');
 background-position:left center;
 background-repeat:no-repeat;
 color:#03476F;
 padding-left:15px;
}
tbody td+td+td+td a:visited { background-image:url('bullet_white.png');
 background-position:left center;
 background-repeat:no-repeat;
}
tbody th, tbody td { text-align:left;
 vertical-align:top;
}
tfoot td { background:#5C443A;
 color:#FFFFFF;
 padding-top:3px;
}
.odd { background:#7EC0EE;
}
.add { background:#6CA6CD;
}
tbody tr:hover { background:#87CEFF;
 border:1px solid #03476F;
 color:#000000;
}

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
		<h1><s:actionmessage disabled="true" /></h1>
		<table>
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
						<c:choose>
							<c:when test="${usuario.id % 2 == 0}">
								<tr class="odd" onmouseover="chama('${usuario.id}')">
									<td>${usuario.id}</td>
									<td>${usuario.nome}</td>
									<td>${usuario.login}</td>
									<td><input type="submit" id="_editarUsuario" name="method:editarUsuario" value="Editar"></td>
									<td><input type="submit" id="_excluirUsuario" name="method:excluirUsuario" value="Excluir"></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr class="add" onmouseover="chama('${usuario.id}')">
									<td>${usuario.id}</td>
									<td>${usuario.nome}</td>
									<td>${usuario.login}</td>
									<td><input type="submit" id="_editarUsuario" name="method:editarUsuario" value="Editar"></td>
									<td><input type="submit" id="_excluirUsuario" name="method:excluirUsuario" value="Excluir"></td>
								</tr>
							</c:otherwise>
						</c:choose>
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