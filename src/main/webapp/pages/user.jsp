<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <title><spring:message code="user" /></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" >
    <link rel="stylesheet" href="<c:url value="/resources/css/user_panel.css" />" >
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-3.1.0.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/js/common.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/js/image_upload.js" />" ></script>
</head>
<body>
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
<c:import url="upperPanel.jsp" />

<div class="main-wrapper">
    <div class="central-user-panel-wrapper">
        <div id="parent-user-info">
            <table class="simple-table entityList">
                <tr>
                    <th><spring:message code="user.number_acc_orders"/> </th>
                </tr>
                <tr><td>${numberOfUserOrders}</td></tr>
            </table>
            <div id="user-info">
                <div id="avatar">
                    <form method="get">
                        <button id="deleteBtn" name="deleteAvatar" value="true">
                            <span>delete</span>
                        </button>
                    </form>
                    <c:if test="${hasAvatar}">
                        <img id="image" alt="img" src="data:image/jpeg;base64,${image}"/>
                    </c:if>
                    <c:if test="${!hasAvatar}">
                        <img id="image" alt="img" src="<c:url value="/resources/images/no_avatar.png"/>"/>
                    </c:if>
                    <form id="change-avatar" method="post" enctype="multipart/form-data">
                        <sec:csrfInput />
                        <div id="imgInput"><spring:message code="user.change"/></div>
                        <input id="inputBtn" type="file" name="avatar" accept="image/*">
                    </form>
                </div>
                <h1 id="username">${username}</h1>

            </div>
        </div>
        <div class="table-wrapper">
            <table class="simple-table entityList">
                <tr>
                    <td></td>
                    <td colspan="6" class="table-header"><spring:message code="user.orders_history"/></td>
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
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>
