<!--
Author: Giovanni Candeo
Version: 1.0
Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Question and related answers view">
    <meta name="author" content="Giovanni Candeo">

    <title>AnswerDemo</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link href="<c:url value='/css/question-answers-style.css' />" rel="stylesheet">

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

<body>
<!-- Header on top-->
<c:import url="/jsp/include/header.jsp"/>
<!--close header-->

<div id="loggedInUser"
     data-loggedInUser="${sessionScope.loggedInUser}"
     data-currentQuestion="${param.questionID}"
></div>
<!-- Container Div -->
<div class="container">


    <div id="question-container" class="question-container clearfix">
        <div class="card questioner-badge" style="width: 14rem;">
            <a href="#">
                <img id="questioneer-photo" class="card-img-top"
                     src="http://media.pn.am/media/issue/159/215/photo/159215.jpg" alt="Card image cap">
            </a>
            <div class="card-body">
                <h5 id="questioneer-name" class="card-title"></h5>
                <h6 id="questioneer-regdate" class="card-subtitle mb-2 text-muted"></h6>
            </div>
        </div>
        <div class="question-panel">
            <div class="question-heading">
                <h4 id="question-title" class="question-title"></h4>
                <h6 id="question-time" class="question-time text-muted"></h6>
            </div>
            <div id="question-body" class="question-body">
            </div>
            <div class="question-footer">
                <small id="question-lastmodified" class="text-muted"></small>
            </div>
        </div>

    </div>
    <!--end of question row-->
    <hr>
    <div class="clearfix">
        <form id="AddAnswerForm">
            <div class="form-group">
                <label for="addAnswerTextArea">Answer this question</label>
                <textarea class="form-control" id="addAnswerTextArea" rows="2"></textarea>
            </div>
            <input id="addAnswerButton" type="button" value="Answer">
        </form>
    </div>
    <hr>
    <!--answer list below-->
    <div id="answerListDiv">
        <ul id="base-answer-list" class="answer-list">
        </ul>
    </div>

</div><!--c container fluid-->

<script type="text/javascript" language="JavaScript" src="<c:url value='/js/ajax-question-answers.js' />"></script>

<!-- Modal for new Answer-->
<div class="modal " id="addAnswerModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="answerModalTitle">Insert a new answer</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <textarea class="form-control" rows="5" id="answerTextAreaModal"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="insertAnswerModal" data-dismiss="modal">Reply</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal for Edit Answer-->
<div class="modal " id="editAnswerModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="answerEditModalTitle">Edit your comment</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <textarea class="form-control" rows="5" id="editTextAreaModal"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="editAnswerModalButton" data-dismiss="modal">Edit</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>