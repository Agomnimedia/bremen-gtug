"use strict";

var communication = function() {
};

communication.getAllItems = function() {
	window.setTimeout(function() {
		allItemsReceived({
			events : [ {
				key : "1",
				name : "Hackathon"
			}, {
				key : "2",
				name : "Stammtisch Januar"
			} ]
		});
	}, 1000);
};

communication.lastUpdate = function(kind) {
	window.setTimeout(function() {
		lastUpdateReceived({
			update : {
				kind : 1,
				timestamp : (new Date()).getTime(),
				additionalinfo : "2"
			}
		});
	}, 1000);
};

communication.signInEvent = function(key) {
	window.setTimeout(function() {
		signInEventReceived();
	}, 1000);
};

communication.signOffEvent = function(key) {
	window.setTimeout(function() {
		signOffEventReceived();
	}, 1000);
};

communication.getMyEvents = function(key) {
	window.setTimeout(function() {
		myEventsReceived({
			events : [ {
				key : "2",
				name : "Stammtisch Januar"
			} ]
		});
	}, 1000);
};