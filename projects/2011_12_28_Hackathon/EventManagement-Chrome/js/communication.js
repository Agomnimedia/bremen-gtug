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

communication.lastUpdate = function() {
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