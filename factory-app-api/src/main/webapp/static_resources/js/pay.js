
var uid=GetUidCookie();
$(document).ready(function() {
	$("#wx_sel").val("1");
	$(".pay_weixin em").addClass("selected");

	if(!sessionCheckOrder("pay",type)) {
		return;
	}
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});

	init();
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
		if(isPageHide){window.location.reload();}
	});
	window.addEventListener('pagehide', function(){isPageHide = true;});
});

function toggleSel(id) {

	$(".pay_weixin em").removeClass("selected");
	$(".pay_yue em").removeClass("selected");

	$(".pay_"+id+" em").addClass("selected");

	$("#weixin_sel").val("0");
	$("#yue_sel").val("0");
	$("#"+id+"_sel").val("1");
	
	if("weixin" == id) {
		$(".box_top p").html("<em>微信</em>支付");
	} else {
		$(".box_top p").html("<em>现金券余额</em>支付");
	}
}
function init(){
	$.ajax({
		url : jsBasePath +'user/balance.user?currencyId=0&uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html='';
			if (data.success) {
				var bl = parseFloat(data.data);
				$("#cashBanlance").val(bl);
				var totalFee = parseFloat($("#totalFee").val());
				if(totalFee<=bl) {
					$(".pay_yue .dd2").html("余额：￥"+bl.toFixed(2));
					
				} else {
					$(".pay_yue").removeAttr("onclick");
					$(".pay_yue .dd2").html("余额：￥"+bl.toFixed(2)+"不足以支付此订单");
				}
				
				$(".pay_btn").show();
			} else {
				showInfoBox(data.msg);
			}
		},
		error : function() {}
	});
}
function paySubmit() {
	
	$(".pay_btn a").attr("href","javascript:void(0);")
	if($("#yue_sel").val()=="1") {
		// 余额支付
		balancePay();
	} else {
		// 微信支付
		weixinPay();
	}
}

function balancePay(){
	$.ajax({
		url : jsBasePath +'pay/balanceToPay.user?uid='+uid,
		type : "POST",
		dataType: "json",  //返回json格式的数据  
		data: {"orderId":orderId,"subOrderId":subOrderId,"totalFee":$("#totalFee").val(),"type":type},  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html='';

			if (data.success) {
				sessionSetOrderStep("paySuccess");
				window.location = jsBasePath+'pay/pay_success.user?uid='+uid+'&orderId='+orderId+'&suborderId='+subOrderId+'&fromWay='+orderType+'&type='+type;
			} else {
				showInfoBox(data.msg);
				$(".pay_btn a").attr("href","javascript:paySubmit();")
			}
		},
		error : function() {}
	});
}

var toPayResult;
function weixinPay(){
	$.ajax({
		url : jsBasePath +'pay/wxOpenPay.user?uid='+uid,
		type : "POST",
		dataType: "json",  //返回json格式的数据  
		data: {"orderId":orderId,"subOrderId":subOrderId,"totalFee":$("#totalFee").val(),"type":type},  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html='';

			if (data.success) {
				toPayResult = data.data;

				if (typeof WeixinJSBridge == "undefined"){
				   if( document.addEventListener ){
				       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
				   }else if (document.attachEvent){
				       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
				       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
				   }
				}else{
				   onBridgeReady();
				} 
				
			} else {
				showInfoBox(data.msg);
				$(".pay_btn a").attr("href","javascript:paySubmit();")
			}
		},
		error : function() {}
	});
}

function onBridgeReady(){
	   WeixinJSBridge.invoke(
	       'getBrandWCPayRequest', {
	           "appId":toPayResult.appId,
	           "timeStamp":toPayResult.timeStamp,
	           "nonceStr":toPayResult.nonceStr,
	           "package":"prepay_id="+toPayResult.prepayid,     
	           "signType":"MD5",         //微信签名方式：     
	           "paySign":toPayResult.paySign
	       },
	       function(res){
	           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
					sessionSetOrderStep("paySuccess");
					window.location = jsBasePath+'pay/pay_success.user?uid='+uid+'&orderId='+orderId+'&suborderId='+subOrderId+'&fromWay='+orderType+'&type='+type;
	           } else {
					sessionSetOrderStep("payCancel");
					$(".pay_btn a").attr("href","javascript:paySubmit();")
	           }
	       }
	   ); 
	}