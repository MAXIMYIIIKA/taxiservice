<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title><spring:message code="dispatch" /></title>
    <sec:csrfMetaTags />
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" >
    <script src="<c:url value="/resources/js/jquery-3.1.0.js" />" ></script>
    <script src="<c:url value="/resources/js/dispatch.js" />" ></script>
</head>
<body>
<c:import url="upperPanel.jsp" />
    <div class="dispatch-table-wrapper">
        <table id="new-orders" class="simple-table">
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
        </table>
    </div>
    <div id="new-order"><div id="new-order-sound"></div></div>
</body>
</html>
