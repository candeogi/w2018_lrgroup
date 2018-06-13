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

Author: Davide Storato
Version: 1.0
Since: 1.0
-->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Show categories name</title>
</head>
<header>
    <c:import url="/jsp/include/header.jsp"/>
</header>
<body>
<h1>Show categories name</h1>
<hr/>

<c:import url="/jsp/include/show-message.jsp"/>
<c:if test='${not empty categories && !message.error}'>

    <table>
        <thead>
        <tr>
            <th>Name</th>
        </tr>
        </thead>
        <!-- display the message -->
        <c:import url="/jsp/include/show-message.jsp"/>
        <tbody>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td><c:out value="${category.name}"/></td>
        </c:forEach>
        </tbody>
    </table>

</c:if>
</body>
</html>