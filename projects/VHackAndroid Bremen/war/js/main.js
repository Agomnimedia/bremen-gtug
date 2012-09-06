// 

"use strict";

// channel aufbauen und phase 0 initialisieren
(function () {

	var privates = {}
	  , phase = new Phase(0)
	  , privates = {};
	
	$(window).resize(function() {
		privates.resize();
	});
	
	privates.resize = function () {
		if($(window).width()<1280 || $(window).height()<780){
			if($("#neusta").attr("src").indexOf("small") === -1){
				$("#neusta").attr("src","./images/neusta_logo_small.jpg");
				$("#enough").attr("src","./images/ES_logo_black_small.jpg");
			}
		}else{
			if($("#neusta").attr("src").indexOf("small") !== -1){
				$("#neusta").attr("src","./images/neusta_logo.jpg");
				$("#enough").attr("src","./images/ES_logo_black.jpg");
			}
		}
	};
	
	privates.resize();
	
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
    	// TODO
	};
	
	privates.onMessage = function (message) {
		console.log("onMessage");
		console.log(message);
		// TODO get phase
		// check new phase with old phase
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