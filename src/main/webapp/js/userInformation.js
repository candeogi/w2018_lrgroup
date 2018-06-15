//initial instruction in order to implement the listener of the button

$(document).ready(function () {
    visualizeWebsite();
    visualizeCertificate();
    $("#modifyButton").click(function () {
        modifyValue();
    });
    $("#saveWebsite").click(function () {
        addWebsite();
    });
    $("#saveCertificate").click(function () {
        addCertificate();
    })

});


var xhrWebsite;
//This function is called when a user wants to add a site, so there is a called to the server and
//then the updating of the User Interface.
function addWebsite() {
    var newAddressWeb = $("#address-form").val();
    console.log("a" + newAddressWeb);
    var newTypeWeb = $("#type-form").val();
    console.log("b" + newTypeWeb);
    xhrWebsite = new XMLHttpRequest();
    xhrWebsite.onreadystatechange = function () {
        if (xhrWebsite.readyState == 4) {
            alert("communication problem, retry")
        }
    }

    xhrWebsite.open('POST', 'create-website', true);
    xhrWebsite.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhrWebsite.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhrWebsite.onreadystatechange = addRowWebsite;
    var information = "address=" + newAddressWeb + "&" + "addrType=" + newTypeWeb;
    xhrWebsite.send(information);
}

//This function is called then the called for adding a new site, this function called
//use an ajax call in order to updating the website table.
function addRowWebsite() {
    $('#websiteModal').modal('hide');
    if (xhrWebsite.readyState === XMLHttpRequest.DONE) {

        if (xhrWebsite.status == 200) {
            var table = document.getElementById("table-website");
            while (table.firstChild) {
                table.removeChild(table.firstChild);

            }

            visualizeWebsite();
        }
        else {
            alert('There was a problem with the request.')
        }
    }


}

var xhr2;
//This funtion is used to request to the server the list of website related to a user
function visualizeWebsite() {
    xhr2 = new XMLHttpRequest();
    xhr2.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            var data = xhr2.responseText;
            alert(data);
        }
    }

    xhr2.onreadystatechange = printResult;
    var username = $("#username-value").text().trim();
    xhr2.open('GET', 'rest/website/user/' + username, true);
    xhr2.send();

}

var xhrCertificate;

//This function is called to retrieve the list of certificate related to a user

function visualizeCertificate() {
    xhrCertificate = new XMLHttpRequest();
    xhrCertificate.onreadystatechange = function () {
        if (xhrCertificate.readyState == 4) {
            var data = xhrCertificate.responseText;
            alert(data);
        }
    }

    xhrCertificate.onreadystatechange = printResultCertificate;
    var username = $("#username-value").text().trim();
    xhrCertificate.open('GET', 'rest/certificate/user/' + username, true);
    xhrCertificate.send();

}

var rowToDeleteCert;
//This function is used to build the content of the certificate table
function printResultCertificate() {
    if (xhrCertificate.readyState === XMLHttpRequest.DONE) {

        if (xhrCertificate.status == 200) {

            var jsonResult = JSON.parse(xhrCertificate.responseText);
            var certificate = jsonResult['resource-list'];
            var table = document.getElementById("table-certificate");

            var thead = document.createElement('thead');
            var tr = document.createElement('tr');

            var th = document.createElement('th');
            th.appendChild(document.createTextNode("ID"));
            th.style.visibility="collapse";
            tr.appendChild(th);


            var th = document.createElement('th');
            th.appendChild(document.createTextNode("Name"));
            tr.appendChild(th);

            var th = document.createElement('th');
            th.appendChild(document.createTextNode("Organization"));
            tr.appendChild(th);

            var th = document.createElement('th');
            th.appendChild(document.createTextNode("Achievement Date"));
            tr.appendChild(th);

            thead.appendChild(tr);
            table.appendChild(thead);

            var tbody = document.createElement('tbody');
            for (var i = 0; i < certificate.length; i++) {
                var tr = document.createElement('tr');


                var td_id = document.createElement('td');
                td_id.appendChild(document.createTextNode(certificate[i].certificate.ID));
                td_id.style.visibility="collapse";
                tr.appendChild(td_id);

                var td_name = document.createElement('td');
                td_name.appendChild(document.createTextNode(certificate[i].certificate.name));
                tr.appendChild(td_name);


                var td_organization = document.createElement('td');
                td_organization.appendChild(document.createTextNode(certificate[i].certificate.organization));
                tr.appendChild(td_organization);

                var td_achievement = document.createElement('td');
                var date = certificate[i].certificate.achievementDate.toString().split("-");
                var newdate = date[2] + "-" + date[1] + "-" + date[0];
                td_achievement.appendChild(document.createTextNode(newdate));
                tr.appendChild(td_achievement);

                var td_delete = document.createElement('td');
                var button = document.createElement('button');
                var trash = document.createElement('i');
                trash.className = "far fa-trash-alt";
                button.appendChild(trash);
                td_delete.appendChild(button);
                tr.appendChild(td_delete);
                tbody.appendChild(tr);
                button.addEventListener("click", function () {
                    rowToDeleteCert = $(this).closest('tr').get(0);
                    var nameCert = rowToDeleteCert.childNodes[0].innerHTML;
                    deleteCertificate(nameCert);
                });
            }

            table.appendChild(tbody);

        }
        else {
            alert('There was a problem with the request.')
        }
    }

}





var rowToDelete;

//when the request for the website is done, this function is called for constructing
//the content for the website table

function printResult() {
    if (xhr2.readyState === XMLHttpRequest.DONE) {

        if (xhr2.status == 200) {

            var jsonResult = JSON.parse(xhr2.responseText);
            console.log("json result: " + jsonResult.toString());
            var website = jsonResult['resource-list'];
            var table = document.getElementById("table-website");

            var thead = document.createElement('thead');
            var tr = document.createElement('tr');
            var th = document.createElement('th');
            th.appendChild(document.createTextNode("Type"));
            tr.appendChild(th);

            var th = document.createElement('th');
            th.appendChild(document.createTextNode("Address"));
            tr.appendChild(th);

            thead.appendChild(tr);
            table.appendChild(thead);

            var tbody = document.createElement('tbody');
            for (var i = 0; i < website.length; i++) {
                var tr = document.createElement('tr');
                var td_type = document.createElement('td');
                var webtype = website[i].website.type;
                var img = document.createElement("img");
                td_type.appendChild(img);
                switch (webtype) {
                    case 'BitBucket':
                        img.src = "images/bitbucket.png";
                        break;
                    case 'Github':
                        img.src = "images/github.png";
                        break;
                    case 'Linkedin':
                        img.src = "images/linkedin.png";
                        break;
                    case 'OwnSite':
                        img.src = "images/site.png";
                        break;
                }
                tr.appendChild(td_type);

                var td_address = document.createElement('td');
                td_address.appendChild(document.createTextNode(website[i].website.address));
                tr.appendChild(td_address);

                var td_delete = document.createElement('td');
                var button = document.createElement('button');
                var trash = document.createElement('i');
                trash.className = "far fa-trash-alt";
                button.appendChild(trash);
                td_delete.appendChild(button);
                tr.appendChild(td_delete);
                tbody.appendChild(tr);
                button.addEventListener("click", function () {
                    rowToDelete = $(this).closest('tr').get(0);
                    var nameWebsite = rowToDelete.childNodes[1].innerHTML;
                    deleteWebsite(nameWebsite);
                });
            }

            table.appendChild(tbody);

        }
        else {
            alert('There was a problem with the request.')
        }
    }

}


var xhrDelete;
//This function is called to delete a website from the list of the user
function deleteWebsite(nameWebsite) {
    xhrDelete = new XMLHttpRequest();
    xhrDelete.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            var data = xhrDelete.responseText;
            alert(data);
        }
    }

    console.log("deleting website");
    xhrDelete.onreadystatechange = deleteRowWebSite;
    var username = $("#username-value").text().trim();
    xhrDelete.open('DELETE', 'rest/website/user/' + username + '/website/' + nameWebsite, true);
    xhrDelete.send();
}

//This function is called when the deleting in the server of the website is done,
//so the system update also the User interface
function deleteRowWebSite() {
    if (xhrDelete.readyState === XMLHttpRequest.DONE) {

        if (xhrDelete.status == 200) {
            console.log("entrato");
            rowToDelete.parentNode.removeChild(rowToDelete);

        }
    }
}

var xhrDeleteCertificate;
//This function is called when a user wants to delete a certificate
function deleteCertificate(nameCert) {
    xhrDeleteCertificate = new XMLHttpRequest();
    xhrDeleteCertificate.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            var data = xhrDelete.responseText;
            alert(data);
        }
    }

    console.log("deleting certificate");
    xhrDeleteCertificate.onreadystatechange = deleteRowCertificate;
    var username = $("#username-value").text().trim();
    var idCertificate = nameCert;
    xhrDeleteCertificate.open('DELETE', 'rest/certificate/user/' + username + '/id/' + idCertificate, true);
    xhrDeleteCertificate.send();


}

//This function is called when the deleting of the certificate in the server is done.
//This function has the goal of updating the user interface
function deleteRowCertificate() {
    if (xhrDeleteCertificate.readyState === XMLHttpRequest.DONE) {

        if (xhrDeleteCertificate.status == 200) {
            console.log("entrato rimozione certificato");
            rowToDeleteCert.parentNode.removeChild(rowToDeleteCert);

        }
    }
}

var xhrCertificate;

//This function is called when the user wants to add a new certificate
function addCertificate() {
    var newName = $("#name-cert-form").val();
    var newOrg = $("#org-cert-form").val();
    var newDate = $("#date-cert-form").val();
    console.log(newDate);
    xhrCertificate = new XMLHttpRequest();
    xhrCertificate.onreadystatechange = function () {
        if (xhrCertificate.readyState == 4) {
            var data = xhrCertificate.responseText;
            alert("communication problem, retry")
        }
    }
    xhrCertificate.open('POST', 'create-have-certificate', true);
    xhrCertificate.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhrCertificate.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhrCertificate.onreadystatechange = addRowCertificate;
    var information = "certificate-name=" + newName + "&" + "organization=" + newOrg + "&" + "achievementDate=" + newDate;
    xhrCertificate.send(information);


}

//When the adding of the certificate in the server is done, this function is called to updating the User Interface
function addRowCertificate() {
    $('#certificationModal').modal('hide');
    if (xhrCertificate.readyState === XMLHttpRequest.DONE) {

        if (xhrCertificate.status == 200) {
            var table = document.getElementById("table-certificate");
            while (table.firstChild) {
                table.removeChild(table.firstChild);

            }

            visualizeCertificate();
        }
        else {
            alert('There was a problem with the request.')
        }


    }
}


//This function is called when the user wants to modify the personal information
function modifyValue() {
    var oldname = $("#name-value").text().trim();
    $("#name-value").hide();
    $("#name-value").after("<div id='form-name'><form><input class='form-control' type='text' name='new-name'</input> </form></div>");
    $('input[name=new-name]').val(oldname);

    var oldsurname = $("#surname-value").text().trim();
    $("#surname-value").hide();
    $("#surname-value").after("<div id='form-surname'><form><input class='form-control' type='text' name='new-surname'</input> </form></div>");
    $('input[name=new-surname]').val(oldsurname);

    var tempbirthday = $("#birthday-value").text().trim().split("/");
    var oldbirthday;
    if (tempbirthday.length == 3) {
        oldbirthday = tempbirthday[2] + "-" + tempbirthday[1] + "-" + tempbirthday[0];
    }
    else {
        oldbirthday = tempbirthday[0];
    }


    console.log("oldbirthday " + oldbirthday);
    $("#birthday-value").hide();
    $("#birthday-value").after("<div id='form-bdate'><form><input type='date' name='new-birthday'</input> </form></div>");
    $('input[name=new-birthday]').val(oldbirthday);


    var oldDescription = $("#description-value").text().trim();//TODO problem with setting description
    $("#description-value").hide();
    $("#description-value").after("<div id='form-description'><form><textarea type='text' rows='5' name='new-description'></textarea> </form></div>");
    $('textarea[name=new-description]').val(oldDescription);


    //PROBLEMA
    $("#photo-div").after("<form id='photo-form' method='POST' enctype='multipart/form-data' action='/update-user-pic'> <input type='file' name='photoProfile' /><button type='submit'>Upload</button></form>");

    $("#modifyButton").text("Confirm");
    $("#modifyButton").unbind();
    $("#modifyButton").click(function () {
        saveValue();
    });
}


//This function is called when the user wants to submit the new personal information
function saveValue() {
    var name = $('input[name=new-name]').val();
    var surname = $('input[name=new-surname]').val();
    var birthday = $('input[name=new-birthday]').val();
    var description = $('textarea[name=new-description]').val();
    var email = $('#email-value').text().trim();
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            var data = xhr.responseText;
            // console.log(data);
        }
    }

    xhr.open('POST', 'update-user', true);
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var information = "name=" + name + "&" + "surname=" + surname + "&" + "bdate=" + birthday + "&";
    information = information + "email=" + email + "&" + "description=" + description;
    xhr.send(information);
    returnToVisualization(name, surname, birthday, description);
}

//After the updating in the server of the user information, this function is called with the aim
//of reporting the UI to normal presentation.
function returnToVisualization(name, surname, birthday, description) {
    $('#form-name').remove();
    $('#name-value').show();
    $('#name-value').text(name);

    $('#form-surname').remove();
    $('#surname-value').show();
    $('#surname-value').text(surname);

    $('#form-bdate').remove();
    $('#birthday-value').show();
    var bdatevalue = birthday.toString().split("-");
    var newBdate = bdatevalue[2] + "/" + bdatevalue[1] + "/" + bdatevalue[0];
    $('#birthday-value').text(newBdate);

    $('#form-description').remove();
    $('#description-value').show();
    $('#description-value').text(description);

    $('#photo-form').remove();

    $("#modifyButton").unbind();
    $("#modifyButton").text("Modify user");
    $("#modifyButton").click(function () {
        modifyValue();
    });
}



