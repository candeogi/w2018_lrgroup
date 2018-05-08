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
	
	<form method="POST" action="<c:url value="/create-question"/>" id="questionForm">
		<label for="title">Title:</label>
		<input name="title" type="text"/><br/>
		
		<textarea rows="4" cols="50" name="body" placeholder="Enter text..">
		</textarea>

		<button type="submit">Submit</button><br/>
		<button type="reset">Reset the form</button>
	</form>
	</body>
</html>
