<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Create Question</title>
		<!-- include summernote css/js -->
	</head>
	<header>
		<c:import url="/jsp/include/header.jsp"/>
	</header>
  <body>
	<h1>Create Question</h1>
	<c:import url="/jsp/include/show-message.jsp"/>

	<form method="POST" action="<c:url value="/create-question"/>" id="questionForm">
		<!--<div class="form-group">-->
			<label for="title">Title:</label> <br/>
			<input name="title" type="text" class="form-control" id="title" aria-describedby="title" placeholder="Enter title">
			<!--<small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small> -->
		<!--</div>-->
            Body: <br/>
		    <textarea id="bodyID" name="body" class="form-control" rows="5" placeholder="Enter the body of your question"></textarea> <br/>

		<button type="submit" class="btn btn-primary">Submit</button>
		<button type="reset" class="btn btn-primary">Reset the form</button>
	</form>

  </body>
	<footer>
		<c:import url="/jsp/include/footer.jsp"/>
	</footer>
</html>
