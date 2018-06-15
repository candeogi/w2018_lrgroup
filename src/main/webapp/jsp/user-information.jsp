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
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <!--<link href="css/style.css" rel="stylesheet">-->

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
    <%--<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>--%>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style-information.css' />">

</head>
<body>

<c:import url="/jsp/include/header.jsp"/>
<div style="position:absolute;right:22%;">
    <button id="modifyButton" type="submit" class="btn btn-primary">Modify User</button>
</div>
<div class="container">
    <div class="row justify-content-between">
        <div class="col col-lg-10">
            <%--<div class="row">
                <div class="col col-lg-3">
                    <p> Username:</p>
                </div>
                <div id="username-value" class="col-md-auto">
                    <p><c:out value="${user.username}"/></p>
                </div>
            </div>
            <div class="row">
                <div class="col col-lg-3">
                    <p> Name:</p>
                </div>
                <div id="name-value" class="col-md-auto">
                    <p><c:out value="${user.name}"/></p>
                </div>
            </div>
            <div class="row">
                <div class="col col-lg-3">
                    <p>Surname:</p>
                </div>
                <div id="surname-value" class="col-md-auto">
                    <p><c:out value="${user.surname}"/></p>
                </div>
            </div>
            <div class="row">
                <div class="col col-lg-3">
                    <p>Birthday:</p>
                </div>
                <div id="birthday-value" class="col-md-auto">
                    <p><c:out value="${user.birthday}"/></p>
                </div>
            </div>
            <div class="row">
                <div class="col col-lg-3">
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
                <div id="email-value" class="col-lg-auto">
                    <p><c:out value="${user.email}"/></p>
                </div>
            </div>
            <table>
                <tr>
                    <td><div><p>Username:</p></div></td>
                    <td>
                        <div id="username-value" class="col-md-auto">
                            <p><c:out value="${user.username}"/></p>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><div><p>Name:</p></div></td>
                    <td>
                        <div id="name-value" class="col-md-auto">
                            <p><c:out value="${user.name}"/></p>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><div><p>Surname:</p></div></td>
                    <td>
                        <div id="surname-value" class="col-md-auto">
                            <p><c:out value="${user.surname}"/></p>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><div><p>Birthday:</p></div></td>
                    <td>
                        <div id="birthday-value" class="col-md-auto">
                            <p><c:out value="${user.birthday}"/></p>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><div><p>Description:</p></div></td>
                    <td>
                        <div id="description-value" class="col-md-auto">
                            <p><c:out value="${user.description}"/></p>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><div><p>Email address:</p></div></td>
                    <td>
                        <div id="email-value" class="col-lg-auto">
                            <p><c:out value="${user.email}"/></p>
                        </div>
                    </td>
                </tr>
            </table>--%>
            <div class="divTable infoTable">
                <div class="divTableBody">
                    <div class="divTableRow">
                        <div class="divTableCell"><p> Username:</p></div><div id="username-value" class="divTableCell"><p><c:out value="${user.username}"/></p></div></div>
                    <div class="divTableRow">
                        <div class="divTableCell"><p> Name:</p></div><div id="name-value" class="divTableCell"><p><c:out value="${user.name}"/></p></div></div>
                    <div class="divTableRow">
                        <div class="divTableCell"><p>Surname:</p></div><div id="surname-value" class="divTableCell"><p><c:out value="${user.surname}"/></p></div></div>
                    <div class="divTableRow">
                        <div class="divTableCell"><p>Birthday:</p></div><div id="birthday-value" class="divTableCell"><p><c:out value="${user.birthday}"/></p></div></div>
                    <div class="divTableRow">
                        <div class="divTableCell"><p>Description:</p></div><div id="description-value" class="divTableCell"><p><c:out value="${user.description}"/></p></div></div>
                    <div class="divTableRow">
                        <div class="divTableCell"><p>Email address: </p></div><div id="email-value" class="divTableCell"><p><c:out value="${user.email}"/></p></div></div>
                </div>
            </div>
        </div>

        <div id="photo-div" class="col align-self-end">
            <img src="data:image/jpeg;base64,${user.photoProfile}" alt="photo of the user" class="profileImg">
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col">
                <div id="website" class="container border border-dark">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#websiteModal" style="position:absolute;right:35pt;top:5pt;width:18pt;height:19.5pt;border-radius: 50%;">
                        <i class="fas fa-plus-circle" style="position:absolute;right:3pt;top:3pt;"></i>
                    </button> <br/>
                    <p>Website:</p>
                    <table id="table-website" class="table table-hover">

                    </table>

                </div>
            </div>

            <div class="col">
                <div id="certification" class="container border border-dark">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#certificationModal" style="position:absolute;right:35pt;top:5pt;idth:18pt;height:19.5pt;border-radius: 50%;">
                        <i class="fas fa-plus-circle" style="position:absolute;right:3pt;top:3pt;"></i>
                    </button><br/>
                    <p>Certification:</p>
                    <table id="table-certificate" class="table table-hover">

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>



<!-- Button trigger modal -->


<!-- Modal -->
<div class="modal fade" id="websiteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
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
                <button type="button" class="btn btn-primary" id="saveWebsite" data-dismiss="modal">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="certificationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
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
                <button type="button" class="btn btn-primary" id="saveCertificate"  data-dismiss="modal">Save changes</button>
            </div>
        </div>
    </div>
</div>



<script type="text/javascript" src="js/userInformation.js"></script>


</body>
</html>
