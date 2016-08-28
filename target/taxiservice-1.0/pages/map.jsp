<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <title>map</title>
    <link rel="stylesheet" href="resources/css/style.css">
    <script type="text/javascript" src="resources/js/jquery-3.1.0.js"></script>
    <script type="text/javascript" src="resources/js/jquery.maskedinput-1.2.2.js"></script>
    <script type="text/javascript" src="resources/js/map.js"></script>
    <script type="text/javascript" src="resources/js/order.js"></script>
</head>
<body>
<input id="origin-input" class="controls" type="text"
       placeholder="Enter an origin location">

<input id="destination-input" class="controls" type="text"
       placeholder="Enter a destination location">

<%--<div id="mode-selector" class="controls">--%>
    <%--<input type="radio" name="type" id="changemode-walking" checked="checked">--%>
    <%--<label for="changemode-walking">Walking</label>--%>

    <%--<input type="radio" name="type" id="changemode-transit">--%>
    <%--<label for="changemode-transit">Transit</label>--%>

    <%--<input type="radio" name="type" id="changemode-driving">--%>
    <%--<label for="changemode-driving">Driving</label>--%>
<%--</div>--%>
<div id="map"></div>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC4KOwBnPvvEgubHllGkwAwlYnqSoD5G78&signed_in=true&libraries=places&callback=initMap&language=en"
        async defer></script>
<form method="post">
    <sec:csrfInput />
    <p><label for="currentLocation">Current location: </label>
    <span id="currentLocation"></span></p>
    <input type="hidden" id="currentLat" name="currentLat">
    <input type="hidden" id="currentLng" name="currentLng">
    <p><label for="targetLocation">Target location: </label>
    <span id="targetLocation"></span></p>
    <input type="hidden" id="targetLat" name="targetLat">
    <input type="hidden" id="targetLng" name="targetLng">
<p>Total Distance: <span id="total"></span></p>
    <p>Phone number: +375
    <input type="tel" id="phone" name="phone" pattern="^[0-9]+$"></p>
    <input type="submit" id="submitRoute">
</form>
<a href="?lang=ru">RU</a>
<a href="?lang=en">EN</a>
</body>
</html>
