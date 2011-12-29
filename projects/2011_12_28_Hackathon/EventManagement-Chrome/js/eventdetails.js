"use strict";

window.onload = function(){
    loadLocales2Html();
    communication.getEventDetails(HTTP_GET['key']);
};

function eventDetailsReceived(data){
    if (data == false) {
        window.close();
    }
    else {
        window.location = data.event.url;
    }
}
