<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Create Question</title>
	</head>

  <body>
	<h1>Create Question</h1>
	<c:import url="/jsp/include/show-message.jsp"/>
	<c:choose>
		<c:when test="${empty sessionScope.loggedInUser}">
			<c:redirect url="/jsp/login-form.jsp" />
		</c:when>
		<c:when  test="${not empty sessionScope.loggedInUser}">
			<p>Loggato come: <c:out value='${sessionScope.loggedInUser}'/></p>
		</c:when>
	</c:choose>	
	<form method="POST" action="<c:url value="/create-question"/>" id="questionForm">
		<label for="title">Title:</label>
		<input name="title" type="text"/><br/>

		<label for="IDUser">IDUser:</label>
		<input name="IDUser" type="text"/><br/>
		
		<textarea rows="4" cols="50" name="body" placeholder="Enter text..">
		</textarea>

		<button type="submit">Submit</button><br/>
		<button type="reset">Reset the form</button>
	</form>
	</body>
</html>
