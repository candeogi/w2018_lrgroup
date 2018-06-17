<!--
Copyright 2018 University of Padua, Italy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Author: Giovanni Candeo
Version: 1.0 (First draft, not optimized for bootstrap)
Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link href="<c:url value='/css/style.css' />" rel="stylesheet">

    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
          integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">


    <!--awesome-font-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
          integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <!--jquery-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!--popper-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <!--bootstrap js-->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>

</head>


<header class="navbar bg-dark text-white navbar-fixed-top">
    <div class="justify-content-start">
        <nav class="nav">
            <a class="nav-link" href="<c:url value="/"/>"><i class="fas fa-home"></i></a>

            <c:choose>
                <c:when test="${empty sessionScope.loggedInUser}">
                    <a class="nav-link" href="<c:url value=""/>" data-toggle="modal" data-target="#modal-login"><i class="fas fa-question-circle"></i></a>

                </c:when>
                <c:when test="${not empty sessionScope.loggedInUser}">
                    <a class="nav-link" href="<c:url value="/?p=show-user-questions"/>"><i class="fas fa-question-circle"></i></a>
                </c:when>
            </c:choose>
            <%--<a class="nav-link" href="<c:url value="/?p=show-user-questions"/>"><i class="fas fa-question-circle"></i></a>--%>


        </nav>
    </div>
    <div class="justify-content-end">
        <c:choose>
            <c:when test="${empty sessionScope.loggedInUser}">
                <%--href="<c:url value="/?p=create-user"/>"--%>
                <a>
                    <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#modal-registration">
                        Register
                    </button>
                </a>
                <%--href="<c:url value="/?p=log-in"/>"--%>
                <a>
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal-login">
                        Login
                    </button>
                </a>
            </c:when>

            <c:when test="${(not empty sessionScope.loggedInUser) and (sessionScope.isAdmin == true)}">
                <a href="<c:url value="/?p=user&u=${sessionScope.loggedInUser}" />">
                    <!--<i class="far fa-user"></i>-->
                    <img src="<c:url value="/images/user.png"/>" alt="avatar" class="avatar">

                </a>
                <a href="<c:url value="/?p=admin-panel"/>">
                    <button type="button" class="btn btn-primary">
                        Admin tools
                    </button>
                </a>
                <a href="<c:url value="/?p=log-out"/>">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                        Logout
                    </button>
                </a>
            </c:when>

            <c:when test="${not empty sessionScope.loggedInUser}">
                <a href="<c:url value="/?p=log-out"/>">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                        Logout
                    </button>
                </a>
                <a href="<c:url value="/?p=user&u=${sessionScope.loggedInUser}" />">
                    <img src="<c:url value="/images/user.png"/>" alt="avatar" class="avatar">
                </a>
            </c:when>

        </c:choose>
    </div>

    <div class="modal fade" id="modal-login" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Login</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="<c:url value="/login"/>" >
                        <div class="form-group">
                            <label for="exampleInputUsername">Username: </label>
                            <input name="username" type="text" class="form-control" id="exampleInputUsername"
                                   aria-describedby="emailHelp" placeholder="Username">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Password: </label>
                            <input name="password" type="password" class="form-control" id="exampleInputPassword1"
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


    <div class="modal fade" id="modal-registration" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" >AnyQuestions.com</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" action="<c:url value="/create-user"/>" >
                        <div class="form-group">
                            <label for="exampleInputUsername">Name</label>
                            <input name="name" type="text" class="form-control" id="name-reg"
                                   aria-describedby="emailHelp" placeholder="Name">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername">Surname</label>
                            <input name="surname" type="text" class="form-control" id="surname-reg"
                                   aria-describedby="emailHelp" placeholder="Surname">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername">Username</label>
                            <input name="username" type="text" class="form-control" id="username-reg"
                                   aria-describedby="emailHelp" placeholder="Username">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername">Name</label>
                            <input name="email" type="text" class="form-control" id="email-reg"
                                   aria-describedby="emailHelp" placeholder="e-mail">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Password</label>
                            <input name="password" type="password" class="form-control" id="password-reg"
                                   placeholder="Password">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Password</label>
                            <input name="password2" type="password" class="form-control" id="password2-reg"
                                   placeholder="Repeat Password">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername">Name</label>
                            <input name="bdate" type="date" class="form-control" id="date-reg"
                                   aria-describedby="emailHelp">
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


</header>

<nav>


</nav>
<hr>
