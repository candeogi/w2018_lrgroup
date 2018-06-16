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


<c:import url="/jsp/include/header.jsp"/>

<body>

<div class="container-fluid">
    <input type="hidden" name="user" id="idUser" value="${sessionScope.loggedInUser}"/>
    <!-- Buttons To Sort Categories + Search -->
    <div class="row button_menu justify-content-center">

        <div class="col-md-8">
            <nav class="nav nav-pills justify-content-center" id="switchQs">
                <a class="nav-link active" href="#popularQs" data-toggle="tab">Popular Questions</a>
                <c:if test="${not empty sessionScope.loggedInUser}">
                    <a class="nav-link" href="#yourQs" data-toggle="tab">Your Questions</a>
                </c:if>
                <a class="nav-link" href="#latestQs" data-toggle="tab">Latest Questions</a>
                <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#categoryQs" role="button" aria-haspopup="true" aria-expanded="false">Categories</a>
                <div class="dropdown-menu" id="listCategoryDropdown">

                </div>
                <div id="filternav">

                </div>
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
        <div class="table-responsive" id="divquestionlist">
            <table class="table table-hover" id="tablequestionlist">


            </table>

        </div><!-- close table div -->
        </div>
        <div class="col-md-4">
            <img class="img-fluid adsbanner" src="https://images-na.ssl-images-amazon.com/images/I/716MJHggVDL._UX342_.jpg" alt="Wowee">
        </div>

    </div> <!-- close After Categories Button Div -->
</div>

<script type="text/javascript" language="JavaScript" src="<c:url value='/js/ajax-question.js' />"></script>
</body>

<footer>
    <c:import url="/jsp/include/footer.jsp"/>
</footer>


</html>