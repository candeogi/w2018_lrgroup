<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Create User</title>
	</head>

	<body>
		<h1>Create User</h1>
		<hr/>
		
		<!-- display the message -->
		<c:import url="/jsp/include/show-message.jsp"/>

		<!-- display the just created employee, if any and no errors -->
		<c:if test='${not empty user && !message.error}'>
			<ul>
				<li>Username: <c:out value="${user.username}"/></li>
				<li>Name: <c:out value="${user.name}"/></li>
				<li>Surname: <c:out value="${user.surname}"/></li>
				<li>Email: <c:out value="${user.email}"/></li>
				<li>RegDate: <c:out value="${user.registrationDate}"/></li>

			</ul>
		</c:if>
	</body>
</html>
