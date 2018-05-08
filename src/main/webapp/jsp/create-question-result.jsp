<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title>Create question</title>
	</head>
	<body>
		<h1>Create Question</h1>
		<hr/>
		<c:import url="/jsp/include/show-message.jsp"/>
		<c:if test='${not empty question && !message.error}'>
			<h2><c:out value="${question.title}" /></h2>
			<p><c:out value="${question.body}" /></p>
			<p><c:out value="${question.IDUser}" /></p>
		</c:if>
	</body>
</html>