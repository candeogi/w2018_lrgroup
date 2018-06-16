/**initial instruction in order to implement the listener of buttons used for
 * add a new website, add a new certificate and for modify the user information.
 */

$(document).ready(function () {
    visualizeWebsite();
    visualizeCertificate();
    $("#saveWebsite").click(function () {
        addWebsite();
    });
    $("#saveCertificate").click(function () {
        addCertificate();
    });
    $("#modifyButton").click(function () {
        modifyValue();
    });


});

/**In this section of code there are all the function related to the inserting and the visualization of the website**/


/**This function is called when a user wants to add a site, so there is a called to the server and
 then the updating of the User Interface.**/
var xhrWebsite;

function addWebsite() {
    var newAddressWeb = $("#address-form").val();

    var newTypeWeb = $("#type-form").val();

    xhrWebsite = new XMLHttpRequest();
    xhrWebsite.onreadystatechange = function () {
        if (xhrWebsite.readyState === 4) {
            alert("communication problem, retry")
        }
    };

    xhrWebsite.open('POST', 'create-website', true);
    xhrWebsite.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhrWebsite.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhrWebsite.onreadystatechange = addRowWebsite;
    var information = "address=" + newAddressWeb + "&" + "addrType=" + newTypeWeb;
    xhrWebsite.send(information);
}

/**This function is called then the called for adding a new site, this function called use
 *an ajax call in order to updating the website table.*/

function addRowWebsite() {
    // noinspection JSUnresolvedFunction
    $('#websiteModal').modal('hide');
    if (xhrWebsite.readyState === XMLHttpRequest.DONE) {

        if (xhrWebsite.status === 200) {
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


/**This function is used to request to the server the list of website related to a user**/

var xhr2;

function visualizeWebsite() {
    xhr2 = new XMLHttpRequest();
    xhr2.onreadystatechange = function () {
        if (xhr2.readyState === 4) {
            alert("problem retry");
        }
    };

    xhr2.onreadystatechange = printResult;
    var username = $("#username-value").text().trim();
    xhr2.open('GET', 'rest/website/user/' + username, true);
    xhr2.send();

}

var rowToDelete;

/**when the request for the website is done, this function is called
 * for constructing the content for the website table
 **/
function printResult() {
    if (xhr2.readyState === XMLHttpRequest.DONE) {

        if (xhr2.status === 200) {

            var jsonResult = JSON.parse(xhr2.responseText);

            var website = jsonResult['resource-list'];
            var table = document.getElementById("table-website");

            var thead = document.createElement('thead');
            var tr_head = document.createElement('tr');
            var th_type = document.createElement('th');
            th_type.appendChild(document.createTextNode("Type"));
            tr_head.appendChild(th_type);

            var th_address = document.createElement('th');
            th_address.appendChild(document.createTextNode("Address"));
            tr_head.appendChild(th_address);

            thead.appendChild(tr_head);
            table.appendChild(thead);

            var tbody = document.createElement('tbody');
            for (var i = 0; i < website.length; i++) {
                var tr_row = document.createElement('tr');
                var td_type = document.createElement('td');
                var webtype = website[i]['website']['type'];
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
                tr_row.appendChild(td_type);

                var td_address = document.createElement('td');
                td_address.appendChild(document.createTextNode(website[i]['website']['address']));
                tr_row.appendChild(td_address);

                var td_delete = document.createElement('td');
                var button = document.createElement('button');
                var trash = document.createElement('i');
                trash.className = "far fa-trash-alt";
                button.appendChild(trash);
                button.className = "btn btn-primary";
                td_delete.appendChild(button);
                tr_row.appendChild(td_delete);
                tbody.appendChild(tr_row);
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

/**This function is called to delete a website from the list of the user**/

var xhrDelete;

function deleteWebsite(nameWebsite) {
    xhrDelete = new XMLHttpRequest();
    xhrDelete.onreadystatechange = function () {
        if (xhrDelete.readyState === 4) {
            alert("problem retry");
        }
    };


    xhrDelete.onreadystatechange = deleteRowWebSite;
    var username = $("#username-value").text().trim();
    xhrDelete.open('DELETE', 'rest/website/user/' + username + '/website/' + nameWebsite, true);
    xhrDelete.send();
}

/**This function is called when the deleting in the server of the website is done,
 *so the system update also the User interface
 */

function deleteRowWebSite() {
    if (xhrDelete.readyState === XMLHttpRequest.DONE) {

        if (xhrDelete.status === 200) {

            rowToDelete.parentNode.removeChild(rowToDelete);

        }
    }
}


/**In this section of code there are all the function related to the inserting and the visualization of the certificate**/

var xhrCertificate;

//This function is called to retrieve the list of certificate related to a user

function visualizeCertificate() {
    xhrCertificate = new XMLHttpRequest();
    xhrCertificate.onreadystatechange = function () {
        if (xhrCertificate.readyState === 4) {
            alert("problem retry");
        }
    };

    xhrCertificate.onreadystatechange = printResultCertificate;
    var username = $("#username-value").text().trim();
    xhrCertificate.open('GET', 'rest/certificate/user/' + username, true);
    xhrCertificate.send();

}

/**This function is used to build the content of the certificate table**/

var rowToDeleteCert;

function printResultCertificate() {
    if (xhrCertificate.readyState === XMLHttpRequest.DONE) {

        if (xhrCertificate.status === 200) {

            var jsonResult = JSON.parse(xhrCertificate.responseText);
            var certificate = jsonResult['resource-list'];
            var table = document.getElementById("table-certificate");

            var thead = document.createElement('thead');
            var tr_head = document.createElement('tr');

            var th_id = document.createElement('th');
            th_id.appendChild(document.createTextNode("ID"));
            th_id.style.visibility = "collapse";
            tr_head.appendChild(th_id);


            var th_name = document.createElement('th');
            th_name.appendChild(document.createTextNode("Name"));
            tr_head.appendChild(th_name);

            var th_org = document.createElement('th');
            th_org.appendChild(document.createTextNode("Organization"));
            tr_head.appendChild(th_org);

            var th_date = document.createElement('th');
            th_date.appendChild(document.createTextNode("Achievement Date"));
            tr_head.appendChild(th_date);

            thead.appendChild(tr_head);
            table.appendChild(thead);

            var tbody = document.createElement('tbody');
            for (var i = 0; i < certificate.length; i++) {
                var tr_row = document.createElement('tr');


                var td_id = document.createElement('td');
                td_id.appendChild(document.createTextNode(certificate[i]['certificate']['ID']));
                td_id.style.visibility = "collapse";
                tr_row.appendChild(td_id);

                var td_name = document.createElement('td');
                td_name.appendChild(document.createTextNode(certificate[i]['certificate']['name']));
                tr_row.appendChild(td_name);


                var td_organization = document.createElement('td');
                td_organization.appendChild(document.createTextNode(certificate[i]['certificate']['organization']));
                tr_row.appendChild(td_organization);

                var td_achievement = document.createElement('td');
                var date = certificate[i]['certificate']['achievementDate'].toString().split("-");
                var newdate = date[2] + "-" + date[1] + "-" + date[0];
                td_achievement.appendChild(document.createTextNode(newdate));
                tr_row.appendChild(td_achievement);

                var td_delete = document.createElement('td');
                var button = document.createElement('button');
                var trash = document.createElement('i');
                trash.className = "far fa-trash-alt";
                button.appendChild(trash);
                button.className = "btn btn-primary";
                td_delete.appendChild(button);
                tr_row.appendChild(td_delete);
                tbody.appendChild(tr_row);
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

/**This function is called when a user wants to delete a certificate**/

var xhrDeleteCertificate;

function deleteCertificate(nameCert) {
    xhrDeleteCertificate = new XMLHttpRequest();
    xhrDeleteCertificate.onreadystatechange = function () {
        if (xhrDeleteCertificate.readyState === 4) {
            alert("problem retry");
        }
    };


    xhrDeleteCertificate.onreadystatechange = deleteRowCertificate;
    var username = $("#username-value").text().trim();
    xhrDeleteCertificate.open('DELETE', 'rest/certificate/user/' + username + '/id/' + nameCert, true);
    xhrDeleteCertificate.send();


}

/**This function is called when the deleting of the certificate in the server is done.
 *  This function has the goal of updating the user interface**/
function deleteRowCertificate() {
    if (xhrDeleteCertificate.readyState === XMLHttpRequest.DONE) {

        if (xhrDeleteCertificate.status === 200) {

            rowToDeleteCert.parentNode.removeChild(rowToDeleteCert);

        }
    }
}


/**This function is called when the user wants to add a new certificate**/

function addCertificate() {
    var newName = $("#name-cert-form").val();
    var newOrg = $("#org-cert-form").val();
    var newDate = $("#date-cert-form").val();

    xhrCertificate = new XMLHttpRequest();
    xhrCertificate.onreadystatechange = function () {
        if (!xhrCertificate) {
            alert("communication problem, retry ");
        }
    };
    xhrCertificate.open('POST', 'create-have-certificate', true);
    xhrCertificate.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhrCertificate.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhrCertificate.onreadystatechange = addRowCertificate;
    var information = "certificate-name=" + newName + "&" + "organization=" + newOrg + "&" + "achievementDate=" + newDate;
    xhrCertificate.send(information);


}

/**When the adding of the certificate in the server is done, this function is called to updating the User Interface**/

function addRowCertificate() {
    // noinspection JSUnresolvedFunction
    $('#certificationModal').modal('hide');
    if (xhrCertificate.readyState === XMLHttpRequest.DONE) {

        if (xhrCertificate.status === 200) {
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

/**In this section of code there all the function related to the modification of the user information**/


/**This function is called when the user wants to modify the personal information, it hides the div that report the information of the user
 * and build the form where the user can insert the new information and the button used to save the value;
 */
function modifyValue() {
    var name_element = $("#name-value");
    var oldname = name_element.text().trim();
    name_element.hide();
    name_element.after("<div id='form-name'><form><input class='form-control' type='text' name='new-name'</input> </form></div>");
    $('input[name=new-name]').val(oldname);

    var surname_element = $("#surname-value");
    var oldsurname = surname_element.text().trim();
    surname_element.hide();
    surname_element.after("<div id='form-surname'><form><input class='form-control' type='text' name='new-surname'</input> </form></div>");
    $('input[name=new-surname]').val(oldsurname);

    var birthday_value = $("#birthday-value");
    var tempbirthday = birthday_value.text().trim().split("/");
    var oldbirthday;
    if (tempbirthday.length === 3) {
        oldbirthday = tempbirthday[2] + "-" + tempbirthday[1] + "-" + tempbirthday[0];
    }
    else {
        oldbirthday = tempbirthday[0];
    }


    birthday_value.hide();
    birthday_value.after("<div id='form-bdate'><form><input type='date' name='new-birthday'</input> </form></div>");
    $('input[name=new-birthday]').val(oldbirthday);


    var description_element = $("#description-value");
    var oldDescription = description_element.text().trim();//TODO problem with setting description
    description_element.hide();
    description_element.after("<div id='form-description'><form><textarea rows='5' name='new-description'></textarea> </form></div>");
    $('textarea[name=new-description]').val(oldDescription);


    var div = document.createElement("div");
    div.setAttribute("id", "form-image");
    var form = document.createElement("form");
    form.setAttribute("method", "POST");
    form.setAttribute("enctype", "multipart/form-data");
    form.setAttribute("action", "update-user-pic");
    var input = document.createElement("input");
    input.setAttribute("type", "file");
    input.setAttribute("name", "photoProfile");

    var button = document.createElement("button");
    button.setAttribute("type", "submit");
    button.setAttribute("class", "btn btn-primary");
    button.innerHTML = "Load";

    form.appendChild(input);
    form.appendChild(button);

    div.appendChild(form);


    var button1 = document.getElementById("photo-div");
    button1.appendChild(div);


    var modifyButton = $("#modifyButton");
    modifyButton.text("Confirm");
    modifyButton.unbind();
    modifyButton.click(function () {
        saveValue();
    });
}


//This function is called when the user wants to submit the new personal information

var name;
var surname;
var birthday;
var description;
var email;
var xhrSave;

function saveValue() {
    name = $('input[name=new-name]').val();
    surname = $('input[name=new-surname]').val();
    birthday = $('input[name=new-birthday]').val();
    description = $('textarea[name=new-description]').val();
    email = $('#email-value').text().trim();
    xhrSave = new XMLHttpRequest();
    xhrSave.onreadystatechange = function () {
        if (!xhrSave) {
            alert("problem");
        }
    };

    xhrSave.open('POST', 'update-user', true);
    xhrSave.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhrSave.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhrSave.onreadystatechange = returnToVisualization;
    var information = "name=" + name + "&" + "surname=" + surname + "&" + "bdate=" + birthday + "&";
    information = information + "email=" + email + "&" + "description=" + description;
    xhrSave.send(information);
}

/**After the updating in the server of the user information, this function is called with the aim
 of reporting the UI to normal presentation**/
function returnToVisualization() {

    if (xhrSave.status === 200) {

        $('#form-name').remove();
        var name_element = $('#name-value');
        name_element.show();
        name_element.text(name);

        $('#form-surname').remove();
        var surname_element = $('#surname-value');
        surname_element.show();
        surname_element.text(surname);

        $('#form-bdate').remove();
        var birthday_element = $('#birthday-value');
        birthday_element.show();
        var bdatevalue = birthday.toString().split("-");
        var newBdate = bdatevalue[2] + "/" + bdatevalue[1] + "/" + bdatevalue[0];
        birthday_element.text(newBdate);

        $('#form-description').remove();

        var description_element = $('#description-value');
        description_element.show();
        description_element.text(description);

        $('#photo-form').remove();


        var button_element = $("#modifyButton");
        button_element.unbind();
        button_element.text("Modify user");
        button_element.click(function () {
            modifyValue();
        });

        $("#form-image").remove();

    }


    else {
        alert("problem with communication");
        location.reload();
    }

}



