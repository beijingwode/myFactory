
var isload = true;
uid=GetUidCookie();

// JavaScript Document
$(document).ready(function() {

	if(!sessionCheckOrder("orderList",0)) {
		return;
	}
	if(isWeiXinOpen()) {
		if (status==0) {//待支付
			loginCheck(4);
		}else if(status==2){//待收货
			loginCheck(5);
		}
    }
	
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true;});
	
	/*if(window.location.href.indexOf("from=pay")>-1) {
		showInfoBox("24小时未支付的订单,系统将自动取消。","setHash()");
	}*/
	$("#pageNum").val("0");
	ajaxOrderData();
	$(function(){
		$(window).scroll(function(){
			Load();
		});
	});
	function Load(){
		if(isload){//ajax在后台获取数据时，设值其false，防止页面多次加载
			var loadHeight = 115;//指定滚动条距离底部还有多少距离时进行数据加载
			var documentHeight = parseInt($(document).height(),10);//可视区域当前高度
			var windowHeight = parseInt(window.innerHeight,10);//窗口当前高度
			var scrollHight = parseInt($(window).scrollTop(),10);//窗口滚动条位置
			if(documentHeight - scrollHight - windowHeight < loadHeight){
				//ajax获取数据，以下为模拟
				isload = false;
				
				ajaxOrderData();
			}
		}
	}
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	$(".top").click(function(e){//点击返回
		window.location.href=jsBasePath+'user/page?uid='+uid;
	});
	
	//关闭选则地区
	$(".thickclose").click(function(e){
		$(".thickdiv").hide();
		$(".orderbox").hide();
		$(".main-cont").removeAttr("style");
	});
	$(".thickdiv").click(function(e){
		$(".thickdiv").hide();
		$(".orderbox").hide();
		$(".main-cont").removeAttr("style");
	});
});

function setHash() {
	location.hash = "win";
}
var userSupplierId=getSupplierIdCookie();
function ajaxOrderData(){
	var page = parseInt($("#pageNum").val()) + 1;
	if(page<1) return;
	$.ajax({
		url : jsBasePath+'order/query.user?uid='+uid+'&status='+status+'&page='+page+'&pageSize='+10,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			var result = data.data.list;
			if (result.length>0) {
				$("#pageNum").val(page);
				var html='';
				for (var i = 0; i < result.length; i++) {
					html +='<div class="order_box">';
					html +='<div class="order_top"><p>';
					if(5==result[i].orderType) {
						html +='[换领]</p><p>&nbsp;';
					} else if(4==result[i].orderType || 1==result[i].orderType) {
						html +='[一起购]</p><p>&nbsp;';
					}
					if (userSupplierId!=''&& userSupplierId==result[i].supplierId) {
						html +='自家';
					}else{
						html +=result[i].supplierName;
					}
					html +='</p><span>';
					var returnorderList = result[i].returnorderList;
					if (result[i].status==0) {
						html+='待支付';
					}else if(result[i].status==1){
						if (result[i].stockUp==1) {
							html+='备货中';
						}else{
							html+='待发货';
						}
					}else if(result[i].status==2){
						html+='待收货';
					}else if(result[i].status==3){
						html+='退货退款中';
					}else if(result[i].status==5){
						html+="仅退款中"
					}else if(result[i].status==4 && (result[i].commentStatus==''||result[i].commentStatus==null)){
						html+='已收货';
					}else if(result[i].commentStatus==1&&result[i].status==4){
						html+='已评价';
					}else if(result[i].status==-1){
						html+='已关闭';
					}else if(result[i].status==11){
						html+='退货退款成功';
					}else if(result[i].status==12){
						html+='仅退款成功';
					}else if(returnorderList && returnorderList[0].status==4){
						html+='买家发出退货';
					}else if(result[i].status==13){
						html+='商家同意退货';
					}else if(result[i].status==14){
						html+='商家拒绝退货';
					}else if(result[i].status==15){
						html+='商家同意退款';
					}else if(result[i].status==16){
						html+='商家拒绝退款';
					}
					html +='</span></div>';
					html +='<ul>';
					var totalNumber=0;
					var subOrderItems = result[i].subOrderItems;
					var rprice = 0;
					var isAfterSaleOrder =false;
					if(result[i].status==3||result[i].status==5||result[i].status==11||result[i].status==12||result[i].status==13||result[i].status==14||result[i].status==15||result[i].status==16||result[i].status==-11||result[i].status==-12){
						isAfterSaleOrder =true;
					}
					
					for (var j = 0; j < subOrderItems.length; j++) {
						//if(!isAfterSaleOrder){
							html +='<li onclick="go2order(\''+result[i].subOrderId+'\');">';
						//}else{
							//html +='<li>';
						//}
						html +='<dl>';
						if(!isAfterSaleOrder){
							html +='<dt><a href="javascript:go2order(\''+result[i].subOrderId+'\');"><img src="'+subOrderItems[j].image+'"/></a>';
						}else{
							html +='<dt><a href="javascript:void(0)"><img src="'+subOrderItems[j].image+'"/></a>';
						}
						if(subOrderItems[j].saleKbn==1) {
							html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon2.png" /></div>';
						} else if(subOrderItems[j].saleKbn==2) {
							html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_c2.png" /></div>';
						} else if(subOrderItems[j].saleKbn==4) {
							html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_z2.png" /></div>';
						}else if(subOrderItems[j].saleKbn==5) {
							html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_t2.png" /></div>';
						}
						html +='</dt>';
						if(!isAfterSaleOrder){
							html +='<dd class="dd1"><a href="javascript:go2order(\''+result[i].subOrderId+'\');">'+subOrderItems[j].productName+'</a></dd>';
						}else{
							html +='<dd class="dd1"><a href="javascript:void(0);">'+subOrderItems[j].productName+'</a></dd>';
						}
						html +='<dd class="dd2">'+subOrderItems[j].itemValues.replace("{","").replace("}","").replace(/\"/g,"")+'</dd>';
						html +='</dl>';
						if(subOrderItems[j].internalPurchasePrice && subOrderItems[j].internalPurchasePrice != null){
							rprice = subOrderItems[j].internalPurchasePrice;
						}else{
							rprice = subOrderItems[j].price*subOrderItems[j].number-subOrderItems[j].companyTicket;
						}
						html +='<div class="price"><span>￥'+((rprice).toFixed(2))+'</span><i>x'+subOrderItems[j].number+'</i><em>'+(parseFloat(subOrderItems[j].companyTicket).toFixed(2))+'券</em></div>';
						html +='</li>';
						totalNumber += subOrderItems[j].number
					}
					html +='</ul>';
					html +='<div class="order_bottom">共<span>'+totalNumber+'</span>件商品，合计：<span>￥'+(result[i].realPrice.toFixed(2))+'</span>（含运费：<span>'+result[i].totalShipping+'</span>）</div>';
					if (result[i].status==0) {//待支付
						var cashPay = result[i].cashPay;
						if (cashPay==''||typeof(cashPay)==undefined) {
							cashPay=0;
						}
						var any = result[i].realPrice-cashPay;
						html +='<div class="order_btn"><a href="javascript:go2Pay(\''+result[i].subOrderId+'\',\''+any+'\',\''+result[i].status+'\');" class="btn_ren">付款</a><a href="javascript:go2Cancel(\''+result[i].subOrderId+'\');">取消</a></div>';
					}else if(result[i].status==1){//待发货
						if (result[i].stockUp==1) {
							if (result[i].selfDelivery==1) {
								html +='<div class="order_btn"><a href="javascript:go2Confirm(\''+result[i].subOrderId+'\');" class="btn_ren">自提•收货</a></div>';
							}else{
								html +='<div class="order_btn"><a href="javascript:go2TX(\''+result[i].subOrderId+'\');" class="btn_ren">提醒发货</a>\</div>';
							}
						}else{
							if (result[i].selfDelivery==1) {
								html +='<div class="order_btn"><a href="javascript:go2Confirm(\''+result[i].subOrderId+'\');" class="btn_ren">自提•收货</a><a href="javascript:go2Cancel(\''+result[i].subOrderId+'\');">取消</a></div>';
							}else{
								html +='<div class="order_btn"><a href="javascript:go2TX(\''+result[i].subOrderId+'\');" class="btn_ren">提醒发货</a><a href="javascript:go2Cancel(\''+result[i].subOrderId+'\');">取消</a></div>';
							}
						}
					}else if(result[i].status==2){//待收货
						if (result[i].userExetendCount>0&&result[i].userExetendCount!=undefined) {
							if (result[i].selfDelivery==1) {
								html +='<div class="order_btn"><a href="javascript:go2Confirm(\''+result[i].subOrderId+'\');" class="btn_ren">确认收货</a></div>';
							}else if(result[i].expressType=='14660000000000001'){//厂家直送
								html +='<div class="order_btn"><a href="javascript:go2Confirm(\''+result[i].subOrderId+'\');" class="btn_ren">确认收货</a><a href="javascript:go2order(\''+result[i].subOrderId+'\');" >厂家直送</a></div>';
							}else if(result[i].expressType=='14660000000000000'){//电子卡券
								html +='<div class="order_btn"><a href="javascript:go2Confirm(\''+result[i].subOrderId+'\');" class="btn_ren">确认收货</a><a href="javascript:go2order(\''+result[i].subOrderId+'\');" >电子卡券</a></div>';
							}else{
								html +='<div class="order_btn"><a href="javascript:go2Confirm(\''+result[i].subOrderId+'\');" class="btn_ren">确认收货</a><a href="javascript:go2CKShipping(\''+result[i].subOrderId+'\',\''+result[i].expressType+'\',\''+result[i].expressNo+'\');">查看物流</a></div>';
							}
						}else{
							if (result[i].selfDelivery==1) {
								html +='<div class="order_btn"><a href="javascript:go2Confirm(\''+result[i].subOrderId+'\');" class="btn_ren">确认收货</a><a href="javascript:go2order(\''+result[i].subOrderId+'\');">自提订单</a><a href="javascript:go2YC(\''+result[i].subOrderId+'\');">延长收货</a></div>';
							}else if(result[i].expressType=='14660000000000001'){
								html +='<div class="order_btn"><a href="javascript:go2Confirm(\''+result[i].subOrderId+'\');" class="btn_ren">确认收货</a><a href="javascript:go2order(\''+result[i].subOrderId+'\');">厂家直送</a><a href="javascript:go2YC(\''+result[i].subOrderId+'\');">延长收货</a></div>';
							}else if(result[i].expressType=='14660000000000000'){
								html +='<div class="order_btn"><a href="javascript:go2Confirm(\''+result[i].subOrderId+'\');" class="btn_ren">确认收货</a><a href="javascript:go2order(\''+result[i].subOrderId+'\');">电子卡券</a><a href="javascript:go2YC(\''+result[i].subOrderId+'\');">延长收货</a></div>';
							}else{
								html +='<div class="order_btn"><a href="javascript:go2Confirm(\''+result[i].subOrderId+'\');" class="btn_ren">确认收货</a><a href="javascript:go2CKShipping(\''+result[i].subOrderId+'\',\''+result[i].expressType+'\',\''+result[i].expressNo+'\');">查看物流</a><a href="javascript:go2YC(\''+result[i].subOrderId+'\');">延长收货</a></div>';
							}
						}
					}else if(result[i].status==4 &&(result[i].commentStatus==''||result[i].commentStatus==null)){//待评价
						if (result[i].selfDelivery==1) {
							html +='<div class="order_btn"><a href="javascript:go2PJ(\''+result[i].subOrderId+'\');" class="btn_ren">评价</a><a href="javascript:go2order(\''+result[i].subOrderId+'\');">自提订单</a><a href="javascript:void(0);" onclick="go2Del(\''+result[i].subOrderId+'\');">删除订单</a></div>';
						}else if(result[i].expressType=='14660000000000001'){
							html +='<div class="order_btn"><a href="javascript:go2PJ(\''+result[i].subOrderId+'\');" class="btn_ren">评价</a><a href="javascript:go2order(\''+result[i].subOrderId+'\');">厂家直送</a><a href="javascript:void(0);" onclick="go2Del(\''+result[i].subOrderId+'\');">删除订单</a></div>';
						}else if(result[i].expressType=='14660000000000000'){
							html +='<div class="order_btn"><a href="javascript:go2PJ(\''+result[i].subOrderId+'\');" class="btn_ren">评价</a><a href="javascript:go2order(\''+result[i].subOrderId+'\');">电子卡券</a><a href="javascript:void(0);" onclick="go2Del(\''+result[i].subOrderId+'\');">删除订单</a></div>';
						}
						else{
							html +='<div class="order_btn"><a href="javascript:go2PJ(\''+result[i].subOrderId+'\');" class="btn_ren">评价</a><a href="javascript:go2CKShipping(\''+result[i].subOrderId+'\',\''+result[i].expressType+'\',\''+result[i].expressNo+'\');" class="btn_ren">查看物流</a><a href="javascript:void(0);" onclick="go2Del(\''+result[i].subOrderId+'\');">删除订单</a></div>';
						}
					}else if(result[i].commentStatus==1&&result[i].status==4){//已评价
						if (result[i].selfDelivery==1) {
							html +='<div class="order_btn"><a href="javascript:void(0);" onclick="go2Del(\''+result[i].subOrderId+'\');">删除订单</a><a href="javascript:go2order(\''+result[i].subOrderId+'\');" class="btn_ren">自提订单</a></div>';
						}else if(result[i].expressType=='14660000000000001'){
							html +='<div class="order_btn"><a href="javascript:void(0);" onclick="go2Del(\''+result[i].subOrderId+'\');">删除订单</a><a href="javascript:go2order(\''+result[i].subOrderId+'\');" class="btn_ren">厂家直送</a></div>';
						}else if(result[i].expressType=='14660000000000000'){
							html +='<div class="order_btn"><a href="javascript:void(0);" onclick="go2Del(\''+result[i].subOrderId+'\');">删除订单</a><a href="javascript:go2order(\''+result[i].subOrderId+'\');" class="btn_ren">电子卡券</a></div>';
						}
						else{
							html +='<div class="order_btn"><a href="javascript:go2CKShipping(\''+result[i].subOrderId+'\',\''+result[i].expressType+'\',\''+result[i].expressNo+'\');"class="btn_ren">查看物流</a><a href="javascript:void(0);" onclick="go2Del(\''+result[i].subOrderId+'\');">删除订单</a></div>';
						}
					}else if(result[i].status==11||result[i].status==12){//退款完毕
						html +='<div class="order_btn"><a href="javascript:go2Del(\''+result[i].subOrderId+'\');">删除订单</a></div>';
					}else if(result[i].status==3||result[i].status==5){//退款申请中
						html +='<div class="order_btn"><a href="javascript:go2AfterOrderDetails(\''+result[i].subOrderId+'\');" class="btn_ren">售后详情</a></div>';
					}else if(result[i].status==-1){//关闭
						html +='<div class="order_btn"><a href="javascript:void(0);" onclick="go2Del(\''+result[i].subOrderId+'\');">删除订单</a></div>';
					}else if(returnorderList && returnorderList[0].status==2){
						html +='<div class="order_btn"><a href="javascript:go2AfterOrderDetails(\''+result[i].subOrderId+'\');" class="btn_ren">填写退货信息</a></div>';
					}else{
						html +='<div class="order_btn"><a href="javascript:go2AfterOrderDetails(\''+result[i].subOrderId+'\');" class="btn_ren">售后详情</a></div>';
					}
					html +='</div>';
					
				}
				$(".main-box").append(html);
			}else {
				$("#pageNum").val(-1);
				var html='';
				html +='<div class="bottom_hint">已经浏览到最后了</div>';
				$(".main-box").append(html);
			}
			isload = true;
		},
		error : function() {}
	});
}
function go2order(subOrderId){//订单详情
	$("#page").val(1);
	window.location=jsBasePath +'orderM?subOrderId='+subOrderId;
}
function go2Pay(subOrderId,totalFee,status){//付款
	var orderType;
	var pruductId='';
	if (status==0) {//未支付订单
		orderType='0';
	}else{//全部订单
		orderType='';
	}
	sessionSetOrderStep("orderPay");
	window.location= jsBasePath+'pay/page.user?uid='+uid+'&subOrderId='+subOrderId+'&orderType='+orderType+'&totalFee='+totalFee+'&pruductId='+pruductId;
}
function go2Del(subOrderId){//删除订单
	showConfirm("确定删除订单?删除之后不可恢复","del('"+subOrderId+"')");
}
function del(subOrderId){
	$.ajax({
	url : jsBasePath+'order/deleteOrder.user?uid='+uid+'&subOrderId='+subOrderId,
	type : "GET",
	dataType: "json",  //返回json格式的数据  
    async: false,
    cache:false,
	success : function(data) {
		if (data.success) {
			refresh();//刷新页面
		}else{
			showInfoBox(data.msg);
		}
	}
});
}
function go2Cancel(subOrderId){//取消订单
	//window.location=jsBasePath+"close_order_list.html?subOrderId="+subOrderId+'&status='+status;
	//showConfirm("确认取消订单？取消之后不可恢复","cancel('"+subOrderId+"')");
	$(".thickdiv").show();
	$(".orderbox").show();
	$(".main-cont").attr("style","position:fixed");
	$("#suborderID").val(subOrderId);
}
var closeReason = null;
function toCancel(){
	closeReason = $(".close_list_con li .em1").prev().html();
	if (closeReason == undefined || closeReason == null || closeReason=='') {
		showInfoBox("未选择取消订单原因");
		setTimeout("closeMsg()", 1000)
	}else{
		if (status=='0') {//未支付订单
			cancel();
		}else{
			showConfirm("退款将返还至您的原支付账户","cancel()")
		}
	}
}
function cancel(){
	var subOrderId=$("#suborderID").val();
	$.ajax({
		url : jsBasePath+'order/cancelOrder.user?uid='+uid+'&subOrderId='+subOrderId+'&closeReason='+closeReason,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				$(".thickdiv").hide();
				$(".orderbox").hide();
				$(".main-cont").removeAttr("style");
				refresh();//刷新页面
			}else{
				showInfoBox(data.msg);
			}
		}
	});
}
function go2TX(subOrderId){//提醒发货
	$.ajax({
		url : jsBasePath+'order/urgedDelivery.user?uid='+uid+'&subOrderId='+subOrderId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				showInfoBox("提醒成功!","refresh()");
				//refresh();//刷新页面
			}else{
				showInfoBox(data.msg)
			}
		}
	});
}
function go2Confirm(subOrderId){//确认收货
	showConfirm("确认订单？确认之后商家会收到钱","confirm('"+subOrderId+"')");
}
function confirm(subOrderId){
	$.ajax({
		url : jsBasePath+'order/confirmOrder.user?uid='+uid+'&subOrderId='+subOrderId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				refresh();//刷新页面
			}else{
				showInfoBox(data.data);
			}
		}
	});
}
function go2PJ(subOrderId){//评价页面
	window.location=jsBasePath+'order/comments/getSubOrderInfo?uid='+uid+'&subOrderId='+subOrderId;
}
function go2CKShipping(subOrderId,expressType,expressNo){//查看物流
	window.location=jsBasePath+'logistics?userId='+uid+'&subOrderId='+subOrderId+'&expressType='+expressType+'&expressNo='+expressNo;
}
function go2YC(subOrderId){//延长收货
	showConfirm("确认延长收货时间？只能延长一次","yanchang('"+subOrderId+"')");
}
function yanchang(subOrderId){
	$.ajax({
		url : jsBasePath+'order/extendedReceipt.user?uid='+uid+'&subOrderId='+subOrderId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				refresh();
			}else{
				showInfoBox(data.msg);
			}
		}
	});
}
function refresh(){
	//location.reload();
	$(".main-box .order_box").remove();
	$(".bottom_hint").remove();
	$("#pageNum").val(0);
	ajaxOrderData();
	
}
function go2AfterOrderDetails(subOrderId){
	window.location=jsBasePath+'orderM/after_SaleDetails?uid='+uid+'&subOrderId='+subOrderId;
}
function go2Service(subOrderId,realPrice){
	if(uid=='') return;
	$.ajax({//获取返现金额
		url : jsBasePath+'order/getTrialReturn.user?uid='+uid+'&subOrderId='+subOrderId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				var returnPrice = data.data;
				if (returnPrice==''||returnPrice==null||typeof(returnPrice)==undefined) {
					returnPrice=0;
				}
				//if (commentStatus=="1") {//已评价
					realPrice =(realPrice-returnPrice).toFixed(2);
				//}
			}
			window.location=jsBasePath+'orderM/applyReturn?subOrderId='+subOrderId+'&realPrice='+realPrice;
		}
	});
}