<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 08.08.2016
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="login.title" /></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" >
    <script src="<c:url value="/resources/js/jquery-3.1.0.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/js/common.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/js/login.js" />" ></script>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    <c:redirect url="/main" />
</sec:authorize>
<div id="parent-alert" class="hidden">
    <div class="alert">
        <div class="alert-error">
            ${error}
        </div>
        <div class="alert-success">
            ${success}
        </div>
        ${function}
    </div>
</div>
<div class = "parent-form">
    <div class="login-form">
        <form onsubmit="return checkForm(this);" action="login" method="post">
            <sec:csrfInput />
            <legend><spring:message code="login.legend" /></legend>
            <input type="text" id="username" name="username" placeholder="<spring:message code="username" />" autofocus/><br/>
            <input type="password" id="password" name="password" placeholder="<spring:message code="password" />"/>
            <div class="form-actions">
                <button type="submit" class="btn"><spring:message code="login_button" /></button>
                <a href="register"><button type="button" class="btn"><spring:message code="register_button" /></button></a>
            </div>
            <a href="?lang=ru"><img src="<c:url value="/resources/images/ru.png"/>" height="20px" width="30px"></a>
            <a href="?lang=en"><img src="<c:url value="/resources/images/en.gif"/>" height="20px" width="30px"></a>
        </form>
    </div>
</div>
</body>
</html>
