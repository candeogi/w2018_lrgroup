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

Author: Giovanni Candeo
Version: 1.0 (First draft, not optimized for bootstrap)
Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<nav>
    <!--Home button-->
    <div><a href="<c:url value="/jsp/index.jsp"/>">Home</a></div>

    <!--Login or Logout-->
    <div>
        <c:choose>
            <c:when test="${empty sessionScope.loggedInUser}">
                <p>Please <a href="<c:url value="/?p=create-user"/>">Register</a> or <a href="<c:url value="/?p=log-in"/>">Login</a></p>
            </c:when>
            <c:when test="${not empty sessionScope.loggedInUser}">
                <p>Logged as: <a href="<c:url value="/?p=user&u=${sessionScope.loggedInUser}" />"><c:out value='${sessionScope.loggedInUser}'/></a></p>
                <a href="<c:url value="/?p=log-out"/>">Logout</a>
            </c:when>
        </c:choose>
    </div>
</nav>
<hr>
