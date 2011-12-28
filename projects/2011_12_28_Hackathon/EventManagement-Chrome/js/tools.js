/****************************************************************************************
 * function for general tasks that can be useed from averywhere
 * © 2011 Jan Stalhut
 ****************************************************************************************/
"use strict";

/**
 * gets one DOM object by its id. A short replacement for document.getElementById(...).
 * @param String objectId
 * @return Object
 */
function $(objectId){
    return document.getElementById(objectId);
}

/**
 * deletes all elements in the given DOM element with exclusion of the given count of first from the top. 
 * @param String objectId
 * @param Int saveCount
 */
function emptyNode(objectId, saveCount){
    if (typeof(saveCount) == "undefined") 
        saveCount = 0;
    var savedNode = new Array();
    while ($(objectId).hasChildNodes()) {
        var tmp = $(objectId).removeChild($(objectId).firstChild);
        if (savedNode.length < saveCount) {
            savedNode[savedNode.length] = tmp;
        }
    }
    while (savedNode.length > 0) {
        $(objectId).appendChild(savedNode.shift());
    }
}
