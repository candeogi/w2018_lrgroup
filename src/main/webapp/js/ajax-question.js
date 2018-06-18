var httpRequest;
var url;
window.onload = onLoadRequest;


/*
 Callback function for loading category
 */
$('#switchQs a[href="#categoryQs"]').on('click', function (event) {
    event.preventDefault(); // To prevent following the link (optional)
    document.getElementById("listCategoryDropdown").classList.toggle("show");
    url = 'rest/category';
    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = categoryDropdown;
    httpRequest.open('GET', url);
    httpRequest.send();
});

/*
 Callback function for selecting a specific question
 */
$('#tablequestionlist').on('click', '.clickable-row', function (e) {
    e.preventDefault(); // To prevent following the link (optional)
    var href = $(this).find("td").attr("id");
    var div = document.getElementById('divquestionlist');
    var table = document.getElementById('tablequestionlist');
    var form = document.createElement('form');
    form.setAttribute('id', 'myform');
    form.setAttribute('method', 'GET');
    form.setAttribute('action', 'jsp/question-answers.jsp');

    var input = document.createElement('input');
    input.setAttribute('type', 'hidden');
    input.setAttribute('name', 'questionID');
    input.setAttribute('value', href);

    form.appendChild(input);
    table.appendChild(form);

    document.forms["myform"].submit();
    document.getElementById('tablequestionlist').innerHTML = "";
});

/*
 Callback function which loads category name in the dropdown menu
 */
function categoryDropdown() {

    if (httpRequest.readyState === XMLHttpRequest.DONE) {

        if (httpRequest.status === 200) {

            var div = document.getElementById('listCategoryDropdown');

            document.getElementById('listCategoryDropdown').innerHTML = "";

            var jsonData = JSON.parse(httpRequest.responseText);
            var resource = jsonData['resource-list'];

            for (var i = 0; i < resource.length; i++) {
                var category = resource[i].category;
                var link = document.createElement("a");
                var text = document.createTextNode(category['name']);
                var hidden = document.createElement("input");
                link.appendChild(text);

                hidden.type = "hidden";
                hidden.id = category['name'];
                hidden.value = category['id'];

                link.href = "#" + category['name'];
                link.className = "dropdown-item";

                div.appendChild(link);
                div.appendChild(hidden);
            }

        } else {
            alert('There was a problem with the request.');
        }
    }

}

function deletefilter() {
    document.getElementById("filternav").innerHTML = "";
}

/*
 Callback function for selecting questions filtered by category
 */
$("#listCategoryDropdown").on('click', '.dropdown-item', function (e) {
    var menu = $(this).html();
    e.preventDefault(); // To prevent following the link (optional)
    //$('#switchQs a[href="#searchedQs"]').attr('class','nav-link disabled');
    document.getElementById("listCategoryDropdown").classList.toggle("show", false);
    url = 'rest/question/category/' + document.getElementById(menu).value;
    deletefilter();

    var link = document.createElement("a");
    var div = document.getElementById("filternav");

    var text = document.createTextNode("Filter by: " + menu);
    link.id = "filter";
    link.className = "nav-link";
    link.href = "#" + document.getElementById(menu).value;
    $(link.id).attr('data-toggle', 'tab');
    link.appendChild(text);

    div.appendChild(link);


    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = loadQsRequest;
    httpRequest.open('GET', url);
    httpRequest.send();

});

/*
 Callback function for selecting popular question
 */
$('#switchQs a[href="#popularQs"]').on('click', function (event) {
    event.preventDefault(); // To prevent following the link (optional)
    //$('#switchQs a[href="#searchedQs"]').attr('class','nav-link disabled');
    url = 'rest/question/byvote'; //TO-DO: popular question(ordered by upvote?)
    httpRequest = new XMLHttpRequest();
    deletefilter();

    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = loadQsRequest;
    httpRequest.open('GET', url);
    httpRequest.send();
});

/*
 Callback function for selecting searched question
 */
$('#switchQs a[href="#searchedQs"]').on('click', function (event) {
    event.preventDefault(); // To prevent following the link (optional)
    //$('#switchQs a[href="#searchedQs"]').attr('class','nav-link disabled');
    if(document.getElementById("searchinqs") == null){
        if(document.getElementById("longSearchinqs").value == ""){
            url = 'rest/question';
            $('#switchQs a[href="#popularQs"]').trigger('click');
        }
        else{
            url = 'rest/question/searchby/' + document.getElementById("longSearchinqs").value;
        }
    }
    else{
        if(document.getElementById("searchinqs").value == ""){
            url = 'rest/question';
            $('#switchQs a[href="#popularQs"]').trigger('click');
        }
        else{
            url = 'rest/question/searchby/' + document.getElementById("searchinqs").value;
        }
    }

    httpRequest = new XMLHttpRequest();
    deletefilter();

    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = loadQsRequest;
    httpRequest.open('GET', url);
    httpRequest.send();
});

/*
 Callback function for search input box
 */
$('#searchBtn').on('click', function (event) {
    event.preventDefault(); // To prevent following the link (optional)
    if (document.getElementById("searchinqs").value == ""){
        url = 'rest/question/byvote';
        $('#switchQs a[href="#popularQs"]').tab('show');}
    else{
        url = 'rest/question/searchby/' + document.getElementById("searchinqs").value;
        $('#switchQs a[href="#searchedQs"]').tab('show');}
    httpRequest = new XMLHttpRequest();
    deletefilter();

    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = loadQsRequest;
    httpRequest.open('GET', url);
    httpRequest.send();
});

/*
 Callback function for long search input box
 */
$('#longSearchBtn').on('click', function (event) {
    event.preventDefault(); // To prevent following the link (optional)
    if (document.getElementById("longSearchinqs").value == ""){
        url = 'rest/question/byvote';
        $('#switchQs a[href="#popularQs"]').tab('show');}
    else{
        url = 'rest/question/searchby/' + document.getElementById("longSearchinqs").value;
        $('#switchQs a[href="#searchedQs"]').tab('show');}
    httpRequest = new XMLHttpRequest();
    deletefilter();

    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = loadQsRequest;
    httpRequest.open('GET', url);
    httpRequest.send();
});


/*
 Callback function for select questions by logged user
 */
$('#switchQs a[href="#yourQs"]').on('click', function (event) {
    event.preventDefault(); // To prevent following the link (optional)
    //$('#switchQs a[href="#searchedQs"]').attr('class','nav-link disabled');
    url = 'rest/question/user/' + document.getElementById('idUser').value;
    httpRequest = new XMLHttpRequest();
    deletefilter();

    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = loadQsRequest;
    httpRequest.open('GET', url);
    httpRequest.send();
});

/*
 Callback function for select questions by timestamp
 */
$('#switchQs a[href="#latestQs"]').on('click', function (event) {
    event.preventDefault(); // To prevent following the link (optional)
    //$('#switchQs a[href="#searchedQs"]').attr('class','nav-link disabled');
    url = 'rest/question/latestQuestion';
    httpRequest = new XMLHttpRequest();
    deletefilter();

    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = loadQsRequest;
    httpRequest.open('GET', url);
    httpRequest.send();
});


/*
 Function called on page load
 */
function onLoadRequest() {

    url = 'rest/question/latestQuestion';

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = loadQsRequest;
    httpRequest.open('GET', url);
    httpRequest.send();
}

/*
 Load all questions in the db
 */
function loadQsRequest() {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {

        if (httpRequest.status === 200) {


            var div = document.getElementById('divquestionlist');
            var table = document.getElementById('tablequestionlist');

            document.getElementById('tablequestionlist').innerHTML = "";

            var thead = document.createElement('thead');
            thead.className = "thead-light";

            var tr = document.createElement('tr');

            var th = document.createElement('th');
            th.appendChild(document.createTextNode('ID'));
            tr.appendChild(th);

            var th = document.createElement('th');
            th.appendChild(document.createTextNode('title'));
            tr.appendChild(th);

            var th = document.createElement('th');
            th.appendChild(document.createTextNode('body'));
            tr.appendChild(th);

            var th = document.createElement('th');
            th.appendChild(document.createTextNode('timestamp'));
            tr.appendChild(th);

            var th = document.createElement('th');
            th.appendChild(document.createTextNode('lastModified'));
            tr.appendChild(th);

            var th = document.createElement('th');
            th.appendChild(document.createTextNode('IDUser'));
            tr.appendChild(th);

            if (document.getElementById('nav-tab-admin') != null) {
                var th = document.createElement('th');
                tr.appendChild(th);
                var th = document.createElement('th');
                tr.appendChild(th);
            }

            thead.appendChild(tr);
            table.appendChild(thead);

            var tbody = document.createElement('tbody');


            var jsonData = JSON.parse(httpRequest.responseText);
            var resource = jsonData['resource-list'];

            for (var i = 0; i < resource.length; i++) {
                var question = resource[i].question;
                var tr = document.createElement('tr');
                tr.className = "clickable-row";

                var td_ID = document.createElement('td');
                td_ID.id = question['ID'];
                td_ID.appendChild(document.createTextNode(question['ID']));
                tr.appendChild(td_ID);

                var td_title = document.createElement('td');
                td_title.appendChild(document.createTextNode(question['title']));
                tr.appendChild(td_title);

                var td_body = document.createElement('td');
                if (question['body'].length < 23) td_body.appendChild(document.createTextNode(question['body']));
                else td_body.appendChild(document.createTextNode(question['body'].substr(0, 20) + '...'));
                tr.appendChild(td_body);

                var td_ts = document.createElement('td');
                td_ts.appendChild(document.createTextNode(question['timestamp']));
                tr.appendChild(td_ts);

                var td_lastMod = document.createElement('td');
                td_lastMod.appendChild(document.createTextNode(question['lastModified']));
                tr.appendChild(td_lastMod);

                var td_iduser = document.createElement('td');
                td_iduser.appendChild(document.createTextNode(question['IDUser']));
                tr.appendChild(td_iduser);

                if (document.getElementById('nav-tab-admin') != null) {
                    var td_deleteq = document.createElement('td');

                    var button_deleteq = document.createElement('button');
                    button_deleteq.type = "submit";
                    button_deleteq.className = "btn btn-danger";

                    var icon_delete = document.createElement('i');
                    icon_delete.className = "far fa-trash-alt";
                    var text = document.createTextNode("Delete");
                    icon_delete.appendChild(text);
                    button_deleteq.appendChild(icon_delete);

                    td_deleteq.appendChild(button_deleteq);
                    tr.appendChild(td_deleteq);


                    var td_updateq = document.createElement('td');

                    var button_updateq = document.createElement('button');
                    button_updateq.type = "submit";
                    button_updateq.className = "btn btn-warning";

                    var icon_update = document.createElement('i');
                    icon_update.className = "far fa-edit";
                    var text = document.createTextNode("Update");
                    icon_update.appendChild(text);
                    button_updateq.appendChild(icon_update);

                    td_updateq.appendChild(button_updateq);
                    tr.appendChild(td_updateq);


                }

                tbody.appendChild(tr);
            }

            table.appendChild(tbody);

            div.appendChild(table);

        } else {
            alert('There was a problem with the request.');
        }
    }
}

/*
 Callback function for adding a question
 */
$("#saveQuestion").click(function () {

    addQuestion();

})

var xhrQuestion;

//This function is called when the user wants to add a new question
function addQuestion() {

    var content = $("#body-form").val();
    var title = $("#title-form").val();

    xhrQuestion = new XMLHttpRequest();
    xhrQuestion.onreadystatechange = function () {
        if (xhrQuestion.readyState == 4) {
            var data = xhrQuestion.responseText;
            alert("communication problem, retry")
        }
    }

    xhrQuestion.open('POST', 'create-question', true);
    xhrQuestion.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhrQuestion.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhrQuestion.onreadystatechange = finalizeQuestion;
    var information = "title=" + title + "&" + "body=" + content+"&"+"category="+selCategory;
    var content = $("#body-form").val('');
    var title = $("#title-form").val('');
    xhrQuestion.send(information);

}

//When the adding of the certificate in the server is done, this function is called to updating the User Interface
function finalizeQuestion() {
    $('#addQuestionModal').modal('hide');
    //TODO call the method that retrieve question from server
    $('#switchQs a[href="#latestQs"]').tab('show');
    onLoadRequest();
}


var httpRequest1;
function showCat() {
    document.getElementById("divModalDrop").classList.toggle("show");
    url = 'rest/category';
    httpRequest1 = new XMLHttpRequest();

    if (!httpRequest1) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest1.onreadystatechange = categoryDropdownMod;
    httpRequest1.open('GET', url);
    httpRequest1.send();

}


var selCategory;
function categoryDropdownMod() {

    if (httpRequest1.readyState === XMLHttpRequest.DONE) {

        if (httpRequest1.status === 200) {

            var div = document.getElementById('divModalDrop');
            div.innerText="";

            var jsonData = JSON.parse(httpRequest1.responseText);
            var resource = jsonData['resource-list'];

            for (var i = 0; i < resource.length; i++) {
                var category = resource[i].category['name'];
                var element=document.createElement("button");
                element.setAttribute("class","dropdown-item");
                element.setAttribute("onClick","selectedCategory(this)");
                element.setAttribute("type","button");
                element.setAttribute("value",category);
                var text=document.createTextNode(category);
                element.appendChild(text);
                div.appendChild(element);
            }


        } else {
            alert('There was a problem with the request.');
        }
    }

}
function  selectedCategory(item) {
    var button=document.getElementById("dropdownButton");
    button.innerText=item.value;
    selCategory=item.value;
    $("#divModalDrop").dropdown("toggle");
    $('#divModalDrop').dropdown("toggle");
    $('#dropdownCat').dropdown("toggle");

}




