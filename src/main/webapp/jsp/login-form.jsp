<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Login Form</title>
        <style>
            h1 {
                text-align : center;
            }
            form {
                width: 300px;
                margin: 0 auto;
            }
            p{
                text-align : center;
            }

        </style>
    </head>
    <header>
    <c:import url="/jsp/include/header.jsp"/>
    </header>
    <body>
        <c:import url="/jsp/include/show-message.jsp"/>
        <div class="grandParentContaniner">
            <div class="parentContainer">
            <h1>Login</h1>

            <form method="POST" action="<c:url value="/login"/>" autocomplete="on">
                <input type="hidden" name="from" value="${from}"/>
                <label for="username">Username:</label>
                <input name="username" type="text"/><br/>

                <label for="password">Password:</label>
                <input name="password" type="password"/><br/>

                <button type="submit" class="btn btn-primary">Login</button>
                <button type="reset" class="btn btn-primary">Reset the form</button>
            </form>
            </div>
        </div>

            <p>Not registered? <a href="<c:url value="/jsp/create-user-form.jsp"/>">Please reconsider your life decisions</a></p>

    </body>
    <c:import url="/jsp/include/footer.jsp"/>
</html>
