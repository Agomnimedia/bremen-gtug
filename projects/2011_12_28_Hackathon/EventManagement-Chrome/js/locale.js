"use strict";

function loadLocales2Html() {
	var imgs = document.querySelectorAll('img');
	for ( var i = 0, img; img = imgs[i]; i++) {
		img.alt = replaceMSG(img.alt);
		img.title = replaceMSG(img.title);
	}
}

function replaceMSG(text) {
	var res = text.match(/^__MSG_([^_]*)__$/);
	if (res.length == 2) {
		text = chrome.i18n.getMessage(res[1]);
	}
	return text;
}