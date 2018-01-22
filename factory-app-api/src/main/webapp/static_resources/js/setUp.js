
//JavaScript Document
$(document).ready(function() {
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	$(".top").click(function(e){
		window.location = 'user/page';
	})
	//个人资料页面
	$("#personal").click(function(e){
		window.location = 'user/personal?pageId=4';
	})
	//收货地址页面
	$("#receiveAddress").click(function(e){
		window.location = 'address/page';
	})
});
function go2logout(){
	$(".recharge_money").show();
}
function go2close(){
	$(".recharge_money").hide();
}
function go2Sure(){
	var uid=GetUidCookie();
	if(uid == "") return;
	$.ajax({
		url : 'wx/logout.user?uid='+uid,
		type : "GET",
	    async: true,
	    success : function(data) {
			go2close();
			window.location="http://supplier.wd-w.com/app/user/logoutWx";
		},
		error : function() {}
	})
}