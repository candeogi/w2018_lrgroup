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
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style-information.css' />">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

</head>
<body>

<c:import url="/jsp/include/header.jsp"/>

<div class="container">
    <div class="row justify-content-between">
        <div class="col col-lg-10">
            <div class="row">
                <div class="col col-lg-2">
                    <p> Username:</p>
                </div>
                <div id="username-value" class="col-md-auto">
                    <p><c:out value="${user.username}"/></p>
                </div>
            </div>
            <div class="row">
                <div class="col col-lg-2">
                    <p> Name:</p>
                </div>
                <div id="name-value" class="col-md-auto">
                    <p><c:out value="${user.name}"/></p>
                </div>
            </div>
            <div class="row">
                <div class="col col-lg-2">
                    <p>Surname:</p>
                </div>
                <div id="surname-value" class="col-md-auto">
                    <p><c:out value="${user.surname}"/></p>
                </div>
            </div>
            <div class="row">
                <div class="col col-lg-2">
                    <p>Birthday:</p>
                </div>
                <div id="birthday-value" class="col-md-auto">
                    <p><c:out value="${user.birthday}"/></p>
                </div>
            </div>
            <div class="row">
                <div class="col col-lg-2">
                    <p>Description:</p>
                </div>
                <div id="description-value" class="col-md-auto">
                    <p><c:out value="${user.description}"/></p>
                </div>
            </div>
            <div class="row">
                <div class="col col-lg-2">
                    <p>Email address: </p>
                </div>
                <div id="email-value" class="col-md-auto">
                    <p><c:out value="${user.email}"/></p>
                </div>
            </div>
        </div>
        <div id="photo-div" class="col align-self-end">
            <img src="data:image/jpeg;base64,${user.photoProfile}" alt="photo of the user" class="img">
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col">
                <div id="website" class="container border border-dark">
                    <p>Website:</p>
                    <table id="table-website" class="table table-hover">

                    </table>

                </div>
            </div>

            <div class="col">
                <div id="certification" class="container border border-dark">
                    <p>Certification:</p>
                    <table id="table-certificate" class="table table-hover">

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<button id="modifyButton" type="submit" class="btn btn-primary">Modify User</button>


<script type="text/javascript" src="js/ciao.js"></script>


</body>
</html>
