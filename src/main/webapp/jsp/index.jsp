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

        <!-- Buttons To Sort Categories + Search -->
        <div class="row button_menu justify-content-center">

            <div class="col-md-8">
                <nav class="nav nav-pills justify-content-center">
                    <button type="submit" id="popularQuestions" >Popular Questions</button>
                    <button type="submit" id="yourQuestions" >Your Questions</button>
                    <button type="submit" id="latestQuestions" >Latest Questions</button>
                    <button type="submit" data-target="#categoryModal" data-toggle="modal" id="categoriesList">Categories</button>
                    <button type="submit" class="nav-link disabled" href="#">Your Questions</button>
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
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Solved</th>
                        <th></th>
                        <th>Question Title</th>
                        <th>Author</th>
                        <th>Last Modified</th>
                    </tr>
                    </thead>

                    <tbody>
                        <div id="results">

                        </div>
                    <!--<tr>
                        <td><i class="fas fa-check-circle solved"></i></td>
                        <td>25</td>
                        <td>Question 1</td>
                        <td>Doe</td>
                        <td>11/05/2017 - 11:05</td>
                    </tr>
                    <tr>
                        <td><i class="fas fa-check-circle solved"></i></td>
                        <td>5</td>
                        <td>Question 2</td>
                        <td>Moe</td>
                        <td>11/05/2017 - 12:05</td>
                    </tr>
                    <tr>
                        <td><i class="fas fa-times-circle"></i></td>
                        <td>32</td>
                        <td>Question 3</td>
                        <td>Dooley</td>
                        <td>11/05/2017 - 15:05</td>
                    </tr>
                    <tr>
                        <td><i class="fas fa-check-circle solved"></i></td>
                        <td>21</td>
                        <td>Question 4</td>
                        <td>Han</td>
                        <td>11/05/2017 - 15:05</td>
                    </tr>
                    <tr>
                        <td><i class="fas fa-times-circle"></i></td>
                        <td>-15</td>
                        <td>Question 5</td>
                        <td>Dave</td>
                        <td>11/05/2017 - 15:05</td>
                    </tr>-->
                    </tbody>
                </table>
            </div><!-- close table div -->

            <div class="col-md-4">
                <img class="img-fluid adsbanner" src="https://images-na.ssl-images-amazon.com/images/I/716MJHggVDL._UX342_.jpg" alt="Wowee">
            </div>

        </div> <!-- close After Categories Button Div -->

    </div><!-- /.container -->

    <!--modal for categories-->
    <div class="modal fade" id="categoryModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="container">
                        <div id="categoriesResults">
                        </div>
                       <!-- <div class="row">
                            <div class="col-md-auto">
                                <nav class="nav nav-pills justify-content-center">
                                    <a class="nav-link" href="dea">Data structures and algoritms</a>
                                </nav>
                            </div>
                            <div class="col-md-auto">
                                <nav class="nav nav-pills justify-content-center">
                                    <a class="nav-link" href="drink">Drink time</a>
                                </nav>
                            </div>
                        </div> <!--break line-->
                        <!--<div class="row">
                            <div class="col-md-auto">
                                <nav class="nav nav-pills justify-content-center">
                                    <a class="nav-link" href="ss">Some say</a>
                                </nav>
                            </div>
                            <div class="col-md-auto">
                                <nav class="nav nav-pills justify-content-center">
                                    <a class="nav-link" href="nm">Network modelling</a>
                                </nav>
                            </div>
                        </div>-->
                    </div>
                </div>
                <!--<div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                  <button type="button" class="btn btn-primary">Save changes</button>
                </div> -->
            </div>
        </div>
    </div>

    <script>
        // Get the modal
        var modal = document.getElementById('categoryModal');

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function(event) {
            if (event.target === modal) {
                modal.style.display = "none";
            }
        }
    </script>

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

    <script src="<c:url value="/js/latestQuestions.js"/>"></script>
    <script src="<c:url value="/js/getCategoriesName.js"/>"></script>
    </body>
</html>
