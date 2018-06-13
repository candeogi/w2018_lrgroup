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
        <a class="nav-item nav-link" id="nav-certificate-tab" data-toggle="tab" href="#nav-certificate" role="tab" aria-controls="nav-certificate" aria-selected="false">Add certificate</a>
        <a class="nav-item nav-link" id="nav-category-tab" data-toggle="tab" href="#nav-category" role="tab" aria-controls="nav-category" aria-selected="false">Add category</a>
    </div>
</nav>
<div class="tab-content" id="nav-tabContent">
    <div class="tab-pane fade show active" id="nav-user" role="tabpanel" aria-labelledby="nav-user-tab">
        <div class="container-fluid">
            <!-- Browse Users Table Div -->
            <div class="col-md-8">
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
                                    <!--<img src="<c:out value="${base64}${user.photoProfile}"/>" class="rounded-circle"> -->
                                    <i class="far fa-image"></i>
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
                                            <c:when test="${sessionScope.isAdmin == true and sessionScope.loggedInUser != user.username and not user.isAdmin()}">
                                                <button type="submit" class="btn btn-primary btn-sm">
                                                    <i class="far fa-trash-alt"></i> Delete</button><br/>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn btn-primary btn-sm" disabled>
                                                    <i class="far fa-trash-alt"></i> Delete</button><br/>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </td>
                                <td>
                                    <form method="POST" action="<c:url value="/update-user"/>" id="updateForm">
                                        <input type="hidden" name="username" value="${user.username}"/>
                                        <c:choose>
                                            <c:when test="${sessionScope.isAdmin == true and sessionScope.loggedInUser != user.username and not user.isAdmin()}">
                                                <button type="submit" class="btn btn-primary btn-sm">
                                                    <i class="far fa-edit"></i>Update</button><br/>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn btn-primary btn-sm" disabled>
                                                    <i class="far fa-edit"></i>Update</button><br/>
                                            </c:otherwise>
                                        </c:choose>

                                    </form>
                                </td>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <div class="tab-pane fade" id="nav-question" role="tabpanel" aria-labelledby="nav-question-tab">
        <div class="container-fluid">
            <div class="col-md-8">
                <div class="table-responsive" id="divquestionlist">
                    <p></p>
                    <table class="table table-hover table-striped table-responsive" id="tablequestionlist">
                    </table>
                </div><!-- close table div -->
            </div>
        </div>
    </div>
    <div class="tab-pane fade" id="nav-certificate" role="tabpanel" aria-labelledby="nav-certificate-tab">...</div>
    <div class="tab-pane fade" id="nav-category" role="tabpanel" aria-labelledby="nav-category-tab">...</div>
</div>

<script type="text/javascript" language="JavaScript" src="<c:url value='/js/ajax-question.js' />"></script>
</body>

<c:import url=  "/jsp/include/footer.jsp"/>

</html>