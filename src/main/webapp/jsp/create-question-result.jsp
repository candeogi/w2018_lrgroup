<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title>Create question</title>
	</head>
	<header>
		<c:import url="/jsp/include/header.jsp"/>
	</header>
	<body>
		<h1>Create Question</h1>
		<c:if test='${not empty update && !message.error}'>
			<h1>Updated</h1>
		</c:if>
		<hr/>
		<c:import url="/jsp/include/show-message.jsp"/>
		<c:if test='${not empty question && !message.error}'>
			<h2><c:out value="${question.title}" /></h2>
			<p><c:out value="${question.body}" /></p>
			<p><c:out value="${question.IDUser}" /></p>
		</c:if>
	</body>
	<footer>
		<c:import url="/jsp/include/footer.jsp"/>
	</footer>
</html>