function close(){
	try{
		if (isWeiXinH5()) {
			window.history.back();
		}else{
		Toast.show("exit");
		}
	} catch(e) {
		window.location = "exit";
	}
}



function isWeiXinH5(){
    var browser = window.browser;
    if (browser && browser.isWechatBrowser) return true;

    var ua = window.navigator.userAgent.toLowerCase();
    return (ua.match(/MicroMessenger/i) == 'micromessenger');
}