var uid=GetUidCookie();
var isload = true;
$(document).ready(function() {
	if(!sessionCheckOrder("orderList",5)) {
		return;
	}
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true; sessionStorage.removeItem("orderInfo");});
	
	$("#pageNum").val("0");
	init();
	$(function(){
		$(window).scroll(function(){
			Load();
		});
	});
	function Load(){
		if(isload){//ajax在后台获取数据时，设值其false，防止页面多次加载
			var loadHeight = 55;//指定滚动条距离底部还有多少距离时进行数据加载
			var documentHeight = parseInt($(document).height(),10);//可视区域当前高度
			var windowHeight = parseInt(window.innerHeight,10);//窗口当前高度
			var scrollHight = parseInt($(window).scrollTop(),10);//窗口滚动条位置
			if(documentHeight - scrollHight - windowHeight < loadHeight){
				//ajax获取数据，以下为模拟
				isload = false;
				init();
			}
		}
	}
	
	//关闭选则
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
function init(){
	go2ExchangeOrder();//欲领清单
	//go2WishOrder();//心愿订单
	getMyAmount();
	
}
function go2ExchangeOrder(){
	if(uid=='') return;
	var page = parseInt($("#pageNum").val()) + 1;
	if(page<1) return;
	$.ajax({
		url : jsBasePath+'/exchangeOrder/query.user?uid='+uid+'&page='+page+'&pageSize='+5,
		type : "POST",
		dataType : "json",
		cache : false,
		success : function(data){
			var result = data.data.list;
			var html = '';
			if(result.length>0){
				$("#pageNum").val(page);
				for (var i = 0; i < result.length; i++) {
					html+='<div class="main_con">';
					html+='<div class="pp_top">';
					html+='<span>'+result[i].supplierName+'</span><em>';
					if(result[i].exchangeStatus==1){
						 html+=	'匹配中';
					}else if(result[i].exchangeStatus==2){
						 html+=	'匹配成功';
					}else if(result[i].exchangeStatus==3){
						 html+=	'匹配失败，<i>已退款</i>';
					}else if(result[i].exchangeStatus==4){
						 html+=	'匹配失败，已调剂';
					}else if(result[i].exchangeStatus==0){
						html+=	'待支付';
					}else if(result[i].status==-1){
						html+=	'已关闭';
					}
					html+='</em></div>';
					html+='<div class="pp_pro">';
					for (var j = 0; j < result[i].subOrderItems.length; j++) {
						html+='<dl>';
						html+='<dt><a href="javascript:;"><img src="'+result[i].subOrderItems[j].image+'" /></a></dt>';
						html+='<dd class="dd1"><a href="javascript:;">'+result[i].subOrderItems[j].productName+'</a></dd>';
						html+='<dd class="dd2">'+result[i].subOrderItems[j].itemValues.replace("{","").replace("}", "").replace(/"([^"]*)"/g, "$1");+'</dd>';
						html+='<dd class="dd3"><span>'+result[i].subOrderItems[j].internalPurchasePrice.toFixed(2)+'</span><i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" /></i><em>x '+result[i].subOrderItems[j].number+'</em></dd>';
						html+='</dl>';
					}
					if(result[i].exchangeStatus==0){
						html+='<div class="hlb_price"><span>应付：¥'+result[i].realPrice.toFixed(2)+'</span><em>（含运费￥'+result[i].totalShipping+'）</em></div>';
					}else{
						html+='<div class="hlb_price"><span>实付：¥'+result[i].realPrice.toFixed(2)+'+'+result[i].benefitAmount.toFixed(2)+'</span><i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" /></i><em>（含运费￥'+result[i].totalShipping+'）</em></div>';
					}
					html+='</div>';
					if(result[i].exchangeStatus==1){
						html+='<div class="btn_box"><a href="javascript:openEwm(\''+result[i].subOrderId+'\');">收货码</a><a href="javascript:go2Cancle(\''+result[i].subOrderId+'\');">取消订单</a></div>';
					}else if(result[i].exchangeStatus==2){
						html+='<div class="btn_box"><a href="javascript:go2order(\''+result[i].subOrderId+'\');">查看订单</a></div>';
					}else if(result[i].exchangeStatus==4){
						html+='<div class="btn_box"><a href="javascript:go2CKAdjustDetail(\''+result[i].batchId+'\');">查看调剂详情</a></div>';
					}else if(result[i].exchangeStatus==0){
						var cashPay = result[i].cashPay;
						if (cashPay==''||typeof(cashPay)==undefined) {
							cashPay=0;
						}
						var totalAdjustment = result[i].totalAdjustment;
						if (totalAdjustment==''||typeof(totalAdjustment)==undefined) {
							totalAdjustment=0;
						}
						var any = result[i].realPrice-cashPay-totalAdjustment;
						html+='<div class="btn_box"><a href="javascript:go2Pay(\''+result[i].subOrderId+'\',\''+any+'\',\''+result[i].status+'\');">立即支付</a><a href="javascript:go2Cancle(\''+result[i].subOrderId+'\',\''+result[i].exchangeStatus+'\');">取消订单</a></div>';
					}else if(result[i].exchangeStatus==-1){
						html+='<div class="btn_box"><a href="javascript:go2Del(\''+result[i].subOrderId+'\');">删除订单</a></div>';
					}
					html+='</div>';
				}
				$("#exchangOrderList").append(html);
			}else {
				$("#pageNum").val(-1);
				var html='';
				$("#exchangOrderList").append(html);
			}
			isload = true;
		}
	});
}

function openEwm(orderId){
	var url=jsBasePath+"exchangeOrder/exchangeOrderDetailPageEx.user?subOrderId="+orderId;
	$(".t_ewm").html('<img src=\''+jsBasePath+'userShare/getQr?text='+url+'\'/>')
	$(".thickdiv").show();
	$(".t_ewm").show();
}
function getMyAmount(){
	if(uid=='') return;
	$.ajax({
		url : jsBasePath+'/exchangeOrder/getExchageTicket.user?uid='+uid,
		type : "POST",
		dataType : "json",
		cache : false,
		success : function(data){
			if(data.success){
				var balance= data.data.balance;
				var total = data.data.total;
				$("#balance span").html('换领币余额：'+balance.toFixed(2));
				$("#total span").html('总额：'+total.toFixed(2));
			}
		}
	});
}
function go2WishOrder(){//调剂清单
	window.location = jsBasePath +'exchangeOrder/towishPage.user?uid='+uid
}

function go2order(subOrderId){//查看订单
	window.location=jsBasePath +'orderM?subOrderId='+subOrderId;
}

function go2CKAdjustDetail(batchId){//查看调剂详情
	if(batchId && batchId!=null && batchId!=''){
		window.location=jsBasePath +'exchangeOrder/batchPage.user?uid='+uid+'&batchId='+batchId;
	}
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
	window.location= jsBasePath+'pay/page.user?uid='+uid+'&subOrderId='+subOrderId+'&orderType='+orderType+'&totalFee='+totalFee+'&pruductId='+pruductId+'&type=5';
}

function go2Cancle(subOrderId,exchangeStatus){//取消订单
	$(".thickdiv").show();
	$(".orderbox").show();
	$(".main-cont").attr("style","position:fixed");
	$("#suborderID").val(subOrderId);
	$("#exchangeStatus").val(exchangeStatus);
}
var closeReason = null;
function toCancel(){
	closeReason = $(".close_list_con li .em1").prev().html();
	if (closeReason == undefined || closeReason == null || closeReason=='') {
		showInfoBox("未选择取消订单原因");
		setTimeout("closeMsg()", 1000)
	}else{
		if (exchangeStatus=='0') {//未支付订单
			cancel();
		}else{
			showConfirm("退款将返还至您的原支付账户","cancel()")
		}
	}
}
function cancel(){
	var subOrderId=$("#suborderID").val();
	$.ajax({
		url : jsBasePath+'exchangeOrder/cancelOrder.user?uid='+uid+'&subOrderId='+subOrderId+'&closeReason='+closeReason,
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
function refresh(){
	//location.reload();
	$("#exchangOrderList").html('');
	$("#pageNum").val(0);
	init();
	
}
function go2Del(subOrderId){//删除订单
	showConfirm("确定删除订单?删除之后不可恢复","del('"+subOrderId+"')");
}
function del(subOrderId){
	$.ajax({
	url : jsBasePath+'exchangeOrder/deleteOrder.user?uid='+uid+'&subOrderId='+subOrderId,
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