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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

</head>
<body>
<div class="container-fluid">
    Menu di navigazione
</div>
<div class="row justify-content-between">
    <div class="col col-lg-10">
        <div class="row">
            <div class="col col-lg-2">
                Name:
            </div>
            <div id="name-value" class="col-md-auto">
                <c:out value="${user.name}"/>
            </div>
        </div>
        <div class="row">
            <div class="col col-lg-2">
                Surname:
            </div>
            <div id="surname-value" class="col-md-auto">
                <c:out value="${user.surname}"/>
            </div>
        </div>
        <div class="row">
            <div class="col col-lg-2">
                Birthday:
            </div>
            <div id="birthday-value" class="col-md-auto">
                <c:out value="${user.birthday}"/>
            </div>
        </div>
        <div class="row">
            <div class="col col-lg-2">
                Description:
            </div>
            <div class="col-md-auto">

            </div>
        </div>
    </div>
    <div class="col align-self-end">
        <img src="user.png" alt="photo of the user" class="img">
    </div>

</div>

<button id="modifyButton"  type="submit" class="btn btn-primary" onclick="myFunction()">Modify User</button>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!--TODO: export to external js file-->
<script>
    function myFunction() {
        $("#name-value").replaceWith("<div><form><input type='text' name='new-name' placeholder='${user.name}' </input> </form></div>");
        $("#surname-value").replaceWith("<div><form><input type='text' name='new-name' placeholder='${user.surname}' </input> </form></div>")
        $("#birthday-value").replaceWith("<div><form><input type='date' name='new-name' placeholder='${user.birthday}' </input> </form></div>")
        $("#modifyButton").text("Confirm");
        $("#modifyButton").attr("onclick","saveValue()");
    }
</script>

<script>
    function saveValue(){
        alert("ciao bello");
    }
</script>

</body>
</html>
