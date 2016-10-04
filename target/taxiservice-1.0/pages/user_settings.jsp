<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 28.09.2016
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="settings" /></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" >
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-3.1.0.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/js/image_preview.js" />" ></script>
</head>
<body>
<%--<img alt="img" src="data:image/jpeg;base64,${image}"/>--%>
<img id="preview"  alt="" />
    <form method="post" enctype="multipart/form-data">
        <sec:csrfInput />
        <input id="imgInput" type="file" name="avatar" accept="image/jpeg, image/png, image/gif">
        <input type="submit" value="<spring:message code="submit" />">
    </form>
</body>
</html>
