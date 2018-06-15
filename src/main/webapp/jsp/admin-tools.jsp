<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Shows questions</title>
</head>
<header>
    <c:import url="/jsp/include/header.jsp"/>
</header>
<body>
<c:set var="base64" value="data:image/jpeg;base64," />
<c:import url="/jsp/include/show-message.jsp"/>
<nav>
    <div class="nav nav-tabs" id="nav-tab-admin" role="tablist">
        <a class="nav-item nav-link active" id="nav-user-tab" data-toggle="tab" href="#nav-user" role="tab" aria-controls="nav-user" aria-selected="true">Edit users</a>
        <a class="nav-item nav-link" id="nav-question-tab" data-toggle="tab" href="#nav-question" role="tab" aria-controls="nav-profile" aria-selected="false">Edit questions</a>
        <!--<a class="nav-item nav-link" id="nav-certificate-tab" data-toggle="tab" href="#nav-certificate" role="tab" aria-controls="nav-certificate" aria-selected="false">Add certificate</a>-->
        <a class="nav-item nav-link" id="nav-category-tab" data-toggle="tab" href="#nav-category" role="tab" aria-controls="nav-category" aria-selected="false">Add category</a>
    </div>
</nav>
<div class="tab-content" id="nav-tabContent">
    <div class="tab-pane fade show active" id="nav-user" role="tabpanel" aria-labelledby="nav-user-tab">
        <div class="container-fluid">
            <!-- Browse Users Table Div -->
                <!--<c:if test='${not empty users && !message.error}'>-->
                    <div class="table-responsive" id="divuserlist">
                        <table class="table table-hover table-striped table-responsive">
                            <thead class="thead-light">
                            <tr>
                                <th></th><th>Email</th><th>Name</th><th>Surname</th><th>Username</th><th>Birthday</th><th>Reg. date</th><th>Admin?</th><th></th><th></th>
                            </tr>
                            </thead>
                            <!-- display the message -->
                            <c:import url="/jsp/include/show-message.jsp"/>
                            <tbody>
                            <c:forEach var="user" items="${users}">
                            <tr>
                                <td class="col-md-2">
                                    <img src="data:image/jpeg;base64,${user.photoProfile}" alt="photo of the user" class="rounded-circle" style="width:15%">
                                </td>
                                <td><c:out value="${user.email}"/></td>
                                <td><c:out value="${user.name}"/></td>
                                <td><c:out value="${user.surname}"/></td>
                                <td><c:out value="${user.username}"/></td>
                                <td><c:out value="${user.birthday}"/></td>
                                <td><c:out value="${user.registrationDate}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.isAdmin()}">
                                            <i class="fas fa-check-circle"></i>
                                        </c:when>

                                        <c:when test="${not user.isAdmin()}">
                                            <i class="fas fa-times-circle"></i>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <form method="POST" action="<c:url value="/delete-user"/>" id="deleteForm">
                                        <input type="hidden" name="from" value="${from}"/>
                                        <input type="hidden" name="username" value="${user.username}"/>
                                        <c:choose>
                                            <c:when test="${sessionScope.isAdmin == true}">
                                                <button type="submit" class="btn btn-primary btn-sm" id="deleteBtn">
                                                    <i class="far fa-trash-alt"></i> Delete</button><br/>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn btn-primary btn-sm" disabled >
                                                    <i class="far fa-trash-alt"></i> Delete</button><br/>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </td>
                                <td>
                                    <c:choose>
                                            <c:when test="${sessionScope.isAdmin == true}">
                                                <button type="button" class="btn btn-primary btn-sm"  user-name="<c:out value="${user.username}"/>" data-toggle="modal" data-target="#modal-update-user">
                                                    <i class="far fa-edit"></i>Update</button><br/>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="button" class="btn btn-primary btn-sm" disabled>
                                                    <i class="far fa-edit"></i>Update</button><br/>
                                            </c:otherwise>
                                        </c:choose>
                                </td>

                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            <div class="modal fade" id="modal-update-user" tabindex="-1" role="dialog" aria-labelledby="UpdateUserModal"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" >Update user</h5>
                            <div name="userupdating"> </div>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div name="userupdating" id="userupdatingID"> </div></br>
                            <form method="POST" action="<c:url value="/update-user"/>" id="formUser" >
                                <!--<div class="form-group">
                                    <label for="usernameInput">Username</label>
                                    <input name="username" type="text" class="form-control" id="username-user"
                                           aria-describedby="usernameHelp" placeholder="Username" value="">
                                </div> -->
                                <input type="hidden" name="username" value="">
                                <input type="hidden" name="view" value="admin-panel">
                                <input type="hidden" name="admin-modifying" value="true">
                                <input type="hidden" name="admin-user" id="admin-username" value="${sessionScope.loggedInUser}">


                                <div class="form-group">
                                    <label for="emailInput">Email</label>
                                    <input name="email" type="text" class="form-control" id="email-user"
                                           aria-describedby="emailHelp" placeholder="Email"value="">
                                </div>

                                <div class="form-group">
                                    <label for="nameInput">Name</label>
                                    <input name="name" type="text" class="form-control" id="name-user"
                                           aria-describedby="nameHelp" placeholder="Name"value="">
                                </div>

                                <div class="form-group">
                                    <label for="surnameInput">Surname</label>
                                    <input name="surname" type="text" class="form-control" id="surname-user"
                                           aria-describedby="surnameHelp" placeholder="Surname" value="">
                                </div>

                                <div class="form-group">
                                    <label for="descrInput">Description</label>
                                    <textarea name="description" class="form-control" id="description-user"
                                              aria-describedby="descrHelp" placeholder="Description"></textarea>
                                </div>

                                <div class="form-group">
                                    <label for="bDateInput">Birthday Date</label>
                                    <input name="bdate" type="date" class="form-control" id="bdate-user"
                                           aria-describedby="bdateHelp" placeholder="">
                                </div>

                                <div class="form-group">
                                    <input name="isAdmin" type="checkbox" class="form-control" id="isAdmin-user"
                                           aria-describedby="isAdminHelp">
                                    <label for="isAdminInput">Is Admin?</label>
                                </div>

                                <button type="submit" class="btn btn-primary ">Update User</button>
                            </form>
                        </div>
                        <!--<div class="modal-footer">
                          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                          <button type="button" class="btn btn-primary">Save changes</button>
                        </div> -->
                    </div>
                </div>
            </div>

        </div>

    </div>

    <div class="tab-pane fade" id="nav-question" role="tabpanel" aria-labelledby="nav-question-tab">
        <div class="container-fluid">
                <div class="table-responsive" id="divquestionlist">
                    <p></p>
                    <table class="table table-hover table-striped table-responsive" id="tablequestionlist">
                    </table>
                </div><!-- close table div -->
            </div>

        <div class="modal fade" id="modal-update-question" tabindex="-1" role="dialog" aria-labelledby="UpdateQuestionModal"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" >Update question</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form method="POST" action="<c:url value="/update-question"/>" id="formQuestion">
                            <input type="hidden" name="id" value="">
                            <input type="hidden" name="from" value="admin-panel">


                            <div class="form-group">
                                <label for="titleInput">Title</label>
                                <input name="title" type="text" class="form-control" id="title-qs"
                                       aria-describedby="titleHelp" placeholder="Title">
                            </div>

                            <div class="form-group">
                                <label for="bodyInput">Body</label>
                                <textarea rows="4" cols="50" name="body" class="form-control" id="body-qs"
                                          aria-describedby="bodyHelp" placeholder="Body"></textarea>
                            </div>


                            <button type="submit" class="btn btn-primary ">Update Question</button>
                        </form>
                    </div>
                    <!--<div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                      <button type="button" class="btn btn-primary">Save changes</button>
                    </div> -->
                </div>
            </div>
        </div>


    </div>
    <!--<div class="tab-pane fade" id="nav-certificate" role="tabpanel" aria-labelledby="nav-certificate-tab">
    </div>-->


    <div class="tab-pane fade" id="nav-category" role="tabpanel" aria-labelledby="nav-category-tab">
        <div class="container-fluid">
            <button type="button" class="btn btn-primary btn-sm"  data-toggle="modal" data-target="#modal-add-category">
                <i class="far fa-edit"></i>Add Category</button><br/>
        <div class="table-responsive" id="divcategorylist">
            <p></p>
            <table class="table table-hover table-striped table-responsive" id="tablecategorylist">
            </table>
        </div><!-- close table div --></div>

        <div class="modal fade" id="modal-add-category" tabindex="-1" role="dialog" aria-labelledby="AddCategoryModal"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" >Add category</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form method="POST" action="<c:url value="/create-category"/>" id="formUser" >
                            <!--<div class="form-group">
                                <label for="usernameInput">Username</label>
                                <input name="username" type="text" class="form-control" id="username-user"
                                       aria-describedby="usernameHelp" placeholder="Username" value="">
                            </div> -->
                            <input type="hidden" name="admincategory" value="admin-panel">

                            <div class="form-group">
                                <label for="nameInput">Name</label>
                                <input name="name" type="text" class="form-control" id="name-category"
                                       aria-describedby="nameHelp" placeholder="Name"value="">
                            </div>

                            <div class="form-group">
                                <label for="descrInput">Description</label>
                                <input name="description" type="text" class="form-control" id="desc-category"
                                       aria-describedby="descHelp" placeholder="Description"value="">
                            </div>

                            <div class="form-group">
                                <label for="iscompanyInput">isCompany</label>
                                <input name="isCompany" type="checkbox" class="form-control" id="iscompany-category"
                                       aria-describedby="iscompanyHelp">
                            </div>

                            <button type="submit" class="btn btn-primary ">Add category</button>
                        </form>
                    </div>
                    <!--<div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                      <button type="button" class="btn btn-primary">Save changes</button>
                    </div> -->
                </div>
            </div>
        </div>


</div>
</div>

<script type="text/javascript" language="JavaScript" src="<c:url value='/js/admin-functions.js' />"></script>

</body>

<c:import url=  "/jsp/include/footer.jsp"/>

</html>