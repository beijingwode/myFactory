
var uid=GetUidCookie();//用户id
// JavaScript Document
$(function(){
	if(isWeiXinOpen()) {
		   loginCheck(3);
	}
	ajaxDetailsData();
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	$(".sz_btn").click(function(e){//点击设置
		window.location = jsBasePath+'setup.html';
	});
	$(".header_con dl").click(function(e){//点击头像
		window.location = 'personal?pageId='+4;
	});
	$(".product").click(function(e){//点击商品收藏
		window.location = jsBasePath+'collectProduct/page';
	});
	$(".shop").click(function(e){//点击店铺收藏
		window.location = jsBasePath+'collectShop/page'
	});
	$("#orderstatus dl").each(function(index){//待支付，待收货订单切换
		var status;
		if (index==0) {//待支付
			status=0;
		}else if(index==1){//待发货
			status=1;
		}else if(index==2){//待收货
			status=2;
		}else if(index==3){//待评论
			status=4;
		}else if(index==4){//售后
			status=311;
		}
		$(this).click(function(){//点击
			window.location = jsBasePath+'order/page?status='+status;
		});
	});
	/*$("#zc dl").each(function(index){//资产切换
		var cId;
		if (index==0) {
			cId=0;
		}else if(index==1){
			cId=1;
		}else if(index==2){
			cId=3;
		}else if(index==3){
			cId=2;
		}
		$(this).click(function(){//点击
			window.location=jsBasePath+'bargainFlow?wxOpen=1&empId='+uid+'&cId='+cId;
		});
	});*/
	$(".xianjin a").click(function(){
		cId=1;
		window.location=jsBasePath+'bargainFlow?wxOpen=1&empId='+uid+'&cId='+cId;
	})
	$(".fuli a").click(function(){
		cId=1;
		window.location=jsBasePath+'limitTicket/page.user?uid='+uid;
	})
	$(".huanling a").click(function(){
		cId=3;
		window.location=jsBasePath+'bargainFlow?wxOpen=1&empId='+uid+'&cId='+cId;
	})
	/*$(".balance").click(function(){//点击充值
		window.location=jsBasePath+'bargainFlow?wxOpen=1&empId='+uid+'&cId=0&charge=1';
	});*/
	$(".add_money-mask").click(function(){
		go2close();
	})
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true;});
});
function goIBuyPage(){
	window.location=jsBasePath+'group/page_ibuy.user?uid='+uid;
}
function go2Address(){
	window.location=jsBasePath+'address/page'
}
function ajaxDetailsData(){
	if(uid=="") return;
	$.ajax({
		url:jsBasePath+'user/statistic.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		cache:false,
	    async: false,
	    success : function(data) {
	    	if (data.success) {
	    		//个人中心数据
	    		var result = data.data;
	    		var avatar = result.avatar;
	    		$(".header_con dd").html(result.nickName);//名称
	    		if (avatar!=null && avatar!= undefined && avatar!='') {
	    			$(".header_con dt img").attr("src",result.avatar+'');//头像
				}
	    		$(".header_con .dd2").html(result.companyName);//公司名称
	    		$(".xianjin em").html(result.companyTicket.toFixed(2));//现金券
	    		$(".fuli em").html(result.ticketCount+"<i>张</i>");//内购券
	    		$(".huanling em").html(result.exchangeTicket.toFixed(2));//换领币
	    		$(".product em").html(result.wishlistCount);//商品收藏个数
	    		$(".shop em").html(result.shopCollectCount);//店铺收藏个数
	    		$(".header_con .header_bottom .message em").html(0);//消息
	    		if (result.nonPaymentCount==0) {
	    			$(".orders_con .dfk span").hide();
				}else{
					$(".orders_con .dfk span").html(result.nonPaymentCount);//待付款
				}
	    		if (result.unfilledCount==0) {
	    			$(".orders_con .dfh span").hide();
				}else{
					$(".orders_con .dfh span").html(result.unfilledCount);//待发货
				}
	    		if(result.notReceivingCount==0){
	    			$(".orders_con .dsh span").hide();
	    		}else{
	    			$(".orders_con .dsh span").html(result.notReceivingCount);//待收货
	    		}
	    		if (result.unvaluedCount==0) {
	    			$(".orders_con .dpl span").hide();
				}else{
					$(".orders_con .dpl span").html(result.unvaluedCount);//待评论
				}
	    		if(result.refundCount==0){
	    			$(".orders_con .sh span").hide();
	    		}else{
	    			$(".orders_con .sh span").html(result.refundCount);//售后
	    		}
	    		$("#friend").val(result.employeeType);//用户类型
	    		
	    		if(result.supplierTicketCount==0){
	    			$("#intoAction").hide();
	    		}else{
	    			$("#intoAction").show();
	    		}
	    		$("#supplierId").val(result.userFactory.supplierId);//企业名称
	    		
			}
			
	    },
	    error : function() {}
	});
}
function go2friend(){//进入亲友页面
	var employeeType=$("#friend").val();//用户类型
	window.location=jsBasePath+'friend/page.user?uid='+uid+'&employeeType='+employeeType;
	//window.location=jsBasePath+'friend_circle.html';
}
function go2MyShare(){//分享页面
	window.location=jsBasePath+'userShare/page.user?uid='+uid;
}
function inviteFriends(){
	window.location=jsBasePath+'userShare/invitationMarketingPage.user?uid='+uid;
}
function go2close(){
	$(".add_money-mask").hide();
	$(".recharge_money").hide();
}
function go2ShareColleague(){
	if (uid=="") return;
	var supplierId=$("#supplierId").val();
	var truePath = system_domain;
	if(typeof(jsBasePath)!="undefined") {
		truePath=jsBasePath;
	}
	var text= truePath + 'userShare/toCompanyBind'+supplierId+'?fromId='+uid;
	text=text.replace(/&/g,"____");
	text=encodeURI(text);
	var getQrUrl=encodeURI(jsBasePath+'userShare/getQr?text='+text);
	var img='<img src="'+getQrUrl+'" width="auto" height="auto" style="vertical-align: middle;">';
	$(".recharge_money p").html(img);
	$(".recharge_money").show();
	$(".add_money-mask").show();
}
function go2AllOrders(){//点击全部订单
	var status="";
	window.location = jsBasePath+'order/page?status='+status;
}
function go2hl(){
	window.location = jsBasePath+'exchangeOrder/myhl.user?uid='+uid;
}
function xianxiafafang(){
	window.location = jsBasePath+'managerOrderRecord/toOfflineRecordPage.user?uid='+uid;
}