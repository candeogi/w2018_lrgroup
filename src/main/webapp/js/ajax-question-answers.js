/*Ajax question and related answer view
Author: Giovanni Candeo
 */
var loggedInUser = document.getElementById("loggedInUser");
var currentUser = loggedInUser.getAttribute("data-loggedInUser");
var currentQuestion = loggedInUser.getAttribute("data-currentQuestion");


/*REST URL*/
var myQuestionRestUrl='http://localhost:8080/web-app-project/rest/question/id/'+currentQuestion;
var myAnswersRestUrl='http://localhost:8080/web-app-project/rest/answer/question/'+currentQuestion;

/*At start do this*/
window.onload = initialPageLoad;

function initialPageLoad(){
    visualizeQuestion();
    visualizeAnswers();

    $("#insertAnswerModal").click(function () {
        replyAnswerAjax();
    });
}
/* USER MANAGEMENT */

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
            var p = document.createElement('p')
            p.appendChild(document.createTextNode(question['body']));
            questionBody.appendChild(p);
            //question last modified TODO check last modified how it behaves
            if(question["lastModified"] !== ""){
                var questionLastModified = document.getElementById('question-lastmodified');
                questionLastModified.appendChild(document.createTextNode("Last modified on "+question['lastModified']));
            }
            //username - questioneer
            var questioneerUsername = document.getElementById('questioneer-name');
            questioneerUsername.appendChild(document.createTextNode(question['IDUser']));
        },
        error: function(){
            alert("Something went wrong on loading the question!");
        }
    });
}
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
            baseAnswerList.id =0+'ul';
            baseAnswerList.className = 'answer-list';
            answerListDiv.appendChild(baseAnswerList);


            //lets print all the answers
            for(var i =0; i< resourceList.length; i++){
                console.log(resourceList[i].answer);
                printSingleAnswer(resourceList[i].answer, 0);
            }
        },
        error: function(){
            alert("Something went wrong on loading the answers!");
        }
    });
}

function printSingleAnswer(answer, whereToAppendId){
    //console.log(answer['text']);

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
    var voteUpIcon = document.createElement("i");
    voteUpIcon.className = 'fas fa-chevron-up';
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
    editLink.setAttribute('onclick','editAnswer('+answer['ID']+')');
    editLink.setAttribute('href','javascript:void(0);');
    editLink.className = 'editButton';

    //create custom timestamp
    var small = document.createElement('small');
    var timestampText = document.createTextNode('Sent on '+answer['timestamp']
        + ' by ' +answer['IDUser']+ '    ');

    //append from top
    baseAnswerList.appendChild(answerListElement);
    answerListElement.appendChild(answerContainer);
    answerContainer.appendChild(answerContent);
    answerContent.appendChild(answerVote);
    //answer vote
    answerVote.appendChild(voteUpIcon);
    answerVote.appendChild(voteNumberDiv);
    voteNumberDiv.appendChild(voteNumber);
    answerVote.appendChild(voteDownIcon);
    //answer content
    answerContent.appendChild(answerBody);
    answerBody.appendChild(p);
    answerBody.appendChild(small);

    p.appendChild(answerTextBody);
    small.appendChild(timestampText);
    small.appendChild(replyLink);
    replyLink.appendChild(document.createTextNode(' reply '));
    small.appendChild(deleteLink);
    deleteLink.appendChild(document.createTextNode(' delete '));
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


$("#addAnswerButton").click(function () {
    addNewAnswerForm();
});
/*Add a new answer function - works but goes on error TODO*/
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
            console.log(data.answer);
            printSingleAnswer(data.answer, 0);
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

function deleteAnswer(id){
    //doesnt work and goes on error TODO
    var urlToDelete = "http://localhost:8080/web-app-project/rest/answer/"+id;
    console.log(urlToDelete);
    $.ajax({
        type: "DELETE",
        url: urlToDelete,,
        success: function() {
            //alert("hey its me working");
        },
        error: function(jqXHR,textStatus,errorThrown){
            alert("" +
                " |jqXHR:"+jqXHR+
                " |textStatus: "+textStatus+
                " |errorThrown:"+errorThrown);
        }
    });
    $('#'+id+'').hide();
}
function editAnswer(id){
    var answerParagraph = $('#'+id).find('p').first();
    var answerPreviousText = answerParagraph.text();
    answerParagraph.replaceWith($('<textarea id="swap">'+answerPreviousText+'</textarea><br>'));
    var buttonPressed = $('#'+id).find('.editButton').first();

    var doneButton = document.createElement('a');
    doneButton.setAttribute('onclick','editDoneAnswer('+id+')');
    doneButton.setAttribute('href','javascript:void(0);');
    doneButton.className = 'doneButton';
    doneButton.appendChild(document.createTextNode(' done '));
    buttonPressed.replaceWith(doneButton);
}
function editDoneAnswer(id){
    var answerNewTextArea = $('#'+id).find('textarea').first();
    var newAnswerTextFromTextArea = answerNewTextArea.val();
    /*
    var newAnswerObject ={
        "ID":id,
        "text": newAnswerTextFromTextArea
    };
    $.ajax({
        method: "PUT",
        url: "http://localhost:8080/web-app-project/rest/answer/",
        data: JSON.stringify(newAnswerObject),
        contentType: "application/json",
        dataType: 'json',
        success: function() {
            //alert("it works!);
        },
        error: function(){
            //alert("error on POST http://localhost:8080/web-app-project/rest/answer/ "+this.data);
        }
    });*/
    answerNewTextArea.replaceWith($('<p>'+newAnswerTextFromTextArea+'</p>'));
    $('#'+id).find('.answer-body').find('br').first().replaceWith('');
    var doneButton = $('#'+id).find('.doneButton').first();

    var editLink = document.createElement('a');
    editLink.setAttribute('onclick','editAnswer('+id+')');
    editLink.setAttribute('href','javascript:void(0);');
    editLink.className = 'editButton';
    editLink.appendChild(document.createTextNode(' edit '));
    doneButton.replaceWith(editLink);
}

function setReplyModalTarget(id){
    $('#answerTextAreaModal').attr('data-reply-target',id);
    /*This code is to set up the textarea with the parent text
    var answerParagraph = $('#'+id).find('p').first();
    var answerPreviousText = answerParagraph.text();*/
    $('#answerTextAreaModal').val("");
}
function replyAnswerAjax(){
    //get id setReplyModalTarget
    var parentID= $('#answerTextAreaModal').attr('data-reply-target');
    console.log("parentID:" +parentID);
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
            console.log(data.answer);
            printSingleAnswer(data.answer, parentID);
            $("#addAnswerTextArea").val('');

        },
        error: function(jqXHR,textStatus,errorThrown){
            alert("" +
                " |jqXHR:"+jqXHR+
                " |textStatus: "+textStatus+
                " |errorThrown:"+errorThrown);
        }
    });
}