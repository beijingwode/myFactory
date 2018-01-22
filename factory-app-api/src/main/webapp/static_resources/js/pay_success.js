var uid=GetUidCookie();
$(document).ready(function() {

	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	

	/*window.onpopstate = function() {
		if(location.hash.indexOf("#win")>-1){
        }else{
        	WeixinJSBridge.call('closeWindow');
        }
	  };*/
	  var isPageHide = false;           
		window.addEventListener('pageshow', function(){
		if(isPageHide){window.location.reload();}
		});           
		window.addEventListener('pagehide', function(){isPageHide = true; sessionStorage.removeItem("orderInfo");});
	init();
	userCheck();
});
function userCheck(){
	$.ajax({
		url : jsBasePath+'wx/checkSubscribe.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				checkUser(data.data);
			} 
		},
		error : function() {
		}
	});
}
function checkUser(openId){
	$.ajax({
		url : jsBasePath+'wx/checkUser.user?uid='+uid+"&openId="+openId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				if("1"==data.data){
					$(".ewm").hide();
				}else{
					$(".ewm").show();
				}
			}
		},
		error : function() {
		}
	});
}
function init(){
	/*if(location.hash.indexOf("#win")==-1){
		showInfoBox("支付成功，您可以查看订单详情或再去首页逛逛","setHash()");
	}*/
	if(uid=='')return;
	$.ajax({
		url : jsBasePath+'order/getOrderInfo.user?uid='+uid+'&orderId='+orderId+'&subOrderId='+subOrderId+'&type='+type,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				var result =null;
				var userExchange =0;//可用换领币
				var addCnt  = 0;//心愿单数
				if(type==1){
					result = data.data.groupOrders;
					$(".btns").hide();
				}else if(type==5){
					result = data.data.order;
					addCnt =data.data.addCnt;
					userExchange =data.data.userExchange;
				}
				else{
					$(".TogetherToBuy_help").hide();
					result = data.data.order;
				}
				if(type!=5){
					$(".cons_name").html("<span>收货人：</span><p>"+result.name+"</p><em>"+result.mobile+"</em>")
					$(".address p").html(result.address);
					$(".li1 em").html("-¥"+result.totalAdjustment.toFixed(2));
					$(".li2 em").html("<i>¥</i>"+result.realPrice.toFixed(2));
					if(type==1){//团购
						var groupBuy = data.data.groupBuy;
						var saveAmount = result.saveAmount;
						if(uid==groupBuy.userId &&  parseInt(saveAmount)== 0){//团长 且第一笔支付
							$(".pay_success_hint .li2").html("购物团已建立，下载APP邀请好友参团。");
						}else if(parseInt(saveAmount)>0){
							$(".pay_success_hint .li2").html("本次购物为大家共节省了"+parseFloat(saveAmount).toFixed(2)+"。");
							$(".TogetherToBuy_help a").html("下载APP，关注团内动态");
						}else if(uid != groupBuy.userId && parseInt(saveAmount)== 0){
							$(".pay_success_hint .li2").html("");
							$(".TogetherToBuy_help a").html("下载APP，关注团内动态");
						}
					}
					if(type!=1){
						if(fromWay && fromWay!="cart"){
							if (result.subOrderId && result.subOrderId != '') {
								subOrderId = result.subOrderId;
							} else {
								subOrderId = result.items[0].subOrderId;
							}
						}
					}
				}else{
					var html ='';
					var subOrderItems = result.subOrderItems;
					if(subOrderItems && subOrderItems!=null &&subOrderItems!=''){
						html+='<dt><a href="javascript:void(0);"><img src="'+subOrderItems[0].image+'" /></a></dt>';
						html+='<dd class="dd1"><a href="javascript:void(0);">'+subOrderItems[0].productName+'</a></dd>';
						html+='<dd class="dd2">'+subOrderItems[0].itemValues.replace("{","").replace("}", "").replace(/"([^"]*)"/g, "$1");+'</dd>';
						html+='<dd class="dd3"><span>'+subOrderItems[0].internalPurchasePrice.toFixed(2)+'</span><i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" /></i><em>x '+subOrderItems[0].number+'</em></dd>';
					}
					$(".pp_pro dl").html(html);
					$("#realPay span").html('实付：¥'+result.realPrice.toFixed(2)+'+'+result.benefitAmount.toFixed(2));
					$("#realPay em").html('（含运费￥'+result.totalShipping.toFixed(2)+'）');
					$("#balance span").html('换领币余额：'+userExchange.toFixed(2));
					
					if(addCnt && addCnt>0){
						$(".wish_list_btn span").html(addCnt);
					}else{
						$(".wish_list_btn span").hide();
					}
					showAdjustProduct();
				}
				
				}else{
					showInfoBox(data.msg);
				}
		},
		error : function() {}
	});
}

function setHash() {
	location.hash = "win";
}
/**
 * 跳转首页
 */
function go2Index(){
	sessionStorage.setItem("order", "paysuccess");
	window.location=jsBasePath +'index_m.htm';
}
function go2Order(){
	if(fromWay && fromWay!='' &&fromWay=="cart"){
		sessionStorage.setItem("order", "paysuccess");
		window.location=jsBasePath+'order/page?status=1';
	}else{
		if(subOrderId && subOrderId!=''){
			sessionStorage.setItem("order", "paysuccess");
			window.location=jsBasePath +'orderM?subOrderId='+subOrderId;
		}else{
			showInfoBox("参数错误");
		}
	}
}
function go2more(){//换领频道
	sessionStorage.setItem("order", "paysuccess");
	window.location=jsBasePath+'huanling.html';
}
function go2Wish(){//调剂清单
	sessionStorage.setItem("order", "paysuccess");
	window.location=jsBasePath +'exchangeOrder/towishPage.user?uid='+uid;
}
function showAdjustProduct(){
	$.getScript(jsBasePath+"static_resources/js/exchangeOrder/getMaySelectable.js", function() {
		adjustProduct();
	});
}