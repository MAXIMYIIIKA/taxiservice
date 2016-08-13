<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 08.08.2016
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="main.title" /></title>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
${msg}
<sec:authorize access="hasRole('ROLE_USER')">
    <form action="logout" method="post">
        <input type="submit" value="<spring:message code="logout_button" />">
        <sec:csrfInput/>
    </form>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ANONYMOUS')">
    <a href="login"><button type="button"><spring:message code="login_button" /></button></a>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <a href="admin"><button type="button"><spring:message code="admin_button" /></button></a>
</sec:authorize>
<a href="?lang=ru">RU</a>
<a href="?lang=en">EN</a>
</body>
</html>
