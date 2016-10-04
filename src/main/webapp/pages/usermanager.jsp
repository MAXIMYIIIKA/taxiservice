<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 10.08.2016
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="usrmanager" /></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" >
    <script src="<c:url value="/resources/js/jquery-3.1.0.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/resources/js/common.js" />" ></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/usermanager.css" />" >
</head>
<body>
<c:import url="upperPanel.jsp" />
<div id="parent-alert" class="hidden">
    <div class="alert">
        <div class="alert-success">
            ${success}
        </div>
        <div class="alert-error">
            ${error}
        </div>
        ${function}
    </div>
</div>
<div id="features" class="simple-table">
    <form method="get">
        <label for="userId"><spring:message code="usrmanager.findbyid" /></label>
        <input type="text" id="userId" name="userId" required pattern="^[0-9]+$" placeholder="id">
        <button type="submit"><spring:message code="find_button" /></button>
    </form>
    <form method="get">
        <input type="submit" value="<spring:message code="usrmanager.showall_button" />" name="showAllUsers">
    </form>
</div>
<div id="tables">
    <table class="simple-table user-list">
        <tr>
            <td></td>
            <td class="table-header" colspan="4"><spring:message code="users" /></td>
        </tr>
        <tr>
            <th>id</th>
            <th><spring:message code="username" /></th>
            <th><spring:message code="usrmanager.is_enable" /></th>
            <th><spring:message code="usrmanager.action" /></th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.userId}</td>
                <td>${user.username}</td>
                <td>${user.enabled}</td>
                <td>
                    <form>
                        <button type="submit" value="${user.username}" name="delete"><spring:message code="delete_button"/></button>
                        <c:choose>
                            <c:when test="${user.enabled}">
                                <button type="submit" value="${user.username}" name="disable"><spring:message code="disable_button"/></button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" value="${user.username}" name="enable"><spring:message code="enable_button"/></button>
                            </c:otherwise>
                        </c:choose>
                        <button type="submit" value="${user.username}" name="openEditForm"><spring:message code="edit_button"/></button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td><input form="createUser" type="text" id="username" name="username" required pattern="^[a-zA-Z]{3,}$" placeholder="<spring:message code="username" />"/></td>
            <td><input form="createUser" type="password" id="password" name="password" required pattern="^[a-zA-Z0-9]{4,}$" placeholder="<spring:message code="password" />"/></td>
            <td><input form="createUser" type="submit" value="<spring:message code="create_button" />"/></td>
        </tr>
        <form id="createUser" method="post">
            <sec:csrfInput />
        </form>
    </table>
    <table class="simple-table userRolesList">
        <tr>
            <td></td>
            <td class="table-header" colspan="3"><spring:message code="user_roles" /></td>
        </tr>
        <tr>
            <th><spring:message code="username" /></th>
            <th><spring:message code="user_roles" /></th>
            <th><spring:message code="usrmanager.action" /></th>
        </tr>
        <c:forEach var="user_roles" items="${user_roles}">
            <tr>
                <td>${user_roles.key}</td>
                <td>
                    <table class="subtable">
                        <c:forEach var="role" items="${user_roles.value}">
                            <tr>
                                <td>${role}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
                <td><form><button type="submit" value="${user_roles.key}" name="openRolesForm"><spring:message code="edit_button"/></button></form></td>
            </tr>
        </c:forEach>
    </table>
</div>

<c:if test="${editForm}">
    <div class="userManagerParentForm">
        <div class="login-form mananger">
            <form action="?editUser" method="post">
                <sec:csrfInput />
                <legend><spring:message code="usrmanager.editform_legend" /> ${editUser.username}</legend>
                <label for="username"><spring:message code="usrmanager.change_id" /></label>
                <input type="text" id="newId" name="newId" value="${editUser.userId}" disabled readonly/>
                <label for="newUsername"><spring:message code="usrmanager.change_username" /></label>
                <input type="text" id="newUsername" name="newUsername" pattern="^[a-zA-Z]{3,}$" placeholder="<spring:message code="username" />" value="${editUser.username}"/>
                <label for="newPassword_1"><spring:message code="usrmanager.change_password" /></label>
                <input type="password" id="newPassword_1" name="newPassword_1" pattern="^[a-zA-Z0-9]{4,}$" placeholder="<spring:message code="usrmanager.new_password" />"/>
                <input type="password" id="newPassword_2" name="newPassword_2" pattern="^[a-zA-Z0-9]{4,}$" placeholder="<spring:message code="usrmanager.new_password" />"/>
                <div class="form-actions">
                    <button type="submit" class="btn"><spring:message code="save_button" /></button>
                    <button type="reset" class="btn"><spring:message code="reset_button" /></button>
                    <a href="?">Close</a>
                </div>
            </form>
        </div>
    </div>
</c:if>
<c:if test="${rolesForm}">
    <div class="userManagerParentForm">
        <div class="login-form mananger">
            <form action="?editRoles" method="post">
                <sec:csrfInput />
                <br/>
                <legend><spring:message code="usrmanager.editRolesform_legend" /> ${editUser.username}</legend>
                <c:forEach var="role" items="${AllRoles}">
                    <p><input type="checkbox" name="roles" value="${role}" <c:forEach var="testRole" items="${userRoles}"><c:if test="${role == testRole}">checked</c:if></c:forEach>/> ${role}</p>
                </c:forEach>
                <div class="form-actions">
                    <button type="submit" class="btn"><spring:message code="save_button" /></button>
                    <button type="reset" class="btn"><spring:message code="reset_button" /></button>
                    <a href="?">Close</a>
                </div>
            </form>
        </div>
    </div>
</c:if>
</body>
</html>
