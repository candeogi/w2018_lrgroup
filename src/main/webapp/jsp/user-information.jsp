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

<c:import url="/jsp/include/header.jsp"/>

<div class="container">
    <div class="row">
        <div class="<col>">
            <div class="row">

            </div>
            <div class="row">

            </div>
            <div class="row">

            </div>
            <div class="row">

            </div>
        </div>
        <div class="<col>">
            <div class="row">

            </div>
            <div class="row">

            </div>
        </div>
        <div class="<col>">

        </div>
    </div>
</div>


<div class="container">
    <div class="row justify-content-between">
        <div class="col col-lg-10">
            <div class="row">
                <div class="col col-lg-2">
                    <p> Username:</p>
                </div>
                <div id="username-value" class="col-md-auto">
                    <p><c:out value="${user.username}"/></p>
                </div>
            </div>
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
    <div class="container">
        <div class="row">
            <div class="col">
                <div id="website" class="container border border-dark">
                    <button type="button" class="btn btn-primary adding" data-toggle="modal"
                            data-target="#websiteModal">
                        <i class="fas fa-plus-circle"></i>
                    </button>
                    <label for="table-website">Website:</label>
                    <table id="table-website" class="table table-hover">

                    </table>

                </div>
            </div>

            <div class="col">
                <div id="certification" class="container border border-dark">
                    <button type="button" class="btn btn-primary adding" data-toggle="modal"
                            data-target="#certificationModal">
                        <i class="fas fa-plus-circle"></i>
                    </button>

                    <label for="table-certificate">Certificates:</label>
                    <table id="table-certificate" class="table table-hover">

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<button id="modifyButton" type="submit" class="btn btn-primary">Modify User</button>

<!-- Button trigger modal -->


<!-- Modal -->
<div class="modal " id="websiteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel1">Insert a new website</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="address-form">Website address</label>
                    <input name="address" type="text" class="form-control" id="address-form"
                           aria-describedby="emailHelp" placeholder="Website address">
                </div>
                <div class="form-group">
                    <div class="input-group mb-3">
                        <select class="custom-select" id="type-form">
                            <option selected="OwnSite">Select the type</option>
                            <option value="BitBucket">BitBucket</option>
                            <option value="OwnSite">OwnSite</option>
                            <option value="Github">Github</option>
                            <option value="OwnSite">Site</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="saveWebsite">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal " id="certificationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Insert a new Certification</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="name-cert-form">Name of Certification</label>
                    <input name="address" type="text" class="form-control" id="name-cert-form"
                           aria-describedby="emailHelp" placeholder="Insert the name of certification">
                </div>
                <div class="form-group">
                    <label for="address-form">Name of Organization</label>
                    <input name="address" type="text" class="form-control" id="org-cert-form"
                           aria-describedby="emailHelp" placeholder="Insert the name of the organization">
                </div>
                <div class="form-group">
                    <label for="date-cert-form">Achievement Date</label>
                    <input name="bdate" type="date" class="form-control" id="date-cert-form"
                           aria-describedby="emailHelp">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="saveCertificate">Save changes</button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="js/userInformation.js"></script>


</body>
</html>
