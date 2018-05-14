<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>User Profile</title>
    </head>

    <body>
        <c:if test="${empty user}">
            <c:redirect url="/jsp/error.jsp" /> <!--Forse c'è un metodo migliore per impedire l'accesso diretto alla pagina-->
        </c:if>
        <h1>User Profile</h1>
        <p><c:out value="${sessionScope.loggedInUser}" /></p>
    </body>
</html>
