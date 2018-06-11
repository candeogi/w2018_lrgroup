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
    <link href="css/style.css" rel="stylesheet">

    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
          integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">

    <!--jquery-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!--popper-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <!--bootstrap js-->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>

</head>


<header class="navbar bg-dark text-white">
    <div class="justify-content-start">
        <nav class="nav">
            <a class="nav-link">
                <i class="fas fa-question-circle"></i>
            </a>
            <a class="nav-link">AnyQuestions.com</a>
        </nav>
    </div>
    <div class="justify-content-end">
        <c:choose>
            <c:when test="${empty sessionScope.loggedInUser}">
                <a href="<c:url value="/?p=create-user"/>">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                        Register
                    </button>
                </a> or <a href="<c:url value="/?p=log-in"/>">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                    Login
                </button>
            </a>
            </c:when>

            <c:when test="${not empty sessionScope.loggedInUser}">
                <a href="<c:url value="/?p=user&u=${sessionScope.loggedInUser}" />">
                    <img src="<c:url value="/images/user.png"/>" class="rounded-circle">
                </a>
                <a href="<c:url value="/?p=log-out"/>">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                        Logout
                    </button>
                </a>
            </c:when>
        </c:choose>
    </div>
</header>

<nav>
    <!--Home button-->
    <a href="<c:url value="/jsp/index.jsp"/>">Home</a> |

</nav>
<hr>
