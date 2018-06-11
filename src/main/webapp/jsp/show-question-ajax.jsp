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

Author: Alberto Forti
Version: 1.0
Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">

    <!--jquery-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!--popper-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <!--bootstrap js-->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>


</head>

<header>
    <c:import url="/jsp/include/header.jsp"/>
</header>

<body>

<div class="container-fluid">
    <input type="hidden" name="user" id="idUser" value="${sessionScope.loggedInUser}"/>
    <!-- Buttons To Sort Categories + Search -->
    <div class="row button_menu justify-content-center">

        <div class="col-md-8">
            <nav class="nav nav-pills justify-content-center" id="switchQs">
                <a class="nav-link active" href="#popularQs" data-toggle="tab">Popular Questions</a>
                <a class="nav-link" href="#yourQs" data-toggle="tab">Your Questions</a>
                <a class="nav-link" href="#latestQs" data-toggle="tab">Latest Questions</a>
            </nav>


        </div>

        <div class="col-md-4">
            <form>
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search a Question">
                    <div class="input-group-btn">
                        <button class="btn btn-default" type="submit">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </div>
            </form>
        </div>

    </div><!-- Buttons+Search Div -->

    <!-- After Categories Button Div -->
    <div class="row">

        <!-- Browse Questions Table Div -->
        <div class="col-md-8">
        <div class="table-responsive">
            <table class="table table-hover">


            </table>

        </div><!-- close table div -->
        </div>
        <div class="col-md-4">
            <img class="img-fluid adsbanner" src="https://images-na.ssl-images-amazon.com/images/I/716MJHggVDL._UX342_.jpg" alt="Wowee">
        </div>

    </div> <!-- close After Categories Button Div -->

<script type="text/javascript" language="JavaScript" src="<c:url value='/js/ajax-question.js' />"></script>
</body>

<footer>
    <c:import url=  "/jsp/include/footer.jsp"/>
</footer>


</html>