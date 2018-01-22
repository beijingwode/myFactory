
var uid=GetUidCookie();
$(function(){
	if(isWeiXinOpen()) {
    	loginCheck(2);
    }
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	
	$("#search_btn").click(function(){//点击搜索触发事件
		if(uid=="")return;
		ajaxSearch();
	});
	cookieShow();
});
function ajaxSearch(){
	var key = $("#keyword").val();
	rememberKey();
	go2Search(mkParam(key));
}
