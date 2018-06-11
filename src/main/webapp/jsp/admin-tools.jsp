<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Shows questions</title>
</head>
<header>
    <c:import url="/jsp/include/header.jsp"/>
</header>
<body>
<h1>User list</h1>
<hr/>
<c:import url="/jsp/include/show-message.jsp"/>
<c:if test='${not empty users && !message.error}'>

    <table>
        <thead>
        <tr>
            <th>Email</th><th>Name</th><th>Surname</th><th>Username</th><th>Birthday</th><th>Reg. date</th>
        </tr>
        </thead>
        <!-- display the message -->
        <c:import url="/jsp/include/show-message.jsp"/>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.surname}"/></td>
                <td><c:out value="${user.username}"/></td>
                <td><c:out value="${user.birthday}"/></td>
                <td><c:out value="${user.registrationDate}"/></td>
                <c:if test="${sessionScope.isAdmin == true and sessionScope.loggedInUser != user.username}">
                    <td>
                        <form method="POST" action="<c:url value="/delete-user"/>" id="deleteForm">
                            <input type="hidden" name="username" value="${user.username}"/>

                            <button type="submit">Delete</button><br/>
                        </form>
                    </td>
                    <td>
                        <form method="POST" action="<c:url value="/to-update-user-form"/>" id="updateForm">
                            <input type="hidden" name="username" value="${user.username}"/>

                            <button type="submit">Update</button><br/>
                        </form>
                    </td>
                </c:if>
        </c:forEach>
        </tbody>
    </table>

</c:if>
</body>
</html>