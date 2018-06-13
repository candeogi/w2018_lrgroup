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
                    <a class="nav-link" href="#yourQs" data-toggle="tab">Your Questions</a>
                    <a class="nav-link" href="#latestQs" data-toggle="tab">Latest Questions</a>
                    <div class="dropdown-container">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#categoryQs" role="button" aria-haspopup="true" aria-expanded="false">Categories</a>
                        <div class="dropdown-menu" id="listCategoryDropdown">

                        </div>
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


   <!--Custom script test-->
    <script type="text/javascript" src="test.js"></script>

        <h1>Index</h1>
        <p><a href="<c:url value="/?p=create-question"/>">Create a question</a></p>
        <p><a href="<c:url value="/show-questions"/>">Show questions</a></p>

        <p><a href="<c:url value="/jsp/show-question-ajax.jsp"/>">Show questions (via AJAX/REST)</a></p>

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

    <script type="text/javascript" language="JavaScript" src="<c:url value='/js/ajax-question.js' />"></script>
    </body>

    <c:import url="/jsp/include/footer.jsp"/>

</html>
