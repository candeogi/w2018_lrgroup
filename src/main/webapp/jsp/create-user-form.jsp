<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Create User</title>
	</head>
	<header>
	<c:import url="/jsp/include/header.jsp"/>
	</header>
  <body>
	<h1>Create User</h1>
	<c:if  test="${not empty sessionScope.loggedInUser}">
		<c:redirect url="/" />
	</c:if>
	<form method="POST" action="<c:url value="/create-user"/>">
		<label for="name">Name:</label>
		<input name="name" type="text"/><br/>

		<label for="surname">Surname:</label>
		<input name="surname" type="text"/><br/>
		
		<label for="username">Username:</label>
		<input name="username" type="text"/><br/>

		<label for="email">email:</label>
		<input name="email" type="email"/><br/>
		
		<label for="password">Password:</label>
		<input name="password" type="password"/><br/>
		
		<label for="password2">Repeat Password:</label>
		<input name="password2" type="password"/><br/><br/>

		<label for="bdate">Birthday</label>
		<input name="bdate" type="date"/><br/><br/>

		<button type="submit">Submit</button><br/>
		<button type="reset">Reset the form</button>
	</form>
	</body>
	<footer>
		<c:import url="/jsp/include/footer.jsp"/>
	</footer>
</html>
