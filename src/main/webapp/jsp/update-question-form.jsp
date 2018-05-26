<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Update Question</title>
</head>
<header>
    <c:import url="/jsp/include/header.jsp"/>
</header>
<body>
<h1>Update Question</h1>
<c:import url="/jsp/include/show-message.jsp"/>
<c:if test="${not empty sessionScope.loggedInUser}">
    <p>Logged as: <c:out value='${sessionScope.loggedInUser}'/></p>
</c:if>
<form method="POST" action="<c:url value="/update-question"/>" id="questionForm">
    <p>Old title: <c:out value='${oldtitle}'/></p>
    <label for="title">Title:</label>
    <input name="title" type="text"/><br/>

    <%--<label for="id">question id:</label>
    <input name="id" type="text"/><br/>--%>
    <input type="hidden" name="id" value="${questionid}"/>

    <p>Old body: <c:out value='${oldbody}'/></p>

    <label for="body">Body:</label>
    <textarea rows="4" cols="50" name="body" placeholder="Enter text..">
		</textarea>

    <button type="submit">Submit</button><br/>
    <button type="reset">Reset the form</button>
</form>
</body>
</html>
