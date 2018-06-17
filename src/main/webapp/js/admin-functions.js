var username;
var email;
var name;
var surname;
var bdate;
var description;
var isadmin;
var httpRequest2;
var url2;
var httpRequest;
var httpRequest4;
var url;
var url3;
var title;
var body;
window.onload=onLoadRequest;

/*
 Show user update modal
 */
$('#modal-update-user').on('show.bs.modal', function (e) {
    // get information to update quickly to modal view as loading begins
    var opener=e.relatedTarget;//this holds the element who called the modal
    //we get details from attributes
    username = $(opener).attr('user-name');
    //set what we got to our form
    $('#formUser').find('[name="username"]').val(username);
    url2 = './rest/user/id/'+username;
    httpRequest2 = new XMLHttpRequest();
    if (!httpRequest2) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest2.onreadystatechange = userData;
    httpRequest2.open('GET', url2);
    httpRequest2.send();


});

/*
 Show question update modal
 */
$('#modal-update-question').on('show.bs.modal', function (e) {
    // get information to update quickly to modal view as loading begins
    var opener=e.relatedTarget;//this holds the element who called the modal
    //we get details from attributes
    var idquestion = $(opener).attr('id-qs');
    //set what we got to our form
    $('#formQuestion').find('[name="id"]').val(idquestion);

    url3 = './rest/question/id/'+idquestion;
    httpRequest4 = new XMLHttpRequest();
    if (!httpRequest4) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest4.onreadystatechange = placeholderQs;
    httpRequest4.open('GET', url3);
    httpRequest4.send();
});

/*
 Set placeholder in the input box of the update question form
 */
function placeholderQs(){
    if (httpRequest4.readyState === XMLHttpRequest.DONE) {
        if (httpRequest4.status === 200) {
            var jsonData = JSON.parse(httpRequest4.responseText);
            var resource = jsonData['resource-list'];
            var question = resource[0].question;
            title = question['title'];
            body = question['body'];
        }
        else {
            alert('There was a problem with the request.');
        }
    }
    $('#formQuestion').find('[name="title"]').val(title);
    $('#formQuestion').find('[name="body"]').val(body);
}

/*
 Set placeholder in the input box of the update user form
 */
function userData(){
    if (httpRequest2.readyState === XMLHttpRequest.DONE) {
        if (httpRequest2.status === 200) {
            var jsonData = JSON.parse(httpRequest2.responseText);
            var resource = jsonData['resource-list'];
            var user = resource[0].user;
                email = user['email'];
                name = user['name'];
                surname = user['surname'];
                bdate = user['birthday'];
                description = user['description'];
                isadmin = user['isAdmin'];
        }
        else {
            alert('There was a problem with the request.');
        }
    }

    fillForm();
}
/*
 Function which fill placeholder
 */
function fillForm(){
    $('#formUser').find('[name="isAdmin"]').attr("disabled",false);
    $('#userupdatingID').text("You're updating " + username);
    $('#formUser').find('[name="email"]').val(email);
    $('#formUser').find('[name="name"]').val(name);
    $('#formUser').find('[name="surname"]').val(surname);
    $('#formUser').find('[name="bdate"]').val(bdate);
    $('#formUser').find('[name="description"]').text(description);
    $('#formUser').find('[name="isAdmin"]').prop("checked",isadmin);
    if($('#admin-username').val() == username){
        $('#formUser').find('[name="isAdmin"]').attr("disabled",true);
    }
}

var httpRequest3;
var urlcategory;

/*
 Callback function checking if tab is opened
 */
$('#nav-tab-admin a').on('click', function (e) {
    e.preventDefault();

    var value = e.target.getAttribute('href');
    localStorage.setItem('selectedTab', value);

    $(this).tab('show');
})


/*
 Load category in category tab (wrapper)
 */
function loadCategory(){
    httpRequest3 = new XMLHttpRequest();
    urlcategory = './rest/category';

    if (!httpRequest3) {
        alert('Giving up :( Cannot create an XMLHttpRequest instance');
        return false;
    }

    httpRequest3.onreadystatechange = writeCategory;
    httpRequest3.open('GET', urlcategory);
    httpRequest3.send();


    }

    /*
     Load category in category tab

     */
    function writeCategory() {
        if (httpRequest3.readyState === XMLHttpRequest.DONE) {

        if (httpRequest3.status === 200) {
            var div = document.getElementById('divcategorylist');
            var table = document.getElementById('tablecategorylist');

            document.getElementById('tablecategorylist').innerHTML = "";

            var thead = document.createElement('thead');
            thead.className = "thead-light";

            var tr = document.createElement('tr');

            var th = document.createElement('th');
            th.appendChild(document.createTextNode('ID'));
            tr.appendChild(th);

            var th = document.createElement('th');
            th.appendChild(document.createTextNode('Name'));
            tr.appendChild(th);

            var th = document.createElement('th');
            th.appendChild(document.createTextNode('Description'));
            tr.appendChild(th);

            var th = document.createElement('th');
            th.appendChild(document.createTextNode('isCompany'));
            tr.appendChild(th);

            thead.appendChild(tr);
            table.appendChild(thead);

            var tbody = document.createElement('tbody');


            var jsonData = JSON.parse(httpRequest3.responseText);
            var resource = jsonData['resource-list'];

            for (var i = 0; i < resource.length; i++) {
                var category = resource[i].category;
                var tr = document.createElement('tr');

                var td_ID = document.createElement('td');
                td_ID.appendChild(document.createTextNode(category['id']));
                tr.appendChild(td_ID);

                var td_name = document.createElement('td');
                td_name.appendChild(document.createTextNode(category['name']));
                tr.appendChild(td_name);

                var td_description = document.createElement('td');
                td_description.appendChild(document.createTextNode(category['description']));
                tr.appendChild(td_description);

                var td_iscompany = document.createElement('td');
                if(category['isCompany'] == "true"){
                    var is = document.createElement('i');
                    is.className = "fas fa-check-circle";
                    td_iscompany.appendChild(is);
                }
                else{
                    var is = document.createElement('i');
                    is.className = "fas fa-times-circle";
                    td_iscompany.appendChild(is);
                }

                tr.appendChild(td_iscompany);


                tbody.appendChild(tr);
            }

            table.appendChild(tbody);

            div.appendChild(table);

        } else {
            alert('There was a problem with the request.');
        }
    }}


/*
 Function called on page load
 */
function onLoadRequest() {

    loadCategory();
    var selectedTab = localStorage.getItem('selectedTab');
    if (selectedTab != null) {
        $('#nav-tab-admin a[href="' + selectedTab + '"]').tab('show');}

    url = './rest/question';

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

            if(document.getElementById('nav-tab-admin') != null) {
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

                var formdlt = document.createElement('form');
                formdlt.setAttribute("method","POST");
                formdlt.setAttribute("action","delete-question");
                //formdlt.setAttribute("id","formdltQs");

                var inputid = document.createElement('input');
                inputid.setAttribute("type","hidden");
                inputid.setAttribute("name","idquestion");
                inputid.setAttribute("value",question['ID']);
                formdlt.appendChild(inputid);
                var inputid = document.createElement('input');
                inputid.setAttribute("type","hidden");
                inputid.setAttribute("name","from");
                inputid.setAttribute("value","admin-panel");
                formdlt.appendChild(inputid);

                var td_deleteq = document.createElement('td');

                    var button_deleteq = document.createElement('button');
                    button_deleteq.type = "submit";
                    button_deleteq.className = "btn btn-danger";
                    //button_deleteq.id="deleteQsBtn";

                    var icon_delete = document.createElement('i');
                    icon_delete.className = "far fa-trash-alt";
                    var text = document.createTextNode("Delete");
                    icon_delete.appendChild(text);
                    button_deleteq.appendChild(icon_delete);

                    formdlt.appendChild(button_deleteq);
                td_deleteq.appendChild(formdlt);

                tr.appendChild(td_deleteq);


                    var td_updateq = document.createElement('td');

                    var button_updateq = document.createElement('button');
                    button_updateq.type = "submit";
                    //button_updateq.id = "updateqsBtn";
                    button_updateq.className = "btn btn-warning";
                button_updateq.setAttribute("data-target","#modal-update-question");
                button_updateq.setAttribute("data-toggle","modal");
                button_updateq.setAttribute("id-qs",td_ID.id);



                var icon_update = document.createElement('i');
                    icon_update.className = "far fa-edit";
                    var text = document.createTextNode("Update");
                    icon_update.appendChild(text);
                    button_updateq.appendChild(icon_update);

                    td_updateq.appendChild(button_updateq);
                    tr.appendChild(td_updateq);


                tbody.appendChild(tr);
            }

            table.appendChild(tbody);

            div.appendChild(table);

        } else {
            alert('There was a problem with the request.');
        }
    }
}
