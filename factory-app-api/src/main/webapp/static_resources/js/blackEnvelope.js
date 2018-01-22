function goNext(msg,forId)
{	
	var uid = GetUidCookie();
	if(uid!="") {
		window.location = jsBasePath + "blackEnvelope/openPage"+forId +'.user?uid='+uid;
		return;
	} else {
		if(isWeiXin()) {
			sessionStorage.setItem("loginNextUrl", jsBasePath + "blackEnvelope/openPage"+forId +'.user');
			sessionStorage.setItem("loginPreUrl", window.location.href);
			sessionStorage.setItem("type", "2");
			sessionStorage.setItem("shareId", forId);
			
			var state="friendBind";
			var rtn = encodeURI(jsBasePath+"wx/hasBind");
			window.location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weixin_open_appId+"&redirect_uri="+rtn+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";

		} else {
			window.location = jsBasePath + "user/toLogin?msg="+msg+"&forId="+forId +"&exp1=&toUrl=user&type=2";
		}
	}
}
