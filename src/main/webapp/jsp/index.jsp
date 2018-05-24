<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Login Form</title>
    </head>
    <header>
        <c:import url="/jsp/include/header.jsp"/>
    </header>
    <body>
        <h1>Index</h1>
        <p><a href="<c:url value="/?p=create-question"/>">Crea una domanda</a></p>
        <p><a href="<c:url value="/rest/question"/>">Visualizza domande</a></p>

        <c:import url="/jsp/include/show-message.jsp"/>
        <c:choose>
            <c:when test="${empty sessionScope.loggedInUser}">

            </c:when>
            <c:when test="${not empty sessionScope.loggedInUser}">
                <a href="<c:url value="/?p=answer"/>">Answer to a Question</a>
            </c:when>
        </c:choose>
    </body>
</html>
