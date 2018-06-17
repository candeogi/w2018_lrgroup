/*Ajax question and related answer view
Author: Giovanni Candeo
 */
var loggedInUser = document.getElementById("loggedInUser");
var currentUser = loggedInUser.getAttribute("data-loggedInUser");
var currentQuestion = loggedInUser.getAttribute("data-currentQuestion");
var isAdmin = (loggedInUser.getAttribute("data-isAdmin") === 'true');
var questioneerID = ' ';

/*REST URL*/
var myQuestionRestUrl='http://localhost:8080/web-app-project/rest/question/id/'+currentQuestion;
var myAnswersRestUrl='http://localhost:8080/web-app-project/rest/answer/question/'+currentQuestion;

/*At start do this*/
window.onload = initialPageLoad;

function initialPageLoad(){
    visualizeQuestion();
    visualizeAnswers();
    /* old add answer from text area
    $("#addAnswerButton").click(function () {
        addNewAnswerForm();
    });*/
    $("#insertAnswerModal").click(function () {
        replyAnswerAjax();
    });

    $("#editAnswerModalButton").click(function () {
        editAnswerAjax();
    });

    $("#editAnswerModalButton").click(function () {
        editAnswerAjax();
    });


}
/*
* User Management
*/
function visualizeQuestioneer(username){
    questioneerID = username;
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/web-app-project/rest/user/id/'+username,
        success: function(data){
            var resourceList = data['resource-list'];
            var user = resourceList[0].user;
            $("#questioneer-name").text(user['name']+' '+user['surname']);
            if(user['isAdmin'] === true){
                $("#questioneer-badge").addClass('fas fa-certificate');
                $("#questioneer-badge").css('color', 'green');
            }else{
                $("#questioneer-badge").addClass('fas fa-user');
            }
            $("#questioneer-regdate").text('Registered on '+user['registrationDate']);
            $("#questioneer-photo").attr("src","data:image/jpeg;base64,"+user['photoProfile']);
            //link to questioneer profile
            var questioneerLinkToProfile = "http://localhost:8080/web-app-project/?p=user&u="+user['username'];
            $("#questioneer-profilelink").attr("href", questioneerLinkToProfile);
        },
        error: function(jqXHR,textStatus,errorThrown){
            /*alert("" +
                " |jqXHR:"+jqXHR+
                " |textStatus: "+textStatus+
                " |errorThrown:"+errorThrown);*/
        }
    });
}
/*
* Visualize the Question at Start
*/
function visualizeQuestion(){
    $.ajax({
        method: 'GET',
        url: myQuestionRestUrl,
        success: function(data) {
            var resourceList = data['resource-list'];
            var question = resourceList[0].question;

            var divQuestionContainer = document.getElementById('question-container');
            //question title
            var questionTitle = document.getElementById('question-title');
            questionTitle.appendChild(document.createTextNode(question['title']));
            //question creation timestamp
            var questionTime = document.getElementById('question-time');
            questionTime.appendChild(document.createTextNode("Sent by "+question['IDUser']+" on "+question['timestamp']));
            //question body
            var questionBody = document.getElementById('question-body');
            var p = $('#question-paragraph');
            p.text(question['body']);
            //question last modified TODO check last modified how it behaves
            var lastModifiedTimeStamp = question["lastModified"];
            if(lastModifiedTimeStamp == null) {
            }else{
                var questionLastModified = document.getElementById('question-lastmodified');
                questionLastModified.appendChild(document.createTextNode("Last modified on "+question['lastModified']));
            }
            if(currentUser === ""){//if logged i can answer
            }else{
                var replyLink = document.createElement('button');
                replyLink.setAttribute('class', 'btn btn-primary');
                replyLink.setAttribute('data-toggle', 'modal');
                replyLink.setAttribute('data-target', '#addAnswerModal');
                replyLink.setAttribute('onclick','setReplyModalTarget('+-1+')');
                //replyLink.setAttribute('href','javascript:void(0);');
                $('#question-buttons').append(replyLink);
                replyLink.appendChild(document.createTextNode(' Answer '));
            }
            if(currentUser === question['IDUser']) { //if im the questioneer i can edit
                var editQLink = document.createElement('button');
                editQLink.setAttribute('class', 'btn btn-secondary');
                editQLink.setAttribute('data-toggle', 'modal');
                editQLink.setAttribute('data-target', '#editQuestionModal');
                editQLink.setAttribute('onclick', 'setEditQModalTarget()');
                //editQLink.setAttribute('href', 'javascript:void(0);');
                $('#question-buttons').append(editQLink);
                editQLink.appendChild(document.createTextNode(' Edit '));
            }
            if(isAdmin || (currentUser === question['IDUser'])) { //if im the questioneer i can edit
                var deleteLink = document.createElement('button');
                deleteLink.setAttribute('class', 'btn btn-danger');
                deleteLink.setAttribute('data-toggle', 'modal');
                deleteLink.setAttribute('data-target', '#deleteQuestionModal');
                deleteLink.setAttribute('onclick', 'setDeleteModalTarget()');
                //deleteLink.setAttribute('href', 'javascript:void(0);');
                $('#question-buttons').append(deleteLink);
                deleteLink.appendChild(document.createTextNode(' Delete '));
            }
            //username - questioneer
            var questioneerUsername = document.getElementById('questioneer-name');
            questioneerUsername.appendChild(document.createTextNode(question['IDUser']));
            visualizeQuestioneer(question['IDUser']);

        },
        error: function(){
            alert("Something went wrong on loading the question!");
        }
    });
}
/*
* Visualize the Answers at Start
*/
function visualizeAnswers(){
    $.ajax({
        method: 'GET',
        url: myAnswersRestUrl,
        success: function(data) {
            var resourceList = data['resource-list'];
            //alert(resourceList[0].answer['text']); test rest if works

            //clear old list
            $("#answerListDiv").html("");

            //<div id="answerListDiv><ul class="answer-list">
            var answerListDiv = document.getElementById("answerListDiv");
            var baseAnswerList = document.createElement("ul");
            baseAnswerList.id =-1+'ul';
            baseAnswerList.className = 'answer-list';
            answerListDiv.appendChild(baseAnswerList);


            //lets print all the answers
            for(var i =0; i< resourceList.length; i++){
                printSingleAnswer(resourceList[i].answer, -1);
            }
        },
        error: function(){
            alert("Something went wrong on loading the answers!");
        }
    });
}
/*
* Print Single answers
* answer: answer to print,
* whereToAppendId: ul element where do i append my new answer
*/
function printSingleAnswer(answer, whereToAppendId){

    //always append to the unordered list with id 'id'+'ul'
    var baseAnswerList = document.getElementById(whereToAppendId+'ul');

    // <li id=answer['ID']>
    var answerListElement = document.createElement("li");
    answerListElement.id = ''+answer['ID'];
    var answerContainer = document.createElement("div");
    answerContainer.className = 'answer-container';
    var answerContent = document.createElement("div");
    answerContent.className = 'answer-content d-flex';
    var answerVote = document.createElement("div");
    answerVote.className = 'answer-vote align-self-start p-2';
    var answerBody = document.createElement("div");
    answerBody.className = 'answer-body align-self-center p-2';
    //Place holder for vote up and down, voteUpIcon is a placeholder atm
    var voteUpIcon = document.createElement("i");
    voteUpIcon.className = 'fas fa-angle-right';
    var voteNumberDiv = document.createElement("div");
    voteNumberDiv.className = 'text-center';
    var voteNumber = document.createTextNode('0'); //TODO placeholder implement votes
    var voteDownIcon = document.createElement("i");
    voteDownIcon.className = 'fas fa-chevron-down';
    var p = document.createElement("p");
    var answerTextBody = document.createTextNode(answer['text']);

    var replyLink = document.createElement('a');
    replyLink.setAttribute('data-toggle', 'modal');
    replyLink.setAttribute('data-target', '#addAnswerModal');
    replyLink.setAttribute('onclick','setReplyModalTarget('+answer['ID']+')');
    replyLink.setAttribute('href','javascript:void(0);');

    //create custom function call for each answer TODO better
    var deleteLink = document.createElement('a');
    deleteLink.setAttribute('onclick','deleteAnswer('+answer['ID']+')');
    deleteLink.setAttribute('href','javascript:void(0);');

    var editLink = document.createElement('a');
    editLink.setAttribute('data-toggle', 'modal');
    editLink.setAttribute('data-target', '#editAnswerModal');
    editLink.setAttribute('onclick','setEditModalTarget('+answer['ID']+')');
    editLink.setAttribute('href','javascript:void(0);');

    //create custom timestamp
    var small = document.createElement('small');
    small.setAttribute('class', 'text-muted')
    var timestampText = document.createTextNode('Sent on '+answer['timestamp']
        + ' by ' +answer['IDUser']+ '    ');

    //append from top
    baseAnswerList.appendChild(answerListElement);
    answerListElement.appendChild(answerContainer);
    answerContainer.appendChild(answerContent);
    /* TODO implement vote */
    answerContent.appendChild(answerVote);
    //answer vote
    answerVote.appendChild(voteUpIcon);
    //answerVote.appendChild(voteNumberDiv);
    //voteNumberDiv.appendChild(voteNumber);
    //answerVote.appendChild(voteDownIcon);

    //answer content
    answerContent.appendChild(answerBody);
    answerBody.appendChild(p);
    answerBody.appendChild(small);

    p.appendChild(answerTextBody);
    small.appendChild(timestampText);
    if(currentUser === "") {
    }else{
        small.appendChild(replyLink);
        replyLink.appendChild(document.createTextNode(' reply '));
    }
    if(isAdmin) {
        small.appendChild(deleteLink);
        deleteLink.appendChild(document.createTextNode(' delete '));
    }
    if(currentUser === answer['IDUser']){
        small.appendChild(editLink);
        editLink.appendChild(document.createTextNode(' edit '));
    }
    /*Works but there is a loop issue TODO fix*/
    visualizeAnswersToAnswer(answer['ID']);
    //this calls answerDropDown(idAnswer) that calls printSingleAnswer for each answer recursively(answer, idAnswer)

}
function visualizeAnswersToAnswer(idAnswer){
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/web-app-project/rest/answer/parentAns/'+idAnswer,
        success: function(data) {
            var resourceList = data['resource-list'];

            var baseAnswerListItem= document.getElementById(idAnswer);
            var baseAnswerList = document.createElement("ul");
            baseAnswerList.className = 'answer-list';
            //always append to the unordered list with id 'id'+'ul'
            baseAnswerList.id = idAnswer+'ul';
            baseAnswerListItem.appendChild(baseAnswerList);

            //lets print all the answers
            for(var i =0; i< resourceList.length; i++){
                printSingleAnswer(resourceList[i].answer, idAnswer);
            }
        },
        error: function(){
            alert("Something went wrong on loading the answers of answer"+idAnswer+"!");
        }
    });
}

/*
* OLD: Reply the question - works but its replaced with replyAnswerAjax
function addNewAnswerForm(){

    var addAnswerText = $("#addAnswerTextArea").val();

    var currentdate = new Date();
    var timestamp = ""
        + currentdate.getFullYear() + "-"
        + (currentdate.getMonth()+1)  + "-"
        + currentdate.getDate() + " "
        + currentdate.getHours() + ":"
        + currentdate.getMinutes() + ":"
        + currentdate.getSeconds() +"."
        + currentdate.getMilliseconds();

    $.ajax({
        method: "POST",
        url: "http://localhost:8080/web-app-project/rest/answer/",
        data: JSON.stringify({
            "answer":{
                "text": addAnswerText,
                "fixed":false,
                "timestamp": timestamp,
                "IDUser": currentUser,
                "parentID": -1,
                "questionID": parseInt(currentQuestion)
            }

        }),
        contentType: "application/json; charset=utf-8",
        dataType   : "json",
        success: function(data) {
            printSingleAnswer(data.answer, -1);
            $("#addAnswerTextArea").val('');

        },
        error: function(jqXHR,textStatus,errorThrown){
            alert("" +
                " |jqXHR:"+JSON.stringify(jqXHR)+
                " |textStatus: "+textStatus+
                " |errorThrown:"+errorThrown);
        }
    });


}
*/
/*
* Delete answer
*/
function deleteAnswer(id){
    var urlToDelete = "http://localhost:8080/web-app-project/rest/answer/"+id;
    $.ajax({
        type: "DELETE",
        url: urlToDelete,
        success: function() {
            //doesnt work and goes on error but still deletes from db //TODO fix
            //alert("hey its me working");
        },
        error: function(jqXHR,textStatus,errorThrown){
            /*alert("" +
                " |jqXHR:"+jqXHR+
                " |textStatus: "+textStatus+
                " |errorThrown:"+errorThrown);
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);*/
        }
    });
    $('#'+id+'').hide();
}
/*
* Edit answer
*/
function setEditModalTarget(id){
    //setting attribute to edit the right answer on editAnswerAjax
    $('#editTextAreaModal').attr('data-edit-target',id);
    /*This code is to set up the textarea with the parent text*/
    var answerParagraph = $('#'+id).find('p').first();
    var answerPreviousText = answerParagraph.text();
    $('#editTextAreaModal').val(answerPreviousText);
}
function editAnswerAjax(){
    //get id of the answer being edited from data-edit-target
    var id= $('#editTextAreaModal').attr('data-edit-target');

    var addAnswerText = $('#editTextAreaModal').val();

    $.ajax({
        method: "PUT",
        url: "http://localhost:8080/web-app-project/rest/answer/",
        data: JSON.stringify({
            "answer":{
                "ID": parseInt(id),
                "text": addAnswerText,
                "fixed":false
            }
        }),
        contentType: "application/json; charset=utf-8",
        dataType   : "json",
        success: function(data) {
            //This code should change the text in the list element
            var answerParagraph = $('#'+id).find('p').first();
            answerParagraph.text(data.answer["text"]);
            $("#addAnswerTextArea").val('');

        },
        error: function(jqXHR,textStatus,errorThrown){
            /*alert("" +
                " |jqXHR:"+jqXHR+
                " |textStatus: "+textStatus+
                " |errorThrown:"+errorThrown);*/
        }
    });
}
/*
* Reply answer
*/
function setReplyModalTarget(id){
    $('#answerTextAreaModal').attr('data-reply-target',id);
    $('#answerTextAreaModal').val("");
}
function replyAnswerAjax(){
    //get id setReplyModalTarget
    var parentID= $('#answerTextAreaModal').attr('data-reply-target');
    var addAnswerText = $('#answerTextAreaModal').val();

    var currentdate = new Date();
    var timestamp = ""
        + currentdate.getFullYear() + "-"
        + (currentdate.getMonth()+1)  + "-"
        + currentdate.getDate() + " "
        + currentdate.getHours() + ":"
        + currentdate.getMinutes() + ":"
        + currentdate.getSeconds() +"."
        + currentdate.getMilliseconds();

    $.ajax({
        method: "POST",
        url: "http://localhost:8080/web-app-project/rest/answer/",
        data: JSON.stringify({
            "answer":{
                "text": addAnswerText,
                "fixed":false,
                "timestamp": timestamp,
                "IDUser": currentUser,
                "parentID": parseInt(parentID),
                "questionID": parseInt(currentQuestion)
            }

        }),
        contentType: "application/json; charset=utf-8",
        dataType   : "json",
        success: function(data) {
            printSingleAnswer(data.answer, parentID);
            $("#addAnswerTextArea").val('');

        },
        error: function(jqXHR,textStatus,errorThrown){
            /*alert("" +
                " |jqXHR:"+jqXHR+
                " |textStatus: "+textStatus+
                " |errorThrown:"+errorThrown);*/
        }
    });
}
/*
* Edit answer
*/
function setEditQModalTarget(){
    //setting attribute to edit the right answer on editAnswerAjax
    //$('#editTextAreaModal').attr('data-edit-target',id);
    /*This code is to set up the textarea with the parent text*/
    var questionPreviousText = $('#question-paragraph').text();
    $('#editQTextAreaModal').val(questionPreviousText);
    var questionPreviousTitle = $('#question-title').text();
    $('#editQTitleModal').val(questionPreviousTitle);
    $('#urlperilservlet').attr('value', 'question-answers.jsp?questionID='+currentQuestion);
    $('#idperilservlet').attr('value', parseInt(currentQuestion));
}
/*
//Rest not implemented yet for the question modification
function editQuestionAjax(){

    var addQuestionText = $('#editQTextAreaModal').val()

    var currentdate = new Date();
    var timestamp = ""
        + currentdate.getFullYear() + "-"
        + (currentdate.getMonth()+1)  + "-"
        + currentdate.getDate() + " "
        + currentdate.getHours() + ":"
        + currentdate.getMinutes() + ":"
        + currentdate.getSeconds() +"."
        + currentdate.getMilliseconds();

    $.ajax({
        method: "PUT",
        url: "http://localhost:8080/web-app-project/rest/question/",
        data: JSON.stringify({
            "question":{
                "ID": parseInt(currentQuestion),
                "text": addQuestionText,
                "lastModified": timestamp
            }
        }),
        contentType: "application/json; charset=utf-8",
        dataType   : "json",
        success: function(data) {
            //This code should change the text in the paragraph element
            var questionParagraph = $('#question-paragraph');
            questionParagraph.text(data.question["text"]);
            $("#editQTextAreaModal").val('');

        },
        error: function(jqXHR,textStatus,errorThrown){
            alert("" +
                " |jqXHR:"+jqXHR+
                " |textStatus: "+textStatus+
                " |errorThrown:"+errorThrown);
        }
    });
}*/
/*
* Delete question setup
 */
function setDeleteModalTarget(){
    $('#idperilservletdelete').attr('value', parseInt(currentQuestion));
}