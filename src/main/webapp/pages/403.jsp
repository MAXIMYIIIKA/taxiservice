<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 08.08.2016
  Time: 23:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="403.title" /></title>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<h1><spring:message code="403.error" /></h1>
        <a href="main"><button type="button" class="btn"><spring:message code="go_main_button" /></button></a>
<a href="?lang=ru">RU</a>
<a href="?lang=en">EN</a>
</body>
</html>
