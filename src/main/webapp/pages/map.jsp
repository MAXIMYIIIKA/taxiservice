<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 18.08.2016
  Time: 11:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="order" /></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" >
    <link rel="stylesheet" href="<c:url value="/resources/css/map.css" />" >
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-3.1.0.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery_masked_input.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/js/common.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/js/map.js" />" ></script>
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
<input id="origin-input" class="controls" type="text"
       placeholder="<spring:message code="map.enter_origin_location" />">

<input id="destination-input" class="controls" type="text"
       placeholder="<spring:message code="map.enter_target_location" />">

<div id="map"></div>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC4KOwBnPvvEgubHllGkwAwlYnqSoD5G78&signed_in=true&libraries=places&callback=initMap&language=<spring:message code="current_locale" />"
        async defer></script>
<form method="post">
    <sec:csrfInput />
    <table class="simple-table">
        <tr>
            <td>
                <label for="currentLocation">
                    <spring:message code="order.current_position" />:
                </label>
            </td>
            <td>
                <span id="currentLocation"></span>
            </td>
            <input type="hidden" id="currentLat" name="currentLat">
            <input type="hidden" id="currentLng" name="currentLng">
        </tr>
        <tr>
            <td>
                <label for="targetLocation"><spring:message code="order.target_position" />: </label>
            </td>
            <td>
                <span id="targetLocation"></span>
            </td>
            <input type="hidden" id="targetLat" name="targetLat">
            <input type="hidden" id="targetLng" name="targetLng">
        </tr>
        <tr>
            <td><spring:message code="map.total_distance"/>:</td>
            <td><span id="total"></span> </td>
        </tr>
        <tr>
            <td><spring:message code="phone" />:</td>
            <td>+375<input type="tel" id="phone" name="phone" required></td>
        </tr>
        <tr>
            <td></td><td><input type="submit" id="submitRoute" value="<spring:message code="submit" />"></td>
        </tr>
    </table>
</form>
</body>
</html>
