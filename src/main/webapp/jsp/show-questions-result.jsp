<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Shows questions</title>
</head>
<body>
<h1>Shows questions</h1>
<hr/>
<c:import url="/jsp/include/show-message.jsp"/>
<c:if test='${not empty questions && !message.error}'>

    <table>
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
                <c:if test="${isAdmin == true}">
                    <td>
                        <form method="POST" action="<c:url value="/delete-question"/>" id="deleteForm">
                            <input type="hidden" name="idquestion" value="${question.ID}"/>
                            <button type="submit">Delete</button><br/>
                    </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</c:if>
</body>
</html>