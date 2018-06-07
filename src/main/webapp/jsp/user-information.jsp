<%--
  Created by IntelliJ IDEA.
  User: zigio
  Date: 31/05/18
  Time: 11.19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Information</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<script>
    alert("ciao");
</script>
<div class="container-fluid">
    Menu di navigazione
</div>
<div class="row justify-content-between">
    <div class="col col-lg-10">
        <div class="row">
            <div class="col col-lg-2">
                Name:
            </div>
            <div class="col-md-auto">
                <c:out value="${user.name}"/>
            </div>
        </div>
        <div class="row">
            <div class="col col-lg-2">
                Surname:
            </div>
            <div class="col-md-auto">
                <c:out value="${user.surname}"/>
            </div>
        </div>
        <div class="row">
            <div class="col col-lg-2">
                Birthday:
            </div>
            <div class="col-md-auto">
                <c:out value="${user.birthday}"/>
            </div>
        </div>
        <div class="row">
            <div class="col col-lg-2">
                Description:
            </div>
            <div class="col-md-auto">

            </div>
        </div>
    </div>
    <div class="col align-self-end">
        <img src="user.png" alt="photo of the user" class="img">
    </div>
</div>

<button type="button" class="btn btn-primary">Modify User</button></a>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
