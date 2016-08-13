<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 09.08.2016
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="register.title" /></title>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<div id="loginform">
    <form action="" method="post">
        <legend><spring:message code="register.legend" /></legend>

        <div class="alert alert-error">
            ${error}
        </div>
        <div class="alert alert-success">
            ${success}
        </div>
        <label for="username"><spring:message code="username" /></label>
        <input type="text" id="username" name="username"/>
        <label for="password"><spring:message code="password" /></label>
        <input type="password" id="password" name="password"/>
        <div class="form-actions">
            <button type="submit" class="btn"><spring:message code="register_button" /></button>
            <a href="login"><button type="button" class="btn"><spring:message code="login_button" /></button></a>
        </div>
        <a href="?lang=ru">RU</a>
        <a href="?lang=en">EN</a>
    </form>
</div>
</body>
</html>
