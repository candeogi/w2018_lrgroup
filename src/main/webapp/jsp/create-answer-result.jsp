<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title>Create Answer</title>
	</head>
	<body>
		<h1>Create Answer</h1>
		<hr/>
		<c:import url="/jsp/include/show-message.jsp"/>
		<c:if test='${not empty answer && !message.error}'>
			<h2><c:out value="${answer.text}" /></h2>
			<p><c:out value="${answer.answersToID}" /></p>
		</c:if>
	</body>
</html>