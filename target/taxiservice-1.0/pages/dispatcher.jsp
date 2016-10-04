<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 20.08.2016
  Time: 2:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="dispatcher" /></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" >
    <script src="<c:url value="/resources/js/jquery-3.1.0.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/js/common.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/js/converter.js" />" ></script>
</head>
<body>
<c:import url="upperPanel.jsp" />
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
<div>
    <table class="simple-table entityList">
        <tr>
            <td></td>
            <td class="table-header" colspan="7"><spring:message code="orders" /></td>
        </tr>
        <tr>
            <th>id</th>
            <th><spring:message code="username" /></th>
            <th><spring:message code="order.current_position" /></th>
            <th><spring:message code="order.target_position" /></th>
            <th><spring:message code="date" /></th>
            <th><spring:message code="status" /></th>
            <th><spring:message code="phone" /></th>
        </tr>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.orderId}</td>
                <td>${order.username}</td>
                <td>${order.currentLocation.degreesMinutesSeconds}</td>
                <td>${order.targetLocation.degreesMinutesSeconds}</td>
                <td>${order.dateTime.date} ${order.dateTime.time}</td>
                <td>${order.status}</td>
                <td>${order.phone}</td>
                <td><form method="get"><button type="submit" value="${order.orderId}" name="deleteOrder"><spring:message code="delete_button" /></button></form></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
