<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Create Answer</title>
	</head>

  <body>
	<h1>Create Answer</h1>
	<c:import url="/jsp/include/show-message.jsp"/>
	<c:if  test="${not empty sessionScope.loggedInUser}">
		<p>Logged as: <c:out value='${sessionScope.loggedInUser}'/></p>
	</c:if>
	<form method="POST" action="<c:url value="/create-answer"/>" id="answerForm">
		<label for="parentID">Id della domanda a cui rispondo:</label>
		<input name="parentID" type="text"/><br/>

<%--		<label for="IDUser">IDUser:</label>
		<input name="IDUser" type="text"/><br/>--%>

		<textarea rows="4" cols="50" name="text" placeholder="Enter text..">
		</textarea>
		<br/>
		<button type="submit">Submit</button>
		<button type="reset">Reset the form</button>
	</form>
	</body>
</html>
