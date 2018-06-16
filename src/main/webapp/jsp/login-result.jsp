<!--
Author: Giovanni Candeo (giovanni.candeo.1@studenti.unipd.it)
Version: 1.0
Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login Result</title>
</head>
<header>
    <c:import url="/jsp/include/header.jsp"/>
</header>
<body>

<!-- display the message -->
<c:import url="/jsp/include/show-message.jsp"/>
<c:choose>
    <c:when test="${not empty loggedInUser}">
        <p>You're logged as <c:out value='${loggedInUser}'/> </p>
    </c:when>
    <c:when test="${empty loggedInUser}">
        <p>You're not logged in!</p>
    </c:when>
</c:choose>
<a href="<c:url value="/jsp/login-form.jsp" /> ">Retry Login</a>

<%--<c:if test="${not empty loggedInUser}">
    <p>You're logged as <c:out value='${loggedInUser}'/> </p>
</c:if>
<c:else>
&lt;%&ndash;<c:if test="${empty loggedInUser}">&ndash;%&gt;
    <p>You're not logged in!</p>
&lt;%&ndash;</c:if>&ndash;%&gt;
</c:else>--%>

</body>
<footer>
    <c:import url="/jsp/include/footer.jsp"/>
</footer>
</html>
