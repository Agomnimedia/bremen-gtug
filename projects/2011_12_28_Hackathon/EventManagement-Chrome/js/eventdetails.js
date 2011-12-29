"use strict";

window.onload = function() {
	loadLocales2Html();
	alert(HTTP_GET['key']);
	communication.getEventDetails(HTTP_GET['key']);
};

function eventDetailsReceived(data) {
	window.location = data.event.url;
}