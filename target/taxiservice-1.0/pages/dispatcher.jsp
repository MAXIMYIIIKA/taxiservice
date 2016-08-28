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
    <title>dispatcher</title>
    <link rel="stylesheet" href="resources/css/style.css">
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
            <th>Current position</th>
            <th>Target position</th>
            <th>Date</th>
            <th>Status</th>
            <th>Phone</th>
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
                <%--<td>--%>
                    <%--<form>--%>
                        <%--<button type="submit" value="${user.username}" name="delete"><spring:message code="delete_button"/></button>--%>
                        <%--<c:choose>--%>
                            <%--<c:when test="${user.enabled}">--%>
                                <%--<button type="submit" value="${user.username}" name="disable"><spring:message code="disable_button"/></button>--%>
                            <%--</c:when>--%>
                            <%--<c:otherwise>--%>
                                <%--<button type="submit" value="${user.username}" name="enable"><spring:message code="enable_button"/></button>--%>
                            <%--</c:otherwise>--%>
                        <%--</c:choose>--%>
                        <%--<button type="submit" value="${user.username}" name="openEditForm"><spring:message code="edit_button"/></button>--%>
                    <%--</form>--%>
                <%--</td>--%>
            </tr>
        </c:forEach>
    </table>
    <form method="get">
        <input type="submit" value="<spring:message code="usrmanager.showall_button" />" name="showAllOrders">
    </form>
</div>
</body>
</html>
