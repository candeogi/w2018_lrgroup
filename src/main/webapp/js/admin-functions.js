var username;
var email;
var name;
var surname;
var bdate;
var description;
var isadmin;
var httpRequest2;
var url2;


$('#modal-update-user').on('show.bs.modal', function (e) {
    // get information to update quickly to modal view as loading begins
    var opener=e.relatedTarget;//this holds the element who called the modal
    //we get details from attributes
    username = $(opener).attr('user-name');
    //set what we got to our form
    $('#formUser').find('[name="username"]').val(username);
    url2 = 'http://localhost:8080/web-app-project/rest/user/id/'+username;
    httpRequest2 = new XMLHttpRequest();
    if (!httpRequest2) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest2.onreadystatechange = userData;
    httpRequest2.open('GET', url2);
    httpRequest2.send();


});

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

$('#nav-tab-admin a').on('click', function (e) {
    e.preventDefault();
    $(this).tab('show');
    var value = e.target.getAttribute('href');
    if(value == '#nav-category'){
        loadCategory();
    }

})



function loadCategory(){
    httpRequest3 = new XMLHttpRequest();
    urlcategory = 'http://localhost:8080/web-app-project/rest/category';

    if (!httpRequest3) {
        alert('Giving up :( Cannot create an XMLHttpRequest instance');
        return false;
    }

    httpRequest3.onreadystatechange = writeCategory;
    httpRequest3.open('GET', urlcategory);
    httpRequest3.send();


    }

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




            //                                            <i class="fas fa-check-circle"></i>
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