"use strict";

(function() {
	var privates = {}, phase = null, privates = {}, phaseNumber = 0;

	$(window).resize(function() {
		privates.resize();
	});

	privates.resize = function() {
		if (phase != null) {
			phase.resize();
		}
	};

	phase = new Phase(0);
	phase.refreshContent();
	phase.start(null);
	privates.resize();

	$('#logo').bind('click', function() {
		if (phase != null) {
			phase.stop();
		}
		if (phaseNumber >= 4) {
			phaseNumber = 0;
		} else {
			phaseNumber++;
		}
		privates.logAndAlert(phaseNumber);
		phase = new Phase(phaseNumber);
		phase.refreshContent();
		phase.start(null);
		privates.resize();
	});
	
	privates.logAndAlert = function(number){
		console.log("phase: " + number);
		switch(number){
		case 0: 
			alert("Phase: Vor und nach dem Event");
			break;
		case 1: 
			alert("Phase: Zur Begrüßung");
			break;
		case 2: 
			alert("Phase: Nach Begrüßung");
			break;
		case 3: 
			alert("Phase: Zu der Beurteilung");
			break;
		case 4: 
			alert("Phase: Zum Abschluß");
			break;
			default:
				console.log("Unknown phase");
		}
	}

}());