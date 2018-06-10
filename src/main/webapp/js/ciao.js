function modifyValue() {
    var oldname = $("#name-value").text().trim();
    $("#name-value").hide();
    $("#name-value").after("<div id='form-name'><form><input type='text' name='new-name'</input> </form></div>");
    $('input[name=new-name]').val(oldname);

    var oldsurname = $("#surname-value").text().trim();
    $("#surname-value").hide();
    $("#surname-value").after("<div id='form-surname'><form><input type='text' name='new-surname'</input> </form></div>");
    $('input[name=new-surname]').val(oldsurname);

    var tempbirthday = $("#birthday-value").text().trim().split("/");
    var oldbirthday;
    if(tempbirthday.length==3){
        oldbirthday=tempbirthday[2]+"-"+tempbirthday[1]+"-"+tempbirthday[0];
    }
    else {
        oldbirthday=tempbirthday[0];
    }


    console.log("oldbirthday "+oldbirthday);
    $("#birthday-value").hide();
    $("#birthday-value").after("<div id='form-bdate'><form><input type='date' name='new-birthday'</input> </form></div>");
    $('input[name=new-birthday]').val(oldbirthday);


    var oldDescription = $("#description-value").text().trim();//TODO problem with setting description
    $("#description-value").hide();
    $("#description-value").after("<div id='form-description'><form><textarea type='text' rows='5' name='new-description'> </textarea> </form></div>");
    $('input[name=new-description]').attr("placeholder",oldDescription);


    $("#modifyButton").text("Confirm");
    $("#modifyButton").attr("onclick", "saveValue()");
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
    returnToVisualization(name,surname,birthday,description);
}

function returnToVisualization(name,surname,birthday,description) {
   $('#form-name').remove();
   $('#name-value').show();
   $('#name-value').text(name);

   $('#form-surname').remove();
   $('#surname-value').show();
   $('#surname-value').text(surname);

   $('#form-bdate').remove();
   $('#birthday-value').show();
   var bdatevalue=birthday.toString().split("-");
   var newBdate=bdatevalue[2]+"/"+bdatevalue[1]+"/"+bdatevalue[0];
   $('#birthday-value').text(newBdate);

   $('#form-description').remove();
   $('#description-value').show();
   $('#description-value').text(description);



    $("#modifyButton").text("Modify user");
    $("#modifyButton").attr("onclick", "modifyValue()");
}



