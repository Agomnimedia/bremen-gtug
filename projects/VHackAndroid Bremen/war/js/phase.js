var Phase = function(number) {
	"use strict";
	
	var privates = {}
	  , content = document.querySelector('.content')
	  , slideArray = new Array()
	  , counter = 0
	  , arrayLength = 0
	  , switcher = null
	  , that = this;
	
	privates.phase0 = function (jsonData) {
		if(slideArray.length===0){
			slideArray.push(new SlideDefault());
			slideArray.push(new SlideGlobalSponsor());
			slideArray.push(new SlideLocalSponsor());
			slideArray.push(new SlidePartners());
		}
	};
	
	privates.phase1 = function (jsonData) {
		if(slideArray.length===0){
			slideArray.push(new SlideWelcome());
			slideArray.push(new SlideGlobalSponsor());
			slideArray.push(new SlideLocalSponsor());
			slideArray.push(new SlidePartners());
		}
	};
	
	privates.phase2 = function (jsonData) {
		// add slide ???
		// add slide sponsor
	};
	
	privates.phase3 = function (jsonData) {
		// add slide ???
		// add slide sponsor
	};
	
	privates.phase4 = function (jsonData) {
		// add slide ???
		// add slide sponsor
	};
	
	this.resize = function(){
		slideArray[counter].resize();
	}
	
	this.start = function(jsonData){
		switch(number){
		case 0: // vor Event und danach
			privates.phase0(jsonData);
			break;
		case 1: // Zur Begrüßung
			privates.phase1(jsonData);
			break;
		case 2: // Nach Begrüßung
			privates.phase2(jsonData);
			break;
		case 3: // Zu der Beurteilung
			privates.phase3(jsonData);
			break;
		case 4: // Zum Abschluß
			privates.phase4(jsonData);
			break;
			default:
				console.log("Unknown phase");
		}
		counter = 0;
		arrayLength = slideArray.length;
		slideArray[counter].showSlide(jsonData);
		if(arrayLength>1){
			counter++;
		}
		switcher = window.setInterval(function () {
			slideArray[counter].showSlide(jsonData);
			slideArray[counter].resize();
			if(counter==arrayLength-1){
				counter = 0;
			}else{
				counter++;
			}
		}, 30000);
	}
	
	this.stop = function(){
		window.clearInterval(switcher);
	}
	
	this.refreshContent = function (jsonData) {
		var arrayLength = slideArray.length;
		for(var i=0; i<arrayLength; i++){
			slideArray[i].refreshContent(jsonData);
		}
	};
}