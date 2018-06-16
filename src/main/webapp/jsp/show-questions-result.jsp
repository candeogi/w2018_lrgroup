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
<h1>Shows questions</h1>
<hr/>
<c:import url="/jsp/include/show-message.jsp"/>
<c:choose>
    <c:when test='${not empty questions && !message.error}'>
        <div class="col-md-8" style="float:left; width:80%;">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th><th>Title</th><th>Body</th><th>timestamp</th><th>lastModified</th><th>IDUser</th>
                    </tr>
                    </thead>
                    <!-- display the message -->
                    <c:import url="/jsp/include/show-message.jsp"/>
                    <tbody>
                    <c:forEach var="question" items="${questions}">
                        <tr>
                            <td><c:out value="${question.ID}"/></td>
                            <td><c:out value="${question.title}"/></td>
                            <td><c:out value="${question.body}"/></td>
                            <td><c:out value="${question.timestamp}"/></td>
                            <td><c:out value="${question.lastModified}"/></td>
                            <td><c:out value="${question.IDUser}"/></td>
                            <c:if test="${sessionScope.isAdmin == true}">
                                <td>
                                    <form method="POST" action="<c:url value="/delete-question"/>" id="deleteForm">
                                        <input type="hidden" name="idquestion" value="${question.ID}"/>

                                        <button type="submit" class="btn btn-primary btn-sm">Delete</button><br/>
                                    </form>
                                </td>
                            </c:if>
                            <c:if test="${(sessionScope.isAdmin == true) or (not empty sessionScope.loggedInUser and sessionScope.loggedInUser==question.IDUser)}">
                                <td>
                                    <form method="POST" action="<c:url value="/to-update-form"/>" id="updateForm">
                                        <input type="hidden" name="IDquestion" value="${question.ID}"/>
                                        <input type="hidden" name="oldtitle" value="${question.title}"/>
                                        <input type="hidden" name="oldbody" value="${question.body}"/>
                                        <button type="submit" class="btn btn-primary btn-sm">Update</button><br/>
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>


                </table>

            </div>
        </div>
    </c:when>
    <c:when test="${empty questions && !message.error}">
            <div style="float:left; width:80%;">
                There are no questions. Do you want to create one? <a href="<c:url value="/?p=create-question"/>">Create a question</a>
            </div>
    </c:when>


</c:choose>
    <div class="col-md-4" style="float:left; width:20%;">
        <img class="img-fluid adsbanner" src="https://images-na.ssl-images-amazon.com/images/I/716MJHggVDL._UX342_.jpg" alt="Wowee">
    </div>

</body>
<footer>
    <c:import url="/jsp/include/footer.jsp"/>
</footer>
</html>