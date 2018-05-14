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

Author: Giovanni Candeo (giovanni.candeo.1@studenti.unipd.it)
Version: 1.0
Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login Result</title>
</head>

<body>

<!-- display the message -->
<c:import url="/jsp/include/show-message.jsp"/>
<c:choose>
    <c:when test="${not empty loggedInUser}">
        <p>You're logged as <c:out value='${loggedInUser}'/> </p>
    </c:when>
    <c:when test="${empty loggedInUser}">
        <p>You're not logged in!</p>
    </c:when>
</c:choose>
<a href="<c:url value="/jsp/login-form.jsp" /> ">Retry Login</a>

<%--<c:if test="${not empty loggedInUser}">
    <p>You're logged as <c:out value='${loggedInUser}'/> </p>
</c:if>
<c:else>
&lt;%&ndash;<c:if test="${empty loggedInUser}">&ndash;%&gt;
    <p>You're not logged in!</p>
&lt;%&ndash;</c:if>&ndash;%&gt;
</c:else>--%>

</body>
</html>
