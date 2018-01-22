
//var uid=GetUidCookie();
// JavaScript Document
$(document).ready(function() {	
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	/**
	 * 微信ios history.back页面不刷新
	 */
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true;});
	
	ajaxOrderDeatilsData();
	$(".top").click(function(e){//点击返回
		window.location.href=jsBasePath+'user/page?uid='+uid;
	});
//	 $("#hlj img").click(function(e){
//		  $(".theme-popover-mask2").show();
//		  $(".popover2").show();
//	  });
	 $(".thickdiv").click(function(e){
		$(".thickdiv").hide();
		$(".orderbox").hide();
		$(".smallPopup").hide();
	});
});
var expressNo='';//物流单号
var expressName='';//物流公司
var userSupplierId=getSupplierIdCookie();
var hasTestProdut=false;//标识是否有使用商品
var realPrice = '';
var isStockUp =false;
var invoiceStatus ='';
function ajaxOrderDeatilsData(){
	if(uid=="") return;
	$.ajax({
		url : jsBasePath+'exchangeOrder/exchangeOrderDetail.user?uid='+uid+'&subOrderId='+subOrderId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			var result = data.data.order;
			var refund = data.data.refund;
			var returnOrder = data.data.return;
			invoiceStatus = result.invoiceStatus;//订单发票状态
			var html='';
			if (result!=null) {
				$("#name").html(result.name);//姓名
				$("#orderStatus").val(result.status)//订单状态
				realPrice = result.realPrice
				if (result.mobile!=null) {
					$("#mobile").html(result.mobile);//手机
				}
				$(".p2").html(result.address);//地址
				if (result.status=='-1'||result.status=='0'||result.status=='1'||result.selfDelivery==1||result.express==undefined) {
					$(".logistics").hide();
				}else{
					if (result.express!=undefined&&result.express!=null) {
						expressName = result.express.pinYin;//物流公司
						expressNo = result.expressNo;//物流单号
					}
					if (expressName!='') {//物流名称
						$(".dd1 span").html(result.express.name);
					}
					if (expressNo!='') {//物流单号
						$(".dd1 em").html(result.expressNo);
					}
					if (expressName!=''&&expressNo!='') {
						if (result.expressType!='14660000000000000'&&result.expressType!='14660000000000001') {//厂家直送或电子卡券
							ajaxlistlogInfo(expressName,expressNo);
						}
					}
					if (result.expressType!='14660000000000000'&&result.expressType!='14660000000000001') {//厂家直送或电子卡券
						$("#xinxi a").attr("href","javascript:go2CKShipping("+result.subOrderId+","+result.expressType+","+result.expressNo+");");
					}else{
						$("#dd2").hide();
						$(".logistics").css("height","auto");
					}
					$(".logistics").show();
				}
				var e_cardInfo = result.e_cardInfo;//电子卡券
				if(typeof(e_cardInfo)!=undefined && e_cardInfo!=null && e_cardInfo!=''){
					$(".e_card").show();
					$(".address").hide();
					$(".logistics").hide();
					e_cardInfo = $.parseJSON(e_cardInfo.toString().replace(/[\r\n]/g, ""));
					$("#e_card_info dd a").attr("href",e_cardInfo.url+'?pw='+e_cardInfo.pw);
					//$("#e_cardUrl").val(e_cardInfo.url);
				}
				if (userSupplierId!=''&& userSupplierId == result.supplierId) {
					$(".order_top p").html("商铺：自家");
				}else{
					$(".order_top p").html("商铺："+result.supplierName);
				}
				var subOrderItems = result.subOrderItems;
				var totalHLJ =0;//总的换领券
				var totalproduct = 0;//总的商品内购价;
				if (subOrderItems.length>0) {
					 var specificationsId='';
					 var rprice = 0;
					for (var i = 0; i < subOrderItems.length; i++) {
						if(subOrderItems[i].partNumber && typeof(subOrderItems[i].partNumber)!="undefined"){
							specificationsId = subOrderItems[i].partNumber;
						}
						html +='<li>';
						html +='<dl>';
						html +='<dt>';
						html +='<a href="javascript:go2p(\''+subOrderItems[i].productId+'&specificationsId='+specificationsId+'\');"><img src="'+subOrderItems[i].image+'"/></a>';
						if(subOrderItems[i].saleKbn==1) {
							html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon2.png" /></div>';
						} else if(subOrderItems[i].saleKbn==2) {
							html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_c2.png" /></div>';
						} else if(subOrderItems[i].saleKbn==4) {
							html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_z2.png" /></div>';
						} else if(subOrderItems[i].saleKbn==5) {
							html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_t2.png" /></div>';
							if(typeof(subOrderItems[i].commentFlag)==undefined || subOrderItems[i].commentFlag=='0'){
								hasTestProdut = true;
							}
						}
						html +='</dt>';
						html +='<dd class="dd1"><a href="javascript:go2p(\''+subOrderItems[i].productId+'&specificationsId='+specificationsId+'\');">'+subOrderItems[i].productName+'</a></dd>';
						html +='<dd class="dd2">'+subOrderItems[i].itemValues.replace("{","").replace("}","").replace(/\"/g,"")+'</dd>';
						html +='</dl>';
						if(subOrderItems[i].internalPurchasePrice && subOrderItems[i].internalPurchasePrice != null){
							rprice = subOrderItems[i].internalPurchasePrice ;
						}else{
							rprice = subOrderItems[i].price-subOrderItems[i].companyTicket/subOrderItems[i].number ;
						}
						html +='<div class="price"><span>￥'+(rprice.toFixed(2))+'</span><i>x'+subOrderItems[i].number+'</i><em>'+(subOrderItems[i].companyTicket.toFixed(2))+'券</em></div>';
						html +='</li>';
						totalproduct +=(rprice*subOrderItems[i].number);
						totalHLJ+=subOrderItems[i].benefitTicket;
						if (subOrderItems[i].benefitType!=undefined &&subOrderItems[i].benefitType==3) {
							$("#hlj").show();
						}else{
							$("#hlj").hide();
						}
					}
					$(".order_box ul").append(html);
//					if (result.shopPhone!=''||result.shopPhone!=null) {
//						$(".tel a").attr("href","tel:"+result.shopPhone);
//					}else if(result.shopTel!=''||result.shopTel!=null){
//						$(".tel a").attr("href","tel:"+result.shopTel);
//					}
					$("#pz em").html('￥'+(totalproduct.toFixed(2)))
					//$("#pz em").html((result.totalProduct.toFixed(2)-result.totalShipping.toFixed(2)).toFixed(2));
					/*$("#flj em").html('-'+result.companyTicket.toFixed(2));*/
					//换领券
					$("#hlj span").html("换领券(使用"+totalHLJ.toFixed(2)+")")
					$("#hlj em").html('-'+'￥'+(result.benefitAmount.toFixed(2)));
					if (result.benefitAmount<totalHLJ) {
//						$("#hlj img").attr("src",jsBasePath+"static_resources/images/help.png");
//						$("#hlj img").show();
//						$(".theme_popover_con2 p em").html(totalHLJ-result.benefitAmount);
					}
					//运费
					$("#yf em").html('￥'+(result.totalShipping.toFixed(2)));
					//应付金额
					$("#Payable i").html('￥'+(totalproduct+result.totalShipping).toFixed(2));
					//实付金额
					$("#realPay i").html('￥'+result.realPrice.toFixed(2));
					if (result.status==0) {//待支付
						$("#cashPay i").html('￥'+result.cashPay.toFixed(2));
					}else{
						$("#cashPay").hide();
					}
					if (result.status==3||result.status==5) {
						$("#returnPay i").html('￥'+refund.refundPrice.toFixed(2));
					}else{
						$("#returnPay").hide();
					}
					if (result.status==2) {
						$("#distanceAutomaticConfirm em").html('还剩'+result.distanceAutomaticConfirm);
					}else{
						$("#distanceAutomaticConfirm").hide();
					}
					$("#subOrderId").html('订单编号：'+result.subOrderId);
					var createTime = result.createTime;
					$("#createTime").html('创建时间：'+getDate(createTime));
					if (result.payTime!=undefined) {
						$("#payTime").html('付款时间：'+getDate(result.payTime));
					}else{
						$("#payTime").hide();
					}
					if (result.sendTime!=undefined) {
						$("#sendTime").html('发货时间：'+getDate(result.sendTime));
					}else{
						$("#sendTime").hide();
					}
					if (result.selfDelivery==1) {//自提发货时间隐藏
						$("#sendTime").hide()
					}
					if (result.status==3||result.status==5||result.status==11||result.status==12) {
						if (refund!='') {
							$("#serviceTime").html('申请退款时间：'+getDate(refund.createTime))
						}
					}else{
						$("#serviceTime").hide();
					}
					var html='';
					if (result.status==0 && result.orderType != 5) {//待支付
							html += '<div class="order_btn" id="order_btn">';
							html +='<a href="javascript:go2Cancel(\''+result.subOrderId+'\');" class="btn_ren">取消订单</a>';
							html+='</div>'
					}else if(result.status==1){//已支付
						html += '<div class="order_btn" id="order_btn">';
						html +='<a href="javascript:go2Confirm(\''+result.subOrderId+'\');" class="btn_ren">确认发货</a>';
						html +='<a href="javascript:go2Cancel(\''+result.subOrderId+'\');" class="btn_ren">取消订单</a>';
						html+='</div>'
					}else if(result.status==2 && result.orderType != 5){//已发货
						html += '<div class="order_btn" id="order_btn">';
						html +='<a href="javascript:go2Cancel(\''+result.subOrderId+'\');" class="btn_ren">取消订单</a>';
						html+='</div>'
					}else if(result.status==4){//已收货
						html += '<div class="order_btn" id="order_btn">';
						html +='<a href="javascript:void(0);" class="btn_ren">已收货</a>';
						html +='</div>'
					}else if(result.status==11){//退货退款完毕
						html += '<div class="order_btn" id="order_btn">';
						html +='<a href="javascript:void(0);" class="btn_ren">已退货退款完毕</a>';
						html +='</div>'
					}else if(result.status==12){//仅退款完毕
						html += '<div class="order_btn" id="order_btn">';
						html +='<a href="javascript:void(0);" class="btn_ren">已仅退款完成</a>';
						html +='</div>'
					}else if(result.status==3){//退货退款
						html += '<div class="order_btn" id="order_btn">';
						html +='<a href="javascript:void(0);" class="btn_ren">退货退款申请中</a>';
						html +='</div>'
					}else if(result.status==5){//仅退款
						html += '<div class="order_btn" id="order_btn">';
						html +='<a href="javascript:void(0);" class="btn_ren">仅退款申请中</a>';
						html +='</div>'
//					}else if(returnOrder && returnOrder.status==2){
//					}else if(returnOrder && returnOrder.status==4){
//					}else if(returnOrder && returnOrder.status==3){
					}else if(result.status==15){
						html +='<div class="order_btn"><a href="javascript:void(0);">商家同意退款</a></div>';
					}else if(result.status==16){
						html +='<div class="order_btn"><a href="javascript:void(0);">商家拒绝退款</a></div>';
					}else if(result.status==-1){//关闭
						html += '<div class="order_btn" id="order_btn">';
						html +='<a href="javascript:void(0);" class="btn_ren">已关闭</a>';
						html +='</div>'
					}
					$(".main-box").append(html);
				}
			}
		},
		error : function() {}
	});
}



function go2Cancel(subOrderId){//取消订单
	$(".thickdiv").show();
	var html = "";
	html += '<p class="t_p1">是否取消订单？</p>';
	html += '<p class="t_p2">该操作不可逆</p>';
	html += '<div class="t_btns">';
	html += '	<a href="javascript:hide();" class="no_btn">不</a>';
	html += '	<a href="javascript:canOrConfirmOrder(\''+subOrderId+'\',0);" class="yes_btn">取消订单</a>';
	html += '</div>';
	$(".t_btn_box").html(html);
	$(".t_btn_box").show();
}
function go2Confirm(subOrderId){//确认收货
	$(".thickdiv").show();
	var html = "";
	html += '<p class="t_p1">是否确定发货？</p>';
	html += '<p class="t_p2">该操作不可逆</p>';
	html += '<div class="t_btns">';
	html += '	<a href="javascript:hide();" class="no_btn">不</a>';
	html += '	<a href="javascript:canOrConfirmOrder(\''+subOrderId+'\',1);" class="yes_btn">确定发货</a>';
	html += '</div>';
	$(".t_btn_box").html(html);
	$(".t_btn_box").show();
}

function canOrConfirmOrder(subOrderId,type){
	$.ajax({
		url : jsBasePath+'managerOrderRecord/addManagerOrderRecord.user?uid='+uid+'&subOrderId='+subOrderId+"&operationStatus="+type+"&flag="+type,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				showInfoBox("提交成功!","refresh()")
				//refresh();//刷新页面
				//$(".thickdiv").hide();
				//$(".t_btn_box").hide();
				window.location.href=jsBasePath+"managerOrderRecord/toOfflineRecordPage.user?uid="+uid;
			}else{
				showInfoBox(data.msg)
			}
		}
	});
}


function getDate(createTime){
	var tt=new Date(parseInt(createTime))
	Y = tt.getFullYear() + '-';
	M = (tt.getMonth()+1 < 10 ? '0'+(tt.getMonth()+1) : tt.getMonth()+1) + '-';
	D = tt.getDate() + ' ';
	h = tt.getHours() + ':';
	m = (tt.getMinutes()<10 ? '0'+(tt.getMinutes()) :tt.getMinutes())+ ':';
	s = (tt.getSeconds()<10 ? '0'+(tt.getSeconds()) :tt.getSeconds());
    return   Y+M+D+h+m+s;     
}
function ajaxlistlogInfo(expressName,expressNo){//获取物流信息
	$.ajax({
        url: jsBasePath+'express/search',
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		data: {"expressCom":expressName,"express_no":expressNo,"user":uid},
		success : function(data) {
			var json = data.data; 
	    	var ary = eval(json.body.history);
	    	if(ary.length > 0) {
	    		$("#xinxi p").html(ary[0].des);
	    		$("#xinxi i").html(ary[0].dealDate);
	    	}
		}
	});
}
function go2p(url){
	window.location = jsBasePath+'productm?productId='+url+'&from=a';
}

function go2Service(subOrderId,realPrice,commentStatus){
	var status=$("#orderStatus").val();
	if(hasTestProdut){
		if(status==1 ||status==2){
			$(".popover3 .theme_popover_con2 p").html("此订单含有试用商品,您需要收到货后评价方可申请售后,评价后获取试用返现,不影响申请售后.");
			$(".theme-popbod_know").html('<a style="width:70%" href="javascript:void(0);" onclick="closePo2()">我知道了</a>')
		}
		$(".popover3").show();
		 $(".theme-popover-mask2").show();
	}else{
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
}
function go2Del(subOrderId){//删除订单
	$.ajax({
		url : jsBasePath+'order/deleteOrder.user?uid='+uid+'&subOrderId='+subOrderId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				showInfoBox('成功删除订单！');
			}else{
				showInfoBox(data.msg);
			}
			refresh();//刷新页面
		}
	});
}

var closeReason = null;
function toCancel(){
	var status=$("#orderStatus").val();
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
	$.ajax({
		url : jsBasePath+'order/cancelOrder.user?uid='+uid+'&subOrderId='+subOrderId+'&closeReason='+closeReason,
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
function go2TX(subOrderId){//提醒发货
	$.ajax({
		url : jsBasePath+'order/urgedDelivery.user?uid='+uid+'&subOrderId='+subOrderId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				showInfoBox("提醒成功!","refresh()")
				//refresh();//刷新页面
			}else{
				showInfoBox(data.msg)
			}
		}
	});
}

function confirm(subOrderId){
	
//	$.ajax({
//		url : jsBasePath+'order/confirmOrder.user?uid='+uid+'&subOrderId='+subOrderId,
//		type : "GET",
//		dataType: "json",  //返回json格式的数据  
//		async: false,
//	    cache:false,
//		success : function(data) {
//			if (data.success) {
//				showInfoBox(data.data);
//			}else{
//				showInfoBox(data.data)
//			}
//			refresh();//刷新页面
//		}
//	});
}
function go2PJ(subOrderId){//评价页面
	window.location=jsBasePath+'order/comments/getSubOrderInfo?uid='+uid+'&subOrderId='+subOrderId;
}
function go2CKShipping(subOrderId,expressType,expressNo){
	window.location=jsBasePath+'logistics?userId='+uid+'&subOrderId='+subOrderId+'&expressType='+expressType+'&expressNo='+expressNo
}
function go2YC(subOrderId){//延长收货
	showConfirm("确认延长收货？只能延长一次","yanchang('"+subOrderId+"')");
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
				refresh();//刷新页面
			}else{
				showInfoBox(data.msg);
			}
		}
	});
}
function refresh(){
	location.reload();
}
function go2AfterOrderDetails(subOrderId){
	window.location=jsBasePath+'orderM/after_SaleDetails?uid='+uid+'&subOrderId='+subOrderId;
}
function go2more(state){
	var smalPopup='';
	if(state==1 || state==2){
		smalPopup +='<div class="share odb">';
		smalPopup +='<a href="javascript:go2Service(\''+subOrderId+'\',\''+realPrice+'\');">申请售后</a>';
		smalPopup +='</div>';
	}
	smalPopup +='<div class="share odb">';
	if(typeof(invoiceStatus)!=undefined && invoiceStatus!==''){
		if(parseInt(invoiceStatus)==0){
			smalPopup +='<a href="javascript:go2Invoice(\''+subOrderId+'\');">开票申请</a>';
		}else if(invoiceStatus=='2'||invoiceStatus=='1'){
			smalPopup +='<a href="javascript:go2Invoice(\''+subOrderId+'\');">发票详情</a>';
		}
	}
	smalPopup +='</div>';
	$(".smallPopup").html(smalPopup);
	$(".thickdiv").show();
	$(".smallPopup").show();
}
function go2Invoice(){
	window.location=jsBasePath+'invoice/page?uid='+uid+'&suborderId='+subOrderId;
}