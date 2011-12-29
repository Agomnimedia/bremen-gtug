"use strict";

window.onload = function() {
	loadLocales2Html();
	communication.getEventDetails(HTTP_GET['key']);
};

function eventDetailsReceived(data) {
	window.location = data.event.url;
}