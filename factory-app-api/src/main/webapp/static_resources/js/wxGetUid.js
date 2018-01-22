
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
	
	return uid;
}

//取Cookie的值
function GetWxOpenCookie() {
	
	//获取cookie字符串 
	var strCookie = document.cookie;
	//将多cookie切割为多个名/值对 
	var arrCookie = strCookie.split("; ");
	//遍历cookie数组，处理每个cookie对 
	
	var wxOpen = "";
	for (var i = 0; i < arrCookie.length; i++) {
		var arr = arrCookie[i].split("=");
		if (arr[0] == "wxOpen") {
			//ticket
			wxOpen = arr[1];
		} 
	}
	
	return wxOpen;
}
function getSupplierIdCookie(){
	//获取cookie字符串 
	var strCookie = document.cookie;
	//将多cookie切割为多个名/值对 
	var arrCookie = strCookie.split("; ");
	//遍历cookie数组，处理每个cookie对 
	var userSupplierId = "";
	for (var i = 0; i < arrCookie.length; i++) {
		var arr = arrCookie[i].split("=");
		if (arr[0] == "userSupplierId") {
			//ticket
			userSupplierId = arr[1];
		} 
	}
	return userSupplierId;
}
//取Cookie的用户id 
function hasUid() {
	
	var uid = GetUidCookie();
	return (uid != "");
}

function loginCheck(state){
	var uid = GetUidCookie();
	if(uid == "") {
		var rtn = encodeURI(system_domain+"wx/getOpenId");
		window.location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weixin_open_appId+"&redirect_uri="+rtn+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
	} else {
		$.ajax({
			url : system_domain+'wx/checkSubscribe.user?uid='+uid,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				if (data.success) {
				} else {
					var rtn = encodeURI(system_domain+"wx/getOpenId");
					window.location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weixin_open_appId+"&redirect_uri="+rtn+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
				}
			},
			error : function() {
				alert("err");
			}
		});
	}
}

function isWeiXin(){
    var browser = window.browser;
    if (browser && browser.isWechatBrowser) return true;

    var ua = window.navigator.userAgent.toLowerCase();
    return (ua.match(/MicroMessenger/i) == 'micromessenger');
}


function isWeiXinOpen(){
	if(isWeiXin()) {
		var url = window.location.href;
		if(url.indexOf("wxOpen=1") > -1) {
			return true;
		} else {
			var wxOpen = GetWxOpenCookie();
			return (wxOpen == "1");
		}
	}
	return false;
}