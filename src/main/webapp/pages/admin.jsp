<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<h1><spring:message code="admin.hello_msg" /> ${user}! </h1>
<a href="admin/usermanager"><button type="button" class="btn"><spring:message code="usrmanager.title" /></button></a>
<a href="main"><button type="button" class="btn"><spring:message code="go_main_button" /></button></a>
<a href="?lang=ru">RU</a>
<a href="?lang=en">EN</a>
</body>
</html>
