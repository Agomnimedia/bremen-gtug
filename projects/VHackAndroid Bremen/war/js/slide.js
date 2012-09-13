var SlideWelcome = function() {
	"use strict";

	this.showSlide = function(jsonData) {
		$("#contentWrapper")
				.html('<div class="content roboto flexbox-vertical" id="content_welcome"><h3><b>Willkommen zum<br><br>'
						+'V Hack Android<br><br>in Bremen</b></h3><div class="flexbox box-centered"><img id="gdg_logo" '
						+'src="./images/GDG_Bremen_vonJan5g_mitRand.png"></div></div>');
	}

	this.refreshContent = function(jsonData) {

	}
	
	this.resize = function() {
		
	}
}

var SlideGlobalSponsor = function() {
	"use strict";

	this.showSlide = function(jsonData) {
		$("#contentWrapper")
				.html('<div class="content roboto flexbox-vertical" id="content_global_sponsor"><h3><b>Globale Sponsoren</b>'
						+'</h3><div class="flexbox sponsors box-centered top-space"><div id="gold_sponsors" class="flexbox-vertical">'
						+'<div id="gold_sponsors" class="flexbox box-centered"><img id="alcatel" src="./images/alcatel.png"></div></div>'
						+'<div class="flexbox-vertical"><div id="silver_sponsors" class="flexbox box-centered"><img id="sony" '
						+'src="./images/Make_Believe_SB.jpg"></div></div></div></div>');
	}

	this.refreshContent = function(jsonData) {

	}
	
	this.resize = function() {
		
	}
}

var SlideLocalSponsor = function() {
	"use strict";
	this.showSlide = function(jsonData) {
		$("#contentWrapper")
				.html(
						'<div class="content roboto flexbox-vertical" id="content_local_sponsor"><h3><b>Lokale Sponsoren</b></h3>'
								+ '<div class="flexbox sponsors box-centered"><div class="sponsors"><h2>Gold Sponsor</h2></div><div class="sponsors">'
								+ '<h2>Silver Sponsor</h2></div></div><div class="flexbox sponsors box-centered top-space"><div id="gold_sponsors"'
								+ 'class="flexbox-vertical"><div id="gold_sponsors" class="flexbox box-centered"><img id="neusta"'
								+ 'src="./images/neusta_logo.jpg"></div></div><div class="flexbox-vertical"><div id="silver_sponsors" '
								+ 'class="flexbox box-centered"><img id="enough" src="./images/ES_logo_black.jpg"></div></div></div></div>');
	}

	this.refreshContent = function(jsonData) {
		// do nothing
	}

	this.resize = function() {
		/*if ($(window).width() < 1280 || $(window).height() < 780) {
			if ($("#neusta").attr("src").indexOf("small") === -1) {
				$("#neusta").attr("src", "./images/neusta_logo_small.jpg");
				$("#enough").attr("src", "./images/ES_logo_black_small.jpg");
			}
		} else {
			if ($("#neusta").attr("src").indexOf("small") !== -1) {
				$("#neusta").attr("src", "./images/neusta_logo.jpg");
				$("#enough").attr("src", "./images/ES_logo_black.jpg");
			}
		}*/
	}
}

var SlideDefault = function() {
	"use strict";

	this.showSlide = function(jsonData){
		$("#contentWrapper").html('<div class="content roboto flexbox-vertical box-centered" id="content_welcome"><h3>'
			+'<b> V Hack Android<br> <br>in Bremen</b></h3><div class="flexbox box-centered"><img id="gdg_logo" '
			+'src="./images/GDG_Bremen_vonJan5g_mitRand.png"></div></div>');
	}
	
	this.refreshContent = function(jsonData) {

	}
	
	this.resize = function() {
		
	}
}

var SlidePartners = function() {
	"use strict";

	this.showSlide = function(jsonData){
		$("#contentWrapper").html('<div class="content roboto flexbox-vertical" id="content_local_sponsor"><h3><b>Unterstützt von'
				+'</b></h3><div class="flexbox sponsors box-centered top-space"><div id="partner_1" class="flexbox-vertical">'
				+'<div id="gold_sponsors" class="flexbox box-centered"><img id="bremen_verwaltung" src="./images/Bremen_Verwaltung_online.jpg">'
				+'</div></div><div class="flexbox-vertical"><div id="partner_2" class="flexbox box-centered"><img id="jan" src="./images/jan.jpg">'
				+'</div></div></div><div class="flexbox sponsors box-centered top-space"><div id="partner_3" class="flexbox-vertical">'
				+'<div id="gold_sponsors" class="flexbox box-centered"><img id="bremen_verwaltung" src="./images/hsb-logo.jpeg"></div>'
				+'</div><div class="flexbox-vertical"><div id="partner_4" class="flexbox box-centered"><img id="jan"' 
				+' src="./images/logo_uni_bremen.jpg"></div></div></div></div>');
	}
	
	this.refreshContent = function(jsonData) {

	}
	
	this.resize = function() {
		
	}
}

// SlideSchedule (Phase 1, 2, 3)

// SlideRules

// SlidePrizes

// SlideGoodNight

// SlideJurors

// (SlideTeams)

// (SlideResults)
