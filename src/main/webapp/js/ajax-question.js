
    var httpRequest;
    var url;
    window.onload=makeRequest;

    $('#switchQs a[href="#yourQs"]').on('click', function(event) {
        event.preventDefault(); // To prevent following the link (optional)
        url = 'http://localhost:8080/web-app-project/rest/question'; //TO-DO: popular question(ordered by upvote?)
        httpRequest = new XMLHttpRequest();

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


                var div = document.getElementsByClassName('table-responsive');

                var table = document.getElementsByClassName('table table-hover');
                document.getElementsByClassName("table table-hover").item(0).innerHTML = "";

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
                table.item(0).appendChild(thead);

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

                    tbody.appendChild(tr);
                }

                table.item(0).appendChild(tbody);

                div.item(0).appendChild(table.item(0));

            } else {
                alert('There was a problem with the request.');
            }
        }
    }



