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