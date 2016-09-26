<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.1.0.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.sticky.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/common.js" />" ></script>

<script>
    $(window).on('load', function(){
        $("#upper-panel").sticky({ topSpacing: 0 });
    });
</script>

<div id="upper-panel">
    <div id="navigation">
        <sec:authorize access="isAuthenticated()">
            <a href="<c:url value="/main" />" ><button type="button" class="btn"><spring:message code="go_main_button" /></button></a>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="<c:url value="/admin" />" ><button type="button" class="btn"><spring:message code="admin_button" /></button></a>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_ANONYMOUS')">
            <a href="<c:url value="/login" />" ><button type="button" class="btn"><spring:message code="login_register_button" /></button></a>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_USER')">
            <a href="<c:url value="/order" />" ><button type="button" class="btn"><spring:message code="order_button" /></button></a>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_DISPATCHER')">
            <a href="<c:url value="/dispatch" />" ><button type="button" class="btn"><spring:message code="dispatch_button" /></button></a>
            <a href="<c:url value="/dispatcher" />" ><button type="button" class="btn"><spring:message code="dispatcher_button" /></button></a>
        </sec:authorize>
    </div>
    <div id="right-box">
        <sec:authorize access="isAuthenticated()">
            <form action="<c:url value="/logout" />" method="post">
                <input id="logout-button" class="btn" type="submit" value="<spring:message code="logout_button" />">
                <sec:csrfInput/>
            </form>
        </sec:authorize>
        <div id="language-box">
            <a href="?lang=ru"><img src="<c:url value="/resources/images/ru.png"/>" height="20px" width="30px"></a>
            <a href="?lang=en"><img src="<c:url value="/resources/images/en.gif"/>" height="20px" width="30px"></a>
        </div>
    </div>
</div>
