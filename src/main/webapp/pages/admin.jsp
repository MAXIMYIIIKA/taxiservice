<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 10.08.2016
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="admin.title" /></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
</head>
<body>
<c:import url="upperPanel.jsp" />
<h1><spring:message code="admin.hello_msg" /> ${user}! </h1>
<a href="<c:url value="/admin/usermanager" />"><button type="button" class="btn"><spring:message code="usrmanager.title" /></button></a>
</body>
</html>
