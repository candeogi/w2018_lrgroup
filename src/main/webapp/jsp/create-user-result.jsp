<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Create User</title>
	</head>
	<header>
		<c:import url="/jsp/include/header.jsp"/>
	</header>
	<body>
		<h1>Create User</h1>
		<hr/>
		
		<!-- display the message -->
		<c:import url="/jsp/include/show-message.jsp"/>

		<!-- display the just created user, if any and no errors -->
		<c:if test='${not empty user && !message.error}'>
			<ul>
				<li>Username: <c:out value="${user.username}"/></li>
				<li>Name: <c:out value="${user.name}"/></li>
				<li>Surname: <c:out value="${user.surname}"/></li>
				<li>Email: <c:out value="${user.email}"/></li>
				<li>RegDate: <fmt:formatDate pattern = "yyyy-MM-dd" dateStyle="short" value = "${user.registrationDate}" /></li>
				<li>Birthday: <fmt:formatDate pattern = "yyyy-MM-dd" dateStyle="short" value = "${user.birthday}" /></li>
			</ul>
		</c:if>
	</body>
</html>
