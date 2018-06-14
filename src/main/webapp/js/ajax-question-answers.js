/*Ajax question and related answer view
Author: Giovanni Candeo
 */
var loggedInUser = document.getElementById("loggedInUser");
var currentUser = loggedInUser.getAttribute("data-loggedInUser");

var httpRequest;
var url;

var isFirstPassage = true;

/*TEMPORARY TEST URL*/

var myQuestionRestUrl='http://localhost:8080/web-app-project/rest/question/id/1';
var myAnswersRestUrl='http://localhost:8080/web-app-project/rest/answer/id/1';

/*At start do this*/
window.onload = initialPageLoad;


function initialPageLoad(){

    loadDoc(myQuestionRestUrl, loadQuestion);
    loadDoc(myAnswersRestUrl, loadAnswers);
}
/* USER MANAGEMENT */


function loadDoc(url,cFunction){

    httpRequest = new XMLHttpRequest();
    if (!httpRequest) {
        alert('Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            cFunction(this);
        }
    };
    httpRequest.open('GET', url, true);
    httpRequest.send();
}

/* Loads the question via ajax */
function loadQuestion(httpRequest){
    var restResponse = httpRequest.responseText;
    var jsonData = JSON.parse(restResponse);
    var resourceList = jsonData['resource-list'];
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
}



/*Loads answers via ajax*/
function loadAnswers(httpRequest){
    var restResponse = httpRequest.responseText;
    var jsonData = JSON.parse(restResponse);
    var resourceList = jsonData['resource-list'];
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
            printSingleAnswer(resourceList[i].answer, 0);
    }
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

    //create custom function call for each answer TODO better
    var deleteLink = document.createElement('a');
    deleteLink.setAttribute('onclick','deleteAnswer('+answer['ID']+')');
    deleteLink.setAttribute('href','javascript:void(0);');

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
    small.appendChild(deleteLink);
    deleteLink.appendChild(document.createTextNode('delete'));

    loadAnswersToAnswer(answer['ID']);
    //this calls answerDropDown(idAnswer) that calls printSingleAnswer for each answer recursively(answer, idAnswer)

}

function loadAnswersToAnswer(idAnswer){
    url = 'http://localhost:8080/web-app-project/rest/answer/idAnswer/'+idAnswer;
    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Giving up :( Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = answerDropdown(idAnswer);
    httpRequest.open('GET', url);
    httpRequest.send();
}
function answerDropdown(idAnswer){

    if (httpRequest.readyState === XMLHttpRequest.DONE) {

        if (httpRequest.status === 200) {


            var div = document.getElementById('listCategoryDropdown');

            document.getElementById('listCategoryDropdown').innerHTML = "";

            var jsonData = JSON.parse(httpRequest.responseText);
            var resourceList = jsonData['resource-list'];

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

        } else {
            alert('There was a problem with the request.');
        }
    }

}

/*Add a new answer function*/
function addNewAnswerForm(){
    var AddAnswerForm = document.getElementById("AddAnswerForm");
    //var addAnswerTextArea = document.getElementById("addAnswerTextArea").value;
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

    $.post("http://localhost:8080/web-app-project/rest/answer/",
        JSON.stringify({"resource-list":[
                {
                    "answer":{
                        "ID":1,
                        "text": addAnswerText,
                        "fixed":false,
                        "timestamp": timestamp,
                        "IDUser": currentUser,
                        "parentID":-1,
                        "questionID":1
                    }
                }]
        }),
        function(data,status){ //why this doesnt work
            alert("Data: " + data + "\nStatus: " + status);
            $("#addAnswerTextArea").val('');
        }
    );
}

function deleteAnswer(id){
    alert(id);
}



