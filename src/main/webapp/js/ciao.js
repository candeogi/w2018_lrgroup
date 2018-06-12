$(document).ready(function () {
    visualizeWebsite();
    $("#modifyButton").click(function () {
        modifyValue();
    });
});

var  xhr2;
    function visualizeWebsite() {
    console.log("entrato");
    xhr2 = new XMLHttpRequest();
    xhr2.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            var data = xhr2.responseText;
            alert(data);
        }
    }

    xhr2.onreadystatechange = printResult;
    var username=$("#username-value").text().trim();
    xhr2.open('GET', 'rest/website/user/'+username, true);
    xhr2.send();

}

function printResult() {
    if (xhr2.readyState === XMLHttpRequest.DONE) {

        if (xhr2.status == 200) {

            var jsonResult=JSON.parse(xhr2.responseText);
            console.log("json result: "+jsonResult.toString());
            var website=jsonResult['resource-list'];
            console.log("website "+website.toString());
            var table=document.getElementById("table-website");

            var thead=document.createElement('thead');
            var tr=document.createElement('tr');
            var th=document.createElement('th');
            th.appendChild(document.createTextNode("Type"));
            tr.appendChild(th);

            var th=document.createElement('th');
            th.appendChild(document.createTextNode("Address"));
            tr.appendChild(th);

            thead.appendChild(tr);
            table.appendChild(thead);

            var tbody=document.createElement('tbody');
            for (var i = 0; i < website.length; i++) {
                var tr=document.createElement('tr');
                var td_type=document.createElement('td');
                var webtype=website[i].website.type;
                var img=document.createElement("img");
                td_type.appendChild(img);
                switch (webtype){
                    case 'BitBucket':
                        img.src="images/bitbucket.png";
                        break;
                    case 'Github':
                        img.src="images/github.png";
                        break;
                    case 'Linkedin':
                        img.src="images/linkedin.png";
                        break;
                    case 'OwnSite':
                        img.src="images/site.png";
                        break;
                }
                tr.appendChild(td_type);

                var td_address=document.createElement('td');
                td_address.appendChild(document.createTextNode(website[i].website.address))
                tr.appendChild(td_address);

                tbody.appendChild(tr);
            }

            table.appendChild(tbody);

    }
    else {
        alert('There was a problem with the request.')}
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



