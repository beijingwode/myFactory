
var uid=GetUidCookie();
var toPayResult;
function toRecharge(amount){
	if(uid=="") return;
	//var orderId='';
	//var subOrderId='';
	$.ajax({
		url : jsBasePath +'pay/wxOpenPay.user?uid='+uid,
		type : "POST",
		dataType: "json",  //返回json格式的数据  
		data: {"amount":amount},  //返回json格式的数据  
	    async: true,
		success : function(data) {
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
	           "signType":"MD5",         // 微信签名方式：
	           "paySign":toPayResult.paySign
	       },
	       function(res){
	           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
	        	   //go2back();
	        	   if(window.location.href.indexOf("charge=1")>-1) {
	        		   window.location = window.location.href.replace("charge=1","payOk=1");
	        	   } else {
		        	   showInfoBox("充值成功！","refreshPage()");
	        	   }
	           } else {
					/*if(orderType=="directBuy" || orderType=="cart") {
						showInfoBox("支付取消，可以在待支付订单中完成支付，24小时未支付的订单系统将自动取消。");
					}
					$(".pay_btn a").attr("href","javascript:paySubmit();")*/
	        	   showInfoBox("充值失败！")
	           }
	       }
	 );
}


function refreshPage() {
	window.location.reload();
}