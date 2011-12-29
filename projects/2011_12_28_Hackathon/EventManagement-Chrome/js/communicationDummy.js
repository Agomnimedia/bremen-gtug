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

communication.getMyEvents = function() {
	window.setTimeout(function() {
		myEventsReceived({
			events : [ {
				key : "2",
				name : "Stammtisch Januar"
			} ]
		});
	}, 1000);
};

communication.getEventDetails = function(key) {
	if (key == "1") {
		window
				.setTimeout(
						function() {
							eventDetailsReceived({
								event : {
									key : "1",
									name : "Hackathon",
									location : "Büros des team Neusta, Contrescarpe 1, 28203 Bremen",
									startDate : 1325059200,
									endDate : 1325178000,
									url : "http://bremen.gtugs.org/events/ersterhackathon",
									attendies : [ {
										key : "a",
										nickname : "Matze"
									}, {
										key : "b",
										nickname : "Steve"
									}, {
										key : "c",
										nickname : "Jan"
									} ],
									admin : {
										key : "a",
										nickname : "Matze"
									},
									talks : [],
									maxAttendees : 15
								}
							});
						}, 1000);
	}
	if (key == "2") {
		window
				.setTimeout(
						function() {
							eventDetailsReceived({
								event : {
									key : "2",
									name : "Stammtisch Januar",
									location : "Best Western Hotel zur Post (Bahnhofsplatz 11, 28195 Bremen)",
									startDate : 1326132000,
									endDate : 1326146400,
									url : "http://bremen.gtugs.org/events/stammtischjanuar2012",
									attendies : [],
									admin : {
										key : "b",
										nickname : "Steve"
									},
									talks : [ {
										key : "x",
										speaker : {
											key : "c",
											nickname : "Jan"
										},
										name : "HTML5"
									}, {
										key : "y",
										speaker : null,
										name : "Ignite Session"
									} ],
									maxAttendees : 0
								}
							});
						}, 1000);
	}
};