<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 10.08.2016
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User manager</title>
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
            <th>USER_ID</th>
            <th>USERNAME</th>
            <th>IS_ENABLED</th>
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
            <th>USERNAME</th>
            <th>USER_ROLE</th>
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

<a href="../admin" class="panel-button"><button type="button" class="btn">admin panel</button></a>

<div>
    <form method="get">
        <label for="userId">Find user by id:</label>
        <input type="text" id="userId" name="userId">
        <input type="submit" value="findById">
    </form>
    <form method="get">
        <input type="submit" value="showAllUsers" name="showAllUsers">
    </form>
</div>
</body>
</html>
