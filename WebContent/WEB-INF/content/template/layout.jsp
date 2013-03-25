<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tag" uri="/tags/tags" %>

<%-- atributo da request --%>
<tiles:importAttribute name="title" scope="request"/>
<%-- definição do layout --%>
<html>
  <head>
<link rel="stylesheet" type="text/css" href="../../login/css/layout.css">
<!-- recupera o atributo setado na definição do tiles.xml -->
    <title><tiles:getAsString name="title"/></title>
  </head>
  <body>
  <div class="tudo" id="tudo">
    <div class="cabecalho" id="cabecalho">
      <tiles:insertAttribute name="header" />
    </div>
    <div class="menu" id="menu">
      <tiles:insertAttribute name="menu" />
    </div>
    <div class="corpo" id="corpo">
      <tiles:insertAttribute name="body" />
    </div>
    <div class="rodape" id="rodape">
      <tiles:insertAttribute name="footer" />
    </div>
  </div>
  </body>
</html>