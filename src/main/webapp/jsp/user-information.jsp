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

<header class="navbar bg-dark text-white">
    <div class="justify-content-start">
        <nav class="nav">
            <a class="nav-link">
                <i class="fas fa-question-circle"></i>
            </a>
            <a class="nav-link">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                    Home
                </button>
            </a>
            <a>
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                    Question
                </button>
            </a>


        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
            Login
        </button>
        <button id="registerButton" type="button" class="btn btn-secondary">
            Register
        </button>
        </nav>
    </div>
</header><!--close header-->

<!-- The Modal for the login , this code shouldnt be here, maybe we can import this code in a smarter way-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">AnyQuestions.com</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="exampleInputUsername">Username</label>
                        <input type="text" class="form-control" id="exampleInputUsername"
                               aria-describedby="emailHelp" placeholder="Username">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Password</label>
                        <input type="password" class="form-control" id="exampleInputPassword1"
                               placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-primary ">Login</button>
                </form>
            </div>
            <!--<div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div> -->
        </div>
    </div>
</div>

<div class="row justify-content-between">
    <div class="col col-lg-10">
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

<button id="modifyButton" type="submit" class="btn btn-primary">Modify User</button>


<script type="text/javascript" src="js/ciao.js"></script>

</body>
</html>
