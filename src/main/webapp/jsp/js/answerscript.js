/*
JavaScript for Answer Management
Author: Giovanni Candeo
Version: 1.00

List is used to display the answers, not optimal. TODO: use cards or custom divs
*/
var answerList = {
	answers: [],
	addAnswer: function(newText){
		this.answers.push({
			answerText: newText,
			voteUp: false,
			voteDown: false,
			hide: false
		});
	},
	changeAnswer: function(id, newText){
		this.answers[id].answerText = newText;
	},
	deleteAnswer: function(id){
		this.answers.splice(id, 1);
	},
	toggleVoteUp: function(id){
		var answer= this.answers[id];
		if(answer.voteDown === false){
			answer.voteUp = !answer.voteUp;	
		}else{
			answer.voteDown = false;
			answer.voteUp = true;
		}
	},
	toggleVoteDown: function(id){
		var answer= this.answers[id];
		if(answer.voteUp === false){
			answer.voteDown = !answer.voteDown;	
		}else{
			answer.voteUp = false;
			answer.voteDown = true;
		}
	},
	toggleHide: function(id){
		var answer= this.answers[id];
		answer.hide = !answer.hide;	
	},
	toggleHideAll: function(){
		var totalAnswers = this.answers.length;
		var hiddenAnswers = 0;
		//get number of hidden answers
		this.answers.forEach(function(answer){
			if(answer.hide === true){
				hiddenAnswers++;
			}
		});
		//Case 1: if everything is hidden, show everything.
		//Case 2: otherwise, hide everything.
		this.answers.forEach(function(answer){
			if(hiddenAnswers === totalAnswers){
				answer.hide = false;
			}else{
				answer.hide = true;
			}
		});
	}
}

var handlers = {
	/*
	displayAnswers: function(){
		answerList.displayAnswers();
	},*/
	addAnswer: function(){
		var addAnswerTextInput = document.getElementById('addAnswerTextInput');
		answerList.addAnswer(addAnswerTextInput.value);
		addAnswerTextInput.value = "";
		view.displayAnswers();
	},
	changeAnswer: function(position){
		//var changeAnswerPositionInput = document.getElementById('changeAnswerPositionInput');
		var changeAnswerTextInput = document.getElementById('changeAnswerTextInput');
		answerList.changeAnswer(position, changeAnswerTextInput.value);
		//changeAnswerPositionInput.value = '';
		changeAnswerTextInput.value = '';
		view.displayAnswers();
	},
	deleteAnswer: function(position){
		answerList.deleteAnswer(position);
		view.displayAnswers();
	},
	toggleVoteUp: function(position){
		answerList.toggleVoteUp(position);
		view.displayAnswers();
	},
	toggleVoteDown: function(position){
		answerList.toggleVoteDown(position);
		view.displayAnswers();
	},
	toggleHide: function(position){
		answerList.toggleHide(position);
		view.displayAnswers();
	},
	toggleHideAll: function(){
		answerList.toggleHideAll();
		view.displayAnswers();
	}
};

var view = {
	displayAnswers: function(){
		var answerUl = document.querySelector('ul');
		answerUl.innerHTML = '';

		/*"this" refers to the view object. forEach(callback, this)*/
		answerList.answers.forEach(function(answer, position){
			var answerLi = document.createElement('li');
			var answerTextWithVote = '';
			answerLi.id = position;
			var answerLiClassName ='list-group-item';

			if(answer.hide === true){
				//do nothing
			}else{
				//show stuff
				if(answer.voteUp === true){
					answerTextWithVote = '(^)'+answer.answerText;
					answerLiClassName += ' list-group-item-success';
				}else if(answer.voteDown === true){
					answerTextWithVote = '(v)'+answer.answerText;
					answerLiClassName += ' list-group-item-danger';
				}else{
					answerTextWithVote = '(-)'+answer.answerText;
				}
				answerLi.textContent = answerTextWithVote;
				answerLi.appendChild(document.createElement("hr"));
				answerLi.appendChild(this.createVoteUpButton());
				answerLi.appendChild(this.createVoteDownButton());
				answerLi.appendChild(this.createChangeButton());
				answerLi.appendChild(this.createDeleteButton());
			}

			answerLi.className = answerLiClassName;
			answerLi.appendChild(this.createHideButton(answer.hide));
			answerUl.appendChild(answerLi);			
		}, this);

	},
	createChangeButton: function(){
		var changeButton = document.createElement('button');
		changeButton.textContent = 'Change';
		changeButton.className = 'changeButton';
		changeButton.className += ' btn';
		return changeButton;
	},
	createDeleteButton: function(){
		var deleteButton = document.createElement('button');
		deleteButton.textContent = 'Delete';
		deleteButton.className = 'deleteButton';
		deleteButton.className += ' btn';
		return deleteButton;
	},
	createVoteUpButton: function(){
		var voteUpButton = document.createElement('button');
		voteUpButton.textContent = 'Up';
		voteUpButton.className ='voteUpButton';
		voteUpButton.className += ' btn';
		return voteUpButton;
	},
	createVoteDownButton: function(){
		var voteDownButton = document.createElement('button');
		voteDownButton.textContent = 'Down';
		voteDownButton.className ='voteDownButton';
		voteDownButton.className += ' btn';
		return voteDownButton;
	},
	createHideButton: function(hidden){
		var hideButton = document.createElement('button');
		hideButton.className ='hideButton';
		hideButton.className += ' btn';
		if(hidden === true){
			hideButton.textContent = 'Show';
		}else{
			hideButton.textContent = 'Hide';
		}
		return hideButton;
	},
	setUpEventListeners: function(){
		var answerUl = document.querySelector('ul');

		//js event delegation 
		answerUl.addEventListener('click',function(event){
			//Get the element that was clicked on
			var elementClicked = event.target;

			//Check if element clicked is edit button.
			if(elementClicked.className === 'changeButton btn'){
				handlers.changeAnswer(parseInt(elementClicked.parentNode.id));
			}
			//Check if element clicked is a delete button.
			if(elementClicked.className === 'deleteButton btn'){
				handlers.deleteAnswer(parseInt(elementClicked.parentNode.id));
			}
			//Check if element clicked is a vote up button.
			if(elementClicked.className === 'voteUpButton btn'){
				handlers.toggleVoteUp(parseInt(elementClicked.parentNode.id));
			}	
			//Check if element clicked is a vote up button.
			if(elementClicked.className === 'voteDownButton btn'){
				handlers.toggleVoteDown(parseInt(elementClicked.parentNode.id));
			}	
			//Check if the element clicked is the hide button.
			if(elementClicked.className === 'hideButton btn'){
				handlers.toggleHide(parseInt(elementClicked.parentNode.id));
			}
		});
	}
};

view.setUpEventListeners();