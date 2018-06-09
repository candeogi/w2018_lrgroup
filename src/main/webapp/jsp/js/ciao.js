function myFunction() {
    var oldname = $("#name-value").text().trim();
    $("#name-value").replaceWith("<div><form><input type='text' name='new-name'</input> </form></div>");
    $('input[name=new-name]').val(oldname);

    var oldsurname = $("#surname-value").text().trim();
    $("#surname-value").replaceWith("<div><form><input type='text' name='new-surname'</input> </form></div>");
    $('input[name=new-surname]').val(oldsurname);

    var oldbirthday = $("#birthday-value").text().trim();
    console.log(oldbirthday);
    $("#birthday-value").replaceWith("<div><form><input type='date' name='new-birthday'</input> </form></div>");
    $('input[name=new-birthday]').val(oldbirthday);


    var oldDescription = $("#description-value").text().trim();
    $("#description-value").replaceWith("<div><form><textarea type='text' rows='5' name='new-description'> </textarea> </form></div>");
    $('input[name=new-description]').val(oldDescription);


    $("#modifyButton").text("Confirm");
    $("#modifyButton").attr("onclick", "saveValue()");
}

function saveValue() {
    var name = $('input[name=new-name]').val();
    var surname = $('input[name=new-surname]').val();
    var birhday = $('input[name=new-birthday]').val();
    var description = $('textarea[name=new-description]').val();
    var email = $('#email-value').text().trim();
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            var data = xhr.responseText;
            console.log(data);
        }
    }

    xhr.open('POST', 'update-user', true);
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var information = "name=" + name + "&" + "surname=" + surname + "&" + "bdate=" + birhday + "&";
    information = information + "email=" + email + "&" + "description=" + description;
    xhr.send(information);
}


