"use strict";

window.onload = function(){
    loadLocales2Html();
    communication.getAllItems();
};

function resetBadgeText(){
    chrome.browserAction.setBadgeText({
        text: ""
    });
}

function allItemsReceived(data){
    if (data == false) {
        window.close();
    }
    else {
        resetBadgeText();
        $("loading").style.display = "none";
        emptyNode("eventlist");
        for (var i in data.events) {
            var event = data.events[i];
            var li = document.createElement("li");
            li.appendChild(document.createTextNode(event.name));
            li.setAttribute("onclick", "window.open(\"eventdetails.html?key=" +
            event.key +
            "\");");
            $("eventlist").appendChild(li);
        }
        $("eventlist").style.display = "block";
    }
}
