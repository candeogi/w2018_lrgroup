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
	
	<form method="POST" action="<c:url value="/create-answer"/>" id="answerForm">
		<label for="answersToID">Id della domanda a cui rispondo:</label>
		<input name="answersToID" type="text"/><br/>

		<textarea rows="4" cols="50" name="text" placeholder="Enter text..">
		</textarea>

		<button type="submit">Submit</button><br/>
		<button type="reset">Reset the form</button>
	</form>
	</body>
</html>
