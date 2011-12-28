"use strict";

var STORAGE_LASTUPDATE = "lastUpdate";
var STORAGE_EVENTCOUNT = "eventCount";

var lastUpdate = 0;
var eventCount = 0;

function update() {
	communication.lastUpdate();
}

function lastUpdateReceived(data) {
	if(data.update.timestamp > lastUpdate) {
		lastUpdate = data.update.timestamp;
		eventCount = data.update.additionalinfo;
		chrome.browserAction.setBadgeText({
			text : data.update.additionalinfo
		});
	}
}

function loadLocalStorage() {
	lastUpdate = localStorage.getItem(STORAGE_LASTUPDATE);
	if(!lastUpdate) {
		lastUpdate = 0;
	}
	eventCount = localStorage.getItem(STORAGE_EVENTCOUNT);
	if(!eventCount) {
		eventCount = 0;
	}
}

function updateLocalStorage() {
	localStorage.setItem(STORAGE_LASTUPDATE, lastUpdate);
	localStorage.setItem(STORAGE_EVENTCOUNT, eventCount);
}

window.onload = function() {
	loadLocalStorage();
	update();
	window.setInterval("update()", 60*1000); //check for news every minute
};