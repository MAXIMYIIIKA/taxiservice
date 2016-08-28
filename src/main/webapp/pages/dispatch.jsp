<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 21.08.2016
  Time: 0:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test</title>
    <sec:csrfMetaTags />
    <link rel="stylesheet" href="resources/css/style.css">
    <script src="resources/js/jquery-3.1.0.js"></script>
    <script src="resources/js/dispatch.js"></script>
</head>
<body>
    <table id="new-orders" style="border: 3px solid">
        <tr class="table-header">
            <th>id</th>
            <th><spring:message code="username" /></th>
            <th>Current position</th>
            <th>Target position</th>
            <th>Date</th>
            <th>Status</th>
            <th>Phone</th>
        </tr>
    </table>
    <div id="new-order"><div id="new-order-sound"></div></div>
</body>
</html>
