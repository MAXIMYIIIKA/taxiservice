<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 08.08.2016
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<div id="loginform">
    <form action="login" method="post">
            <legend>Please Login</legend>
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
            <div class="alert alert-error">
                ${error}
            </div>
            <div class="alert alert-success">
                ${logout}
            </div>
            <label for="username">Username</label>
            <input type="text" id="username" name="username"/>
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
            <div class="form-actions">
                <button type="submit" class="btn">Log in</button>
                <a href="register"><button type="button" class="btn">Registration</button></a>
            </div>
    </form>
</div>
</body>
</html>
