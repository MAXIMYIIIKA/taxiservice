<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 02.09.2016
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User panel</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" >
</head>
<body>
<c:import url="upperPanel.jsp" />
<div>
    <table class="entityList">
        <tr class="table-header">
            <th><spring:message code="user.number_acc_orders"/> </th>
        </tr>
        <tr><td>${numberOfUserOrders}</td></tr>
    </table>
</div>
<div>
    <h3><spring:message code="user.orders_history"/>: </h3>
    <table class="entityList">
        <tr class="table-header">
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
                <td>Lat: ${order.currentLocation.lat}; Lng: ${order.currentLocation.lng}</td>
                <td>Lat: ${order.targetLocation.lat}; Lng: ${order.targetLocation.lng}</td>
                <td>${order.dateTime.date} ${order.dateTime.time}</td>
                <td>${order.status}</td>
                <td>${order.phone}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
