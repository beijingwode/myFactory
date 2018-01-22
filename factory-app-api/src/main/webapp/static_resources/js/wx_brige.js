
function goStage(jsBasePath,state) {
	window.location = jsBasePath + stage2Url(state);
}
function stage2Url(state){
	var url=stageMap[state];
	if(url==null || typeof(url)=="undefined") {
		url="index_m.htm";
	}
	return url;
}

function go2Regist(jsBasePath,state,openId){
	var url=registMap[state];
	if(url==null || typeof(url)=="undefined") {
		window.location = jsBasePath+"user/toLogin?exp1="+openId+"&toUrl="+state+"&type=W&msg="+URLEncoder.encode("绑定我的福利账号，享个性化服务","UTF-8");
	} else {
		window.location = jsBasePath+url+"?openId="+openId+"&state="+state;
	}
}

var stageMap={"offTrailEvent":"activity/wx_yuyue_suc.html"};
var registMap={"offTrailEvent":"activity/wx_yuyue.html"};
