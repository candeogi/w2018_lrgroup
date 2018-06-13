<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>User Profile</title>
    </head>
    <header>
        <c:import url="/jsp/include/header.jsp"/>
    </header>
    <body>

        <c:if test="${empty user}">
            <c:redirect url="/jsp/error.jsp" /> <!--Forse c'è un metodo migliore per impedire l'accesso diretto alla pagina-->
        </c:if>
        <h1>User Profile</h1>
        <c:import url="/jsp/include/show-message.jsp"/>

        <img height="150px" width="150px" src="data:image/jpeg;base64,${user.photoProfile}" /><br /> <!--Temporary inline height/width, sorry about that!-->
        <c:if test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser==user.username}">
            <form method="POST" enctype="multipart/form-data" action="<c:url value="/update-user-pic" />" >
                <input type="hidden" name="currentPhotoProfile" value="<c:out value="${user.photoProfile}" />">
                <input type="hidden" name="username" value="<c:out value="${user.username}" />">
                <input type="file" name="photoProfile" /><br />
                <button type="submit">Upload</button>
            </form>
        </c:if>


        <p>Name: <c:out value="${user.name}" /></p>
        <c:if test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser==user.username}">
            <form method="POST" action="<c:url value="/update-user" />" >
                <input type="hidden" name="currentName" value="<c:out value="${user.name}" />">
                <input type="text" name="name" /><br />
        </c:if>
    
        <p>Surname: <c:out value="${user.surname}" /></p>
        <c:if test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser==user.username}">
            <input type="hidden" name="currentSurname" value="<c:out value="${user.surname}" />">
            <input type="text" name="surname" /><br />
        </c:if>
        
        <p>Email: <c:out value="${user.email}" /></p>
        <c:if test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser==user.username}">
            <input type="hidden" name="currentEmail" value="<c:out value="${user.email}" />">
            <input type="text" name="email" /><br />
        </c:if>
        
        <p>Username: <c:out value="${user.username}" /></p>
        <c:if test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser==user.username}">
            <input type="hidden" name="username" value="<c:out value="${user.username}" />">
        </c:if>
        <p>Birthday: <c:out value="${user.birthday}" /></p>
        <c:if test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser==user.username}">
            <input type="hidden" name="currentBdate" value="<c:out value="${user.birthday}" />">
            <input type="date" name="bdate" /><br />
        </c:if>

        <p>Registration Date: <c:out value="${user.registrationDate}" /></p>
        <p>Description: <c:out value="${user.description}" /></p>
        <c:if test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser==user.username}">
            <input type="hidden" name="currentDescription" value="<c:out value="${user.description}" />">
            <input type="textarea" name="description" /><br />
            <button type="submit">Apply Changes</button><br/>
            </form>
        </c:if>
    </body>
</html>
