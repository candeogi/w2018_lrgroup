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
        <img src="<c:out value="${user.username}" />" /><br />
        <p>Name: <c:out value="${user.name}" /></p>
        <p>Surname: <c:out value="${user.surname}" /></p>
        <p>Username: <c:out value="${user.username}" /></p>
        <p>Birthday: <c:out value="${user.birthday}" /></p>
        <p>Registration Date: <c:out value="${user.registrationDate}" /></p>
        <p>Description: <c:out value="${user.description}" /></p>
    </body>
</html>
