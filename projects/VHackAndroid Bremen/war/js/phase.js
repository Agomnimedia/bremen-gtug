var Phase = function(number, json) {
	"use strict";
	
	var privates = {}
	  , content = document.querySelector('.content')
	  , that = this;
	
	//that.refreshContent(json);
	
	privates.phase0 = function (jsonData) {
		// this is a static phase
		// add slide sponsor
	};
	
	privates.phase1 = function (jsonData) {
		// add slide welcome
		// add slide sponsor
	};
	
	privates.phase2 = function (jsonData) {
		// add slide ???
		// add slide sponsor
	};
	
	this.refreshContent = function (jsonData) {
		switch(number){
		case 0:
			privates.phase0(jsonData);
			break;
		case 1:
			privates.phase1(jsonData);
			break;
		case 2:
			privates.phase2(jsonData);
			break;
			default:
				console.log("Unknown phase");
		}
	};
}