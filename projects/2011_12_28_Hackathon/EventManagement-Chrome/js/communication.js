"use strict";

var communication = function() {
};
communication.BASEURL = "https://bremengtugeventreg.appspot.com";

communication.getAllItems = function() {
	this.processJsonGetHttpRequest("", "getallevents", "allEventsReceived");
};

communication.lastUpdate = function(kind) {
	this.processJsonGetHttpRequest("kind=" + kind, "lastupdate",
			"lastUpdateReceived");
};

communication.signInEvent = function(key) {
	this.processJsonGetHttpRequest("key=" + key, "signinevent",
			"signInEventReceived");
};

communication.signOffEvent = function(key) {
	this.processJsonGetHttpRequest("key=" + key, "signoffevent",
			"signOffEventReceived");
};

communication.getMyEvents = function() {
	this.processJsonGetHttpRequest("", "getmyevents", "myEventsReceived");
};

communication.getEventDetails = function(key) {
	this.processJsonGetHttpRequest("key=" + key, "geteventdetails",
			"eventDetailsReceived");
};

communication.processJsonGetHttpRequest = function(data, servlet,
		receiveHandler) {
	var xmlObj = new XMLHttpRequest();
	if (data != "") {
		data = "?" + data;
	}
	if (typeof (receiveHandler) != "undefined") {
		xmlObj.onreadystatechange = function() {
			//alert(xmlObj.readyState+"\n"+xmlObj.status);
			if (xmlObj.readyState == 4) {
				if (xmlObj.status == 200) {
					var json = JSON.parse(xmlObj.responseText);
					if (json.ERROR) {
						alert("Server ErrorMessage: " + json.error);
						window[receiveHandler](false);
					} else {
						window[receiveHandler](json);
					}
				}
				else {
					alert("HTTP status code: " + xmlObj.status);
					window[receiveHandler](false);
				}
			}
		};
	}
	//alert(this.BASEURL + '/' + servlet + data);
	xmlObj.open('GET', this.BASEURL + '/' + servlet + data, true);
	xmlObj.send(null);
}

communication.processJsonPostHttpRequest = function(data, key, servlet,
		receiveHandler) {
	var xmlObj = new XMLHttpRequest();
	data = JSON.stringify(data);
	data = key + "=" + encodeURIComponent(data);
	if (typeof (receiveHandler) != "undefined") {
		xmlObj.onreadystatechange = function() {
			alert(xmlObj.readyState+"\n"+xmlObj.status);
			if (xmlObj.readyState == 4) {
				if (xmlObj.status == 200) {
					var json = JSON.parse(xmlObj.responseText);
					if (json.ERROR) {
						alert("Server ErrorMessage: " + json.error);
						window[receiveHandler](false);
					}
					else {
						window[receiveHandler](json);
					}
				}
				else {
					alert("HTTP status code: " + xmlObj.status);
					window[receiveHandler](false);
				}
			}
		};
	}
	xmlObj.open('POST', this.BASEURL + '/' + servlet, true);
	xmlObj.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded", "charset=UTF-8");
	xmlObj.setRequestHeader("Content-length", data.length);
	xmlObj.setRequestHeader("Connection", "close");
	xmlObj.send(data);
}