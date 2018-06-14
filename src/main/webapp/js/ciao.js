$(document).ready(function () {
    visualizeWebsite();
    visualizeCertificate();
    $("#modifyButton").click(function () {
        modifyValue();
    });
    $("#saveWebsite").click(function () {
        addWebsite();
    });

});

var xhrWebsite;
function addWebsite() {
    var newAddressWeb = $("#address-form").val();
    console.log("a"+newAddressWeb);
    var newTypeWeb = $("#type-form").val();
    console.log("b"+newTypeWeb);
    xhrWebsite = new XMLHttpRequest();
    xhrWebsite.onreadystatechange = function () {
        if (xhrWebsite.readyState == 4) {
            var data = xhrWebsite.responseText;
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

function printResultCertificate() {
    if (xhrCertificate.readyState === XMLHttpRequest.DONE) {

        if (xhrCertificate.status == 200) {

            var jsonResult = JSON.parse(xhrCertificate.responseText);
            var certificate = jsonResult['resource-list'];
            var table = document.getElementById("table-certificate");

            var thead = document.createElement('thead');
            var tr = document.createElement('tr');
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
                    rowToDeleteCert=$(this).closest('tr').get(0);
                    var nameCert=rowToDelete.childNodes[1].innerHTML;
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


function deleteRowWebSite() {
    if (xhrDelete.readyState === XMLHttpRequest.DONE) {

        if (xhrDelete.status == 200) {
            console.log("entrato");
            rowToDelete.parentNode.removeChild(rowToDelete);

        }
    }
}

var xhrDeleteCertificate;

function deleteCertificate(){
    xhrDeleteCertificate = new XMLHttpRequest();
    xhrDeleteCertificate.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            var data = xhrDelete.responseText;
            alert(data);
        }
    }

    console.log("deleting certificate");
    xhrDeleteCertificate.onreadystatechange = deleteRowCertificate();
    var username = $("#username-value").text().trim();
    xhrDelete.open('DELETE', 'rest/certificate/user/' + username + '/id/' + idCertificate, true);
    xhrDelete.send();


}


function deleteRowCertificate() {
    if (xhrDeleteCertificate.readyState === XMLHttpRequest.DONE) {

        if (xhrDeleteCertificate.status == 200) {
            console.log("entrato rimozione certificato");
            rowToDelete.parentNode.removeChild(rowToDeleteCert);

        }
    }
}


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



