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

//SlideSchedule (Phase 1, 2, 3)
var SlideSchedule = function() {
	"use strict";

	this.showSlide = function(jsonData){
		$("#contentWrapper").html('<div class="content roboto flexbox-vertical" id="content_local_sponsor"><h3><b>Hackathon-Ablauf</b>'
				+'</h3><div class="flexbox roboto box-centered"><table border="0"><tr><td><p><b>10.00-10.20</b></p></td><td>'
				+'<p>&nbsp;&nbsp;&nbsp;Ankunft, Begrüßung und Teambildung</p></td></tr><tr><tr><td><p><b>10.20-10.30</b></p></td><td>'
				+'<p>&nbsp;&nbsp;&nbsp;Vorstellung der Plattform daten.bremen.de</p></td></tr><tr><tr><td><p><b>10.30-12.45</b>'
				+'</p></td><td><p>&nbsp;&nbsp;&nbsp;Hack-Session</p></td></tr><tr><tr><td><p><b>12.45</b></p></td><td>'
				+'<p>&nbsp;&nbsp;&nbsp;Mittagessen</p></td></tr><tr><tr><td><p><b>12.45-18.30</b></p></td><td><p>&nbsp;&nbsp;&nbsp;Hack-Session</p>'
				+'</td></tr><tr><tr><td><p><b>18.30</b></p></td><td><p>&nbsp;&nbsp;&nbsp;Abendessen</p></td></tr><tr><tr><td><p>'
				+'<b>18.30-19.30</b></p></td><td><p>&nbsp;&nbsp;&nbsp;Hack-Session</p></td></tr><tr><tr><td><p><b>19.30-21.00</b>'
				+'</p></td><td><p>&nbsp;&nbsp;&nbsp;Demos and Voting</p></td></tr></table></div></div>');
	}
	
	this.refreshContent = function(jsonData) {

	}
	
	this.resize = function() {
		
	}
}

// SlideRules (Phase 2, 3)
var SlideRules = function(){
	"use strict";
	
	this.showSlide = function(jsonData){
		$("#contentWrapper").html('<div class="content roboto flexbox-vertical box-centered" id="content_welcome"><h3>'
			+'<b> Regeln </b></h3><ul><li>Die Teilnahme am V Hack Android Wettbewerb ist nur in Kleingruppen (2-3 Personen) erlaubt.</li>'
			+'<li>Es muss eine Android App programmiert werden. Nur diese wird bewertet.</li><li>Die Android App darf nicht vor dem '
			+'Event entwickelt worden sein. Wenn doch dann muss das neue Feature/der Umbau den Juroren genau erklärt werden.</li>'
			+'<li>Die Entwicklung eines Backends ohne Android App ist vor dem Event erlaubt.</li></ul></div>');
	}
	
	this.refreshContent = function(jsonData) {

	}
	
	this.resize = function() {
		
	}
}

//SlideThemes (Phase 2, 3)
var SlideThemes = function(){
	"use strict";
	
	this.showSlide = function(jsonData){
		$("#contentWrapper").html('<div class="content roboto flexbox-vertical box-centered" id="content_welcome"><h3>'
			+'<b> Gern gesehene Themen sind: </b></h3><ul><li><b>Birthday: </b>It&lsquo;s Android&lsquo;s 5th birthday ! Time to '
			+'give your Android device a fitting present!!</li><li><b>NFC: </b>It&lsquo;s no secret Androids like NFC. They&lsquo;re '
			+'still learning how to use it but the choices are unlimited!</li><li><b>Health: </b>Too much cake isn&lsquo;t good for an'
			+'Android. Let&lsquo;s shake a leg.</li><li><b>European Culture: </b>Whether it&lsquo;s art, clothes, cuisine, '
			+'philosophy or sport, culture defines us and we are Europeans. Android needs a passport app that proudly sounds the Ode to Joy.'
			+'But avoid Eurosong..</li></ul></div>');
	}
	
	this.refreshContent = function(jsonData) {

	}
	
	this.resize = function() {
		
	}
}

//SlidePrizes (Phase 1, 2, 3)
var SlidePrizes = function(){
	"use strict";
	
	this.showSlide = function(jsonData){
		$("#contentWrapper").html('<div class="content roboto flexbox-vertical box-centered" id="content_welcome"><h3><b>Preise</b>'
			+'</h3><ul><li><b>1. Platz:</b><ul><li>Reise zum finalen V Hack Android Hackathon (26-28 Oktober 2012; mit Unterkunft und Flug)</li>'
			+'<li>Kostenlose Tickets zur Konferenz droidcon in London (25-26 Oktober 2012)</li><li>1 Gerät pro Teammitglied (darunter 1x Nexus 7)</li>'
			+'</ul></li><li><b>Weitere Plätze:</b><ul><li>Pro Teammitglied ein Gerät nach Wunsch (Geräte von Alcatel One Touch)</li>'
			+'<li>... solange der Vorrat reicht</li></ul></li></ul></div>');
	}
	
	this.refreshContent = function(jsonData) {

	}
	
	this.resize = function() {
		
	}
}

// SlideJurors (Phase 3)
var SlideJurors = function(){
	"use strict";
	
	this.showSlide = function(jsonData){
		$("#contentWrapper").html('<div class="content roboto flexbox-vertical" id="content_jurors"><h3>'
			+'<b>Juroren</b></h3><div class="flexbox roboto box-centered"><table border="0"><tr><td><p>'
			+'<b>Michael Albrecht</b></p></td><td><p>&nbsp;&nbsp;&nbsp;Mitarbeiter von team neusta</p>'
			+'</td></tr><tr><tr><td><p><b>Dirk Wenig</b></p></td><td><p>&nbsp;&nbsp;&nbsp;Mitarbeiter der Uni Bremen</p>'
			+'</td></tr><tr><tr><td><p><b>Iaroslav Sheptykin</b></p></td><td><p>&nbsp;&nbsp;&nbsp;Mitarbeiter der Hochschule '
			+'Bremen</p></td></tr><tr><tr><td><p><b>Steve Liedtke</b></p></td><td><p>&nbsp;&nbsp;&nbsp;Mitgründer der GDG Bremen</p>'
			+'</td></tr></table></div></div>');
	}
	
	this.refreshContent = function(jsonData) {

	}
	
	this.resize = function() {
		
	}
}

// SlideGoodNight (Phase 4)
var SlideGoodNight = function(){
	"use strict";
	
	this.showSlide = function(jsonData){
		$("#contentWrapper").html('<div class="content roboto flexbox-vertical" id="content_welcome">'
			+'<h3>Wir hoffen ihr hattet Spaß!<br>Kommt heil nach Hause<br>und<br>gute Nacht!</h3><div class="flexbox box-centered">'
			+'<img id="gdg_logo" src="./images/GDG_Bremen_vonJan5g_mitRand.png"></div></div>');
	}
	
	this.refreshContent = function(jsonData) {

	}
	
	this.resize = function() {
		
	}
}

var SlideTweetAndroidV = function(){
	"use strict";
	
	this.showSlide = function(jsonData){
		$("#contentWrapper").html('<iframe  src="http://visibletweets.com/#query=%23androidV&animation=1" width="100%" '
			+'height="80%" name="SELFHTML_in_a_box"><p>Ihr Browser kann leider keine eingebetteten Frames anzeigen:' 
			+'Sie k&ouml;nnen die eingebettete Seite &uuml;ber den folgenden Verweis aufrufen:' 
			+'<a href="http://visibletweets.com/#query=%23androidV&animation=1">SELFHTML</a></p></iframe>');
	}
	
	this.refreshContent = function(jsonData) {

	}
	
	this.resize = function() {
		
	}
}


var SlideTweetAndroidVBremen = function(){
	"use strict";
	
	this.showSlide = function(jsonData){
		$("#contentWrapper").html('<iframe  src="http://visibletweets.com/#query=%23androidV%20%23Bremen&animation=3" width="100%" '
			+'height="80%" name="SELFHTML_in_a_box"><p>Ihr Browser kann leider keine eingebetteten Frames anzeigen: Sie '+
			+'k&ouml;nnen die eingebettete Seite &uuml;ber den folgenden Verweis aufrufen: '
			+'<a href="http://visibletweets.com/#query=%23androidV%20%23Bremen&animation=3">SELFHTML</a></p></iframe>');
	}
	
	this.refreshContent = function(jsonData) {

	}
	
	this.resize = function() {
		
	}
}