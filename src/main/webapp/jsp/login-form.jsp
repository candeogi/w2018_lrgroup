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
        <h1>Login</h1>
        <c:import url="/jsp/include/show-message.jsp"/>
        <form method="POST" action="<c:url value="/login"/>">
            <input type="hidden" name="from" value="${from}"/>
            <label for="username">Username:</label>
            <input name="username" type="text"/><br/>

            <label for="password">Password:</label>
            <input name="password" type="password"/><br/>

            <button type="submit">Login</button><br/>
            <button type="reset">Reset the form</button>
        </form>
        <p>Not registered? <a href="<c:url value="/jsp/create-user-form.jsp"/>">Please reconsider your life decisions</a></p>
    </body>
</html>
