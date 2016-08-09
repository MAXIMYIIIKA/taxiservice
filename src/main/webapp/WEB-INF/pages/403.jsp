<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 08.08.2016
  Time: 23:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Access is denied!</title>
</head>
<body>
<h1>Error 403. Sorry, Access is denied!</h1>
<form action="logout"
      method="post">
    <input type="submit"
           value="Log out" />
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</body>
</html>
