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
    <%--//TODO: problema con il css--%>
    <link  rel="stylesheet" type="text/css" href="jsp/css/style-information.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

</head>
<body>
<div class="container-fluid">
    Menu di navigazione
</div>
<div class="row justify-content-between">
    <div class="col col-lg-10">
        <div class="row">
            <div class="col col-lg-2">
                Name:
            </div>
            <div id="name-value" class="col-md-auto">
                <c:out value="${user.name}"/>
            </div>
        </div>
        <div class="row">
            <div class="col col-lg-2">
                Surname:
            </div>
            <div id="surname-value" class="col-md-auto">
                <c:out value="${user.surname}"/>
            </div>
        </div>
        <div class="row">
            <div class="col col-lg-2">
                Birthday:
            </div>
            <div id="birthday-value" class="col-md-auto">
                <c:out value="${user.birthday}"/>
            </div>
        </div>
        <div class="row">
            <div class="col col-lg-2">
                Description:
            </div>
            <div id="description-value"class="col-md-auto">
                <c:out value="${user.description}"/>
            </div>
        </div>
        <div class="row">
            <div class="col col-lg-2">
                email:
            </div>
            <div id="email-value"class="col-md-auto">
                <c:out value="${user.email}"/>
            </div>
        </div>
    </div>
    <div class="col align-self-end">
        <img src="user.png" alt="photo of the user" class="img">
    </div>

</div>

<button id="modifyButton" type="submit" class="btn btn-primary" onclick="myFunction()">Modify User</button>

<script type="text/javascript" src="jsp/js/ciao.js"></script>

</body>
</html>
