

function sendBrainstormingValue(){
	var text = document.getElementById("input_brainstorming");
	if(text.value.length>0){
		for(var i=0; i<14; i++){
			var tdElement = document.getElementById("td_"+i);
			if(tdElement.innerHTML.length==0){
				tdElement.innerHTML = text.value;
				break;
			}
		}
		text.value="";
	}
}

function handleBrainstormKeyPress(e,form){
	var key=e.keyCode || e.which;
	if (key==13){
		document.getElementById("button_brainstorming").click();
	}
}

function sendBrainstormingValue2(){
	var text = document.getElementById("input_brainstorming_2");
	if(text.value.length>0){
		for(var i=0; i<14; i++){
			var tdElement = document.getElementById("td2_"+i);
			console.log("tdElement inner html: " + tdElement.innerHTML);
			console.log("tdElement inner html length: " + tdElement.innerHTML.length);
			if(tdElement.innerHTML.length==0){
				tdElement.innerHTML = text.value;
				break;
			}
		}
		text.value="";
	}
}

function handleBrainstormKeyPress2(e,form){
	var key=e.keyCode || e.which;
	if (key==13){
		document.getElementById("button_brainstorming_2").click();
	}
}