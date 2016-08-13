<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 10.08.2016
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="usrmanager.title" /></title>
    <link rel="stylesheet" href="../resources/css/style.css">
</head>
<body>
<div>
    <div class="alert alert-success">
        ${success}
    </div>
    <div class="alert alert-error">
        ${error}
    </div>
    <table class="userlist">
        <tr class="table-header">
            <th>id</th>
            <th><spring:message code="username" /></th>
            <th><spring:message code="usrmanager.is_enable" /></th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.userId}</td>
                <td>${user.username}</td>
                <td>${user.enabled}</td>
            </tr>
        </c:forEach>
    </table>
    <table class="userlist">
        <tr class="table-header">
            <th><spring:message code="username" /></th>
            <th><spring:message code="user_roles" /></th>
        </tr>
        <c:forEach var="user_roles" items="${user_roles}">
            <tr>
                <td>${user_roles.key}</td>
                <td>
                    <table>
                        <c:forEach var="role" items="${user_roles.value}">
                            <tr>
                                <td>${role}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<a href="../admin" class="panel-button"><button type="button" class="btn"><spring:message code="admin_button" /></button></a>

<div>
    <form method="get">
        <label for="userId"><spring:message code="usrmanager.findbyid" /></label>
        <input type="text" id="userId" name="userId">
        <button type="submit"><spring:message code="find_button" /></button>
    </form>
    <form method="get">
        <input type="submit" value="<spring:message code="usrmanager.showall_button" />" name="showAllUsers">
    </form>
</div>
<a href="?lang=ru">RU</a>
<a href="?lang=en">EN</a>
</body>
</html>
