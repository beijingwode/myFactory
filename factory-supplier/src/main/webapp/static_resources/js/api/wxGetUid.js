
//取Cookie的值
function GetUidCookie() {
	
	//获取cookie字符串 
	var strCookie = document.cookie;
	//将多cookie切割为多个名/值对 
	var arrCookie = strCookie.split("; ");
	//遍历cookie数组，处理每个cookie对 
	
	var ticket = "";
	var uid = "";
	var nic = "";
	for (var i = 0; i < arrCookie.length; i++) {
		var arr = arrCookie[i].split("=");
		if (arr[0] == "user_ticket") {
			//ticket
			ticket = arr[1];
		} else if (arr[0] == "uid") {

			//uid
			uid = arr[1];
		} else if (arr[0] == "nickname") {
			//nic
			nic = arr[1];
		}
	}
	
	return 88516498065379;
}
function getShopIdCookie(){
	//获取cookie字符串 
	var strCookie = document.cookie;
	//将多cookie切割为多个名/值对 
	var arrCookie = strCookie.split("; ");
	//遍历cookie数组，处理每个cookie对 
	var shopId = "";
	for (var i = 0; i < arrCookie.length; i++) {
		var arr = arrCookie[i].split("=");
		if (arr[0] == "shopId") {
			//ticket
			shopId = arr[1];
		} 
	}
	return shopId;
}
//取Cookie的用户id 
function hasUid() {
	
	var uid = GetUidCookie();
	return (uid != "");
}

function loginCheck(state){
	var uid1 = GetUidCookie();
	if(uid1 == "") {

		jQuery.getScript("http://api.wd-w.com/wx/apiUid",
				function(data, status, jqxhr) {
					if(typeof(api_uid) != "undefined" && api_uid!=null ){
						uid = api_uid;
						document.cookie = "uid="+ escape(uid) + ";path=/";
						checkLogout(state,uid);
					} else {
						var rtn = encodeURI("http://api.wd-w.com/wx/getOpenId");
						window.location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb62e121cbeffdddf&redirect_uri="+rtn+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";						
					}
				});
		
	} else {
		checkLogout(state,uid);
	}
}

function checkLogout(state,uid){
	jQuery.getScript("http://api.wd-w.com/wx/jsonpCheckSubscribe.user?uid="+uid,
			function(data, status, jqxhr) {
				if(typeof(jsonpCheckSubscribe) != "undefined" && jsonpCheckSubscribe!=null && jsonpCheckSubscribe=='ok'){
					init();
				} else {
					var rtn = encodeURI("http://api.wd-w.com/wx/getOpenId");
					window.location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb62e121cbeffdddf&redirect_uri="+rtn+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";					
				}
			});
}
function isWeiXin(){
    var browser = window.browser;
    if (browser && browser.isWechatBrowser) return true;

    var ua = window.navigator.userAgent.toLowerCase();
    return (ua.match(/MicroMessenger/i) == 'micromessenger');
}


function isWeiXinOpen(){
	return true;
}
