<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 08.08.2016
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello</title>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
${msg}
<form action="logout" method="post" style="display: ${logout_display}">
    <input type="submit" value="logout">
    <sec:csrfInput/>
</form>
<a href="login" style="display: ${login_display}"><button type="button">Log in</button></a>
</body>
</html>
