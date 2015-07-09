window.onscroll = function() {
	if (document.getElementById("floatad-winpop")) {
		var scrollTop = 0;
		if (typeof document.compatMode != 'undefined'
				&& document.compatMode != 'BackCompat') {
			scrollTop = document.documentElement.scrollTop;
		} else {
			scrollTop = document.body.scrollTop;
		}
	}
}
function tips_pop() {
	var MsgPop = document.getElementById("floatad-winpop");
	var popH = parseInt(MsgPop.style.height);
	if (popH == 0) {
		MsgPop.style.display = "block";
		show = setInterval("changeH('up')", 2);
	} else {
		hide = setInterval("changeH('down')", 2);
	}
}
function changeH(str) {
	var MsgPop = document.getElementById("floatad-winpop");
	var popH = parseInt(MsgPop.style.height);
	if (str == "up") {
		if (popH <= 183) {
			MsgPop.style.height = (popH + 2).toString() + "px";
		} else {
			clearInterval(show);
		}
	}
	if (str == "down") {
		if (popH >= 4) {
			MsgPop.style.height = (popH - 4).toString() + "px";
		} else {
			clearInterval(hide);
			MsgPop.style.display = "none";
		}
	}
}
window.onload = function() {
	document.getElementById('floatad-winpop').style.height = '0px';
	var d = new Date();
	var day = d.getDay();
	setTimeout("tips_pop()", 800);
}