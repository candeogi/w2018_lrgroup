<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Login Form</title>
</head>

<body>
<h1>Login</h1>

<form method="POST" action="<c:url value="/login"/>">

    <%--<label for="username">Username:</label>--%>
    <%--<input name="username" type="text"/><br/>--%>

    <label for="email">Email:</label>
    <input name="email" type="email"/><br/>

    <label for="password">Password:</label>
    <input name="password" type="password"/><br/>

    <button type="submit">Login</button><br/>
    <button type="reset">Reset the form</button>
</form>
</body>
</html>
