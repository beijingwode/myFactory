//JavaScript Document
var uid=GetUidCookie();
var shopId
$(document).ready(function() {
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	function GetQueryString(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if(r!=null)return decodeURI(r[2]); return null;
	}
	shopId= GetQueryString("shopId");
	init();
});
var userSupplierId=getSupplierIdCookie();
function init(){
	if(uid == "") return;
	$.ajax({
		url :'shop/detail?shopId='+ shopId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				var result = data.data;
				var shop = result.shop;
				$("title").html(shop.shopname);
				var supplier = result.supplier;
				if (userSupplierId !='' &&userSupplierId==supplier.id) {
					$(".top h1").html("自家");
				}else{
					$(".top h1").html(shop.shopname);
				}
	    		if (shop.banner!=undefined) {//品牌背景图
					$(".shop_banner img").attr("src",shop.banner);
				}
				if (shop.logo!=""||typeof(shop.logo)!=undefined) {//logo
					$(".shop_xq dt img").attr("src",shop.logo);
				}
				if (userSupplierId !='' &&userSupplierId==supplier.id) {
					$(".shop_xq .dd1").html("自家");
				}else{
					$(".shop_xq .dd1").html(shop.shopname);
				}
				
				//判断收藏
				ajaxGetCollectShop(shopId);
				if (result.shopDescription!=undefined&&result.shopDescription!='') {//店铺描述
					$("#shopDescription").html("描述相符:"+parseFloat(result.shopDescription).toFixed(2))
				}
				if (result.shopService!=undefined&&result.shopService!='') {//店铺服务
					$("#shopService").html("服务态度:"+parseFloat(result.shopService).toFixed(2))
				}
				if (result.shopDescription!=undefined&&result.shopDescription!='') {//发货速度
					$("#deliverySpeed").html("发货速度:"+parseFloat(result.deliverySpeed).toFixed(2))
				}
				if (userSupplierId !='' &&userSupplierId==supplier.id) {
					$("#onlioneServer").html("自家");
				}else{
					$("#onlioneServer").html(shop.shopname);//在线客服
				}
				/*if (shop.cusPhone!=undefined&&shop.cusPhone!=null) {
					$("#shopTel").html(shop.cusPhone)//公司电话
					$("#shopTel").attr("href","tel:"+shop.cusPhone);
				}else{
					$("#shopTel").html(shop.cusTel)//公司电话
					$("#shopTel").attr("href","tel:"+shop.cusTel);
				}*/
				if (userSupplierId !='' &&userSupplierId==supplier.id) {
					$("#companyName").html("自家");
				}else{
					$("#companyName").html(supplier.comName);
				}
				//$("#companyName").html(supplier.comName)//公司名称
				var comState = supplier.comState;
				var comCity = supplier.comCity;
				var comAdd = supplier.comAdd;
				var comAddress = supplier.comAddress;
				var companyAddress = comState+comCity+comAdd+comAddress
				$("#companyAddress").html(companyAddress)//公司地址
			}
		},
		error : function() {}
	})
	
}
function removeShop(){
	if(uid=="") return;
	$.ajax({
		url : 'collectShop/delete.user?uid='+uid+'&shopIdList='+shopId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				window.location.href=window.location.href;
			}
		},
		error : function() {}
	});
}
function increaseShop(){
	if(uid=="") return;
	$.ajax({
		url : 'collectShop/add.user?uid='+uid+'&shopId='+shopId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				window.location.href=window.location.href;
			}
		},
		error : function() {}
	});
}
function ajaxGetCollectShop(shopId){//判断店铺是否收藏
	if(uid=="") return;
	$.ajax({
		url : 'collectShop/check.user?uid='+uid+'&shopId='+shopId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success && data.data) {
				$("#delShop").show();//已收藏
				$("#addShop").hide();//未收藏默认
			}
		},
		error : function() {}
	});
}