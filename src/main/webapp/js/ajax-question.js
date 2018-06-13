
    var httpRequest;
    var url;
    window.onload=makeRequest;

    $('#switchQs a[href="#categoryQs"]').on('click', function(event) {
        event.preventDefault(); // To prevent following the link (optional)
        url = 'http://localhost:8080/web-app-project/rest/category';
        httpRequest = new XMLHttpRequest();

        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }
        httpRequest.onreadystatechange = categoryDropdown;
        httpRequest.open('GET', url);
        httpRequest.send();
    });

    function categoryDropdown(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 200) {


                var div = document.getElementById('listCategoryDropdown');

                document.getElementById('listCategoryDropdown').innerHTML = "";

                var jsonData = JSON.parse(httpRequest.responseText);
                var resource = jsonData['resource-list'];

                for (var i = 0; i < resource.length; i++){
                    var category = resource[i].category;
                    var link = document.createElement("a");
                    var text = document.createTextNode(category['name']);
                    var hidden = document.createElement("input");
                    link.appendChild(text);

                    hidden.type = "hidden";
                    hidden.id = category['name'];
                    hidden.value = category['id'];

                    link.href = "#"+category['name'];
                    link.className = "dropdown-item";

                    div.appendChild(link);
                    div.appendChild(hidden);
                }

            } else {
                alert('There was a problem with the request.');
            }
        }

    }

    function deletefilter(){
        document.getElementById("filternav").innerHTML = "";
    }

    $(".dropdown-menu").on('click', '.dropdown-item', function(e) {
        var menu = $(this).html();
        e.preventDefault(); // To prevent following the link (optional)
        url = 'http://localhost:8080/web-app-project/rest/question/category/'+ document.getElementById(menu).value;
        deletefilter();

        var link = document.createElement("a");
        var div = document.getElementById("filternav");

        var text = document.createTextNode("Filter by: " + menu);
        link.id = "filter";
        link.className = "nav-link";
        link.href = "#"+document.getElementById(menu).value;
        $(link.id).attr('data-toggle','tab');
        link.appendChild(text);

        div.appendChild(link);


        httpRequest = new XMLHttpRequest();

        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }
        httpRequest.onreadystatechange = alertContents;
        httpRequest.open('GET', url);
        httpRequest.send();

    });



    $('#switchQs a[href="#popularQs"]').on('click', function(event) {
        event.preventDefault(); // To prevent following the link (optional)
        url = 'http://localhost:8080/web-app-project/rest/question'; //TO-DO: popular question(ordered by upvote?)
        httpRequest = new XMLHttpRequest();
        deletefilter();

        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }
        httpRequest.onreadystatechange = alertContents;
        httpRequest.open('GET', url);
        httpRequest.send();
    });

    $('#switchQs a[href="#yourQs"]').on('click', function(event) {
        event.preventDefault(); // To prevent following the link (optional)
        url = 'http://localhost:8080/web-app-project/rest/question/user/'+document.getElementById('idUser').value;
        httpRequest = new XMLHttpRequest();
        deletefilter();

        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }
        httpRequest.onreadystatechange = alertContents;
        httpRequest.open('GET', url);
        httpRequest.send();
    });

    $('#switchQs a[href="#latestQs"]').on('click', function(event) {
        event.preventDefault(); // To prevent following the link (optional)
        url = 'http://localhost:8080/web-app-project/rest/question';
        httpRequest = new XMLHttpRequest();
        deletefilter();

        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }
        httpRequest.onreadystatechange = alertContents;
        httpRequest.open('GET', url);
        httpRequest.send();
    });


    function makeRequest() {

        url = 'http://localhost:8080/web-app-project/rest/question';

        httpRequest = new XMLHttpRequest();

        if (!httpRequest) {
            alert('Giving up :( Cannot create an XMLHTTP instance');
            return false;
        }
        httpRequest.onreadystatechange = alertContents;
        httpRequest.open('GET', url);
        httpRequest.send();
    }

    function alertContents() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {

            if (httpRequest.status == 200) {


                var div = document.getElementById('divquestionlist');
                var table = document.getElementById('tablequestionlist');

                document.getElementById('tablequestionlist').innerHTML = "";

                var thead = document.createElement('thead');

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

                thead.appendChild(tr);
                table.appendChild(thead);

                var tbody = document.createElement('tbody');


                var jsonData = JSON.parse(httpRequest.responseText);
                var resource = jsonData['resource-list'];

                for (var i = 0; i < resource.length; i++) {
                    var question = resource[i].question;
                    var tr = document.createElement('tr');

                    var td_ID = document.createElement('td');
                    td_ID.appendChild(document.createTextNode(question['ID']));
                    tr.appendChild(td_ID);

                    var td_title = document.createElement('td');
                    td_title.appendChild(document.createTextNode(question['title']));
                    tr.appendChild(td_title);

                    var td_body = document.createElement('td');
                    if(question['body'].length<23) td_body.appendChild(document.createTextNode(question['body']));
                    else td_body.appendChild(document.createTextNode(question['body'].substr(0,20)+'...'));
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

                    if(document.getElementById('nav-tab-admin') != null) {
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



