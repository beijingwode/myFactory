
var uid=GetUidCookie();
// JavaScript Document
var shopId = getShopIdCookie();
$(function(){
	if(isWeiXinOpen()) {
		loginCheck('shop9');
	}
	init()
	$(".wd-theme-popover-mask").click(function() {
		$(".wd-theme-popover-mask").hide();
		$(".wd-theme-popover").hide();
	})
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
});
function init(){
	if(uid == "") return;
	shopId=$("#shopID").val();
	$.ajax({
		url:jsBasePath+'app/user/statistic.user?uid='+uid+'&shopId='+shopId,
		type : "GET",
		async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		var data2 = data.data;
	    		var shopList = data2.shopList;
	    		var html='';
	    		if (data2.avatar&&data2.avatar!='') {
	    			$("#avatar img").attr("src",data2.avatar+'');//头像
				}
	    		$("#userName").html(data2.userName);//用户昵称
	    		var shopName = data2.shopName;
	    		if (data2.shopName.length>=25) {
	    			shopName=shopName.substring(0,22);
	    			shopName+=" ...";
				}
	    		$("#shopName").html('店铺：'+shopName);//店铺名称
	    		if (shopList && shopList.length>0) {//店铺集合
					for (var i = 0; i < shopList.length; i++) {
						html+='<li onclick="go2Shop(\''+shopList[i].id+'\')">'+shopList[i].shopname+'</li>';
					}
					//$("#shopID").val(shopList[0].id);//默认保存第一个商家id
					$(".wd-theme-popover ul").html(html);
				}
	    		if (data2.userFactory.type==1) {//买家
					$(".li3 a").attr("href","javascript:void(0);");
					$("#chooseShop").removeAttr("onclick");
				}
			}
	    },
	    error : function() {}
	})
}
function go2Shop(id){
	$("#shopID").val(id);
	$(".wd-theme-popover-mask").hide();
	$(".wd-theme-popover").hide();
	init();
}
//个人信息
function go2UserInfo(){
	var shopId=$("#shopID").val();
	window.location = jsBasePath+'app/user/page?uid='+uid+'&shopId='+shopId;
}
function go2Setsafe(){//安全设置
	window.location = jsBasePath+'logout.html?uid='+uid;
}
function chooseShop(){
	$(".wd-theme-popover-mask").show();
	$(".wd-theme-popover").show();
}
