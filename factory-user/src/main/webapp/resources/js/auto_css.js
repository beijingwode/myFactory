if(window.innerWidth) {
	var width = window.innerWidth;
	if(width>1700) {
		document.write('<link rel="stylesheet" type="text/css" href="css/index_1620.css">');
	} else {
		document.write('<link rel="stylesheet" type="text/css" href="css/index.css">');
		
	}
} else {
	document.write('<link rel="stylesheet" type="text/css" href="css/index.css">');
}

