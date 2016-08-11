<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 09.08.2016
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register page</title>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<div id="loginform">
    <form action="" method="post">
        <legend>Register form</legend>
        <sec:csrfInput />
        <div class="alert alert-error">
            ${error}
        </div>
        <div class="alert alert-success">
            ${success}
        </div>
        <label for="username">Username</label>
        <input type="text" id="username" name="username"/>
        <label for="password">Password</label>
        <input type="password" id="password" name="password"/>
        <div class="form-actions">
            <button type="submit" class="btn">Register</button>
            <a href="login"><button type="button" class="btn">Log in</button></a>
        </div>
    </form>
</div>
</body>
</html>
