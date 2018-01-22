$(document).ready(function() {
	var time = 3000;
	setTimeout("redirect()",time);
});

function redirect(){
	location.href=wode.domain+"/login.html?from=/index.html";
}