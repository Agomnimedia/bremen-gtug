// 

"use strict";

// channel aufbauen und phase 0 initialisieren
(function () {

	var privates = {}
	  , phase = null
	  , phaseNumber = 0
	  , privates = {};
	
	$(window).resize(function() {
		privates.resize();
	});
	
	privates.resize = function () {
		if(phase != null){
			phase.resize();
		}
	};
	
	$.ajax({
		url: "/token",
		dataType: 'json',
		type: 'POST',
		success: function(data, textStatus, jqXHR){
			var json = JSON.parse(jqXHR.responseText);
			var channel = new goog.appengine.Channel(json.RESULT.token);
		    var socket = channel.open();
		    socket.onopen = privates.onOpened;
		    socket.onmessage = privates.onMessage;
		    socket.onerror = privates.onError;
		    socket.onclose = privates.onClose;
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("Ein Fehler ist bei der Erstellung des Channels aufgetreten! Bitte aktualisieren (F5) sie das Fenster.")
		}
	});
	
	
	
    privates.onOpened = function () {
    	console.log("onOpened");
    	phase = new Phase(phaseNumber);
    	phase.refreshContent();
    	// TODO get data and give it instead of null
    	phase.start(null);
    	privates.resize();
	};
	
	privates.onMessage = function (message) {
		console.log("onMessage");
		console.log(message);
		// TODO get phase
		// check new phase with old phase
		phase.refreshContent(jsonData);
	};
	
	privates.onError = function (error) {
		console.log("onError");
		console.log(error);
		// TODO
	};
	
	privates.onClose = function () {
		console.log("onClose");
		// Set this property to a function that called when
		// the socket is closed. When the socket is closed,
		// it cannot be reopened. Use the open() method on a
		// goog.appengine.Channel object to create a new socket.
	};
}());