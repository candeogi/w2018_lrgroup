<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Any questions?</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link href="css/style.css" rel="stylesheet">

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
                <c:if test="${not empty sessionScope.loggedInUser}">
                    <a class="nav-link" href="#yourQs" data-toggle="tab">Your Questions</a>
                </c:if>
                <a class="nav-link" href="#latestQs" data-toggle="tab">Latest Questions</a>
                <div class="dropdown-container">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#categoryQs" role="button"
                       aria-haspopup="true" aria-expanded="false">Categories</a>
                    <div class="dropdown-menu" id="listCategoryDropdown">

                    </div>
                </div>
                <div id="filternav">

                </div>

            </nav>


        </div>
        <c:choose>
            <c:when test="${not empty sessionScope.loggedInUser}">
                <div class="col-md-2">
                    <button class="btn btn-primary" data-toggle="modal" data-target="#addQuestionModal">Add Question
                    </button>
                </div>

                <div class="col-md-2">
                    <form>
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Search a Question" id="searchinqs">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="submit" id="searchBtn">
                                    <i class="fas fa-search"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </c:when>
            <c:when test="${empty sessionScope.loggedInUser}">
                <div class="col-md-4">
                    <form>
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Search a Question" id="longSearchinqs">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="submit" id="longSearchBtn">
                                    <i class="fas fa-search"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </c:when>
        </c:choose>

        <%--<div class="col-md-2">
            <c:if test="${not empty sessionScope.loggedInUser}">
                <button class="btn btn-primary" data-toggle="modal" data-target="#addQuestionModal">Add Question
                </button>
            </c:if>
        </div>

        <div class="col-md-2">
            <form>
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search a Question" id="searchinqs">
                    <div class="input-group-btn">
                        <button class="btn btn-default" type="submit" id="searchBtn">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </div>
            </form>
        </div>--%>


    </div>
    <!-- Buttons+Search Div -->

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
            <img class="img-fluid adsbanner"
                 src="https://images-na.ssl-images-amazon.com/images/I/716MJHggVDL._UX342_.jpg" alt="Wowee">
        </div>

    </div> <!-- close After Categories Button Div -->
</div>

<div class="modal fade" id="addQuestionModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel1">Insert a new question</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="title-form">Question Title</label>
                    <input name="title" type="text" class="form-control" id="title-form"
                           aria-describedby="emailHelp" placeholder="Insert the title">
                </div>
                <div class="form-group">
                    <label for="body-form">Question Body</label>
                    <input name="body" type="text" class="form-control" id="body-form"
                           aria-describedby="emailHelp" placeholder="Insert the content ">
                </div>
                <div class="form-group">
                    <div id="dropdownCat"class="dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <button class="btn btn-secondary dropdown-toggle" onclick="showCat()" type="button" id="dropdownButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Category
                        </button>
                        <div  id="divModalDrop" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="dropdown-menu" aria-labelledby="dropdownMenu2">

                        </div>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="saveQuestion" data-dismiss="modal">Save changes
                </button>
            </div>
        </div>
    </div>
</div>


<%--<p><a href="<c:url value="/?p=create-question"/>">Create a question</a></p>
<p><a href="<c:url value="/show-questions"/>">Show questions</a></p>
<p><a href="<c:url value="/jsp/show-question-ajax.jsp"/>">Show questions (via AJAX/REST)</a></p>--%>

<c:import url="/jsp/include/show-message.jsp"/>
<c:choose>
    <c:when test="${empty sessionScope.loggedInUser}">

    </c:when>
    <c:when test="${not empty sessionScope.loggedInUser}">
        <p><a href="<c:url value="/?p=show-user-questions"/>">Show my questions</a></p>
        <p><a href="<c:url value="/?p=answer"/>">Answer to a Question</a></p>
        <%--<p><a href="<c:url value="/?p=update-question"/>">Update a question</a></p>--%>


            </c:when>
        </c:choose>
    <!--Custom script test-->
    <script type="text/javascript" src="js/test.js"></script>
    <script type="text/javascript" language="JavaScript" src="js/ajax-question.js"></script>
    </body>
<footer>
    <c:import url="/jsp/include/footer.jsp"/>
</footer>


</html>
