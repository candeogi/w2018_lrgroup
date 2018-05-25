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

Author: Alberto Forti
Version: 1.0
Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Show question rest</title>

    <style type="text/css">

        div {
            margin: 2em;
        }

    </style>

</head>

<header>
    <c:import url="/jsp/include/header.jsp"/>
</header>

<body>

<h1>Show question rest</h1>

<div>

    <button type="submit" id="ajaxButton">Submit</button><br/>

</div>

<div id="results">

</div>

<script type="text/javascript" language="JavaScript" src="js/ajax-question.js"></script>

</body>
</html>