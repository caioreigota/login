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
    <!-- recupera o atributo setado na definição do tiles.xml -->
    <title><tiles:getAsString name="title"/></title>
  </head>
  <body>  
    <table width="100%" height="100%">
      <tr bgcolor="yellow">
        <td colspan="2">
          <tiles:insertAttribute name="header" />
        </td>
      </tr>
      <tr>
        <td bgcolor="gray">
          <tiles:insertAttribute name="menu" />
        </td>
        <td>
          <tiles:insertAttribute name="body" />
        </td>
      </tr>
      <tr bgcolor="yellow">
        <td colspan="2">
          <tiles:insertAttribute name="footer" />
        </td>
      </tr>
    </table>  
  </body>
</html>