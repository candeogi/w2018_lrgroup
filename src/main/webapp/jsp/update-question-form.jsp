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
        <div style="float:left; width:35%;">
            <p>Old title: <c:out value='${oldtitle}'/></p>
            <input type="hidden" name="currentTitle" value="${oldtitle}"/>

            <p>Old body: <c:out value='${oldbody}'/></p>
            <p><input type="hidden" name="currentBody" value="${oldbody}"/></p>
        </div>
        <div style="float:left; width:35%;">
            <label for="title">Title:</label>
            <input name="title" type="text"/><br/>

            <%--<label for="id">question id:</label>
            <input name="id" type="text"/><br/>--%>
            <input type="hidden" name="id" value="${questionid}"/>
            <label for="body">Body:</label></br>
            <textarea rows="4" cols="50" name="body" placeholder="Enter text"></textarea><br/>

            <button type="submit" class="btn btn-primary">Submit</button>
            <button type="reset" class="btn btn-primary">Reset the form</button>
        </div>
    </form>

    <div style="float:left; width:30%;">
        <img class="img-fluid adsbanner" src="https://images-na.ssl-images-amazon.com/images/I/716MJHggVDL._UX342_.jpg" alt="Wowee">
    </div>

<%--<h1>OLD VIEW</h1>

<form method="POST" action="<c:url value="/update-question"/>" id="questionForm">
    <p>Old title: <c:out value='${oldtitle}'/></p>
    <input type="hidden" name="currentTitle" value="${oldtitle}"/>

    <label for="title">Title:</label>
    <input name="title" type="text"/><br/>

    <%--<label for="id">question id:</label>
    <input name="id" type="text"/><br/>--%>
    <%--<input type="hidden" name="id" value="${questionid}"/>

    <p>Old body: <c:out value='${oldbody}'/></p> <br/>
    <input type="hidden" name="currentBody" value="${oldbody}"/>


    <label for="body">Body:</label></br>
    <textarea rows="4" cols="50" name="body" placeholder="Enter text"></textarea><br/>

    <button type="submit" class="btn btn-primary">Submit</button>
    <button type="reset" class="btn btn-primary">Reset the form</button>
</form>--%>
</body>
<footer>
    <c:import url="/jsp/include/footer.jsp"/>
</footer>
</html>
