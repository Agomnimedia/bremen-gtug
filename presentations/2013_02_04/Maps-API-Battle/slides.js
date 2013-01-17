/*
  Google HTML5 slides template

  Authors: Luke Mahé (code)
           Marcin Wichary (code and design)

           Dominic Mazzoni (browser compatibility)
           Charles Chen (ChromeVox support)

  URL: http://code.google.com/p/html5slides/
*/

var PERMANENT_URL_PREFIX = '../';

var SLIDE_CLASSES = ['far-past', 'past', 'current', 'next', 'far-next'];

var PM_TOUCH_SENSITIVITY = 15;

var curSlide;

var slideEls;

/* ---------------------------------------------------------------------- */
/* classList polyfill by Eli Grey 
 * (http://purl.eligrey.com/github/classList.js/blob/master/classList.js) */

if (typeof document !== "undefined" && !("classList" in document.createElement("a"))) {

(function (view) {

var
    classListProp = "classList"
  , protoProp = "prototype"
  , elemCtrProto = (view.HTMLElement || view.Element)[protoProp]
  , objCtr = Object
    strTrim = String[protoProp].trim || function () {
    return this.replace(/^\s+|\s+$/g, "");
  }
  , arrIndexOf = Array[protoProp].indexOf || function (item) {
    for (var i = 0, len = this.length; i < len; i++) {
      if (i in this && this[i] === item) {
        return i;
      }
    }
    return -1;
  }
  // Vendors: please allow content code to instantiate DOMExceptions
  , DOMEx = function (type, message) {
    this.name = type;
    this.code = DOMException[type];
    this.message = message;
  }
  , checkTokenAndGetIndex = function (classList, token) {
    if (token === "") {
      throw new DOMEx(
          "SYNTAX_ERR"
        , "An invalid or illegal string was specified"
      );
    }
    if (/\s/.test(token)) {
      throw new DOMEx(
          "INVALID_CHARACTER_ERR"
        , "String contains an invalid character"
      );
    }
    return arrIndexOf.call(classList, token);
  }
  , ClassList = function (elem) {
    var
        trimmedClasses = strTrim.call(elem.className)
      , classes = trimmedClasses ? trimmedClasses.split(/\s+/) : []
    ;
    for (var i = 0, len = classes.length; i < len; i++) {
      this.push(classes[i]);
    }
    this._updateClassName = function () {
      elem.className = this.toString();
    };
  }
  , classListProto = ClassList[protoProp] = []
  , classListGetter = function () {
    return new ClassList(this);
  }
;
// Most DOMException implementations don't allow calling DOMException's toString()
// on non-DOMExceptions. Error's toString() is sufficient here.
DOMEx[protoProp] = Error[protoProp];
classListProto.item = function (i) {
  return this[i] || null;
};
classListProto.contains = function (token) {
  token += "";
  return checkTokenAndGetIndex(this, token) !== -1;
};
classListProto.add = function (token) {
  token += "";
  if (checkTokenAndGetIndex(this, token) === -1) {
    this.push(token);
    this._updateClassName();
  }
};
classListProto.remove = function (token) {
  token += "";
  var index = checkTokenAndGetIndex(this, token);
  if (index !== -1) {
    this.splice(index, 1);
    this._updateClassName();
  }
};
classListProto.toggle = function (token) {
  token += "";
  if (checkTokenAndGetIndex(this, token) === -1) {
    this.add(token);
  } else {
    this.remove(token);
  }
};
classListProto.toString = function () {
  return this.join(" ");
};

if (objCtr.defineProperty) {
  var classListPropDesc = {
      get: classListGetter
    , enumerable: true
    , configurable: true
  };
  try {
    objCtr.defineProperty(elemCtrProto, classListProp, classListPropDesc);
  } catch (ex) { // IE 8 doesn't support enumerable:true
    if (ex.number === -0x7FF5EC54) {
      classListPropDesc.enumerable = false;
      objCtr.defineProperty(elemCtrProto, classListProp, classListPropDesc);
    }
  }
} else if (objCtr[protoProp].__defineGetter__) {
  elemCtrProto.__defineGetter__(classListProp, classListGetter);
}

}(self));

}
/* ---------------------------------------------------------------------- */

/* Slide movement */

function gotoSlide(id) {
	if (typeof(id) == "string") {
		for (var i = 0; i < slideEls.length; i++) {
			if (slideEls[i].id == id) {
				slideNo = i;
				break;
			}
		}
	}
	else {
		slideNo = id;
	}
	if(slideNo > curSlide) {
		curSlide++;
		updateSlides();
	}
	if(slideNo < curSlide) {
		curSlide--;
		updateSlides();
	}
	if(slideNo != curSlide) {
		window.setTimeout(function() {
			gotoSlide(slideNo);
		}, 400);
	}
}

function getSlideEl(no) {
  if ((no < 0) || (no >= slideEls.length)) { 
    return null;
  } else {
    return slideEls[no];
  }
};

function updateSlideClass(slideNo, className) {
  var el = getSlideEl(slideNo);
  
  if (!el) {
    return;
  }
  
  if (className) {
    el.classList.add(className);
  }
    
  for (var i in SLIDE_CLASSES) {
    if (className != SLIDE_CLASSES[i]) {
      el.classList.remove(SLIDE_CLASSES[i]);
    }
  }
};

function updateSlides() {
  for (var i = 0; i < slideEls.length; i++) {
    switch (i) {
      case curSlide - 2:
        updateSlideClass(i, 'far-past');
        break;
      case curSlide - 1:
        updateSlideClass(i, 'past');
        break;
      case curSlide: 
        updateSlideClass(i, 'current');
        break;
      case curSlide + 1:
        updateSlideClass(i, 'next');      
        break;
      case curSlide + 2:
        updateSlideClass(i, 'far-next');      
        break;
      default:
        updateSlideClass(i);
        break;
    }
  }

  //triggerLeaveEvent(curSlide - 1);
  triggerEnterEvent(curSlide);

  window.setTimeout(function() {
    // Hide after the slide
    disableSlideFrames(curSlide - 2);
  }, 301);

  enableSlideFrames(curSlide - 1);
  enableSlideFrames(curSlide + 2);
  
  if (isChromeVoxActive()) {
    speakAndSyncToNode(slideEls[curSlide]);
  }  

  updateHash();
};

function buildNextItem() {
  var toBuild  = slideEls[curSlide].querySelectorAll('.to-build');

  if (!toBuild.length) {
    return false;
  }

  if(document.getElementById("phonediv")) {
    var phoneOverlaySrc = toBuild[0].getAttribute('data-screen-overlay');
    if(phoneOverlaySrc) {
	  document.getElementById('phoneoverlay').style.display = 'block';
      document.getElementById('phoneoverlay').src = phoneOverlaySrc;
    } else {
      document.getElementById('phoneoverlay').style.display = 'none';
    }
	var nextscreen = toBuild[0].getAttribute('data-switchphonescreen');
	if(nextscreen) {
		nextScreenShot();
	}
  }
  if(document.getElementById("phonediv2")) {
    var phoneOverlaySrc = toBuild[0].getAttribute('data-screen-overlay2');
    if(phoneOverlaySrc) {
	  document.getElementById('phoneoverlay2').style.display = 'block';
      document.getElementById('phoneoverlay2').src = phoneOverlaySrc;
    } else {
      document.getElementById('phoneoverlay2').style.display = 'none';
    }
	var nextscreen = toBuild[0].getAttribute('data-switchphonescreen2');
	if(nextscreen) {
		nextScreenShot2();
	}
  }
  toBuild[0].classList.remove('to-build', '');

  if (isChromeVoxActive()) {
    speakAndSyncToNode(toBuild[0]);
  }

  return true;
};

function prevSlide() {
  if (curSlide > 0) {
    curSlide--;

    updateSlides();
  }
};

function nextSlide() {
  if (buildNextItem()) {
    return;
  }

  if (curSlide < slideEls.length - 1) {
    curSlide++;

    updateSlides();
  }
};

/* Slide events */

function triggerEnterEvent(no) {
  var el = getSlideEl(no);
  if (!el) {
    return;
  }

  var onEnter = el.getAttribute('onslideenter');
  if (onEnter) {
    new Function(onEnter).call(el);
  }

  var evt = document.createEvent('Event');
  evt.slideNumber = no + 1; // Make it readable
  evt.initEvent('slideenter', true, true);

  el.dispatchEvent(evt);
};

function triggerLeaveEvent(no) {
  var el = getSlideEl(no);
  if (!el) {
    return;
  }

  var onLeave = el.getAttribute('onslideleave');
  if (onLeave) {
    new Function(onLeave).call(el);
  }

  var evt = document.createEvent('Event');
  evt.slideNumber = no + 1; // Make it readable
  evt.initEvent('slideleave', true, true);
  
  el.dispatchEvent(evt);
};

/* Touch events */

function handleTouchStart(event) {
  if (event.touches.length == 1) {
    touchDX = 0;
    touchDY = 0;

    touchStartX = event.touches[0].pageX;
    touchStartY = event.touches[0].pageY;

    document.body.addEventListener('touchmove', handleTouchMove, true);
    document.body.addEventListener('touchend', handleTouchEnd, true);
  }
};

function handleTouchMove(event) {
  if (event.touches.length > 1) {
    cancelTouch();
  } else {
    touchDX = event.touches[0].pageX - touchStartX;
    touchDY = event.touches[0].pageY - touchStartY;
  }
};

function handleTouchEnd(event) {
  var dx = Math.abs(touchDX);
  var dy = Math.abs(touchDY);

  if ((dx > PM_TOUCH_SENSITIVITY) && (dy < (dx * 2 / 3))) {
    if (touchDX > 0) {
      prevSlide();
    } else {
      nextSlide();
    }
  }
  
  cancelTouch();
};

function cancelTouch() {
  document.body.removeEventListener('touchmove', handleTouchMove, true);
  document.body.removeEventListener('touchend', handleTouchEnd, true);  
};

/* Preloading frames */

function disableSlideFrames(no) {
  var el = getSlideEl(no);
  if (!el) {
    return;
  }

  var frames = el.getElementsByTagName('iframe');
  for (var i = 0, frame; frame = frames[i]; i++) {
    disableFrame(frame);
  }
};

function enableSlideFrames(no) {
  var el = getSlideEl(no);
  if (!el) {
    return;
  }

  var frames = el.getElementsByTagName('iframe');
  for (var i = 0, frame; frame = frames[i]; i++) {
    enableFrame(frame);
  }
};

function disableFrame(frame) {
  frame.src = 'about:blank';
};

function enableFrame(frame) {
  var src = frame._src;

  if (frame.src != src && src != 'about:blank') {
    frame.src = src;
  }
};

function setupFrames() {
  var frames = document.querySelectorAll('iframe');
  for (var i = 0, frame; frame = frames[i]; i++) {
    frame._src = frame.src;
    disableFrame(frame);
  }
  
  enableSlideFrames(curSlide);
  enableSlideFrames(curSlide + 1);
  enableSlideFrames(curSlide + 2);  
};

function setupInteraction() {
  /* Clicking and tapping */
  
  var el = document.createElement('div');
  el.className = 'slide-area';
  el.id = 'prev-slide-area';  
  el.addEventListener('click', prevSlide, false);
  document.querySelector('section.slides').appendChild(el);

  var el = document.createElement('div');
  el.className = 'slide-area';
  el.id = 'next-slide-area';  
  el.addEventListener('click', nextSlide, false);
  document.querySelector('section.slides').appendChild(el);  
  
  /* Swiping */
  
  document.body.addEventListener('touchstart', handleTouchStart, false);
}

/* ChromeVox support */

function isChromeVoxActive() {
  if (typeof(cvox) == 'undefined') {
    return false;
  } else {
    return true;
  }
};

function speakAndSyncToNode(node) {
  if (!isChromeVoxActive()) {
    return;
  }
  
  cvox.ChromeVox.navigationManager.switchToStrategy(
      cvox.ChromeVoxNavigationManager.STRATEGIES.LINEARDOM, 0, true);  
  cvox.ChromeVox.navigationManager.syncToNode(node);
  cvox.ChromeVoxUserCommands.finishNavCommand('');
  var target = node;
  while (target.firstChild) {
    target = target.firstChild;
  }
  cvox.ChromeVox.navigationManager.syncToNode(target);
};

function speakNextItem() {
  if (!isChromeVoxActive()) {
    return;
  }
  
  cvox.ChromeVox.navigationManager.switchToStrategy(
      cvox.ChromeVoxNavigationManager.STRATEGIES.LINEARDOM, 0, true);
  cvox.ChromeVox.navigationManager.next(true);
  if (!cvox.DomUtil.isDescendantOfNode(
      cvox.ChromeVox.navigationManager.getCurrentNode(), slideEls[curSlide])){
    var target = slideEls[curSlide];
    while (target.firstChild) {
      target = target.firstChild;
    }
    cvox.ChromeVox.navigationManager.syncToNode(target);
    cvox.ChromeVox.navigationManager.next(true);
  }
  cvox.ChromeVoxUserCommands.finishNavCommand('');
};

function speakPrevItem() {
  if (!isChromeVoxActive()) {
    return;
  }
  
  cvox.ChromeVox.navigationManager.switchToStrategy(
      cvox.ChromeVoxNavigationManager.STRATEGIES.LINEARDOM, 0, true);
  cvox.ChromeVox.navigationManager.previous(true);
  if (!cvox.DomUtil.isDescendantOfNode(
      cvox.ChromeVox.navigationManager.getCurrentNode(), slideEls[curSlide])){
    var target = slideEls[curSlide];
    while (target.lastChild){
      target = target.lastChild;
    }
    cvox.ChromeVox.navigationManager.syncToNode(target);
    cvox.ChromeVox.navigationManager.previous(true);
  }
  cvox.ChromeVoxUserCommands.finishNavCommand('');
};

/* Hash functions */

function getCurSlideFromHash() {
  var slideNo = parseInt(location.hash.substr(1));

  if (slideNo) {
    curSlide = slideNo - 1;
  } else {
    curSlide = 0;
  }
};

function updateHash() {
  location.replace('#' + (curSlide + 1));
};

/* Event listeners */

function handleBodyKeyDown(event) {
  switch (event.keyCode) {
    case 39: // right arrow
    case 13: // Enter
    case 32: // space
    case 34: // PgDn
      nextSlide();
      event.preventDefault();
      break;

    case 37: // left arrow
    case 8: // Backspace
    case 33: // PgUp
      prevSlide();
      event.preventDefault();
      break;

    case 40: // down arrow
      if (isChromeVoxActive()) {
        speakNextItem();
      } else {
        nextSlide();
      }
      event.preventDefault();
      break;

    case 38: // up arrow
      if (isChromeVoxActive()) {
        speakPrevItem();
      } else {
        prevSlide();
      }
      event.preventDefault();
      break;
  }
};

function replaceFields(html, slideNo, h2) {
	html = html.replace("{#}", slideNo);
	html = html.replace("{h2}", h2);
	if(h2.length == 0) {
		html = html.replace(/{if_h2}([^{]*){\/if_h2}/, "");
	}
	else {
		html = html.replace(/{if_h2}([^{]*){\/if_h2}/, "$1");
	}
	return html;
}

function addSliteFooters() {
	if (document.getElementById("footerLeft") || document.getElementById("footerRight")) {
		var slides = document.querySelectorAll('section.slides > article:not(.biglogo):not(.fill)');
		var last_h2 = "";
		for (var i = 0; i < slides.length; i++) {
			var h2 = slides[i].querySelectorAll('h2');
			if(h2.length != 0) {
				last_h2 = h2[0].innerHTML;
			}
			if (document.getElementById("footerLeft")) {
				var fLeft = document.getElementById("footerLeft").cloneNode(true);
				fLeft.innerHTML = replaceFields(fLeft.innerHTML, i + 1, last_h2);
				fLeft.id = null;
				slides[i].appendChild(fLeft);
			}
			if (document.getElementById("footerRight")) {
				var fRight = document.getElementById("footerRight").cloneNode(true);
				fRight.innerHTML = replaceFields(fRight.innerHTML, i + 1, last_h2);
				fRight.id = null;
				slides[i].appendChild(fRight);
			}
		}
	}
}

function addEventListeners() {
  document.addEventListener('keydown', handleBodyKeyDown, false);  
};

/* Initialization */

function addPrettify() {
  var els = document.querySelectorAll('pre');
  for (var i = 0, el; el = els[i]; i++) {
    if (!el.classList.contains('noprettyprint')) {
      el.classList.add('prettyprint');
    }
  }
  
  var el = document.createElement('script');
  el.type = 'text/javascript';
  el.src = PERMANENT_URL_PREFIX + 'prettify.js';
  el.onload = function() {
    prettyPrint();
  }
  document.body.appendChild(el);
};

function addFontStyle() {
  var el = document.createElement('link');
  el.rel = 'stylesheet';
  el.type = 'text/css';
  el.href = 'http://fonts.googleapis.com/css?family=' +
            'Open+Sans:regular,semibold,italic,italicsemibold|Droid+Sans+Mono';

  document.body.appendChild(el);
};

function addGeneralStyle() {
  var el = document.createElement('link');
  el.rel = 'stylesheet';
  el.type = 'text/css';
  el.href = PERMANENT_URL_PREFIX + 'styles.css';
  document.body.appendChild(el);
  
  var el = document.createElement('meta');
  el.name = 'viewport';
  el.content = 'width=1100,height=750';
  document.querySelector('head').appendChild(el);
  
  var el = document.createElement('meta');
  el.name = 'apple-mobile-web-app-capable';
  el.content = 'yes';
  document.querySelector('head').appendChild(el);
};

function makeBuildLists() {
  for (var i = curSlide, slide; slide = slideEls[i]; i++) {
    var items = slide.querySelectorAll('.build > *');
    for (var j = 0, item; item = items[j]; j++) {
      if (item.classList) {
        item.classList.add('to-build');
      }
    }
  }
};

function handleDomLoaded() {
  slideEls = document.querySelectorAll('section.slides > article');

  setupFrames();
  
  initPhoneFrame();

  addSliteFooters();
  addFontStyle();
  addGeneralStyle();
  addPrettify();
  addEventListeners();

  updateSlides();

  setupInteraction();
  makeBuildLists();

  document.body.classList.add('loaded');
};

function initialize() {
  getCurSlideFromHash();

  if (window['_DEBUG']) {
    PERMANENT_URL_PREFIX = '../';
  }

  if (window['_DCL']) {
    handleDomLoaded();
  } else {
    document.addEventListener('DOMContentLoaded', handleDomLoaded, false);
  }
}

// If ?debug exists then load the script relative instead of absolute
if (!window['_DEBUG'] && document.location.href.indexOf('?debug') !== -1) {
  document.addEventListener('DOMContentLoaded', function() {
    // Avoid missing the DomContentLoaded event
    window['_DCL'] = true
  }, false);

  window['_DEBUG'] = true;
  var script = document.createElement('script');
  script.type = 'text/javascript';
  script.src = '../slides.js';
  var s = document.getElementsByTagName('script')[0];
  s.parentNode.insertBefore(script, s);

  // Remove this script
  s.parentNode.removeChild(s);
} else {
  initialize();
}

//Functions to show and rotate phone-screen via WebKey
var defaultIpAddress = "192.168.43.1";
var defaultIpAddress2 = "192.168.43.2";
var phoneUser = "presentation";
var phonePass = "presentation";
var ipaddr;
var ipaddr2;
var curScreenshotNo = 0;
var curScreenshotNo2 = 0;
var imgParams = "vlnnnn";
var phoneUrl = "";
var phoneUrl2 = "";
var currScreenshotNo = 0;
var currScreenshotNo2 = 0;

function startPhoneConnect() {
	ipaddr = prompt("Bitte IP-Adresse des Android Geräts eingeben, auf dem WebKey ausgeführt wird:", defaultIpAddress);
	if(typeof ipaddr === "string" && ipaddr.length >= 7) {
		phoneConnect();
	}
}

function startPhoneConnect2() {
	ipaddr2 = prompt("Bitte IP-Adresse des 2. Android Geräts eingeben, auf dem WebKey ausgeführt wird:", defaultIpAddress2);
	if(typeof ipaddr2 === "string" && ipaddr2.length >= 7) {
		phoneConnect2();
	}
}

function phoneConnect() {
	phoneUrl = "http://" + phoneUser + ":" + phonePass + "@" + ipaddr + "/screenshot.jpg?" + imgParams;
	localStorage.setItem('phoneConnected', phoneUrl);
	
	document.getElementById("phonediv").onclick = phoneDisconnect;
	document.getElementById("phone").onload = function(){
		window.setTimeout(phoneRefresh, 10);
	}
	phoneRefresh();
}

function phoneConnect2() {	
	phoneUrl2 = "http://" + phoneUser + ":" + phonePass + "@" + ipaddr2 + "/screenshot.jpg?" + imgParams;
	localStorage.setItem('phoneConnected2', phoneUrl2);
	
	document.getElementById("phonediv2").onclick = phoneDisconnect2;
	document.getElementById("phone2").onload = function(){
		window.setTimeout(phoneRefresh2, 10);
	}
	phoneRefresh2();
}

function phoneRefresh() {
	document.getElementById("phone").setAttribute("src", phoneUrl + (new Date()).getTime());
}

function phoneRefresh2() {
	document.getElementById("phone2").setAttribute("src", phoneUrl2 + (new Date()).getTime());
}

function phoneDisconnect() {
	localStorage.removeItem('phoneConnected');
	phoneUrl = "";
	document.getElementById("phone").onload = null;
	document.getElementById("phone").setAttribute("src", "images/black.png");
	document.getElementById("phonediv").onclick = startPhoneConnect;
}

function phoneDisconnect2() {
	localStorage.removeItem('phoneConnected2');
	phoneUrl2 = "";
	document.getElementById("phone2").onload = null;
	document.getElementById("phone2").setAttribute("src", "images/black.png");
	document.getElementById("phonediv2").onclick = startPhoneConnect2;
}

function nextScreenShot(e) {
	if(phoneUrl == "") {
		currScreenshotNo++;
		var str = "" + currScreenshotNo;
		while(str.length < 3) {
			str = "0" + str;
		}
		document.getElementById("phone").setAttribute("src", "screenshots/screen_" + str + ".png");
	}
	if(e) {
		e.cancelBubble = true;
		e.returnValue = false;
		if (e.stopPropagation) {
			e.stopPropagation();
			e.preventDefault();
		}
	}
	return false;
}

function nextScreenShot2(e) {
	if(phoneUrl2 == "") {
		currScreenshotNo2++;
		var str = "" + currScreenshotNo2;
		while(str.length < 3) {
			str = "0" + str;
		}
		document.getElementById("phone2").setAttribute("src", "screenshots2/screen_" + str + ".png");
	}
	if(e) {
		e.cancelBubble = true;
		e.returnValue = false;
		if (e.stopPropagation) {
			e.stopPropagation();
			e.preventDefault();
		}
	}
	return false;
}

function initPhoneFrame() {
	var startScreen, url, phonescreennext;
	var phonediv = document.getElementById("phonediv");
	if(phonediv) {
		document.body.addEventListener('slideenter', function() {
			document.getElementById('phoneoverlay').style.display = 'none';
			phonediv.classList.remove("visible");
			phonediv.classList.remove("visible_biglogo");
			phonediv.classList.remove("visible_byside");
			phonediv.classList.remove("visible_sidebyside");
			var newclass = "";
			if(slideEls[curSlide].classList.contains("biglogo")) {
				newclass="visible_biglogo";
			} else if(slideEls[curSlide].classList.contains("onlyphone") && !slideEls[curSlide].classList.contains("secondphone")) {
				newclass="visible";
			} else if(slideEls[curSlide].classList.contains("phonebyside") && !slideEls[curSlide].classList.contains("secondphone")) {
				newclass="visible_byside";
			} else if(slideEls[curSlide].classList.contains("phonessidebyside")) {
				newclass="visible_sidebyside";
			}
			if(newclass != "") {
				startScreen = slideEls[curSlide].getAttribute('data-startphonescreen');
				if(startScreen) {
					currScreenshotNo = parseInt(startScreen)-1;
					nextScreenShot();
				}
				window.setTimeout(function() {
					phonediv.classList.add(newclass);
				}, 310);
			}
			//slideEls[curSlide].appendChild(phonediv);
		}, true);
		
		url = localStorage.getItem('phoneConnected', false);
		if(!url) {
			phonediv.onclick = startPhoneConnect;
		} else {
			phoneUrl = url;
			phonediv.onclick = phoneDisconnect;
			document.getElementById("phone").onload = function(){
				window.setTimeout(phoneRefresh, 10);
			}
			phoneRefresh();
		}
		phonescreennext = document.getElementById('phonescreennext');
		phonescreennext.onclick = nextScreenShot;
	}
	
	var phonediv2 = document.getElementById("phonediv2");
	if(phonediv2) {
		document.body.addEventListener('slideenter', function() {
			document.getElementById('phoneoverlay2').style.display = 'none';
			phonediv2.classList.remove("visible");
			phonediv2.classList.remove("visible_biglogo");
			phonediv2.classList.remove("visible_byside");
			phonediv2.classList.remove("visible_sidebyside");
			var newclass = "";
			if(slideEls[curSlide].classList.contains("biglogo")) {
				newclass="visible_biglogo";
			} else if(slideEls[curSlide].classList.contains("onlyphone") && slideEls[curSlide].classList.contains("secondphone")) {
				newclass="visible";
			} else if(slideEls[curSlide].classList.contains("phonebyside") && slideEls[curSlide].classList.contains("secondphone")) {
				newclass="visible_byside";
			} else if(slideEls[curSlide].classList.contains("phonessidebyside")) {
				newclass="visible_sidebyside";
			}
			if(newclass != "") {
				startScreen = slideEls[curSlide].getAttribute('data-startphonescreen2');
				if(startScreen) {
					currScreenshotNo2 = parseInt(startScreen)-1;
					nextScreenShot2();
				}
				window.setTimeout(function() {
					phonediv2.classList.add(newclass);
				}, 310);
			}
			//slideEls[curSlide].appendChild(phonediv2);
		}, true);
		
		url = localStorage.getItem('phoneConnected2', false);
		if(!url) {
			phonediv2.onclick = startPhoneConnect2;
		} else {
			phoneUrl2 = url;
			phonediv2.onclick = phoneDisconnect2;
			document.getElementById("phone2").onload = function(){
				window.setTimeout(phoneRefresh2, 10);
			}
			phoneRefresh2();
		}
		phonescreennext = document.getElementById('phonescreennext2');
		phonescreennext.onclick = nextScreenShot2;
	}
}

// functions for popup with examples
function openPopup() {
	var popup = slideEls[curSlide].querySelectorAll('aside')[0];
	popup.classList.add('opened');
	var slideNo = slideEls[curSlide].querySelectorAll('.footer-right')[0];
	slideNo.onclick = closePopup;
}
function closePopup() {
	var popup = slideEls[curSlide].querySelectorAll('aside')[0];
	popup.classList.remove('opened');
}
